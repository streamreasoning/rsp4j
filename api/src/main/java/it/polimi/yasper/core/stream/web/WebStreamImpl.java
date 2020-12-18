package it.polimi.yasper.core.stream.web;

public class WebStreamImpl implements WebStream {

    protected String stream_uri;

    public WebStreamImpl(String stream_uri) {
        this.stream_uri = stream_uri;
    }

    @Override
    public String getURI() {
        return stream_uri;
    }


}
