package org.streamreasoning.rsp4j.yasper.content;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.secret.content.ContentFactory;
import org.streamreasoning.rsp4j.api.secret.time.Time;

public class GraphContentFactory implements ContentFactory<Graph, Graph> {

    Time time;

    public GraphContentFactory(Time time) {
        this.time = time;
    }


    @Override
    public Content<Graph, Graph> createEmpty() {
        return new EmptyContent(RDFUtils.createGraph());
    }

    @Override
    public Content<Graph, Graph> create() {
        return new ContentGraph(time);
    }
}
