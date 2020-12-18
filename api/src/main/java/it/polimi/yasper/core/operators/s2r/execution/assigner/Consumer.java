package it.polimi.yasper.core.operators.s2r.execution.assigner;

public interface Consumer<I> {

    void notify(I arg, long ts);
}
