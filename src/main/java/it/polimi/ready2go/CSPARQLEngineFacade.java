package it.polimi.ready2go;

import it.polimi.heaven.core.enums.ExecutionState;
import it.polimi.heaven.core.ts.EventProcessor;
import it.polimi.heaven.core.ts.events.RSPEngineResult;
import it.polimi.heaven.core.ts.events.Stimulus;
import it.polimi.heaven.core.ts.events.TripleContainer;
import it.polimi.heaven.core.ts.rspengine.RSPEngine;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import lombok.extern.log4j.Log4j;
import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;
import eu.larkc.csparql.common.RDFTable;
import eu.larkc.csparql.common.RDFTuple;
import eu.larkc.csparql.core.ResultFormatter;
import eu.larkc.csparql.core.engine.CsparqlEngine;
import eu.larkc.csparql.core.engine.CsparqlEngineImpl;
import eu.larkc.csparql.core.engine.CsparqlQueryResultProxy;

@Log4j
public class CSPARQLEngineFacade implements RSPEngine {

	private final String query;

	private EventProcessor<Stimulus> next;

	private CsparqlEngine engine;

	protected ExecutionState status;

	private RdfStream s = null;

	private CsparqlQueryResultProxy p = null;

	private int rspEventsNumber = 0;

	private class HeavenFormatter extends ResultFormatter {

		@Override
		public void update(Observable o, Object arg) {
			RDFTable r = (RDFTable) arg;
			Set<TripleContainer> statements = new HashSet<TripleContainer>();
			TripleContainer statementStrings;
			for (RDFTuple t : r.getTuples()) {
				statementStrings = new TripleContainer(t.get(0), t.get(1),
						t.get(2));
				statements.add(statementStrings);
			}
			long outputTimestamp = System.currentTimeMillis();
			next.process(new RSPEngineResult("", statements, rspEventsNumber, 0,
					outputTimestamp, false));
			log.debug("Status[" + status
					+ "] C-SPARQL has sent an OutCTEvent downstream");
		}

	}

	public CSPARQLEngineFacade(String name, EventProcessor<Stimulus> next) {
		this.next = next;
		this.query = "REGISTER STREAM TantoPerProvare AS "
				+ "CONSTRUCT {?s ?p ?o} "
				+ "FROM STREAM <http://myexample.org/stream1> [RANGE 5s STEP 1s] "
				+ "WHERE { ?s ?p ?o }";
		;
	}

	@Override
	public ExecutionState init() {
		// create the engine
		engine = new CsparqlEngineImpl();
		// Initialize it
		engine.initialize(true);
		// give an internal name to the stream it will receive data on
		// NOTE: this in not an implementation, it is just an hack
		s = new RdfStream("http://myexample.org/stream1");
		// add the internal stream to the engine
		engine.registerStream(s);
		//
		try {
			// register a query on the internal stream
			p = engine.registerQuery(query, false);
			// adds an observer to the query
			p.addObserver(new HeavenFormatter());
			status = ExecutionState.READY;
			log.debug("Status[" + status + "] Initizalized the RSPEngine");
			return status;
		} catch (ParseException e) {
			status = ExecutionState.ERROR;
			log.debug("Status["
					+ status
					+ "] An error occured the C-SPARQL Engine is not initialized");
			return status;
		}

	}

	@Override
	public ExecutionState close() {
		// clean up (i.e., unregister query and stream)
		engine.unregisterQuery(p.getId());
		engine.unregisterStream(s.getIRI());
		status = ExecutionState.CLOSED;
		log.info("Status [" + status + "] the C-SPARQL Engine is turned off.]");
		return status;
	}

	@Override
	public boolean process(Stimulus event) {
		// NOTE: this is not implemented, the engine will be running in any case
		status = ExecutionState.RUNNING;
		rspEventsNumber++;
		long ts = System.currentTimeMillis();
		int n = 0;
		for (TripleContainer tc : event.getEventTriples()) {
			String[] t = tc.getTriple();
			RdfQuadruple q = new RdfQuadruple(t[0], t[1], t[2], ts);
			s.put(q);
			n++;
		}
		status = ExecutionState.READY;
		log.debug("Status[" + status + "] RSP event number" + rspEventsNumber
				+ "injected in C-SPARQL engine sending " + n + " triples");
		return ExecutionState.READY.equals(status);
	}

	@Override
	public ExecutionState startProcessing() {
		if (isStartable()) {
			status = ExecutionState.READY;
		} else {
			status = ExecutionState.ERROR;
		}
		return status;
	}

	@Override
	public ExecutionState stopProcessing() {
		if (isOn()) {
			// NOTE: sono molto dubbioso che questa implementazione abbia senso
			status = ExecutionState.CLOSED;
		} else {
			status = ExecutionState.ERROR;
		}
		return status;
	}

	@Override
	public int getEventNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {

		return "C-SPARQL Engine";
	}

	@Override
	public void timeProgress() {
		// TODO Auto-generated method stub

	}

	protected boolean isStartable() {
		return ExecutionState.READY.equals(status)
				|| ExecutionState.CLOSED.equals(status);
	}

	protected boolean isOn() {
		return ExecutionState.READY.equals(status);
	}

	protected boolean isReady() {
		return ExecutionState.READY.equals(status);
	}

}
