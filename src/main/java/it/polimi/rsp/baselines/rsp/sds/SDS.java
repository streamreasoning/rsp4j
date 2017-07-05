package it.polimi.rsp.baselines.rsp.sds;

import it.polimi.rsp.baselines.enums.Maintenance;
import it.polimi.rsp.baselines.rsp.query.execution.ContinuousQueryExecution;
import it.polimi.rsp.baselines.rsp.sds.graphs.DefaultTVG;
import it.polimi.rsp.baselines.rsp.sds.graphs.NamedTVG;
import org.apache.jena.graph.Graph;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;

/**
 * Created by riccardo on 01/07/2017.
 */
public interface SDS extends Dataset {
    public boolean addStatementName(String c);

    public boolean addDefaultWindowStream(String uri);

    public boolean addNamedWindowStream(String w, String s, Model m);

    public Model bind(Graph g);

    void addQueryExecutor(ContinuousQueryExecution o);

    void removeQueryObserver(ContinuousQueryExecution o);


    Maintenance getMaintenanceType();

    void addTimeVaryingGraph(DefaultTVG defTVG);

    void addNamedTimeVaryingGraph(String window, NamedTVG tvg);

    void bindTbox(Model tbox);

    Model rebind(Model def);
}
