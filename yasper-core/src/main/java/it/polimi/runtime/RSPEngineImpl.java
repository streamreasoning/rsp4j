package it.polimi.runtime;

import it.polimi.rspql.RSPEngine;
import it.polimi.rspql.SDSBuilder;
import it.polimi.rspql.querying.ContinuousQuery;
import it.polimi.rspql.querying.ContinuousQueryExecution;
import it.polimi.rspql.querying.SDS;
import it.polimi.spe.report.Report;
import it.polimi.spe.scope.Tick;
import it.polimi.spe.stream.rdf.RDFStream;
import it.polimi.runtime.SDSBuilderImpl;
import it.polimi.yasper.core.query.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.stream.StreamItem;
import it.polimi.yasper.core.utils.EngineConfiguration;
import it.polimi.yasper.core.utils.QueryConfiguration;

import java.util.List;
import java.util.Map;

public class RSPEngineImpl implements RSPEngine<RDFStream> {

    private Report report;
    private Tick tick;
    protected EngineConfiguration rsp_config;

    protected Map<String, SDS> assignedSDS;
    protected Map<String, ContinuousQueryExecution> queryExecutions;
    protected Map<String, ContinuousQuery> registeredQueries;
    protected Map<String, List<QueryResponseFormatter>> queryObservers;
    protected Map<String, RDFStream> registeredStreams;

    @Override
    public RDFStream register(RDFStream s) {
        registeredStreams.put(s.getURI(), s);
        return s;
    }

    @Override
    public void unregister(RDFStream s) {
        //TODO stop all the queries that are using s
        // destroy all the window asssigners
        // remove s from registeredStreams
    }

    @Override
    public ContinuousQuery parseQuery(String input) {
        return null;
    }

    @Override
    public ContinuousQueryExecution register(ContinuousQuery q, QueryConfiguration c) {
        SDSBuilder builder = new SDSBuilderImpl(registeredStreams, rsp_config, c);
        q.accept(builder);
        return builder.getContinuousQueryExecution();
    }

    @Override
    public ContinuousQueryExecution register(String q, QueryConfiguration c) {
        return null;
    }

    @Override
    public void unregister(ContinuousQuery qId) {

    }

    @Override
    public void register(ContinuousQuery q, QueryResponseFormatter o) {

    }

    @Override
    public void unregister(ContinuousQuery q, QueryResponseFormatter o) {

    }

    @Override
    public void register(ContinuousQueryExecution cqe, QueryResponseFormatter o) {

    }

    @Override
    public void unregister(ContinuousQueryExecution cqe, QueryResponseFormatter o) {

    }

    @Override
    public boolean process(StreamItem var1) {
        return false;
    }
}
