package it.polimi.jasper.engine.color;

import it.polimi.yasper.core.stream.data.DataStreamImpl;
import it.polimi.yasper.core.stream.web.WebStreamImpl;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;

import java.time.Instant;
import java.util.Random;

/**
 * Created by Riccardo on 13/08/16.
 */
@Log4j
public class ColorsGraphStream extends WebStreamImpl implements Runnable {

    private DataStreamImpl<Graph> s;

    private String type;

    public ColorsGraphStream(String name, String stream_uri) {
        super(stream_uri);
        this.type = name;
    }

    public void setWritable(DataStreamImpl<Graph> e) {
        this.s = e;
    }

    public void run() {
    	
        int i = 1;
        int j = 1;
        
        while (true) {
            Model m = ModelFactory.createDefaultModel();
            Random r = new Random(Instant.now().toEpochMilli());

            String uri = "http://www.streamreasoning.org/ontologies/2018/9/colors#";
            Resource color = ResourceFactory.createResource(stream_uri + "/" + this.type.toLowerCase().charAt(0) + i);
            Resource type = ResourceFactory.createResource(uri + this.type);
            Property hasTimestamp = ResourceFactory.createProperty(uri + "generatedAt");
            int appTimestamp = j * 1000;
            Literal ts = m.createTypedLiteral(new Integer(appTimestamp));

            m.add(m.createStatement(color, hasTimestamp, ts));
            m.add(m.createStatement(color, RDF.type, type));
            
            if(this.type.equals("Yellow")) {
            	Property from = ResourceFactory.createProperty(uri + "from");
            	Resource red = ResourceFactory.createResource(stream_uri + "/r" + i);
            	Resource green = ResourceFactory.createResource(stream_uri + "/g" + i);
            	m.add(m.createStatement(color, from, red));
            	m.add(m.createStatement(color, from, green));
            }

            System.out.println("At [" + appTimestamp + "] [" + System.currentTimeMillis() + "] Sending [" + m.getGraph() + "] on " + stream_uri);

            if (s != null)
                this.s.put(m.getGraph(), appTimestamp);
            try {
                log.info("Sleep");
                int randSleep = 1 + r.nextInt(8);
                j += randSleep;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i += 1;
        }
    }
}
