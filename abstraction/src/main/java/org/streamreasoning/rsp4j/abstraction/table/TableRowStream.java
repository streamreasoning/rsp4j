package org.streamreasoning.rsp4j.abstraction.table;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;

import java.util.ArrayList;
import java.util.List;

public class TableRowStream implements WebDataStream<TableRow> {
    protected String stream_uri;
    protected List<Consumer<TableRow>> consumers = new ArrayList<>();


    public TableRowStream(String stream_uri) {
        this.stream_uri = stream_uri;
    }


    @Override
    public void addConsumer(Consumer<TableRow> c) {
        consumers.add(c);
    }

    @Override
    public void put(TableRow e, long ts) {
        consumers.forEach(graphConsumer -> graphConsumer.notify(e, ts));
    }

    @Override
    public String uri() {
        return stream_uri;
    }
}
