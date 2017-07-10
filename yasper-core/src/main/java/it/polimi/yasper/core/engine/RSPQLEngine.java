package it.polimi.yasper.core.engine;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import it.polimi.streaming.EventProcessor;
import it.polimi.streaming.Stimulus;
import it.polimi.yasper.core.SDS;
import it.polimi.yasper.core.query.ContinuousQuery;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.query.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.stream.StreamItem;
import it.polimi.yasper.core.utils.EncodingUtils;
import lombok.Getter;
import lombok.Setter;
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


    @Setter
    protected Stimulus currentEvent = null;
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

    public boolean process(Stimulus e) {
        log.info("Current runtime is  [" + cepRT.getCurrentTime() + "]");
        this.currentEvent = e;
        StreamItem g = (StreamItem) e;
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
}
