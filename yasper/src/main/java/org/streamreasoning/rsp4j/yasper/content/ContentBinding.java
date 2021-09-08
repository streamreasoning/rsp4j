package org.streamreasoning.rsp4j.yasper.content;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;
import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.BindingImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Var;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarOrTerm;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ContentBinding implements Content<Graph, Binding> {

    private Set<Binding> elements = new HashSet<>();
    private long last_timestamp_changed;
    private VarOrTerm s, p, o;
    private Time time;

    public ContentBinding(Time time, VarOrTerm s, VarOrTerm p, VarOrTerm o) {
        this.s = s;
        this.p = p;
        this.o = o;
        this.time = time;
    }


    @Override
    public int size() {
        return 0;
    }

    @Override
    public void add(Graph e) {
        e.stream().map(t -> {
            Binding b = new BindingImpl();

            boolean sb = this.s.bind(b, t.getSubject());

            boolean ob = o.bind(b, t.getObject());

            boolean pb = p.bind(b, t.getPredicate());

            if (!sb || !ob || !pb) {
                return null;
            }

            return b;
        }).filter(Objects::nonNull).forEach(elements::add);
    }

    @Override
    public Long getTimeStampLastUpdate() {
        return last_timestamp_changed;
    }

    @Override
    public Binding coalesce() {
        return new Binding() {

            @Override
            public String toString() {
                return elements.toString();
            }

            @Override
            public Set<Var> variables() {
                return elements.stream().flatMap(binding -> binding.variables().stream()).collect(Collectors.toSet());
            }

            @Override
            public RDFTerm value(Var v) {
                return elements.stream().filter(binding -> binding.value(v) != null).findFirst().get().value(v);
            }

            @Override
            public boolean compatible(Binding b) {
                return false;
            }

            @Override
            public boolean add(Var s, RDFTerm bind) {
                return false;
            }

            @Override
            public int size() {
                return elements.stream().map(Binding::size).reduce(Integer::sum).orElse(0);
            }
        };
    }

    @Override
    public String toString() {
        return elements.toString();
    }
}
