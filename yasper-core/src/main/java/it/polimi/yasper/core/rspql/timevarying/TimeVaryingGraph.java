package it.polimi.yasper.core.rspql.timevarying;

import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.operators.s2r.execution.assigner.WindowAssigner;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;

@AllArgsConstructor
@RequiredArgsConstructor
public class TimeVaryingGraph implements TimeVarying {

    private final WindowAssigner<Graph> wa;
    private IRI name;
    private Graph graph;

    /**
     * The setTimestamp function merges the element
     * in the content into a single graph
     * and adds it to the current dataset.
     **/
    @Override
    public void materialize(long ts) {
        Content<Graph> content = wa.getContent(ts);
        graph = content.coalesce();
    }

    @Override
    public Object get() {
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
