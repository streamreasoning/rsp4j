package it.polimi.yasper.core.engine;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.client.time.CurrentTimeEvent;
import it.polimi.streaming.EventProcessor;
import it.polimi.yasper.core.SDS;
import it.polimi.yasper.core.query.ContinuousQuery;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.query.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.query.operators.s2r.EsperWindowOperator;
import it.polimi.yasper.core.query.operators.s2r.WindowOperator;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.stream.StreamItem;
import it.polimi.yasper.core.utils.EncodingUtils;
import it.polimi.yasper.core.utils.EngineConfiguration;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

@Getter
@Log4j
public abstract class RSPQLEngine extends Observable implements RSPEngine {

    protected Map<String, Stream> registeredStreams;

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

    public RSPQLEngine(long t0, EngineConfiguration configuration) {

        this.assignedSDS = new HashMap<>();
        this.registeredQueries = new HashMap<>();
        this.registeredStreams = new HashMap<>();
        this.queryObservers = new HashMap<>();
        this.queryExecutions = new HashMap<>();
        this.cep_config = new Configuration();
        this.currentTimestamp = 0L;
        this.t0 = t0;
        cep_config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        cep_config.getEngineDefaults().getLogging().setEnableExecutionDebug(true);
        cep_config.getEngineDefaults().getLogging().setEnableTimerDebug(true);
        cep_config.getEngineDefaults().getLogging().setEnableQueryPlan(true);
        cep_config.getEngineDefaults().getMetricsReporting().setEnableMetricsReporting(true);
        cep_config.getEngineDefaults().getLogging().setEnableQueryPlan(true);

        rsp_config = configuration != null ? configuration : EngineConfiguration.getDefault();

        log.debug("Running Configuration ]");
        log.debug("Event Time [" + rsp_config.isUsingEventTime() + "]");
        log.debug("Partial Window [" + rsp_config.partialWindowsEnabled() + "]");
        log.debug("Query Recursion [" + rsp_config.isRecursionEnables() + "]");
        log.debug("Query Class [" + rsp_config.getQueryClass() + "]");

        //cep_config.addPlugInView("rspql", "win", "rspqlfact");
    }


    public RSPQLEngine(long t0) {
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
        log.info("Current runtime is  [" + cepRT.getCurrentTime() + "]");

        //Event time vs ingestion time
        long time = rsp_config.isUsingEventTime() ? g.getAppTimestamp() : g.getSysTimestamp();

        if (cepRT.getCurrentTime() < time) {
            log.info("Sent time event with current [" + time + "]");
            cepRT.sendEvent(new CurrentTimeEvent(time));
            currentTimestamp = time;// TODO
            log.info("Current runtime is now [" + cepRT.getCurrentTime() + "]");
        }

        cepRT.sendEvent(g, EncodingUtils.encode(g.getStreamURI()));
        log.info("Received Stimulus [" + g + "]");
        rspEventsNumber++;
        log.info("Current runtime is  [" + this.cepRT.getCurrentTime() + "]");

        return true;
    }


    @Override
    public boolean setNext(EventProcessor<?> eventProcessor) {
        return false;
    }

    protected EPStatement getStream(String uri) {
        return cepAdm.getStatement(EncodingUtils.encode(uri));
    }

    protected EPStatement createStream(String stream, String uri) {
        log.info("Stream [ " + stream + "] uri [" + uri + "]");
        String s = EncodingUtils.encode(uri);
        return cepAdm.createEPL(stream, s);
    }

    public boolean checkStreamExistence(String uri) {
        return cepAdm.getStatement(EncodingUtils.encode(uri)) != null;
    }

    protected WindowOperator createWindow(long t0, long range, long step, String windowEPL, String name) {
        log.info("Stream [ " + windowEPL + "] uri [" + name + "]");
        return new EsperWindowOperator(cepAdm.createEPL(windowEPL, name), t0, range, step);
    }


    protected WindowOperator createWindow(long t0, long range, long step, EPStatementObjectModel windowEPL, String name) {
        log.info("Stream [ " + windowEPL.toEPL() + "] name [" + name + "]");
        return new EsperWindowOperator(cepAdm.create(windowEPL, name), t0, range, step);
    }

    protected WindowOperator createWindow(long t0, long range, long step, String windowEPL) {
        log.info("Stream [ " + windowEPL + "]");
        return new EsperWindowOperator(cepAdm.createEPL(windowEPL), t0, range, step);
    }


    protected WindowOperator createWindow(long t0, long range, long step, EPStatementObjectModel windowEPL) {
        log.info("Stream [ " + windowEPL.toEPL() + "]");
        return new EsperWindowOperator(cepAdm.create(windowEPL), t0, range, step);
    }

}
