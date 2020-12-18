package it.polimi.deib.sr.rsp.api.engine.features;

import it.polimi.deib.sr.rsp.api.querying.ContinuousQueryExecution;
import it.polimi.deib.sr.rsp.api.querying.ContinuousQuery;
import it.polimi.deib.sr.rsp.api.sds.SDSConfiguration;

public interface QueryRegistrationFeature<Q extends ContinuousQuery> {

    ContinuousQueryExecution register(Q q);

    ContinuousQueryExecution register(Q q, SDSConfiguration c);

}
