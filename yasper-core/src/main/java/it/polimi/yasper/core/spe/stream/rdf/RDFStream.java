package it.polimi.yasper.core.spe.stream.rdf;

import it.polimi.yasper.core.rspql.Stream;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
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
    public void addWindowAssiger(WindowAssigner windowAssigner) {

    }

}
