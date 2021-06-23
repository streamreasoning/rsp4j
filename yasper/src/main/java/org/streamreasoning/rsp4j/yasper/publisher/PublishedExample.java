package org.streamreasoning.rsp4j.yasper.publisher;

import org.streamreasoning.rsp.Distribution;
import org.streamreasoning.rsp.Publisher;
import org.streamreasoning.rsp.WebDataStream;

public class PublishedExample {

    public static void main(String[] args) throws InterruptedException {

        WebDataStream<String> stream = SLD.<String>fetch("http://localhost:4567/colours");

        Distribution[] distribution = stream.distribution();

        distribution[0].start(); //starts the thread that allows the internal consumption

        stream.addConsumer((arg, ts) -> {});

        Publisher publisher1 = stream.publisher();

    }


}
