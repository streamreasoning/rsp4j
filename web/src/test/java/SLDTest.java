import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.rdf.model.Model;
import org.junit.Test;
import org.streamreasoning.rsp.SLD;
import org.streamreasoning.rsp.builders.DistributionBuilder;
import org.streamreasoning.rsp.enums.Format;
import org.streamreasoning.rsp.enums.License;
import org.streamreasoning.rsp.enums.Protocol;
import org.streamreasoning.rsp.enums.Security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SLDTest {

    JenaRDF rdf = new JenaRDF();
    String uri = "https://raw.githubusercontent.com/streamreasoning/rsp4j/r2r/web/src/test/resources/sgraph.ttl";
    Model ttl = rdf.createGraph().asJenaModel().read(uri, "TTL");
    Graph g = rdf.asGraph(ttl);
    Graph graph = SLD.fetchStreamDescriptor(uri);

    public void fetch() {
        JenaRDF rdf = SLD.rdf;
        Graph g = rdf.asGraph(rdf.createGraph().asJenaModel().read(uri, "TTL"));

        g.stream().forEach(triple -> {
            assertTrue(graph.contains(triple));
        });

    }

    @Test
    public void distribution() {

        SLD.Distribution<Object> actual = SLD.extractDistributions(graph)[0];

        SLD.Distribution<Object> expected = new DistributionBuilder("").
                access("ws://localhost:4567/access/colours", false)
                .format(Format.STRING)
                .license(License.CC)
                .protocol(Protocol.WebSocket)
                .security(Security.SSL)
                .publisher(SLD.extractPublisher(g))
                .buildSource(g);


//        expected.describe().stream().forEach(triple -> {
//            assertTrue(actual.describe().contains(triple));
//        });
//
//        actual.describe().stream().forEach(triple -> {
//            assertTrue(expected.describe().contains(triple));
//
//        });


        SLD.WebDataStream<Object> expectedWebStream = expected.getWebStream();
        SLD.WebDataStream<Object> actualWebStream = actual.getWebStream();

        assertEquals(expectedWebStream, actualWebStream);
    }

    @Test
    public void publisher() {

        SLD.Publisher expected = SLD.publisher("http://localhost:4567");

        SLD.Publisher actual = SLD.extractPublisher(g);

        assertEquals(expected.uri(), actual.uri());

        expected.describe().stream().forEach(triple -> {
            assertTrue(actual.describe().contains(triple));
        });

        actual.describe().stream().forEach(triple -> {
            assertTrue(expected.describe().contains(triple));
        });

    }

}