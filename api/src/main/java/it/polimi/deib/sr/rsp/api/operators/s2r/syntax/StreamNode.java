package it.polimi.deib.sr.rsp.api.operators.s2r.syntax;

import it.polimi.deib.sr.rsp.api.stream.web.WebStream;

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
