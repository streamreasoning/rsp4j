package it.polimi.yasper.core.rspql.sds;

import it.polimi.yasper.core.rspql.timevarying.TimeVarying;
import org.apache.commons.rdf.api.IRI;

/**
 * Created by riccardo on 01/07/2017.
 */
public interface SDS {

    void add(IRI iri, TimeVarying tvg);

    void add(TimeVarying tvg);

    void materialize(long ts);
}