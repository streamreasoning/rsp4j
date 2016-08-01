package it.polimi.rsp.baselines.jena.encoders;

import it.polimi.rsp.baselines.jena.events.stimuli.StatementStimulus;
import it.polimi.heaven.core.teststand.data.Line;
import it.polimi.heaven.core.teststand.data.RDFLine;
import it.polimi.heaven.core.teststand.events.HeavenInput;
import it.polimi.heaven.core.teststand.rspengine.events.Stimulus;
import it.polimi.heaven.core.teststand.streamer.Encoder;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.RDF;
import it.polimi.rsp.baselines.utils.RDFSUtils;

public class RDF2StatementStimulusEncoder implements Encoder {

	@Override
	public Stimulus[] encode(HeavenInput e) {
		Graph abox = ModelFactory.createMemModelMaker().createDefaultModel().getGraph();

		int size = e.size();
		Stimulus[] stimuli = new StatementStimulus[size];
		int i = 0;
		for (Line tc : e.getLines()) {
			RDFLine rdf = (RDFLine) tc;
			String[] t = rdf.getTriple();
			abox.add(createTriple(t));
			stimuli[i] = new StatementStimulus(e.getStimuli_application_timestamp(), createStatement(t), e.getStream_name());
			i++;
		}
		return stimuli;
	}

	private Triple createTriple(String[] eventTriple) {
		Resource subject = ResourceFactory.createResource(eventTriple[0]);
		Property predicate = (eventTriple[1] != RDFSUtils.TYPE_PROPERTY) ? ResourceFactory.createProperty(eventTriple[1]) : RDF.type;
		RDFNode object = ResourceFactory.createResource(eventTriple[2]);
		return new Triple(subject.asNode(), predicate.asNode(), object.asNode());
	}

	private Statement createStatement(String[] eventTriple) {
		Resource subject = ResourceFactory.createResource(eventTriple[0]);
		Property predicate = (eventTriple[1] != RDFSUtils.TYPE_PROPERTY) ? ResourceFactory.createProperty(eventTriple[1]) : RDF.type;
		RDFNode object = ResourceFactory.createResource(eventTriple[2]);
		return ResourceFactory.createStatement(subject, predicate, object);
	}

}
