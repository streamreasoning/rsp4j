package it.polimi.yasper.core.quering.rspql.tvg;

import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.rdf.api.IRI;

@AllArgsConstructor
@RequiredArgsConstructor
public class TimeVaryingGraph implements TimeVarying {

    @NonNull
    private final WindowAssigner wa;
    private IRI name;

    /**
     * The setTimestamp function merges the element
     * in the content into a single graph
     * and adds it to the current dataset.
     **/
    @Override
    public <T> T materialize(long ts) {
        Content<T> content = wa.getContent(ts);
        return content.coalesce();
    }

    @Override
    public boolean isNamed() {
        return name != null;
    }
}
