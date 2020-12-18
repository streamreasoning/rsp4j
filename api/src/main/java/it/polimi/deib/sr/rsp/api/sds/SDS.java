package it.polimi.deib.sr.rsp.api.sds;

import it.polimi.deib.sr.rsp.api.sds.timevarying.TimeVarying;
import org.apache.commons.rdf.api.IRI;

import java.util.Collection;

/**
 * Created by riccardo on 01/07/2017.
 */
public interface SDS<E> {

    Collection<TimeVarying<E>> asTimeVaryingEs();

    void add(IRI iri, TimeVarying<E> tvg);

    void add(TimeVarying<E> tvg);

    default void materialize(long ts) {
        asTimeVaryingEs().forEach(eTimeVarying -> eTimeVarying.materialize(ts));
    }
}
