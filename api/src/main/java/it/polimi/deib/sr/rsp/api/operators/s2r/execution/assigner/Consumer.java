package it.polimi.deib.sr.rsp.api.operators.s2r.execution.assigner;

public interface Consumer<I> {

    void notify(I arg, long ts);
}
