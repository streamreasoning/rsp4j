package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.rdf.api.IRI;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;

import java.util.Collection;

@AllArgsConstructor
@RequiredArgsConstructor
public class TimeVaryingCollection<I extends EventBean<V>,V> implements TimeVarying<Collection<I>> {

    private CSPARQLStreamToRelationOpSingle<I,V> op;
    private IRI name;
    private Collection<I> collection;

    @Override
    public void materialize(long ts) {
        collection = this.op.content(ts).coalesce();
    }

    @Override
    public Collection<I> get() {
        return collection;
    }

    @Override
    public String iri() {
        return name.getIRIString();
    }
}
