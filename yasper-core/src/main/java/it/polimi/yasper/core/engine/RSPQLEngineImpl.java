package it.polimi.yasper.core.engine;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import it.polimi.rspql.RSPEngine;
import it.polimi.rspql.Stream;
import it.polimi.rspql.querying.ContinuousQuery;
import it.polimi.rspql.querying.ContinuousQueryExecution;
import it.polimi.rspql.querying.SDS;
import it.polimi.yasper.core.query.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.stream.RegisteredStream;
import it.polimi.yasper.core.stream.StreamItem;
import it.polimi.yasper.core.stream.StreamSchema;
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
public abstract class RSPQLEngineImpl<S1 extends Stream, S extends RegisteredStream> implements RSPEngine<S1, S> {

    protected Map<String, S> registeredStreams;
    protected Map<String, SDS> assignedSDS;
    protected Map<String, ContinuousQueryExecution> queryExecutions;
    protected Map<String, ContinuousQuery> registeredQueries;
    protected Map<String, List<QueryResponseFormatter>> queryObservers;

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

    public RSPQLEngineImpl(long t0, EngineConfiguration configuration) {

        this.assignedSDS = new HashMap<>();
        this.registeredQueries = new HashMap<>();
        this.registeredStreams = new HashMap<>();
        this.queryObservers = new HashMap<>();
        this.queryExecutions = new HashMap<>();
        this.cep_config = new Configuration();
        this.currentTimestamp = 0L;
        this.t0 = t0;
        cep_config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        //  cep_config.getEngineDefaults().getLogging().setEnableExecutionDebug(true);
        //  cep_config.getEngineDefaults().getLogging().setEnableTimerDebug(true);
        //  cep_config.getEngineDefaults().getLogging().setEnableQueryPlan(true);
        //  cep_config.getEngineDefaults().getMetricsReporting().setEnableMetricsReporting(true);
        cep_config.addEventType("TStream", new HashMap<>());
        //cep_config.addPlugInView("rspql", "time", WindowOperatorFactory.class.getCanonicalName());
        //cep_config.addPlugInView("obsda", "window", VirtualWindowOperatorFactory.class.getCanonicalName());
        //cep_config.setEPServicesContextFactoryClassName(EPServicesContextFactoryRSP.class.getCanonicalName());
        cep = EPServiceProviderManager.getProvider(this.getClass().getCanonicalName(), cep_config);
        cepAdm = cep.getEPAdministrator();
        cepRT = cep.getEPRuntime();

        rsp_config = configuration != null ? configuration : EngineConfiguration.getDefault();

        StreamSchema.Factory.registerSchema(rsp_config.getStreamSchema());

        log.debug("Running Configuration ]");
        log.debug("Event Time [" + rsp_config.isUsingEventTime() + "]");
        log.debug("Partial Window [" + rsp_config.partialWindowsEnabled() + "]");
        log.debug("Query Recursion [" + rsp_config.isRecursionEnables() + "]");
        log.debug("Query Class [" + rsp_config.getQueryClass() + "]");
        log.debug("StreamItem Class [" + rsp_config.getStreamSchema() + "]");

    }

    @Override
    public void unregister(RegisteredStream s) {
        log.info("Unregistering Stream [" + s + "]");
        EPStatement statement = cepAdm.getStatement(EncodingUtils.encode(s.getURI()));
        statement.removeAllListeners();
        statement.destroy();
        registeredStreams.remove(EncodingUtils.encode(s.getURI()));
    }

    public RSPQLEngineImpl(long t0) {
        this(t0, EngineConfiguration.getDefault());
    }

    public void startProcessing() {
        cepRT.sendEvent(new CurrentTimeEvent(t0));
    }

    public void stopProcessing() {
        log.info("Engine is closing");
        // stop the CEP engine
        for (String stmtName : cepAdm.getStatementNames()) {
            EPStatement stmt = cepAdm.getStatement(stmtName);
            if (!stmt.isStopped()) {
                stmt.stop();
            }
        }
    }

    public boolean process(StreamItem g) {
        log.debug("Current runtime is  [" + cepRT.getCurrentTime() + "]");

        //Event time vs ingestion time
        long time = rsp_config.isUsingEventTime() ? g.getAppTimestamp() : g.getSysTimestamp();

        if (cepRT.getCurrentTime() < time) {
            log.debug("Sent time event with current [" + time + "]");
            cepRT.sendEvent(new CurrentTimeEvent(time));
            currentTimestamp = time;// TODO
            log.debug("Current runtime is now [" + cepRT.getCurrentTime() + "]");
        }

        String encode = EncodingUtils.encode(g.getStreamURI());
        cepRT.sendEvent(g, encode);
        log.debug("Received Stimulus [" + g + "] on stream [" + encode + "]");
        rspEventsNumber++;
        log.debug("Current runtime is  [" + this.cepRT.getCurrentTime() + "]");

        return true;
    }

    protected EPStatement createStream(String stream, String uri) {
        String s = EncodingUtils.encode(uri);
        log.debug("EPL Schema Statement [ " + stream.replace(s, uri) + "] uri [" + uri + "]");
        return cepAdm.createEPL(stream, s);
    }

    protected void persist(ContinuousQuery q, ContinuousQueryExecution accept, SDS sds) {
        registeredQueries.put(q.getID(), q);
        queryObservers.put(q.getID(), new ArrayList<>());
        assignedSDS.put(q.getID(), accept.getSDS());
        queryExecutions.put(q.getID(), accept);
        assignedSDS.put(q.getID(), sds);

    }

    public Stream getStream(String uri){
        //TODO should this be encoded?
        return registeredStreams.get(uri);
    }

public ContinuousQuery getQuery(String id){
        return registeredQueries.get(id);
}

}