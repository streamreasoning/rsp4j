package it.polimi.yasper.core.spe.operators.s2r;

import it.polimi.yasper.core.spe.operators.s2r.execution.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.RegisteredStream;

public interface WindowOperator<E> {

    String iri();

    boolean named();

    WindowAssigner<E> apply(RegisteredStream<E> s);

}
