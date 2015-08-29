package it.polimi.baselines;

import it.polimi.processing.EventProcessor;
import it.polimi.processing.enums.ExecutionState;
import it.polimi.processing.events.CTEvent;
import it.polimi.processing.rspengine.abstracts.RSPEsperEngine;
import it.polimi.processing.rspengine.abstracts.RSPListener;
import it.polimi.utils.WindowUtils;
import lombok.extern.log4j.Log4j;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.ConfigurationMethodRef;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.time.CurrentTimeEvent;

@Log4j
public abstract class JenaEngine extends RSPEsperEngine {

	private final RSPListener listener;

	private final String query;

	private EPStatement out = null;

	public JenaEngine(String name, EventProcessor<CTEvent> collector, RSPListener listener, String query) {
		super(name, collector);
		super.cepConfig = new Configuration();
		this.listener = listener;
		this.query = query;

	}

	protected void initQueries() {
		log.info("Registering query [" + query + "]");
		out = cepAdm.createEPL(query);
		out.addListener(listener);
	}

	@Override
	public ExecutionState init() {
		ref = new ConfigurationMethodRef();

		cep = EPServiceProviderManager.getProvider(JenaEngine.class.getName(), cepConfig);
		// We register an EPL statement
		cepAdm = cep.getEPAdministrator();
		cepRT = cep.getEPRuntime();
		resetTime();
		initQueries();
		status = ExecutionState.READY;
		log.debug("Status[" + status + "] Initizalized the RSPEngine");
		return status;
	}

	@Override
	public ExecutionState startProcessing() {
		if (isStartable()) {
			cepRT.sendEvent(new CurrentTimeEvent(registrationTime + 1));
			status = ExecutionState.READY;
		} else {
			status = ExecutionState.ERROR;
		}
		return status;
	}

	@Override
	public boolean process(CTEvent e) {
		setCurrentEvent(e);
		status = ExecutionState.RUNNING;
		rspEventsNumber++;
		handleEvent(e);
		status = ExecutionState.READY;
		log.debug("Status[" + status + "] Parsing done, prepare time scheduling...");
		return ExecutionState.READY.equals(status);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.processing.rspengine.abstracts.RSPEsperEngine#moveTime();
	 */
	@Override
	public void timeProgress() {
		moveTime();
	}

	protected void handleEvent(CTEvent e) {
		this.sentTimestamp = System.currentTimeMillis();
	}

	@Override
	public ExecutionState stopProcessing() {
		if (isOn()) {
			status = ExecutionState.CLOSED;
		} else {
			status = ExecutionState.ERROR;
		}
		return status;
	}

	@Override
	public ExecutionState close() {
		status = ExecutionState.CLOSED;
		log.info("Status [" + status + "] Turing Off Processed RSPEvents [" + rspEventsNumber + "]  EsperEvents [" + esperEventsNumber
				+ "] Windows [" + windowShots + "] Snapshots [" + (time / WindowUtils.beta) + "]");
		return status;
	}

}
