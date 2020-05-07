package it.polimi.sr.rsp.csparql.engine;

import it.polimi.jasper.engine.EsperRSPEngine;
import it.polimi.jasper.engine.esper.EsperStreamRegistrationService;
import it.polimi.sr.rsp.csparql.syntax.QueryFactory;
import it.polimi.sr.rsp.csparql.syntax.RSPQLJenaQuery;
import it.polimi.yasper.core.engine.config.EngineConfiguration;
import it.polimi.yasper.core.engine.features.QueryObserverRegistrationFeature;
import it.polimi.yasper.core.engine.features.QueryRegistrationFeature;
import it.polimi.yasper.core.engine.features.QueryStringRegistrationFeature;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.enums.ReportGrain;
import it.polimi.yasper.core.exceptions.UnregisteredQueryExeception;
import it.polimi.yasper.core.format.QueryResultFormatter;
import it.polimi.yasper.core.querying.ContinuousQuery;
import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.sds.SDSConfiguration;
import it.polimi.yasper.core.secret.report.ReportImpl;
import it.polimi.yasper.core.secret.report.strategies.ReportingStrategy;
import lombok.extern.log4j.Log4j;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.jena.graph.Graph;
import org.apache.jena.sparql.engine.binding.Binding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j
public class CSPARQLEngine extends EsperRSPEngine<Graph> implements QueryObserverRegistrationFeature, QueryRegistrationFeature<RSPQLJenaQuery>, QueryStringRegistrationFeature {

    private Maintenance maintenance;

    public CSPARQLEngine(long t0, EngineConfiguration configuration) {
        super(t0, configuration);
        this.reportGrain = ReportGrain.SINGLE;
        this.maintenance = Maintenance.NAIVE;
        this.stream_registration_service = new EsperStreamRegistrationService<>(admin);
    }

    public void setReport(ReportingStrategy... sr) {
        this.report = new ReportImpl();
        Arrays.stream(sr).forEach(this.report::add);
    }

    @Override
    public ContinuousQueryExecution<Graph, Graph, Binding> register(RSPQLJenaQuery continuousQuery) {
        try {
            return register(continuousQuery, SDSConfiguration.getDefault());
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ContinuousQueryExecution<Graph, Graph, Binding> register(String s) {
        try {
            return register(s, SDSConfiguration.getDefault());
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ContinuousQueryExecution<Graph, Graph, Binding> register(String q, SDSConfiguration queryConfiguration) {
        log.info("Parsing Query [" + q + "]");
        try {
            return register(QueryFactory.parse(base_uri, q), queryConfiguration);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ContinuousQueryExecution<Graph, Graph, Binding> register(RSPQLJenaQuery q, SDSConfiguration c) {

        SDSManagerImpl builder = new SDSManagerImpl(
                this,
                q,
                this.time,
                this.base_uri,
                this.report,
                this.responseFormat,
                this.enabled_recursion,
                this.usingEventTime,
                this.reportGrain,
                this.tick,
                this.stream_registration_service,
                this.maintenance,
                this.tbox,
                this.entailment);


        return save(q, builder.build(), builder.getContinuousQueryExecution());
    }

    @Override
    public void register(ContinuousQuery q, QueryResultFormatter f) {
        String qID = q.getID();
        log.info("Registering Observer [" + f.getClass() + "] to Query [" + qID + "]");
        if (!registeredQueries.containsKey(qID))
            throw new UnregisteredQueryExeception(qID);
        else {
            ContinuousQueryExecution ceq = queryExecutions.get(qID);
            ceq.add(f);
            createQueryObserver(f, qID);
        }
    }

    private void createQueryObserver(QueryResultFormatter o, String qID) {
        if (queryObservers.containsKey(qID)) {
            List<QueryResultFormatter> l = queryObservers.get(qID);
            if (l != null) {
                l.add(o);
            } else {
                l = new ArrayList<>();
                l.add(o);
                queryObservers.put(qID, l);
            }
        }
    }
}
