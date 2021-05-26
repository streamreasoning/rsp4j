package org.streamreasoning.rsp4j.yasper.content;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.secret.content.ContentFactory;

public class GraphContentFactory implements ContentFactory<Graph, Graph> {

    @Override
    public Content<Graph, Graph> createEmpty() {
        return new EmptyGraphContent();
    }

    @Override
    public Content<Graph, Graph> create() {
        return new ContentGraph();
    }
}
