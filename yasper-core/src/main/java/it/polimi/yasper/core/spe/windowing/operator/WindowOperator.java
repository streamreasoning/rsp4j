package it.polimi.yasper.core.spe.windowing.operator;

import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.stream.rdf.Named;

import javax.naming.Name;

public interface WindowOperator extends Named {

    String getName();

    WindowAssigner apply(Stream s);

}
