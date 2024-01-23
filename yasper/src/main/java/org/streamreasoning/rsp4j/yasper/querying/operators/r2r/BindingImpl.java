package org.streamreasoning.rsp4j.yasper.querying.operators.r2r;

import org.apache.commons.rdf.api.RDFTerm;
import org.streamreasoning.rsp4j.api.operators.r2r.Var;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class BindingImpl implements Binding {

    Map<Var, RDFTerm> internal;

    public BindingImpl() {
        internal= new HashMap<>();
    }
    public BindingImpl(int size) {
        internal= new HashMap<>(size);
    }
    public BindingImpl(Map<Var, RDFTerm> possibleBinding) {
        this.internal = new HashMap<>(possibleBinding);
    }

    @Override
    public Set<Var> variables() {
        return internal.keySet();
    }

    @Override
    public RDFTerm value(Var v) {
        return internal.get(v);
    }
    @Override
    public RDFTerm value(String v) {
        return internal.get(new VarImpl(v));
    }

    @Override
    public boolean compatible(Binding b) {
        return this.variables().stream().anyMatch(var -> b.variables().contains(var));
    }

    @Override
    public boolean add(Var s, RDFTerm bind) {

        if (internal.containsKey(s)) {
            return bind.equals(internal.get(s));
        }

        return internal.put(s, bind) == null;
    }

    @Override
    public int size() {
        return internal.size();
    }

    @Override
    public String toString() {
       return internal.toString().replace("=", " -> ");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BindingImpl binding = (BindingImpl) o;
        return Objects.equals(internal, binding.internal);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return copy();
    }


    @Override
    public int hashCode() {
        return Objects.hash(internal);
    }

    public Map<Var, RDFTerm> getInternals() {
        return internal;
    }
}
