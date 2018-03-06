package it.polimi.jasper.engine.reasoning;

import it.polimi.yasper.simple.windowing.TimeVarying;
import org.apache.jena.graph.Graph;
import org.apache.jena.reasoner.InfGraph;

public class TimeVaryingInfGraphImpl implements TimeVarying<InfGraph> {

    private final InfGraph infgraph;
    private final TimeVarying<Graph> data;

    public TimeVaryingInfGraphImpl(TimeVarying<Graph> data, InfGraph bind) {
        this.data=data;
        this.infgraph=bind;
    }

    @Override
    public InfGraph eval(long ts) {
        return null;
    }

    @Override
    public InfGraph asT() {
        return infgraph;
    }
}
