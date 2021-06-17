package org.streamreasoning.rsp4j.yasper.querying.operators.r2r;

import org.apache.commons.rdf.api.RDFTerm;

import java.util.Set;

public interface Binding {

    Set<Var> variables();

    RDFTerm value(Var v);

    boolean compatible(Binding b);

    default Binding union(Binding b) {
        Set<Var> res = this.variables();
        Binding r = new BindingImpl();
        res.forEach(v -> r.add(v, this.value(v)));
        return r;
    }

    default Binding difference(Binding b) {
        Set<Var> res = this.variables();
        res.removeAll(b.variables());
        Binding r = new BindingImpl();
        res.forEach(v -> r.add(v, this.value(v)));
        return r;
    }

    default Binding intersection(Binding b) {
        Set<Var> res = this.variables();
        res.retainAll(b.variables());
        Binding r = new BindingImpl();
        res.forEach(v -> r.add(v, this.value(v)));
        return r;
    }

    boolean add(Var s, RDFTerm bind);

    int size();
}
