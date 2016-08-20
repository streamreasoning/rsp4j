package it.polimi.rsp.baselines.esper;

import com.espertech.esper.client.*;
import it.polimi.heaven.core.teststand.EventProcessor;
import it.polimi.heaven.core.teststand.rsp.RSPEngine;
import it.polimi.heaven.core.teststand.rsp.Receiver;
import it.polimi.heaven.core.teststand.rsp.data.Stimulus;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class RSPEsperEngine implements RSPEngine {

	protected Configuration cepConfig;
	protected EPServiceProvider cep;
	protected EPRuntime cepRT;
	protected EPAdministrator cepAdm;
	protected ConfigurationMethodRef ref;

	protected Receiver receiver;

	@Setter
	protected Stimulus currentEvent = null;
	protected long sentTimestamp;

	protected int rspEventsNumber = 0, esperEventsNumber = 0;
	protected long currentTimestamp;

	public RSPEsperEngine(Receiver receiver, Configuration config) {
		this.receiver = receiver;
		this.cepConfig = config;
		this.currentTimestamp = 0L;
	}


	@Override
	public boolean setNext(EventProcessor<?> eventProcessor) {
		return false;
	}

	public void registerReceiver(Receiver receiver) {

	}
}
