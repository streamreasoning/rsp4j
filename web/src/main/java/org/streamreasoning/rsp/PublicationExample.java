package org.streamreasoning.rsp;

import org.streamreasoning.rsp.builders.DistributionBuilder;
import org.streamreasoning.rsp.builders.WebStreamBuilder;
import org.streamreasoning.rsp.enums.Format;
import org.streamreasoning.rsp.enums.License;
import org.streamreasoning.rsp.enums.Protocol;
import org.streamreasoning.rsp.enums.Security;

import java.util.Random;

public class PublicationExample {

    public static void main(String[] args) throws InterruptedException {

        String base = "http://localhost:4567";

        SLD.Publisher publisher = SLD.publisher(base);
        WebStreamBuilder wsb = new WebStreamBuilder(base);
        DistributionBuilder d = new DistributionBuilder(base);

        SLD.WebStream<String> ws = wsb
                .stream("colours", true)
                .name("Colour Stream")
                .description("stream of colours")
                .publisher(publisher)
                .distribution(d.access("colours", true) // defines if the distribution uri will be a fragment uri (need a proxy otherwise). (Can be used to change port)
                        .protocol(Protocol.WebSocket)  // Determine what sink to choose
                        .security(Security.SSL) // we need to include secure protocol
                        .license(License.CC) //mostly for documentation
                        .format(Format.STRING)) //relates with serialization strategy
                .<String>build();

        ws.describe().stream().forEach(System.err::println);

        SLD.WebDataStream<String> stream = ws.serve();

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
