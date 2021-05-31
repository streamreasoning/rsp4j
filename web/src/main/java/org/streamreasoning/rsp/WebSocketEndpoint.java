package org.streamreasoning.rsp;

import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.streamreasoning.rsp4j.api.stream.web.WebStream;


import static spark.Spark.*;

public class WebSocketEndpoint<E> implements WebStreamEndpoint<E> {

    private final String access, path;
    private WebSocketHandler wsh;


    public WebSocketEndpoint(String access, String path) {
        this.access = access;
        this.path = path;
    }

    @Override
    public WebDataStream<E> deploy() {
        ignite();
        return new WebDataStreamImpl<E>(path, wsh);
    }

    @Override
    public WebStream serve() {
        ignite();
        return () -> path;
    }

    private void ignite() {
        webSocket("/access/" + access, this.wsh = new WebSocketHandler());
        get(path, (request, response) -> "Hello colours");
        init();
    }


}

