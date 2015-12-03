package it.polimi.heaven.baselines.jena;

import it.polimi.heaven.baselines.esper.RSPListener;
import it.polimi.heaven.baselines.jena.abstracts.JenaEngine;
import it.polimi.heaven.baselines.jena.events.StatementEvent;
import it.polimi.heaven.core.ts.EventProcessor;
import it.polimi.heaven.core.ts.events.Stimulus;
import it.polimi.heaven.core.ts.events.TripleContainer;
import it.polimi.utils.RDFSUtils;
import lombok.extern.log4j.Log4j;

import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * In this example rdfs property of subclass of is exploited by external static
 * functions which can be called form EPL No data or time windows are considered
 * in event consuming, se the related example for that time is externally
 * controlled all event are sent in the samte time interval
 * 
 * the query doesn't include joins
 * 
 * events are pushed, on incoming events, in 3 differents queue which are pulled
 * by refering statements
 * 
 * **/
@Log4j
public class StatementBaseline extends JenaEngine {

	public StatementBaseline(RSPListener listener, EventProcessor<Stimulus> collector) {
		super(new StatementEvent(), collector);
	}

	@Override
	protected void handleEvent(Stimulus e) {
		abox = ModelFactory.createMemModelMaker().createDefaultModel().getGraph();
		for (TripleContainer tc : e.getEventTriples()) {
			String[] t = tc.getTriple();
			cepRT.sendEvent(new StatementEvent(cepRT.getCurrentTime(), System.currentTimeMillis(), createStatement(t)), e.getStreamName());
			esperEventsNumber++;
		}
	}

	private Statement createStatement(String[] eventTriple) {
		Resource subject = ResourceFactory.createResource(eventTriple[0]);
		Property predicate = (eventTriple[1] != RDFSUtils.TYPE_PROPERTY) ? ResourceFactory.createProperty(eventTriple[1]) : RDF.type;
		RDFNode object = ResourceFactory.createResource(eventTriple[2]);
		return ResourceFactory.createStatement(subject, predicate, object);
	}

}
