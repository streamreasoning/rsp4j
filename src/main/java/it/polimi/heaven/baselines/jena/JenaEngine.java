package it.polimi.heaven.baselines.jena;

import it.polimi.heaven.baselines.RSPEsperEngine;
import it.polimi.heaven.baselines.RSPListener;
import it.polimi.heaven.core.enums.ExecutionState;
import it.polimi.heaven.core.ts.EventProcessor;
import it.polimi.heaven.core.ts.events.Stimulus;
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

	public JenaEngine(String name, EventProcessor<Stimulus> collector, RSPListener listener, String query) {
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
			cepRT.sendEvent(new CurrentTimeEvent(1));
			status = ExecutionState.READY;
		} else {
			status = ExecutionState.ERROR;
		}
		return status;
	}

	@Override
	public boolean process(Stimulus e) {
		setCurrentEvent(e);
		status = ExecutionState.RUNNING;
		rspEventsNumber++;
		handleEvent(e);
		status = ExecutionState.READY;
		log.info("Status[" + status + "] Parsing done, prepare time scheduling...");
		timeProgress(new CurrentTimeEvent(e.getTimestamp()));
		return ExecutionState.READY.equals(status);
	}

	@Override
	public boolean timeProgress(CurrentTimeEvent cte) {
		currentTimestamp = cte.getTimeInMillis();
		log.info("Sent time Event current runtime ts [" + currentTimestamp + "]");
		cepRT.sendEvent(cte);
		return true;
	}

	protected void handleEvent(Stimulus e) {
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
		log.info("Status [" + status + "] Turing Off Processed RSPEvents [" + rspEventsNumber + "]  EsperEvents [" + esperEventsNumber + "]");
		return status;
	}

}
