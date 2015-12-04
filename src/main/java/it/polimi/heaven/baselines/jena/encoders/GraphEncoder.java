package it.polimi.heaven.baselines.jena.encoders;

import it.polimi.heaven.baselines.jena.events.stimuli.GraphStimulus;
import it.polimi.heaven.core.ts.data.TripleContainer;
import it.polimi.heaven.core.ts.events.engine.Stimulus;
import it.polimi.heaven.core.ts.events.heaven.HeavenInput;
import it.polimi.heaven.core.ts.streamer.Encoder;
import it.polimi.utils.RDFSUtils;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;

public class GraphEncoder implements Encoder {

	@Override
	public Stimulus[] encode(HeavenInput e) {
		Graph abox = ModelFactory.createMemModelMaker().createDefaultModel().getGraph();
		for (TripleContainer tc : e.getEventTriples()) {
			String[] t = tc.getTriple();
			abox.add(createTriple(t));
		}
		return new Stimulus[] { new GraphStimulus(e.getStimuli_application_timestamp(), System.currentTimeMillis(), abox, e.getStream_name()) };
	}

	private Triple createTriple(String[] eventTriple) {
		Resource subject = ResourceFactory.createResource(eventTriple[0]);
		Property predicate = (eventTriple[1] != RDFSUtils.TYPE_PROPERTY) ? ResourceFactory.createProperty(eventTriple[1]) : RDF.type;
		RDFNode object = ResourceFactory.createResource(eventTriple[2]);
		return new Triple(subject.asNode(), predicate.asNode(), object.asNode());
	}

}
