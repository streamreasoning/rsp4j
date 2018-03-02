package it.polimi.rspql;

/**
 * Created by riccardo on 05/09/2017.
 */
public interface SDSBuilder<Q extends ContinuousQuery> {

    void visit(Q query);

    SDS getSDS();

    Q getContinuousQuery();

    ContinuousQueryExecution getContinuousQueryExecution();

}
