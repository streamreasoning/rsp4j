package it.polimi.yasper.core.spe.windowing;

import it.polimi.yasper.core.rspql.Stream;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;

public interface WindowOperator {

    String getName();

    boolean isNamed();

    WindowAssigner apply(Stream s);

}
