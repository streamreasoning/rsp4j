package org.streamreasoning.rsp4j.api.operators.s2r.syntax;

import org.streamreasoning.rsp4j.api.stream.web.WebStream;

public class StreamNode implements WebStream {

    private String uri;

    public StreamNode(String uri) {
        this.uri = uri;
    }

    @Override
    public String uri() {
        return uri;
    }

}
