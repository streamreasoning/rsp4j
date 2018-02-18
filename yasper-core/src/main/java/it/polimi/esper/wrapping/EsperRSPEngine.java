package it.polimi.esper.wrapping;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import it.polimi.rspql.RSPEngine;
import it.polimi.rspql.querying.ContinuousQuery;
import it.polimi.rspql.querying.ContinuousQueryExecution;
import it.polimi.rspql.querying.SDS;
import it.polimi.spe.report.Report;
import it.polimi.spe.scope.Tick;
import it.polimi.spe.stream.rdf.RDFStream;
import it.polimi.yasper.core.query.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.stream.StreamItem;
import it.polimi.yasper.core.stream.StreamSchema;
import it.polimi.yasper.core.utils.EngineConfiguration;
import it.polimi.yasper.core.utils.QueryConfiguration;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
public abstract class EsperRSPEngine implements RSPEngine<RDFStream> {

    protected Map<String, EsperWindowAssigner> stream_dispatching_service;
    protected Map<String, SDS> assignedSDS;
    protected Map<String, ContinuousQueryExecution> queryExecutions;
    protected Map<String, ContinuousQuery> registeredQueries;
    protected Map<String, List<QueryResponseFormatter>> queryObservers;

    protected EsperStreamRegistrationService stream_registration_service;

    private Report report;
    private Tick tick;

    protected Configuration cep_config;
    protected EngineConfiguration rsp_config;
    protected EPServiceProvider cep;
    protected EPRuntime cepRT;
    protected EPAdministrator cepAdm;
    protected ConfigurationMethodRef ref;
    protected long t0;

    protected long sentTimestamp;

    protected int rspEventsNumber = 0, esperEventsNumber = 0;
    protected long currentTimestamp;

    protected boolean is_deltas = false;

    public EsperRSPEngine(long t0, EngineConfiguration config) {
        this.t0 = t0;

        this.assignedSDS = new HashMap<>();
        this.registeredQueries = new HashMap<>();
        this.queryObservers = new HashMap<>();
        this.queryExecutions = new HashMap<>();
        this.currentTimestamp = 0L;

        cep_config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        cep_config.addEventType("TStream", new HashMap<>());
        cep = EPServiceProviderManager.getProvider(this.getClass().getCanonicalName(), cep_config);
        cepAdm = cep.getEPAdministrator();
        cepRT = cep.getEPRuntime();

        rsp_config = config != null ? config : EngineConfiguration.getDefault();

        StreamSchema.Factory.registerSchema(rsp_config.getStreamSchema());

        log.debug("Running Configuration ]");
        log.debug("Event Time [" + rsp_config.isUsingEventTime() + "]");
        log.debug("Partial Window [" + rsp_config.partialWindowsEnabled() + "]");
        log.debug("Query Recursion [" + rsp_config.isRecursionEnables() + "]");
        log.debug("Query Class [" + rsp_config.getQueryClass() + "]");
        log.debug("StreamItem Class [" + rsp_config.getStreamSchema() + "]");

        cepRT.sendEvent(new CurrentTimeEvent(t0));
    }

    @Override
    public RDFStream register(RDFStream s) {
        EPStatement epl = stream_registration_service.register(s);
        return s;
    }

    @Override
    public void unregister(RDFStream s) {
        stream_registration_service.unregister(s);
    }

    @Override
    public boolean process(StreamItem e) {
        if (stream_dispatching_service.containsKey(e.getStreamURI())) {
            stream_dispatching_service.get(e.getStreamURI()).process(e);
            return true;
        }
        return false;
    }

    protected void save(ContinuousQuery q, ContinuousQueryExecution cqe, SDS sds) {
        registeredQueries.put(q.getID(), q);
        queryObservers.put(q.getID(), new ArrayList<>());
        assignedSDS.put(q.getID(), sds);
        queryExecutions.put(q.getID(), cqe);
    }

}
