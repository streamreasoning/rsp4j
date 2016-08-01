package it.polimi.rsp.baselines.jena;

import it.polimi.rsp.baselines.enums.OntoLanguage;
import it.polimi.rsp.baselines.esper.RSPEsperEngine;
import it.polimi.rsp.baselines.esper.RSPListener;
import it.polimi.rsp.baselines.jena.events.stimuli.BaselineStimulus;
import it.polimi.rsp.baselines.jena.query.BaselineQuery;
import it.polimi.heaven.core.enums.Reasoning;
import it.polimi.heaven.core.teststand.EventProcessor;
import it.polimi.heaven.core.teststand.rspengine.Query;
import it.polimi.heaven.core.teststand.rspengine.events.Response;

import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.Receiver;

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
	protected final boolean internalTimerEnabled;

	public JenaEngine(BaselineStimulus eventType, EventProcessor<Response> receiver) {
		super(receiver, new Configuration());
		this.queries = new HashMap<Query, RSPListener>();
		this.internalTimerEnabled = false;
		ref = new ConfigurationMethodRef();
		cepConfig.getEngineDefaults().getThreading().setInternalTimerEnabled(internalTimerEnabled);
		cepConfig.getEngineDefaults().getLogging().setEnableExecutionDebug(true);
		cepConfig.getEngineDefaults().getLogging().setEnableTimerDebug(true);

		log.info("Added [" + eventType + "] as TEvent");
		cepConfig.addEventType("TEvent", eventType);
		cep = EPServiceProviderManager.getProvider(JenaEngine.class.getName(), cepConfig);
		cepAdm = cep.getEPAdministrator();
		cepRT = cep.getEPRuntime();

	}

	public JenaEngine(BaselineStimulus eventType, EventProcessor<Response> receiver, boolean internalTimerEnabled) {
		super(receiver, new Configuration());
		this.queries = new HashMap<Query, RSPListener>();
		this.internalTimerEnabled = internalTimerEnabled;
		ref = new ConfigurationMethodRef();
		cepConfig.getEngineDefaults().getThreading().setInternalTimerEnabled(internalTimerEnabled);
		log.info("Added [" + eventType + "] as TEvent");
		cepConfig.addEventType("TEvent", eventType);
		cepConfig.getEngineDefaults().getLogging().setEnableTimerDebug(true);
		cep = EPServiceProviderManager.getProvider(JenaEngine.class.getName(), cepConfig);
		cepAdm = cep.getEPAdministrator();
		cepRT = cep.getEPRuntime();
	}

	public void setStreamEncoding(String encoding, BaselineStimulus eventType) {
		log.info("Added [" + eventType + "] as " + encoding);
		cepConfig.addEventType(encoding, eventType);
	}

	@Override
	public void startProcessing() {
		cepRT.sendEvent(new CurrentTimeEvent(1406872790001L));
	}

	@Override
	public void stopProcessing() {
		log.info("Engine is closing");
	}

	public void registerQuery(Query q) {
		BaselineQuery bq = (BaselineQuery) q;
		String esperQuery = bq.getEsper_queries();
		for (String c : bq.getEsperStreams()) {
			log.info("create schema " + c + "() inherits TEvent");
			cepAdm.createEPL("create schema " + c + "() inherits TEvent");
		}
		log.info("Register esper query [" + esperQuery + "]");
		String[] split = esperQuery.split("\\;");
		RSPListener listener = new JenaListener(next, bq, reasoning, ontology_language, "http://streamreasoning.org/heaven/" + bq.getId());
		for (String string : split) {
			EPStatement epl = cepAdm.createEPL(string);
			epl.addListener(listener);
		}
		queries.put(q, listener);
	}

	public void registerReceiver(Receiver r) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean setNext(EventProcessor<?> ep) {
		// TODO Auto-generated method stub
		return false;
	}
}
