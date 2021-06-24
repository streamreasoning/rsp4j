package org.streamreasoning.rsp4j.io;

import org.apache.commons.rdf.api.Graph;
import org.junit.Test;
import org.streamreasoning.rsp4j.io.sources.HTTPPullSource;
import org.streamreasoning.rsp4j.io.utils.BufferedConsumer;
import org.streamreasoning.rsp4j.io.utils.ParsingStrategyTest;
import org.streamreasoning.rsp4j.io.utils.RDFBase;
import org.streamreasoning.rsp4j.io.utils.parsing.JenaRDFParsingStrategy;
import org.streamreasoning.rsp4j.io.utils.serialization.JenaRDFSerializationStrategy;
import spark.Spark;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class HTTPPullTest {

   // @Test
    public void testHTTPPull() throws InterruptedException {
        String pathName = "pull";
        //set up http pull access point
        Spark.port(9000);
        Spark.get("/" + pathName, (req, res) -> {
            return "<http://test/subject> <http://test/property> <http://test/object>.";
        });
        // create http pull source
        JenaRDFParsingStrategy parsingStrategy = new JenaRDFParsingStrategy(RDFBase.NT);
        HTTPPullSource<Graph> pullSource = new HTTPPullSource<Graph>("http://localhost:9000/"+pathName,100,parsingStrategy);
        Thread.sleep(100);
        pullSource.stream();
        // create dummy consumer
        BufferedConsumer<Graph> bufferedConsumer = new BufferedConsumer<>();
        pullSource.addConsumer(bufferedConsumer);

        Thread.sleep(1000);
        pullSource.stop();

        // should have received at least 1 message
        assertNotEquals(0,bufferedConsumer.getSize());
        // compare content of expected graph and actual received graph
        Graph expectedGraph = ParsingStrategyTest.createGraph();
        ParsingStrategyTest.compareGraph(expectedGraph,bufferedConsumer.getMessage(0));
        // stop http access point
        Spark.stop();
    }
}
