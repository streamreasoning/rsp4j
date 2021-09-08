package org.streamreasoning.rsp4j.io.utils.file;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.io.utils.serialization.StringSerializationStrategy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSinkConsumer<T> implements Consumer<T> {

    private final StringSerializationStrategy serializationStrategy;
    private final String path;

    public FileSinkConsumer(String path, StringSerializationStrategy serializationStrategy) {
        this.path = path;
        this.serializationStrategy = serializationStrategy;
    }

    @Override
    public void notify(T arg, long ts) {
        Path path = Paths.get(this.path);

        // create file and write lines to file
        try {
            FileWriter fw = new FileWriter(path.toFile(), true);
            BufferedWriter writer = new BufferedWriter(fw);

            String outputLine = serializationStrategy.serialize(arg);
            writer.write(outputLine);
            if (!outputLine.endsWith(System.lineSeparator())) {
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
