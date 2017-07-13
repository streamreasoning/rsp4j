package it.polimi.yasper.core;

import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.query.ContinuousQuery;
import it.polimi.yasper.core.timevarying.DefaultTVG;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.timevarying.NamedTVG;

/**
 * Created by riccardo on 01/07/2017.
 */
public interface SDS  {

    public boolean addDefaultWindowStream(String statementName, String uri);

    Maintenance getMaintenanceType();

    void addTimeVaryingGraph(DefaultTVG defTVG);

    void addNamedTimeVaryingGraph(String statementName, String window_uri, String window, NamedTVG tvg);

    public void addQueryExecutor(ContinuousQuery bq, ContinuousQueryExecution o);

}
