package it.polimi.heaven.baselines.timekeeping.internal.snapshot;

import it.polimi.heaven.WindowUtils;
import it.polimi.heaven.baselines.JenaEngine;
import it.polimi.heaven.baselines.RSPListener;
import it.polimi.heaven.baselines.events.jena.StatementEvent;
import it.polimi.heaven.core.ts.EventProcessor;
import it.polimi.heaven.core.ts.events.Stimulus;
import it.polimi.heaven.core.ts.events.TripleContainer;
import it.polimi.utils.RDFSUtils;
import lombok.extern.log4j.Log4j;

import com.espertech.esper.client.Configuration;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.RDF;

@Log4j
public class JenaEngineStmt extends JenaEngine {

	public JenaEngineStmt(String name, EventProcessor<Stimulus> collector, RSPListener listener) {
		super(name, collector, listener, WindowUtils.JENA_INPUT_QUERY_SNAPTSHOT);

		cepConfig = new Configuration();
		cepConfig.getEngineDefaults().getThreading().setInternalTimerEnabled(true);
		log.info("Added [" + StatementEvent.class + "] as TEvent");
		cepConfig.addEventType("TEvent", StatementEvent.class.getName());

	}

	@Override
	protected void handleEvent(Stimulus e) {
		super.handleEvent(e);
		for (TripleContainer tc : e.getEventTriples()) {
			String[] t = tc.getTriple();
			esperEventsNumber++;
			cepRT.sendEvent(new StatementEvent(createStatement(t), cepRT.getCurrentTime(), System.currentTimeMillis()));
		}

	}

	private Statement createStatement(String[] eventTriple) {
		Resource subject = ResourceFactory.createResource(eventTriple[0]);
		Property predicate = (eventTriple[1] != RDFSUtils.TYPE_PROPERTY) ? ResourceFactory.createProperty(eventTriple[1]) : RDF.type;
		RDFNode object = ResourceFactory.createResource(eventTriple[2]);
		return ResourceFactory.createStatement(subject, predicate, object);
	}

}
