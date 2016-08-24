package it.polimi.rsp.baselines.jena;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.ConfigurationMethodRef;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.time.CurrentTimeEvent;
import it.polimi.heaven.rsp.rsp.querying.ContinousQueryExecution;
import it.polimi.heaven.rsp.rsp.querying.Query;
import it.polimi.rsp.baselines.enums.OntoLanguage;
import it.polimi.rsp.baselines.enums.Reasoning;
import it.polimi.rsp.baselines.esper.RSPEsperEngine;
import it.polimi.rsp.baselines.exceptions.StreamRegistrationException;
import it.polimi.rsp.baselines.exceptions.UnsuportedQueryFormatExecption;
import it.polimi.rsp.baselines.jena.events.stimuli.BaselineStimulus;
import it.polimi.rsp.baselines.jena.query.BaselineQuery;
import it.polimi.rsp.baselines.jena.query.JenaCQueryExecution;
import it.polimi.sr.rsp.RSPQuery;
import it.polimi.sr.rsp.streams.Window;
import it.polimi.sr.rsp.utils.EncodingUtils;
import it.polimi.streaming.EventProcessor;
import it.polimi.streaming.Response;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Node;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.QueryFactory;

import java.util.HashMap;
import java.util.Map;

@Log4j
public abstract class JenaEngine extends RSPEsperEngine {

	protected final long t0;
	@Setter
	private Reasoning reasoning;
	@Setter
	private OntoLanguage ontology_language;

	private Map<Query, JenaListener> queries;
	protected final boolean internalTimerEnabled;

	public JenaEngine(BaselineStimulus eventType, EventProcessor<Response> receiver, long t0, String provider) {
		super(receiver, new Configuration());
		this.queries = new HashMap<Query, JenaListener>();
		this.internalTimerEnabled = false;
		this.t0 = t0;
		ref = new ConfigurationMethodRef();
		cepConfig.getEngineDefaults().getThreading().setInternalTimerEnabled(internalTimerEnabled);
		cepConfig.getEngineDefaults().getLogging().setEnableExecutionDebug(true);
		cepConfig.getEngineDefaults().getLogging().setEnableTimerDebug(true);

		log.info("Added [" + eventType.getClass() + "] as TStream");
		cepConfig.addEventType("TStream", eventType);
		cep = EPServiceProviderManager.getProvider(provider, cepConfig);
		cepAdm = cep.getEPAdministrator();
		cepRT = cep.getEPRuntime();

	}

	public JenaEngine(BaselineStimulus eventType, EventProcessor<Response> receiver, long t0,
			boolean internalTimerEnabled, String provider) {
		super(receiver, new Configuration());
		this.t0 = t0;
		this.queries = new HashMap<Query, JenaListener>();
		this.internalTimerEnabled = internalTimerEnabled;
		ref = new ConfigurationMethodRef();
		cepConfig.getEngineDefaults().getThreading().setInternalTimerEnabled(internalTimerEnabled);
		log.info("Added [" + eventType.getClass() + "] as TStream");
		cepConfig.addEventType("TStream", eventType);
		cepConfig.getEngineDefaults().getLogging().setEnableTimerDebug(true);
		cep = EPServiceProviderManager.getProvider(provider, cepConfig);
		cepAdm = cep.getEPAdministrator();
		cepRT = cep.getEPRuntime();
	}

	public void setStreamEncoding(String encoding, BaselineStimulus eventType) {
		log.info("Added [" + eventType + "] as " + encoding);
		cepConfig.addEventType(encoding, eventType);
	}

