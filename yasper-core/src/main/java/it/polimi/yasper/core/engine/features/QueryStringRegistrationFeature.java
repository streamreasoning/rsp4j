package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.querying.ContinuousQuery;
import it.polimi.yasper.core.sds.SDSConfiguration;

public interface QueryStringRegistrationFeature<Q extends ContinuousQuery> {

    ContinuousQueryExecution register(String q);

    ContinuousQueryExecution register(String q, SDSConfiguration c);

}
