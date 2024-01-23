package org.streamreasoning.rsp4j.yasper.querying.formatter;

public interface Differentiable<R, O> {

    //TODO should implement the base case that r-r == 0;
    O difference(R r);

}