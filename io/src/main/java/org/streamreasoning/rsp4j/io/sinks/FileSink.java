package org.streamreasoning.rsp4j.io.sinks;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.streamreasoning.rsp4j.io.utils.file.FileSinkConsumer;
import org.streamreasoning.rsp4j.io.utils.serialization.StringSerializationStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * WebDataStream Sink that write to file. See FileSinkConsumer when only a consumer is needed to write to file.
 * This sink uses a SerializationStrategy to convert objects of type T to strings, before writing them to file.
 *
 * @param <T>  objects of type T that need to be written to file.
 */
public class FileSink<T> implements WebDataStream<T> {
    private final StringSerializationStrategy<T> serializatinStrategy;
    private final String path;
    private final FileSinkConsumer<T> fileConsumer;
    protected List<Consumer<T>> consumers = new ArrayList<>();
    protected String stream_uri;

    /**
     * Creates a new File sink that write strings to file at location path.
     * The conversion to string is done through the SerializationStrategy.
     * @param path  path where the content of the stream will be written to
     * @param serializationStrategy  serialization strategy used for converting object of type T to strings.
     */
    public FileSink(String path, StringSerializationStrategy<T> serializationStrategy){
        this.path = path;
        this.serializatinStrategy = serializationStrategy;
        this.fileConsumer = new FileSinkConsumer<T>(path,serializationStrategy);
        this.addConsumer(fileConsumer);
    }

    @Override
    public void addConsumer(Consumer<T> c) {
        consumers.add(c);
    }

    @Override
    public void put(T t, long ts) {
        consumers.forEach(graphConsumer -> graphConsumer.notify(t, ts));
    }

    @Override
    public String uri() {
        return this.stream_uri;
    }
}
