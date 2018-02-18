package it.polimi.runtime;

import it.polimi.rspql.SDSBuilder;
import it.polimi.rspql.Stream;
import it.polimi.rspql.querying.ContinuousQuery;
import it.polimi.spe.windowing.WindowOperator;
import it.polimi.yasper.core.enums.StreamOperator;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;

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

    @Override
    public Set<WindowOperator> getWindowsSet() {
        return null;
    }

    @Override
    public Set<WindowOperator> getNamedWindowsSet() {
        return null;
    }

    @Override
    public Map<WindowOperator, Stream> getWindowMap() {
        return null;
    }

    public Map<IRI, Graph> getNamedGraphs() {
        return null;
    }

    public Set<Graph> getGraphs() {
        return null;
    }

    @Override
    public Set<Stream> getStreamSet() {
        return null;
    }

    @Override
    public void accept(SDSBuilder v) {
        v.visit(this);
    }
}
