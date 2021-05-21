package org.streamreasoning.rsp4j.yasper.sds;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;

@RequiredArgsConstructor
public class TimeVaryingObject<E> implements TimeVarying<E> {

    private final StreamToRelationOp<?, E> op;
    private final IRI name;
    private E graph;

    /**
     * The setTimestamp function merges the element
     * in the content into a single graph
     * and adds it to the current dataset.
     **/
    @Override
    public void materialize(long ts) {
        graph = op.content(ts).coalesce();
    }

    @Override
    public E get() {
        return graph;
    }

    @Override
    public String iri() {
        return name.getIRIString();
    }


}
