package it.polimi.yasper.core.spe.windowing.operator;

import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.RegisteredStream;

public interface WindowOperator {

    String iri();

    boolean named();

    WindowAssigner apply(RegisteredStream s);

}
