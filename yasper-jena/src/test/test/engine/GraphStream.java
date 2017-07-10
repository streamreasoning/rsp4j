package test.engine;

import it.polimi.jasper.engine.stream.GraphStimulus;
import it.polimi.yasper.core.engine.RSPQLEngine;
import lombok.AllArgsConstructor;

import java.util.Random;

/**
 * Created by Riccardo on 13/08/16.
 */
@AllArgsConstructor
public class GraphStream implements Runnable {

    RSPQLEngine e;
    private String name;
    private String stream_uri;
    private int grow_rate;

    @Override
    public void run() {


        int i = 1;
        int j = 1;
        while (true) {
            Model m = ModelFactory.createDefaultModel();
            Random r = new Random();

            String uri = "http://www.streamreasoning/it.polimi.jasper.test/artist#";
            Resource person = ResourceFactory.createResource(stream_uri + "/artist" + i);
            Resource type = ResourceFactory.createResource(uri + name);
            Property hasAge = ResourceFactory.createProperty(uri + "hasAge");
            Property hasTimestamp = ResourceFactory.createProperty(uri + "generatedAt");
            Literal age = m.createTypedLiteral(r.nextInt(99));
            Literal ts = m.createTypedLiteral(new Integer(i * 1000));

            //m.add(m.createStatement(person, RDF.type, type));
            // m.add(m.createStatement(person, hasAge, age));
            m.add(m.createStatement(person, hasTimestamp, ts));

            GraphStimulus t = new GraphStimulus(i * 1000, m.getGraph(), stream_uri);
            System.out.println("[" + System.currentTimeMillis() + "] Sending [" + t + "] on " + stream_uri + " at " + i * 1000);
            this.e.process(t);
            try {
                Thread.sleep(grow_rate * 998);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i += grow_rate;
            j++;
        }

    }
}
