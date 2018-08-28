package it.polimi.yasper.core.spe.operators.s2r;

import it.polimi.yasper.core.spe.operators.s2r.execution.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.RegisteredStream;

/*
* I represents the variable type of the input, e.g., RDF TRIPLE, RDF GRAPHS OR TUPLE
* O represents the variable type of the maintained status, e.g., BAG of RDF Triple, RDF Graph (set) or RELATION
* */
public interface WindowOperator<I, O> {

    String iri();

    boolean named();

    WindowAssigner<I, O> apply(RegisteredStream<I> s);

}
