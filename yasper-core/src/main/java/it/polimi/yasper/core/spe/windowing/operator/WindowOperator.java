package it.polimi.yasper.core.spe.windowing.operator;

import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.RegisteredStream;
import it.polimi.yasper.core.Named;

public interface WindowOperator extends Named {

    WindowAssigner apply(RegisteredStream s);

}
