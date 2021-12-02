package org.streamreasoning.rsp4j.reasoning.datalog;

import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarOrTerm;

import java.util.Objects;

public class ReasonerTriple {

    private final VarOrTerm o;
    private final VarOrTerm p;
    private final VarOrTerm s;

    public ReasonerTriple(String s, String p, String o){
        this.s = VarOrTerm.create(s) ;
        this.o = VarOrTerm.create(o);
        this.p = VarOrTerm.create(p);
    }

    public ReasonerTriple(VarOrTerm s, VarOrTerm p, VarOrTerm o) {
        this.s = s ;
        this.o = o;
        this.p = p;
    }

    public VarOrTerm getObject() {
        return o;
    }

    public VarOrTerm getProperty() {
        return p;
    }

    public VarOrTerm getSubject() {
        return s;
    }












    @Override
    public boolean equals(Object o1) {
        if (this == o1) return true;
        if (o1 == null || getClass() != o1.getClass()) return false;
        ReasonerTriple triple = (ReasonerTriple) o1;
        return Objects.equals(o, triple.o) &&
                Objects.equals(p, triple.p) &&
                Objects.equals(s, triple.s);
    }

    @Override
    public int hashCode() {
        return Objects.hash(o, p, s);
    }

    @Override
    public String toString() {
        return "Triple{" +
                "s=" + s +
                ", p=" + p +
                ", o=" + o +
                '}';
    }

}
