package it.polimi.yasper.core.runtime;

import it.polimi.yasper.core.rspql.SDS;
import it.polimi.yasper.core.spe.content.ContentGraph;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class SDSImpl implements SDS {

    private Set<Graph> dg;
    private Map<IRI, Graph> ng;

    private Set<DefaultStreamView> ds;
    private Map<IRI, NamedStreamView> ns;

    List<Graph> getallGraph() {
        return ns.values().stream()
                .map(ns -> ((ContentGraph) ns.getContent()).coaleseGraphs())
                .collect(Collectors.toList());
    }

    @Override
    public void beforeEval() {

    }

    @Override
    public void afterEval() {

    }

}
