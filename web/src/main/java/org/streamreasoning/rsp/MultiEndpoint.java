package org.streamreasoning.rsp;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.api.stream.web.WebStream;
import org.streamreasoning.rsp4j.io.DataStreamImpl;

import java.util.ArrayList;
import java.util.List;

public class MultiEndpoint<E> implements WebStreamEndpoint<E> {


    private final String path;
    private List<WebStreamEndpoint> wses;

    public MultiEndpoint(String path, List<WebStreamEndpoint> wses) {
        this.wses = wses;
        this.path = path;
    }

    public MultiEndpoint(String path) {
        this(path, new ArrayList<>());
    }

    @Override
    public DataStream<E> deploy() {
        wses.forEach(wse -> wse.deploy());
        //TODO to create the multi endpoint we need a way to pipe different streams;
        return new DataStreamImpl<E>("");
    }

    @Override
    public WebStream serve() {
        wses.forEach(wse -> wse.serve());
        return null;
    }

    @Override
    public Graph describe() {
        return null;
    }
}
