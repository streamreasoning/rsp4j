package test;

import it.polimi.rsp.baselines.rsp.RSPQLEngine;
import it.polimi.rsp.baselines.rsp.stream.element.GraphStimulus;
import lombok.AllArgsConstructor;
import org.apache.jena.rdf.model.*;

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
            Property predicate = ResourceFactory.createProperty("http://somewhere/num");
            Literal object = m.createTypedLiteral(new Integer(i * 1000));
            Resource subject = ResourceFactory.createResource("http://somewhere/" + name + j);
            GraphStimulus t = new GraphStimulus(i * 1000, m.add(ResourceFactory.createStatement(subject, predicate, object)).getGraph(), stream_uri);
            System.out.println("[" + System.currentTimeMillis() + "] Sending [" + t + "] on " + stream_uri + " at " + i * 1000);
            e.process(t);
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
