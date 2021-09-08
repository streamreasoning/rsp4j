package org.streamreasoning.rsp4j.yasper.content;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.secret.content.ContentFactory;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.BindingImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarOrTerm;

public class BindingContentFactory implements ContentFactory<Graph, Binding> {

    Time time;
    private VarOrTerm s, p, o;

    public BindingContentFactory(Time time, VarOrTerm s, VarOrTerm p, VarOrTerm o) {
        this.s = s;
        this.p = p;
        this.o = o;
        this.time = time;
    }

    public BindingContentFactory(Time time) {
        this.time = time;
    }

    public void setS(VarOrTerm s) {
        this.s = s;
    }

    public void setP(VarOrTerm p) {
        this.p = p;
    }

    public void setO(VarOrTerm o) {
        this.o = o;
    }

    @Override
    public Content<Graph, Binding> createEmpty() {
        return new EmptyContent<Graph, Binding>(new BindingImpl());
    }

    @Override
    public Content<Graph, Binding> create() {
        return new ContentBinding(time, s, p, o);
    }
}
