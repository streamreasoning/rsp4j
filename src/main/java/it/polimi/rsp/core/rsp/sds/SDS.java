package it.polimi.rsp.core.rsp.sds;

import it.polimi.rsp.core.enums.Maintenance;
import it.polimi.rsp.core.rsp.query.execution.ContinuousQueryExecution;
import it.polimi.rsp.core.rsp.sds.windows.DefaultWindow;
import it.polimi.rsp.core.rsp.sds.windows.NamedWindow;
import it.polimi.rsp.core.rsp.sds.windows.WindowModel;
import it.polimi.sr.rsp.RSPQuery;
import org.apache.jena.query.Dataset;

/**
 * Created by riccardo on 01/07/2017.
 */
public interface SDS extends Dataset {


    public boolean addDefaultWindowStream(String statementName, String uri);

    public boolean addNamedWindowStream(String w, String s, WindowModel m);

    Maintenance getMaintenanceType();

    void addTimeVaryingGraph(DefaultWindow defTVG);

    void addNamedTimeVaryingGraph(String statementName, String window_uri, String window, NamedWindow tvg);

    public void addQueryExecutor(RSPQuery bq, ContinuousQueryExecution o);
}
