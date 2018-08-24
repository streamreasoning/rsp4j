package it.polimi.yasper.core.rspql.sds;

import it.polimi.yasper.core.rspql.timevarying.TimeVarying;
import org.apache.commons.rdf.api.IRI;

/**
 * Created by riccardo on 01/07/2017.
 */
public interface SDS<E> {

    void add(IRI iri, TimeVarying<E> tvg);

    void add(TimeVarying<E> tvg);

    void materialize(long ts);
}
