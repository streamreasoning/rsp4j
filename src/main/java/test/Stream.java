package test;

import it.polimi.rsp.baselines.jena.JenaEngine;
import it.polimi.rsp.baselines.jena.events.stimuli.GraphStimulus;
import lombok.AllArgsConstructor;
import org.apache.jena.rdf.model.*;

/**
 * Created by Riccardo on 13/08/16.
 */
@AllArgsConstructor
public class Stream implements Runnable {

    JenaEngine e;
    private String name;
    private String window_uri;
    private String stream_uri;
    private int grow_rate;

    @Override
    public void run() {

        Model m = ModelFactory.createDefaultModel();
        Property predicate = ResourceFactory.createProperty("http://somewhere/num");
        int i = 1;
        while (true) {
            Literal object = m.createTypedLiteral(new Integer(i * 1000));
            Resource subject = ResourceFactory.createResource("http://somewhere/" + name + i);
            System.out.println("Sending on " + stream_uri + " at " + i * 1000);
            e.process(new GraphStimulus(i * 1000, m.add(ResourceFactory.createStatement(subject, predicate, object)).getGraph(), window_uri, stream_uri));
            try {
                Thread.sleep(grow_rate * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i += grow_rate;
        }

    }
}
