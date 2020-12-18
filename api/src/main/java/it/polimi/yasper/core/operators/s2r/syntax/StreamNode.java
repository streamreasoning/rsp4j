package it.polimi.yasper.core.operators.s2r.syntax;

import it.polimi.yasper.core.stream.web.WebStream;

public class StreamNode implements WebStream {

    private String uri;

    public StreamNode(String uri) {
        this.uri = uri;
    }

    @Override
    public String getURI() {
        return uri;
    }

}
