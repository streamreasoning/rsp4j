package it.polimi.yasper.core.quering;

import it.polimi.yasper.core.exceptions.UnsupportedContentFormatException;
import it.polimi.yasper.core.quering.TimeVarying;
import it.polimi.yasper.core.spe.content.*;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;

@RequiredArgsConstructor
public class TimeVaryingGraph implements TimeVarying<Graph> {

    @NonNull
    private final IRI name;
    @NonNull
    private final RDF rdf;
    @NonNull
    private final WindowAssigner wa;

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
            return rdf.createGraph();
        } else {
            throw new UnsupportedContentFormatException();
        }
    }

    @Override
    public Graph asT() {
        return rdf.createGraph();
    }
}
