package it.polimi.deib.ssp.utils;

import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.RegisteredStream;
import org.apache.commons.rdf.api.Graph;

import java.util.ArrayList;
import java.util.List;

public class WritableStream implements RegisteredStream<Graph> {

    List<WindowAssigner<Graph>> assigners = new ArrayList<>();

    @Override
    public String getURI() {
        return null;
    }

    @Override
    public void addWindowAssiger(WindowAssigner windowAssigner) {
        assigners.add(windowAssigner);
    }

    @Override
    public void put(Graph e, long ts) {
        assigners.forEach(a -> a.notify(e, ts));
    }

}
