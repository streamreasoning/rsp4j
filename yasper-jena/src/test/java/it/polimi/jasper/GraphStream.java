package it.polimi.jasper;

import it.polimi.jasper.engine.stream.items.GraphStreamItem;
import it.polimi.jasper.engine.stream.items.GraphStreamSchema;
import it.polimi.yasper.core.rspql.RSPEngine;
import it.polimi.yasper.core.spe.stream.rdf.RDFStream;
import it.polimi.yasper.core.stream.StreamSchema;
import lombok.extern.log4j.Log4j;
import org.apache.jena.rdf.model.*;

import java.util.Random;

/**
 * Created by Riccardo on 13/08/16.
 */
@Log4j
public class GraphStream extends RDFStream implements Runnable {

    private StreamSchema schema = new GraphStreamSchema();

    @Override
    public StreamSchema getSchema() {
        return schema;
    }

    protected int grow_rate;
    private RSPEngine e;

    private String name;

    public GraphStream(String name, String stream_uri, int grow_rate) {
        super(stream_uri);
        this.name = name;
        this.grow_rate = grow_rate;
    }

    public RSPEngine getRSPEngine() {
        return e;
    }

    public void setRSPEngine(RSPEngine e) {
        this.e = e;
    }

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

            //m.apply(m.createStatement(person, RDF.type, type));
            // m.apply(m.createStatement(person, hasAge, age));
            m.add(m.createStatement(person, hasTimestamp, ts));

            GraphStreamItem t = new GraphStreamItem(i * 1000, m.getGraph(), stream_uri);
            GraphStreamItem t2 = new GraphStreamItem(i * 1000, m.getGraph(), stream_uri);
            GraphStreamItem t3 = new GraphStreamItem(i * 1000, m.getGraph(), stream_uri);
            System.out.println("[" + System.currentTimeMillis() + "] Sending [" + t + "] on " + stream_uri + " at " + i * 1000);

            if (e != null)
                this.e.process(t);
            this.e.process(t2);
            this.e.process(t3);
            try {
                log.info("Sleep");
                Thread.sleep(grow_rate * 998);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i += grow_rate;
            j++;
        }
    }
}
