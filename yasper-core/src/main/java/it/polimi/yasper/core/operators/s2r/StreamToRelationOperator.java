package it.polimi.yasper.core.operators.s2r;

import it.polimi.yasper.core.sds.timevarying.TimeVarying;
import it.polimi.yasper.core.stream.data.WebDataStream;

/*
 * The assigner connects the stream data to the time-varying data structure representing
 * the finite querable view.
 * I represents the variable type of the input, e.g., RDF TRIPLE, RDF GRAPHS OR TUPLE
 * O represents the variable type of the maintained status, e.g., BAG of RDF Triple, RDF Graph (set) or RELATION
 *
 *
 * */
public interface StreamToRelationOperator<I, O> {

    String iri();

    default boolean named() {
        return iri() != null;
    }

    TimeVarying<O> apply(WebDataStream<I> s);

}
