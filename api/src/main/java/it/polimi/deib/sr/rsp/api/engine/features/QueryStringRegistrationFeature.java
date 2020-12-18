package it.polimi.deib.sr.rsp.api.engine.features;

import it.polimi.deib.sr.rsp.api.querying.ContinuousQueryExecution;
import it.polimi.deib.sr.rsp.api.querying.ContinuousQuery;
import it.polimi.deib.sr.rsp.api.sds.SDSConfiguration;

public interface QueryStringRegistrationFeature<Q extends ContinuousQuery> {

    ContinuousQueryExecution register(String q);

    ContinuousQueryExecution register(String q, SDSConfiguration c);

}
