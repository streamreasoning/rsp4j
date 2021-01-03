package it.polimi.deib.sr.rsp.api.operators.s2r;

import it.polimi.deib.sr.rsp.api.sds.timevarying.TimeVarying;
import it.polimi.deib.sr.rsp.api.stream.data.WebDataStream;
import org.apache.commons.rdf.api.IRI;


public interface StreamToRelationOperatorFactory<I, O> {

    TimeVarying<O> apply(WebDataStream<I> s, IRI iri);

}
