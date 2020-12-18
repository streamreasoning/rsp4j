package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.querying.ContinuousQuery;
import it.polimi.yasper.core.sds.SDSConfiguration;

public interface QueryRegistrationFeature<Q extends ContinuousQuery> {

    ContinuousQueryExecution register(Q q);

    ContinuousQueryExecution register(Q q, SDSConfiguration c);

}
