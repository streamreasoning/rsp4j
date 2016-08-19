package it.polimi.rsp.baselines.jena.encoders;

import it.polimi.heaven.core.teststand.data.Line;
import it.polimi.heaven.core.teststand.data.RDFLine;
import it.polimi.heaven.core.teststand.events.HeavenInput;
import it.polimi.heaven.core.teststand.rsp.data.Stimulus;
import it.polimi.heaven.core.teststand.streamer.Encoder;
import it.polimi.rsp.baselines.jena.events.stimuli.GraphStimulus;
import it.polimi.rsp.baselines.utils.RDFSUtils;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;

public class RDF2GraphStimulusEncoder implements Encoder {

    @Override
    public Stimulus[] encode(HeavenInput e) {
        Graph abox = ModelFactory.createMemModelMaker().createDefaultModel().getGraph();
        for (Line tc : e.getLines()) {
            RDFLine rdf = (RDFLine) tc;
            String[] t = rdf.getTriple();
            abox.add(createTriple(t));
        }
        //TODO add stream_uri and window_uri to heavenInput
        return new Stimulus[]{new GraphStimulus(e.getStimuli_application_timestamp(), abox, "default", e.getStream_name())};
    }

    private Triple createTriple(String[] eventTriple) {
        Resource subject = ResourceFactory.createResource(eventTriple[0]);
        Property predicate = (eventTriple[1] != RDFSUtils.TYPE_PROPERTY) ? ResourceFactory.createProperty(eventTriple[1]) : RDF.type;
        RDFNode object = ResourceFactory.createResource(eventTriple[2]);
        return new Triple(subject.asNode(), predicate.asNode(), object.asNode());
    }

}
