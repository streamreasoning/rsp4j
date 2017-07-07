package it.polimi.rsp.baselines.rsp.stream;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.core.service.EPServiceProviderImpl;
import com.espertech.esper.view.ViewServiceProvider;
import it.polimi.heaven.rsp.rsp.RSPEngine;
import it.polimi.rsp.baselines.rsp.stream.item.StreamItem;
import it.polimi.sr.rsp.utils.EncodingUtils;
import it.polimi.streaming.EventProcessor;
import it.polimi.streaming.Stimulus;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Getter
@Log4j
public abstract class RSPEsperEngine implements RSPEngine {

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

    public RSPEsperEngine() {
        this.cepConfig = new Configuration();
        this.currentTimestamp = 0L;

        cepConfig.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        cepConfig.getEngineDefaults().getThreading().setThreadPoolInbound(true);
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
            cepRT.sendEvent(new CurrentTimeEvent(g.getAppTimestamp() + 1));
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

}
