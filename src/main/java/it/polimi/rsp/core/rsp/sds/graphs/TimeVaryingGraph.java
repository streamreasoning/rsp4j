package it.polimi.rsp.core.rsp.sds.graphs;

import it.polimi.rsp.core.rsp.sds.windows.WindowModel;
import org.apache.jena.graph.Graph;

/**
 * Created by riccardo on 05/07/2017.
 */
public interface TimeVaryingGraph extends Graph {

    public long getTimestamp();

    public void setTimestamp(long ts);

    public WindowModel getWindowOperator();

    public void setWindowOperator(WindowModel w);

}
