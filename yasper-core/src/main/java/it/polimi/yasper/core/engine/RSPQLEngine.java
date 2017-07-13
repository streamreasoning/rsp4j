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
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Log4j
public abstract class RSPQLEngine implements RSPEngine {

    protected Map<String, Stream> registeredStreams;

    protected Map<String, SDS> assignedSDS;
    protected Map<String, ContinuousQueryExecution> queryExecutions;
    protected Map<String, ContinuousQuery> registeredQueries;
    protected Map<String, List<QueryResponseFormatter>> queryObservers;

    protected Configuration cepConfig;
    protected EPServiceProvider cep;
    protected EPRuntime cepRT;
    protected EPAdministrator cepAdm;
    protected ConfigurationMethodRef ref;
    protected long t0;

    protected long sentTimestamp;

    protected int rspEventsNumber = 0, esperEventsNumber = 0;
    protected long currentTimestamp;

    public RSPQLEngine() {
        this.assignedSDS = new HashMap<>();
        this.registeredQueries = new HashMap<>();
        this.registeredStreams = new HashMap<>();
        this.queryObservers = new HashMap<>();
        this.queryExecutions = new HashMap<>();
        this.cepConfig = new Configuration();
        this.currentTimestamp = 0L;
        cepConfig.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        cepConfig.getEngineDefaults().getLogging().setEnableExecutionDebug(true);
        cepConfig.getEngineDefaults().getLogging().setEnableTimerDebug(true);
        cepConfig.getEngineDefaults().getLogging().setEnableQueryPlan(true);
        cepConfig.getEngineDefaults().getMetricsReporting().setEnableMetricsReporting(true);
        //cepConfig.addPlugInView("rspql", "win", "rspqlfact");
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
        if (cepRT.getCurrentTime() < g.getAppTimestamp()) {
            log.info("Sent time event with current [" + (g.getAppTimestamp()) + "]");
            cepRT.sendEvent(new CurrentTimeEvent(g.getAppTimestamp()));
            currentTimestamp = g.getAppTimestamp();// TODO
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

    public SDS getSDS(ContinuousQuery q) {
        return assignedSDS.get(q);
    }


    protected EPStatement getStream(String uri) {
        return cepAdm.getStatement(EncodingUtils.encode(uri));
    }

    protected EPStatement createStream(String stream, String uri) {
        log.info("Stream [ " + stream + "] uri [" + uri + "]");
        String s = EncodingUtils.encode(uri);
        return cepAdm.createEPL(stream, s);
    }


    protected WindowOperator createWindow(String windowEPL, String name) {
        log.info("Stream [ " + windowEPL + "] uri [" + name + "]");
        return new EsperWindowOperator(cepAdm.createEPL(windowEPL, name));
    }


    protected WindowOperator createWindow(EPStatementObjectModel windowEPL, String name) {
        log.info("Stream [ " + windowEPL.toEPL() + "] name [" + name + "]");
        return new EsperWindowOperator(cepAdm.create(windowEPL, name));
    }

    protected WindowOperator createWindow(String windowEPL) {
        log.info("Stream [ " + windowEPL + "]");
        return new EsperWindowOperator(cepAdm.createEPL(windowEPL));
    }


    protected WindowOperator createWindow(EPStatementObjectModel windowEPL) {
        log.info("Stream [ " + windowEPL.toEPL() + "]");
        return new EsperWindowOperator(cepAdm.create(windowEPL));
    }

}
