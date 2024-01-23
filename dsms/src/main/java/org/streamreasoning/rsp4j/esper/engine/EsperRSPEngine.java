package org.streamreasoning.rsp4j.esper.engine;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import org.streamreasoning.rsp4j.esper.engine.esper.EsperStreamRegistrationService;
import org.streamreasoning.rsp4j.esper.querying.Entailment;
import org.streamreasoning.rsp4j.esper.secret.EsperTime;
import org.streamreasoning.rsp4j.esper.operators.s2r.epl.RuntimeManager;

import lombok.extern.log4j.Log4j;
import org.streamreasoning.rsp4j.api.engine.config.EngineConfiguration;
import org.streamreasoning.rsp4j.api.engine.features.QueryDeletionFeature;
import org.streamreasoning.rsp4j.api.engine.features.StreamDeletionFeature;
import org.streamreasoning.rsp4j.api.engine.features.StreamRegistrationFeature;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.format.QueryResultFormatter;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.api.stream.metadata.StreamSchema;

import java.util.*;

@Log4j
public abstract class EsperRSPEngine<T> implements StreamRegistrationFeature<DataStream<T>, DataStream>, StreamDeletionFeature<T>, QueryDeletionFeature {

    protected final boolean enabled_recursion;
    protected final String responseFormat;
    protected final Boolean usingEventTime;
    protected Entailment entailment;
    protected Report report;
    protected ReportGrain reportGrain;
    protected Tick tick;
    protected String tbox;

    protected final String base_uri;
    protected Map<String, SDS> assignedSDS;
    protected Map<String, ContinuousQueryExecution> queryExecutions;
    protected Map<String, ContinuousQuery> registeredQueries;
    protected Map<String, List<QueryResultFormatter>> queryObservers;

    protected EsperStreamRegistrationService<T> stream_registration_service;

    protected EngineConfiguration rsp_config;

    private final RuntimeManager manager;
    private final EPServiceProvider cep;
    private final EPRuntime runtime;
    protected final EPAdministrator admin;
    protected final Time time;

    public EsperRSPEngine(long t0, EngineConfiguration configuration) {
        this.assignedSDS = new HashMap<>();
        this.registeredQueries = new HashMap<>();
        this.queryObservers = new HashMap<>();
        this.queryExecutions = new HashMap<>();
        this.rsp_config = configuration;

        StreamSchema.Factory.registerSchema(this.rsp_config.getStreamSchema());

        this.cep = RuntimeManager.getCEP();
        this.manager = RuntimeManager.getInstance();
        this.runtime = RuntimeManager.getEPRuntime();
        this.admin = RuntimeManager.getAdmin();

        String entailment = rsp_config.getString("jasper.entailment");

        if (entailment == null)
            this.entailment = Entailment.NONE;
        else {
            this.entailment = Entailment.valueOf(entailment);
        }

        this.enabled_recursion = rsp_config.isRecursionEnables();
        this.responseFormat = rsp_config.getResponseFormat();

        this.usingEventTime = rsp_config.isUsingEventTime();
        this.reportGrain = rsp_config.getReportGrain();
        this.tick = rsp_config.getTick();

        this.base_uri = rsp_config.getBaseURI();
        report = rsp_config.getReport();


        log.debug("Running Configuration ]");
        log.debug("Event Time [" + this.rsp_config.isUsingEventTime() + "]");
        log.debug("Partial Window [" + this.rsp_config.partialWindowsEnabled() + "]");
        log.debug("Query Recursion [" + this.rsp_config.isRecursionEnables() + "]");
        log.debug("Query Class [" + this.rsp_config.getQueryClass() + "]");
        log.debug("StreamItem Class [" + this.rsp_config.getStreamSchema() + "]");

        this.time = new EsperTime(runtime, t0);

    }

    @Override
    public DataStream<T> register(DataStream s) {
        return stream_registration_service.register(s);
    }

    @Override
    public void unregister(DataStream<T> s) {
        stream_registration_service.unregister(s);
    }

    public void unregister_query(String id) {
        registeredQueries.remove(id);
        queryObservers.remove(id);
        assignedSDS.remove(id);
        queryExecutions.remove(id);
    }

    @Override
    public void unregister(ContinuousQuery q) {
        unregister_query(q.getID());
    }

    protected ContinuousQueryExecution save(ContinuousQuery q, SDS sds, ContinuousQueryExecution cqe) {
        String id = q.getID();
        registeredQueries.put(id, q);
        queryObservers.put(id, new ArrayList<>());
        assignedSDS.put(id, sds);
        queryExecutions.put(id, cqe);
        return cqe;
    }

}
