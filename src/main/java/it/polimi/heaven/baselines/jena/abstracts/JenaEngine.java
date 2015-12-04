package it.polimi.heaven.baselines.jena.abstracts;

import it.polimi.heaven.baselines.esper.RSPEsperEngine;
import it.polimi.heaven.baselines.esper.RSPListener;
import it.polimi.heaven.baselines.jena.BaselineQuery;
import it.polimi.heaven.baselines.jena.events.BaselineEvent;
import it.polimi.heaven.core.enums.ExecutionState;
import it.polimi.heaven.core.enums.Reasoning;
import it.polimi.heaven.core.ts.EventProcessor;
import it.polimi.heaven.core.ts.events.Stimulus;
import it.polimi.heaven.core.ts.rspengine.Query;
import it.polimi.heaven.enums.OntoLanguage;

import java.util.HashMap;
import java.util.Map;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.ConfigurationMethodRef;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.hp.hpl.jena.graph.Graph;

@Log4j
public abstract class JenaEngine extends RSPEsperEngine {

	protected Graph abox;

	@Setter
	private Reasoning reasoning;
	@Setter
	private OntoLanguage ontology_language;

	private Map<Query, RSPListener> queries;
	private final boolean internalTimerEnabled;

	public JenaEngine(BaselineEvent eventType, EventProcessor<Stimulus> collector) {
		super(collector, new Configuration());
		this.queries = new HashMap<Query, RSPListener>();
		this.internalTimerEnabled = false;
		ref = new ConfigurationMethodRef();
		cepConfig = new Configuration();
		cepConfig.getEngineDefaults().getThreading().setInternalTimerEnabled(internalTimerEnabled);
		log.info("Added [" + eventType + "] as TEvent");
		cepConfig.addEventType("TEvent", eventType);
		cep = EPServiceProviderManager.getProvider(JenaEngine.class.getName(), cepConfig);
		cepAdm = cep.getEPAdministrator();
		cepRT = cep.getEPRuntime();
	}

	public JenaEngine(BaselineEvent eventType, EventProcessor<Stimulus> collector, boolean internalTimerEnabled) {
		super(collector, new Configuration());
		this.queries = new HashMap<Query, RSPListener>();
		this.internalTimerEnabled = internalTimerEnabled;
		ref = new ConfigurationMethodRef();
		cepConfig = new Configuration();
		cepConfig.getEngineDefaults().getThreading().setInternalTimerEnabled(internalTimerEnabled);
		log.info("Added [" + eventType + "] as TEvent");
		cepConfig.addEventType("TEvent", eventType);
		cep = EPServiceProviderManager.getProvider(JenaEngine.class.getName(), cepConfig);
		cepAdm = cep.getEPAdministrator();
		cepRT = cep.getEPRuntime();
	}

	public void setStreamEncoding(String encoding, BaselineEvent eventType) {
		log.info("Added [" + eventType + "] as " + encoding);
		cepConfig.addEventType(encoding, eventType);
	}

	@Override
	public ExecutionState init() {

		status = ExecutionState.READY;
		log.debug("Status[" + status + "] Initizalized the RSPEngine");
		if (!internalTimerEnabled)
			cepRT.sendEvent(new CurrentTimeEvent(0));
		return status;
	}

	@Override
	public ExecutionState close() {
		status = ExecutionState.CLOSED;
		log.info("Status [" + status + "] Turing Off Processed RSPEvents [" + rspEventsNumber + "]  EsperEvents [" + esperEventsNumber + "]");
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
	public ExecutionState stopProcessing() {
		if (isOn()) {
			status = ExecutionState.CLOSED;
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
		currentTimestamp = e.getTimestamp();
		log.info("Sent time Event current runtime ts [" + currentTimestamp + "]");
		if (!internalTimerEnabled)
			cepRT.sendEvent(new CurrentTimeEvent(e.getTimestamp()));
		return ExecutionState.READY.equals(status);
	}

	protected abstract void handleEvent(Stimulus e);

	public void registerQuery(Query q) {
		BaselineQuery bq = (BaselineQuery) q;
		String esperQuery = bq.getEsperQuery();
		String sparqlQuery = bq.getSparqlQuery();

		for (String c : bq.getEsperStreams()) {
			log.info("create schema " + c + "() copyfrom TEvent");
			cepAdm.createEPL("create schema " + c + "() inherits TEvent");

		}

		EPStatement epl = cepAdm.createEPL(esperQuery);
		RSPListener listener = bq.hasTBox() ? new JenaListener(bq.getTbox(), next, sparqlQuery, reasoning, ontology_language) : new JenaListener(
				next, sparqlQuery, reasoning, ontology_language);
		epl.addListener(listener);
		queries.put(q, listener);
	}
}
