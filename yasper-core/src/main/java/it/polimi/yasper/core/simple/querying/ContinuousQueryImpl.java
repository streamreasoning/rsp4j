package it.polimi.yasper.core.simple.querying;

import it.polimi.yasper.core.enums.StreamOperator;
import it.polimi.yasper.core.rspql.ContinuousQuery;
import it.polimi.yasper.core.rspql.SDSBuilder;
import it.polimi.yasper.core.rspql.Stream;
import it.polimi.yasper.core.spe.stream.rdf.RDFStream;
import it.polimi.yasper.core.spe.windowing.WindowOperator;
import it.polimi.yasper.core.spe.windowing.assigner.TimeWindowOperator;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.simple.SimpleRDF;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ContinuousQueryImpl implements ContinuousQuery {

    private RDF rdf = new SimpleRDF();

    @Override
    public String getID() {
        return "q1";
    }

    @Override
    public StreamOperator getR2S() {
        return null;
    }

    @Override
    public boolean isRecursive() {
        return false;
    }


    public Map<WindowOperator, Stream> getWindowMap() {
        Map<WindowOperator, Stream> windows = new HashMap<>();
        WindowOperator w = new TimeWindowOperator(rdf, rdf.createIRI("w1"), 5000, 5000, 0);
        Stream s = new RDFStream("painter");
        windows.put(w, s);
        return windows;
    }

    public Map<IRI, Graph> getNamedGraphs() {
        return null;
    }

    public Set<Graph> getGraphs() {
        return null;
    }

    @Override
    public void accept(SDSBuilder v) {
        v.visit(this);
    }
}
