package org.streamreasoning.rsp4j.api.operators.s2r;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.apache.commons.rdf.api.IRI;


public interface StreamToRelationOperatorFactory<I, O> {

    StreamToRelationOp<I, O> apply(WebDataStream<I> s, IRI iri);

}
