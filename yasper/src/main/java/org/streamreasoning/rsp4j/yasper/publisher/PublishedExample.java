package org.streamreasoning.rsp4j.yasper.publisher;

import org.streamreasoning.rsp.Publisher;
import org.streamreasoning.rsp.WebDataStream;

import java.util.Random;

public class PublishedExample {

    public static void main(String[] args) throws InterruptedException {

        Publisher publisher = new YPublisher("http://example.org/");

        WebDataStream<String> stream = publisher.fetch("http://localhost:4567/colours");



        stream.describe().stream().forEach(System.err::println);

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
