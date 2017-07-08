package test.r2s;

import it.polimi.rsp.core.rsp.RSPQLEngine;
import it.polimi.rsp.core.rsp.stream.item.jena.GraphStimulus;
import lombok.AllArgsConstructor;
import org.apache.jena.rdf.model.*;

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
        String uri = "http://www.streamreasoning/test#";
        Property hasTimestamp = ResourceFactory.createProperty(uri + "generatedAt");

        Model m = ModelFactory.createDefaultModel();

        genAndSend("A", hasTimestamp, 1);
        genAndSend("B", hasTimestamp, 2);
        genAndSend("A", hasTimestamp, 3);
        genAndSend("B", hasTimestamp, 4);
        genAndSend("A", hasTimestamp, 5);
        genAndSend("B", hasTimestamp, 6);
        genAndSend("A", hasTimestamp, 7);
        genAndSend("C", hasTimestamp, 8);
        genAndSend("A", hasTimestamp, 9);
        genAndSend("C", hasTimestamp, 10);
        genAndSend("D", hasTimestamp, 11);
        genAndSend("C", hasTimestamp, 12);


    }

    private void genAndSend(String name, Property hasTimestamp, int i) {
        Model m = ModelFactory.createDefaultModel();
        Resource token = ResourceFactory.createResource(stream_uri + "#" + name );
        Literal ts = m.createTypedLiteral(new Integer(i * 1000));
        m.add(m.createStatement(token, hasTimestamp, ts));
        GraphStimulus t = new GraphStimulus(i * 1000, m.getGraph(), stream_uri);
        System.err.println("[" + System.currentTimeMillis() + "] Sending [" + t + "] on " + stream_uri + " at " + i * 1000);

        e.process(t);
        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
