package it.polimi.jasper.engine.querying.execution.observer;

import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.resultset.ResultSetMem;

import java.util.List;

/**
 * Created by riccardo on 08/07/2017.
 */
public class TimeVaryingResultSetMem extends ResultSetMem {


    public TimeVaryingResultSetMem(List<Binding> rows, List<String> varNames) {
        this.rows = rows;
        this.varNames = varNames;
    }

    public void addBinding(Binding b) {
        rows.add(b);
    }

    public void addVarName(String b) {
        varNames.add(b);
    }

}
