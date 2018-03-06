package it.polimi.jasper.engine.spe.content;

import it.polimi.yasper.simple.windowing.TimeVarying;
import org.apache.jena.graph.Graph;
import org.apache.jena.mem.GraphMem;

public class TimeVaryingJenaGraph extends GraphMem implements TimeVarying<Graph> {

    @Override
    public Graph eval(long ts) {
        return this;
    }

    @Override
    public Graph asT() {
        return this;
    }


    @Override
    public String toString() {
        return "TimeVaryingJenaGraph@" + hashCode();
    }
}
