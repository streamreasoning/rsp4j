package org.streamreasoning.rsp4j.esper.engine;


import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.DataStreamImpl;

import java.util.Random;

/**
 * Created by Riccardo on 13/08/16.
 */
@Log4j
public class GraphStream extends DataStreamImpl implements Runnable {


    protected int grow_rate;
    private DataStream<Graph> s;

    private String type;

    public GraphStream(String name, String stream_uri, int grow_rate) {
        super(stream_uri);
        this.type = name;
        this.grow_rate = grow_rate;
    }

    public void setWritable(DataStream<Graph> e) {
        this.s = e;
    }

    public void run() {
        int i = 1;
        int j = 1;
        while (true) {
            Model m = ModelFactory.createDefaultModel();
            Random r = new Random();

            String uri = "http://www.streamreasoning/artist#";
            Resource person = ResourceFactory.createResource(stream_uri + "/artist1");
            Resource type = ResourceFactory.createResource(uri + this.type);
            Property hasAge = ResourceFactory.createProperty(uri + "hasAge");
            Property hasTimestamp = ResourceFactory.createProperty(uri + "generatedAt");
            Literal age = m.createTypedLiteral(i);
            int appTimestamp1 = i * 1000;
            Literal ts = m.createTypedLiteral(new Integer(appTimestamp1));

            //m.apply(m.createStatement(person, RDF.type, type));
            // m.apply(m.createStatement(person, hasAge, age));
            m.add(m.createStatement(person, hasTimestamp, ts));
            m.add(m.createStatement(person, RDF.type, type));
            m.add(m.createStatement(person, hasAge, age));

//            System.out.println("At [" + appTimestamp1 + "] [" + System.currentTimeMillis() + "] Sending [" + m.getGraph() + "] on " + stream_uri);

            if (s != null)
                this.s.put(m.getGraph(), appTimestamp1);
            try {
//                log.info("Sleep");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i += grow_rate;
            j++;
        }
    }
}
