package it.polimi.runtime;

import it.polimi.rspql.SDSBuilder;
import it.polimi.rspql.Stream;
import it.polimi.rspql.ContinuousQuery;
import it.polimi.spe.stream.rdf.RDFStream;
import it.polimi.spe.windowing.WindowOperator;
import it.polimi.spe.windowing.assigner.TimeWindowOperator;
import it.polimi.yasper.core.enums.StreamOperator;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ContinuousQueryImpl implements ContinuousQuery {

    @Override
    public String getID() {
        return null;
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
        WindowOperator w = new TimeWindowOperator("w1", 5000, 5000, 0);
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
