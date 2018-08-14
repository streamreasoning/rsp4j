package it.polimi.yasper.core.stream.rdf;

import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.Stream;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class RDFStream implements Stream {

    @NonNull
    protected String stream_uri;
    List<WindowAssigner> assigners = new ArrayList<>();

    public RDFStream(String stream_uri) {
        this.stream_uri = stream_uri;
    }

    @Override
    public String getURI() {
        return stream_uri;
    }

    @Override
    public void addWindowAssiger(WindowAssigner windowAssigner) {
        assigners.add(windowAssigner);
    }

}
