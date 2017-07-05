package it.polimi.rsp.baselines.rsp.sds;

import it.polimi.rsp.baselines.enums.Maintenance;
import it.polimi.rsp.baselines.rsp.query.execution.ContinuousQueryExecution;
import it.polimi.rsp.baselines.rsp.sds.windows.DefaultWindow;
import it.polimi.rsp.baselines.rsp.sds.windows.NamedWindow;
import it.polimi.rsp.baselines.rsp.sds.windows.WindowModel;
import org.apache.jena.graph.Graph;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;

/**
 * Created by riccardo on 01/07/2017.
 */
public interface SDS extends Dataset {


    public boolean addDefaultWindowStream(String statementName, String uri);

    public boolean addNamedWindowStream(String w, String s, WindowModel m);

    void addQueryExecutor(ContinuousQueryExecution o);

    Maintenance getMaintenanceType();

    void addTimeVaryingGraph(DefaultWindow defTVG);

    void addNamedTimeVaryingGraph(String statementName, String window_uri, String window, NamedWindow tvg);

}
