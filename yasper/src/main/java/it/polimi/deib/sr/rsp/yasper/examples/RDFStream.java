package it.polimi.deib.sr.rsp.yasper.examples;

import it.polimi.deib.sr.rsp.api.operators.s2r.execution.assigner.Consumer;
import it.polimi.deib.sr.rsp.api.stream.data.WebDataStream;
import it.polimi.deib.sr.rsp.api.stream.web.WebStreamImpl;
import org.apache.commons.rdf.api.Graph;

import java.util.ArrayList;
import java.util.List;

public class RDFStream extends WebStreamImpl implements WebDataStream<Graph> {

    protected List<Consumer<Graph>> consumers = new ArrayList<>();

    public RDFStream(String stream_uri) {
        super(stream_uri);
    }

    @Override
    public void addConsumer(Consumer<Graph> c) {
        consumers.add(c);
    }

    @Override
    public void put(Graph e, long ts) {
        consumers.forEach(graphConsumer -> graphConsumer.notify(e, ts));
    }
}
