package it.polimi.spe.stream.rdf;

import it.polimi.rspql.Stream;
import it.polimi.spe.windowing.assigner.WindowAssigner;
import lombok.NonNull;

public class RDFStream implements Stream {

    @NonNull
    protected String stream_uri;

    public RDFStream(String stream_uri) {
        this.stream_uri = stream_uri;
    }

    @Override
    public String getURI() {
        return stream_uri;
    }

    @Override
    public void addObserver(WindowAssigner windowAssigner) {

    }

}
