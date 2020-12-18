package it.polimi.deib.sr.rsp.yasper.sds;

import it.polimi.deib.sr.rsp.api.operators.s2r.execution.assigner.StreamToRelationOp;
import it.polimi.deib.sr.rsp.api.sds.timevarying.TimeVarying;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;

@AllArgsConstructor
@RequiredArgsConstructor
public class TimeVaryingGraph implements TimeVarying<Graph> {

    private final StreamToRelationOp<Graph, Graph> wa;
    private IRI name;
    private Graph graph;

    /**
     * The setTimestamp function merges the element
     * in the content into a single graph
     * and adds it to the current dataset.
     **/
    @Override
    public void materialize(long ts) {
        graph = wa.getContent(ts).coalesce();
    }

    @Override
    public Graph get() {
        return graph;
    }

    @Override
    public String iri() {
        return name.getIRIString();
    }

    @Override
    public boolean named() {
        return name != null;
    }

}
