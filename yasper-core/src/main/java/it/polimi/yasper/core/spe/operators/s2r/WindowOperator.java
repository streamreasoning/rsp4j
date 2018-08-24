package it.polimi.yasper.core.spe.operators.s2r;

import it.polimi.yasper.core.spe.operators.s2r.execution.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.RegisteredStream;

public interface WindowOperator {

    String iri();

    boolean named();

    WindowAssigner apply(RegisteredStream s);

}
