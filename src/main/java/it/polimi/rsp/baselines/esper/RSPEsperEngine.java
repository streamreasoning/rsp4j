package it.polimi.rsp.baselines.esper;

import com.espertech.esper.client.*;
import it.polimi.heaven.rsp.rsp.RSPEngine;
import it.polimi.streaming.EventProcessor;
import it.polimi.streaming.Response;
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

    protected EventProcessor<Response> receiver;

    @Setter
    protected Stimulus currentEvent = null;
    protected long sentTimestamp;

    protected int rspEventsNumber = 0, esperEventsNumber = 0;
    protected long currentTimestamp;

    public RSPEsperEngine(EventProcessor<Response> receiver, Configuration config) {
        this.receiver = receiver;
        this.cepConfig = config;
        this.currentTimestamp = 0L;
    }


    @Override
    public boolean setNext(EventProcessor<?> eventProcessor) {
        return false;
    }

}
