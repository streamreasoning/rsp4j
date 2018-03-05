package it.polimi.jasper.esper.engine;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import it.polimi.jasper.engine.stream.items.StreamItem;
import it.polimi.jasper.esper.RuntimeManager;
import it.polimi.yasper.core.engine.RSPEngine;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.quering.ContinuousQuery;
import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.quering.SDS;
import it.polimi.yasper.core.stream.rdf.RDFStream;
import it.polimi.yasper.core.quering.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.stream.schema.StreamSchema;
import it.polimi.yasper.core.utils.EncodingUtils;
import it.polimi.yasper.core.utils.EngineConfiguration;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Log4j
public abstract class RSPQLEngineImpl implements RSPEngine<StreamItem> {

    private final RuntimeManager manager;
    private final EPServiceProvider cep;
    protected final EPRuntime runtime;
    protected final EPAdministrator admin;

    protected Map<String, Stream> registeredStreams;
    protected Map<String, SDS> assignedSDS;
    protected Map<String, ContinuousQueryExecution> queryExecutions;
    protected Map<String, ContinuousQuery> registeredQueries;
    protected Map<String, List<QueryResponseFormatter>> queryObservers;

    protected EngineConfiguration rsp_config;


    protected ConfigurationMethodRef ref;
    protected long t0;

    protected long sentTimestamp;

    protected int rspEventsNumber = 0, esperEventsNumber = 0;
    protected long currentTimestamp;

    protected boolean is_deltas = false;

    public RSPQLEngineImpl(long t0, EngineConfiguration configuration) {

        this.assignedSDS = new HashMap<>();
        this.registeredQueries = new HashMap<>();
        this.registeredStreams = new HashMap<>();
        this.queryObservers = new HashMap<>();
        this.queryExecutions = new HashMap<>();
        this.currentTimestamp = 0L;
        this.t0 = t0;
        this.rsp_config = configuration;

        StreamSchema.Factory.registerSchema(rsp_config.getStreamSchema());

        log.debug("Running Configuration ]");
        log.debug("Event Time [" + rsp_config.isUsingEventTime() + "]");
        log.debug("Partial Window [" + rsp_config.partialWindowsEnabled() + "]");
        log.debug("Query Recursion [" + rsp_config.isRecursionEnables() + "]");
        log.debug("Query Class [" + rsp_config.getQueryClass() + "]");
        log.debug("StreamItem Class [" + rsp_config.getStreamSchema() + "]");

        this.cep = RuntimeManager.getCEP();
        this.manager = RuntimeManager.getInstance();
        this.runtime = RuntimeManager.getEPRuntime();
        this.admin = RuntimeManager.getAdmin();

        runtime.sendEvent(new CurrentTimeEvent(t0));

    }


    @Override
    public void unregister(RDFStream s) {
        log.info("Unregistering Stream [" + s + "]");
        EPStatement statement = admin.getStatement(EncodingUtils.encode(s.getURI()));
        statement.removeAllListeners();
        statement.destroy();
        registeredStreams.remove(EncodingUtils.encode(s.getURI()));
    }

    public boolean process(StreamItem g) {
        log.debug("Current runtime is  [" + runtime.getCurrentTime() + "]");

        //Event time vs ingestion time
        long time = rsp_config.isUsingEventTime() ? g.getAppTimestamp() : g.getSysTimestamp();

        if (runtime.getCurrentTime() < time) {
            log.debug("Sent time event with current [" + time + "]");
            runtime.sendEvent(new CurrentTimeEvent(time));
            currentTimestamp = time;// TODO
            log.debug("Current runtime is now [" + runtime.getCurrentTime() + "]");
        }

        String encode = EncodingUtils.encode(g.getStreamURI());
        runtime.sendEvent(g, encode);
        log.debug("Received Stimulus [" + g + "] on stream [" + encode + "]");
        rspEventsNumber++;
        log.debug("Current runtime is  [" + this.runtime.getCurrentTime() + "]");

        return true;
    }

    protected EPStatement createStream(String stream, String uri) {
        String s = EncodingUtils.encode(uri);
        log.debug("EPL Schema Statement [ " + stream.replace(s, uri) + "] uri [" + uri + "]");
        return admin.createEPL(stream, s);
    }

    protected void persist(ContinuousQuery q, ContinuousQueryExecution accept, SDS sds) {
        registeredQueries.put(q.getID(), q);
        queryObservers.put(q.getID(), new ArrayList<>());
        assignedSDS.put(q.getID(), accept.getSDS());
        queryExecutions.put(q.getID(), accept);
        assignedSDS.put(q.getID(), sds);

    }

}