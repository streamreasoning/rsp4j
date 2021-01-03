package it.polimi.deib.sr.rsp.api.stream.web;

public class WebStreamImpl implements WebStream {

    protected String stream_uri;

    public WebStreamImpl(String stream_uri) {
        this.stream_uri = stream_uri;
    }

    @Override
    public String uri() {
        return stream_uri;
    }


}
