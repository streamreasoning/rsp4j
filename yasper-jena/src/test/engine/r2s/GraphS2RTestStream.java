package engine.r2s;

import it.polimi.jasper.engine.stream.GraphStreamItem;
import it.polimi.yasper.core.engine.RSPQLEngine;
import lombok.AllArgsConstructor;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;

import java.util.Random;

/**
 * Created by Riccardo on 13/08/16.
 */
@AllArgsConstructor
public class GraphS2RTestStream implements Runnable {

    RSPQLEngine e;
    private String stream_uri;

    @Override
    public void run() {
        Random r = new Random();
        String uri = "http://www.streamreasoning/it.polimi.jasper.test#";
        Property hasTimestamp = ResourceFactory.createProperty(uri + "generatedAt");

        Model m = ModelFactory.createDefaultModel();

        genAndSend("A", RDF.type, 1);
        genAndSend("B", RDF.type, 2);
        genAndSend("A", RDF.type, 3);  //1 AND 3 COLLAPSE
        genAndSend("B", RDF.type, 4);
        genAndSend("A", RDF.type, 5);  //5 AND 3 COLLAPSE
        genAndSend("B", RDF.type, 6);
        genAndSend("A", RDF.type, 7);  //5 AND 7 COLLAPSE
        genAndSend("A", RDF.type, 8);
        genAndSend("C", RDF.type, 9);  //7 AND 8 COLLAPSE
        genAndSend("A", RDF.type, 10);
        genAndSend("C", RDF.type, 11); //11 AND 9 COLLAPSE
        genAndSend("D", RDF.type, 12);
        genAndSend("C", RDF.type, 13); //7 AND 8 COLLAPSE


    }

    private void genAndSend(String name, Property p, int i) {
        Model m = ModelFactory.createDefaultModel();
        Resource s = ResourceFactory.createResource(stream_uri + "#" + name);
        Resource o = ResourceFactory.createResource(stream_uri + "#Sample");
        //Literal ts = m.createTypedLiteral(new Integer(i * 1000));
        m.add(m.createStatement(s, p, o));
        GraphStreamItem t = new GraphStreamItem(i * 1000, m.getGraph(), stream_uri);
        System.err.println("[" + System.currentTimeMillis() + "] Sending [" + t + "] on " + stream_uri + " at " + i * 1000);

        e.process(t);
        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
