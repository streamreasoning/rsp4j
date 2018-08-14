package it.polimi.yasper.core.spe.windowing.operator;

import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.Stream;

public interface WindowOperator {

    String getName();

    boolean isNamed();

    WindowAssigner apply(Stream s);

}
