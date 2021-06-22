package org.streamreasoning.rsp4j.yasper.publisher;

import org.streamreasoning.rsp.Distribution;
import org.streamreasoning.rsp.Publisher;
import org.streamreasoning.rsp.WebDataStream;
import org.streamreasoning.rsp.WebStreamEndpoint;
import org.streamreasoning.rsp.enums.Format;
import org.streamreasoning.rsp.enums.License;
import org.streamreasoning.rsp.enums.Protocol;
import org.streamreasoning.rsp.enums.Security;

import java.util.Random;

public class PublicationExample {

    public static void main(String[] args) throws InterruptedException {

        Publisher publisher = new YPublisher("http://example.org/");

        Distribution d = new YDistribution();

        WebStreamEndpoint<String> wse = publisher
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


        wse.describe().stream().forEach(System.err::println);

        WebDataStream<String> stream = wse.<String>serve();

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
