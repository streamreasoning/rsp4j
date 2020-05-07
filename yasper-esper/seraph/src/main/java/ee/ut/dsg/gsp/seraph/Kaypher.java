package ee.ut.dsg.gsp.seraph;


import it.polimi.jasper.engine.EsperRSPEngine;
import it.polimi.jasper.engine.esper.EsperStreamRegistrationService;
import it.polimi.yasper.core.engine.config.EngineConfiguration;
import it.polimi.yasper.core.engine.features.QueryObserverRegistrationFeature;
import it.polimi.yasper.core.engine.features.QueryRegistrationFeature;
import it.polimi.yasper.core.engine.features.QueryStringRegistrationFeature;
import it.polimi.yasper.core.format.QueryResultFormatter;
import it.polimi.yasper.core.querying.ContinuousQuery;
import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.sds.SDSConfiguration;
import ee.ut.dsg.gsp.seraph.poc.Seraph;

public class Kaypher extends EsperRSPEngine<PGraph> implements QueryObserverRegistrationFeature, QueryRegistrationFeature<Seraph>, QueryStringRegistrationFeature {

    public Kaypher(long t0, EngineConfiguration configuration) {
        super(t0, configuration);
        stream_registration_service = new EsperStreamRegistrationService<PGraph>(admin);
    }

    @Override
    public void register(ContinuousQuery q, QueryResultFormatter f) {

    }

    @Override
    public ContinuousQueryExecution register(Seraph seraph) {
        return null;
    }

    @Override
    public ContinuousQueryExecution register(Seraph seraph, SDSConfiguration sdsConfiguration) {

        return null;
    }

    @Override
    public ContinuousQueryExecution register(String s) {
        return null;
    }

    @Override
    public ContinuousQueryExecution register(String s, SDSConfiguration sdsConfiguration) {
        return null;
    }
}