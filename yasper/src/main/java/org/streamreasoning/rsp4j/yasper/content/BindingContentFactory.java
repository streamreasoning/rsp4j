package org.streamreasoning.rsp4j.yasper.content;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.secret.content.ContentFactory;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.BindingImpl;

public class BindingContentFactory implements ContentFactory<Graph, Binding> {

    @Override
    public Content<Graph, Binding> createEmpty() {
        return new EmptyContent<Graph, Binding>(new BindingImpl());
    }

    @Override
    public Content<Graph, Binding> create() {
        return new ContentBinding();
    }
}
