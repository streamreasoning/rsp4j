package org.streamreasoning.rsp;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp.builders.DistributionBuilder;
import org.streamreasoning.rsp.builders.WebStreamBuilder;
import org.streamreasoning.rsp.enums.Format;
import org.streamreasoning.rsp.enums.License;
import org.streamreasoning.rsp.enums.Protocol;
import org.streamreasoning.rsp.enums.Security;
import org.streamreasoning.rsp4j.io.sinks.WebsocketServerSink;
import org.streamreasoning.rsp4j.io.sources.WebsocketClientSource;

import java.util.Random;

public class PublicationExample {

    public static void main(String[] args) throws InterruptedException {

        WebStreamBuilder webStreamBuilder = new WebStreamBuilder(SLD.publisher("http://example.org/"), "http://localhost:4567");

        DistributionBuilder d = new DistributionBuilder();

        SLD.Distribution<String>[] wse = webStreamBuilder
                .stream("colours", true)
                .name("Colour Stream")
                .description("stream of colours")
                .distribution(d.
                        access("colours", false) // defines if the distribution uri will be a fragment uri (need a proxy otherwise). (Can be used to change port)
                        .protocol(Protocol.WebSocket)  // Determine what sink to choose
                        .security(Security.SSL) // we need to include secure protocol
                        .license(License.CC) //mostly for documentation
                        .format(Format.STRING)) //relates with serialization strategy
                .<String>build();

        wse[0].describe().stream().forEach(System.err::println);

        SLD.WebDataStream<String> stream = wse[0].serve();

//        WebsocketServerSink<String> stream = new WebsocketServerSink<String>(9000,"test",String::toString);
//        stream.startSocket();

        new Thread(() -> {
            try {
                Random r = new Random();
                String[] colors = new String[]{"Red", "Yellow", "Blue"};
                while (true) {
                    stream.put(colors[r.nextInt(colors.length - 1)], System.currentTimeMillis());
                    Thread.sleep(5 * 1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }


}
