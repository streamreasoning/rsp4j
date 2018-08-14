package it.polimi.yasper.core.quering;

import it.polimi.yasper.core.exceptions.UnsupportedContentFormatException;
import it.polimi.yasper.core.spe.content.*;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.utils.RDFUtils;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;

@AllArgsConstructor
@RequiredArgsConstructor
public class TimeVaryingGraph implements TimeVarying<Graph> {

    @NonNull
    private final WindowAssigner wa;
    private IRI name;

    /**
     * The setTimestamp function merges the element
     * in the content into a single graph
     * and adds it to the current dataset.
     **/
    @Override
    public Graph eval(long ts) {
        Content content = wa.getContent(ts);
        //TODO I leave the instance of that my give the idea of optimizations
        if (content instanceof ContentGraph) {
            return ((ContentGraph) content).coalese();
        } else if (content instanceof ContentTriple) {
            return ((ContentTriple) content).coalese();
        } else if (content instanceof ContentQuad) {
            return ((ContentQuad) content).coalese();
        } else if (content instanceof EmptyContent) {
            return RDFUtils.createGraph();
        } else {
            throw new UnsupportedContentFormatException();
        }
    }

    @Override
    public Graph asT() {
        return RDFUtils.createGraph();
    }

    @Override
    public boolean isNamed() {
        return name != null;
    }
}