	public void startProcessing() {
		// TODO put 0 1406872790001L
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

	public ContinousQueryExecution registerQuery(Query q) {
		if (q instanceof BaselineQuery)
			return registerQuery((BaselineQuery) q);
		else if (q instanceof RSPQuery)
			return registerQuery((RSPQuery) q);
		throw new UnsuportedQueryFormatExecption();
	}

	public ContinousQueryExecution registerQuery(RSPQuery bq) {
		Dataset dataset = DatasetFactory.create();

		log.info(bq.getQ().toString());

		JenaListener listener = new JenaListener(dataset, receiver, bq, bq.getQ(), reasoning, ontology_language, "");

		int i = 0;
		if (bq.getWindows() != null) {
			for (Window window : bq.getWindows()) {
				log.info(window.getStream().toEPLSchema());
				cepAdm.createEPL(window.getStream().toEPLSchema());
				String stream = EncodingUtils.encode(window.getStreamURI());
				if (!listener.addDefaultWindowStream(stream)) {
					throw new StreamRegistrationException("Impossible to register stream [" + stream + "]");
				}
				String statementName = "QUERY" + "STMT_" + i;
				EPStatement epl = cepAdm.create(window.toEPL(), statementName);
				epl.addListener(listener);
				listener.addStatementName(statementName);
				i++;
			}
		}

		// NAMED

		int j = 0;

		if (bq.getNamedwindows() != null) {

			for (Map.Entry<Node, Window> entry : bq.getNamedwindows().entrySet()) {
				Window w = entry.getValue();
				String stream = EncodingUtils.encode(w.getStreamURI());
				String window = w.getIri().getURI();
				log.info(w.getStream().toEPLSchema());
				cepAdm.createEPL(w.getStream().toEPLSchema());
				log.info("creating named graph " + window + "");
				if (!listener.addNamedWindowStream(window, stream)) {
					throw new StreamRegistrationException(
							"Impossible to register window named  [" + window + "] on stream [" + stream + "]");
				}

				String statementName = "QUERY" + bq.getId() + "STMT_NDM" + j;
				EPStatement epl = cepAdm.create(w.toEPL(), statementName);
				epl.addListener(listener);
				listener.addStatementName(statementName);
				j++;


			}
		}
		queries.put(bq, listener);
		return new JenaCQueryExecution(dataset, listener);
	}

	public ContinousQueryExecution registerQuery(BaselineQuery bq) {
		Dataset dataset = DatasetFactory.create();
		JenaListener listener = new JenaListener(dataset, receiver, bq, QueryFactory.create(bq.getSparql_query()),
				reasoning, ontology_language, "http://streamreasoning.org/heaven/" + bq.getId());

		for (String c : bq.getEsperStreams()) {
			log.info("create schema " + c + "() inherits TStream");
			cepAdm.createEPL("create schema " + c + "() inherits TStream");
			if (!listener.addDefaultWindowStream(c)) {
				throw new StreamRegistrationException("Impossible to register stream [" + c + "]");
			}
		}

		int i = 0;
		for (String eq : bq.getEPLStreamQueries()) {
			log.info("Register esper query [" + eq + "]");
			String statementName = "QUERY" + bq.getId() + "STMT_" + i;
			EPStatement epl = cepAdm.createEPL(eq, statementName);
			epl.addListener(listener);
			listener.addStatementName(statementName);
			i++;
		}

		for (String[] pair : bq.getEsperNamedStreams()) {
			String stream = pair[1];
			String window = pair[0];
			log.info("create named schema " + stream + "() inherits TStream");
			cepAdm.createEPL("create schema " + stream + "() inherits TStream");
			log.info("creating named graph " + window + "");
			if (!listener.addNamedWindowStream(window, stream)) {
				throw new StreamRegistrationException(
						"Impossible to register window named  [" + window + "] on stream [" + stream + "]");
			}
		}

		i = 0;
		for (String eq : bq.getEPLNamedStreamQueries()) {
			log.info("Register esper query [" + eq + "]");
			String statementName = "QUERY" + bq.getId() + "STMT_NDM" + i;
			EPStatement epl = cepAdm.createEPL(eq, statementName);
			epl.addListener(listener);
			listener.addStatementName(statementName);
			i++;
		}

		queries.put(bq, listener);
		return new JenaCQueryExecution(dataset, listener);
	}

	public void registerReceiver(javax.sound.midi.Receiver receiver) {

	}
}
