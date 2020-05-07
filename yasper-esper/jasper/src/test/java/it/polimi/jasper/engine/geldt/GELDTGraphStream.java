package it.polimi.jasper.engine.geldt;

import it.polimi.yasper.core.stream.data.DataStreamImpl;
import it.polimi.yasper.core.stream.web.WebStreamImpl;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Graph;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.time.Instant;
import java.util.Random;

/**
 * Created by Riccardo on 13/08/16.
 */
@Getter
@Log4j
public class GELDTGraphStream extends WebStreamImpl implements Runnable {

    private String igraphpath;
    private Model sgraph;
    private Model igraphlast;

    private DataStreamImpl<Graph> s;
    private int sample;

    private String type;

    public GELDTGraphStream(int sample, String keywords, String type) {
        super(null);
        this.sgraph = ModelFactory.createDefaultModel();
        this.sgraph.read(GELDTImageExample.class.getResource("/geldt/streams/" + type + "/sgraph").getPath(), "TTL");
        this.type = keywords;
        this.sample = sample;
        Query query = QueryFactory.create("" +
                "PREFIX : <https://www.gdeltproject.org/ontology#> \n" +
                "PREFIX dcat: <http://www.w3.org/ns/dcat> \n" +
                "SELECT ?url WHERE { ?url dcat:dataset ?y }");

        ResultSet resultSet = QueryExecutionFactory.create(query, this.sgraph).execSelect();

        if (resultSet.hasNext())
            super.stream_uri = resultSet.next().get("?url").toString();

        URL resource = GELDTGraphStream.class.getResource("/geldt/streams/" + type + "/igraph");
        igraphpath = resource.getPath();

    }


    public void setWritable(DataStreamImpl<Graph> e) {
        this.s = e;
    }

    public void run() {

        int i = 0;
        Random r = new Random(Instant.now().toEpochMilli());

        // timestamp extractor
        // Query query = QueryFactory.create("" +
        // "PREFIX vocals: <http://w3id.org/rsp/vocals#> . \n" +
        // "SELECT ?ts WHERE { ?s vocals:receivedAt  ?ts . }");
        // for now we use processing time

        while (true) {

            Model igraph = ModelFactory.createDefaultModel().read(igraphpath + (i % sample), "TTL");

            if (s != null)
                this.s.put(igraph.getGraph(), System.currentTimeMillis());
            try {
                int millis = 1 + r.nextInt(8) * 1000;
                log.info("Sleep [" + millis + "]");
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }

    @Override
    public String toString() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        RDFDataMgr.write(baos, sgraph, Lang.TTL);
        return baos.toString();
    }
}
