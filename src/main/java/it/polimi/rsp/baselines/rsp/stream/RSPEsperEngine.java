package it.polimi.rsp.baselines.rsp.stream;

import com.espertech.esper.client.*;
import it.polimi.heaven.rsp.rsp.RSPEngine;
import it.polimi.streaming.EventProcessor;
import it.polimi.streaming.Stimulus;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class RSPEsperEngine implements RSPEngine {

    protected Configuration cepConfig;
    protected EPServiceProvider cep;
    protected EPRuntime cepRT;
    protected EPAdministrator cepAdm;
    protected ConfigurationMethodRef ref;

    @Setter
    protected Stimulus currentEvent = null;
    protected long sentTimestamp;

    protected int rspEventsNumber = 0, esperEventsNumber = 0;
    protected long currentTimestamp;

    public RSPEsperEngine() {
        this.cepConfig = new Configuration();
        this.currentTimestamp = 0L;

        cepConfig.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        cepConfig.getEngineDefaults().getThreading().setThreadPoolOutbound(true);
        cepConfig.getEngineDefaults().getLogging().setEnableExecutionDebug(true);
        cepConfig.getEngineDefaults().getLogging().setEnableTimerDebug(true);
        cepConfig.getEngineDefaults().getLogging().setEnableQueryPlan(true);
        cepConfig.getEngineDefaults().getMetricsReporting().setEnableMetricsReporting(true);
    }


    @Override
    public boolean setNext(EventProcessor<?> eventProcessor) {
        return false;
    }

}
