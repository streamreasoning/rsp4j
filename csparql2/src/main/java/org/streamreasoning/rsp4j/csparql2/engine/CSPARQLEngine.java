package org.streamreasoning.rsp4j.csparql2.engine;

import org.streamreasoning.rsp4j.csparql2.syntax.QueryFactory;
import org.streamreasoning.rsp4j.csparql2.syntax.RSPQLJenaQuery;
import org.streamreasoning.rsp4j.esper.engine.EsperRSPEngine;
import org.streamreasoning.rsp4j.esper.engine.esper.EsperStreamRegistrationService;

import lombok.extern.log4j.Log4j;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.jena.graph.Graph;
import org.apache.jena.sparql.engine.binding.Binding;
import org.streamreasoning.rsp4j.api.engine.config.EngineConfiguration;
import org.streamreasoning.rsp4j.api.engine.features.QueryObserverRegistrationFeature;
import org.streamreasoning.rsp4j.api.engine.features.QueryRegistrationFeature;
import org.streamreasoning.rsp4j.api.engine.features.QueryStringRegistrationFeature;
import org.streamreasoning.rsp4j.api.enums.Maintenance;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.exceptions.UnregisteredQueryExeception;
import org.streamreasoning.rsp4j.api.format.QueryResultFormatter;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDSConfiguration;
import org.streamreasoning.rsp4j.api.secret.report.ReportImpl;
import org.streamreasoning.rsp4j.api.secret.report.strategies.ReportingStrategy;

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
    public ContinuousQueryExecution<Graph, Graph, SolutionMapping<Binding>, SolutionMapping<Binding>> register(RSPQLJenaQuery continuousQuery) {
        try {
            return register(continuousQuery, SDSConfiguration.getDefault());
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ContinuousQueryExecution<Graph, Graph, SolutionMapping<Binding>, SolutionMapping<Binding>> register(String s) {
        try {
            return register(s, SDSConfiguration.getDefault());
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ContinuousQueryExecution<Graph, Graph, SolutionMapping<Binding>, SolutionMapping<Binding>> register(String q, SDSConfiguration queryConfiguration) {
        log.info("Parsing Query [" + q + "]");
        try {
            return register(QueryFactory.parse(base_uri, q), queryConfiguration);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public ContinuousQueryExecution<Graph, Graph, SolutionMapping<Binding>, SolutionMapping<Binding>> register(RSPQLJenaQuery q, SDSConfiguration c) {

        SDSManagerImpl builder = new SDSManagerImpl(
                this,
                q,
                this.time,
                this.report,
                this.responseFormat,
                this.enabled_recursion,
                this.usingEventTime,
                this.reportGrain,
                this.tick,
                this.stream_registration_service,
                this.maintenance,
                c.getTboxLocation(),
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

            //ceq.add(f);
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
