package test;

import it.polimi.rsp.baselines.jena.JenaEngine;
import it.polimi.rsp.baselines.jena.events.stimuli.GraphStimulus;
import lombok.AllArgsConstructor;
import org.apache.jena.rdf.model.*;

import java.util.Random;

/**
 * Created by Riccardo on 13/08/16.
 */
@AllArgsConstructor
public class Stream implements Runnable {

    JenaEngine e;
    private String name;
    private String streamname;

    @Override
    public void run() {

        Model m = ModelFactory.createDefaultModel();
        Property predicate = ResourceFactory.createProperty("http://somewhere/num");
        Random r = new Random();
        int i = 1;
        while (true) {
            Literal object = m.createTypedLiteral(new Integer(i));
            Resource subject = ResourceFactory.createResource("http://somewhere/" + name + i);
            System.out.println("Sending on " + streamname + " at " + i * 1000);
            e.process(new GraphStimulus(i * 1000, m.add(ResourceFactory.createStatement(subject, predicate, object)).getGraph(), streamname));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }

    }
}
