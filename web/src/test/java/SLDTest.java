import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.rdf.model.Model;
import org.junit.Test;
import org.streamreasoning.rsp.SLD;
import org.streamreasoning.rsp.builders.DistributionBuilder;
import org.streamreasoning.rsp.enums.Format;
import org.streamreasoning.rsp.enums.License;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SLDTest {

    @Test
    public void fetch() {
        JenaRDF rdf = new JenaRDF();
        String uri = "https://raw.githubusercontent.com/riccardotommasini/webstreams/site/src/main/resources/streams/sgraphs/gdelt.ttl";
        Graph g = rdf.asGraph(rdf.createGraph().asJenaModel().read(uri, "TTL"));
        Graph graph = SLD.fetchStreamDescriptor(uri);

        g.stream().forEach(triple -> {
            assertTrue(graph.contains(triple));
        });

    }

    @Test
    public void distribution() {

        JenaRDF rdf = new JenaRDF();
        String uri = "https://raw.githubusercontent.com/riccardotommasini/webstreams/master/src/main/resources/streams/sgraphs/gkg.ttl";
        Model ttl = rdf.createGraph().asJenaModel().read(uri, "TTL");
        Graph g = rdf.asGraph(ttl);

        System.out.println(SLD.publisherQuery.toString());
        SLD.Distribution<Object> distribution = SLD.extractDistributions(g)[0];

        SLD.Distribution<Object> expected = new DistributionBuilder("").
                access("ws://localhost:8080/gkg", false)
                .format(Format.JSONLD)
                .license(License.CC)
                .buildSource("ws://localhost:8080/gkg", false);

        assertEquals(expected, distribution);

    }

}