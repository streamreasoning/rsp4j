package it.polimi.runtime;

import it.polimi.rspql.RSPEngine;
import it.polimi.rspql.SDSBuilder;
import it.polimi.rspql.Stream;
import it.polimi.rspql.ContinuousQuery;
import it.polimi.rspql.ContinuousQueryExecution;
import it.polimi.rspql.SDS;
import it.polimi.spe.report.Report;
import it.polimi.spe.report.ReportGrain;
import it.polimi.spe.report.ReportImpl;
import it.polimi.spe.report.strategies.OnWindowClose;
import it.polimi.spe.scope.Tick;
import it.polimi.yasper.core.query.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.stream.StreamItem;
import it.polimi.yasper.core.utils.EngineConfiguration;
import it.polimi.yasper.core.utils.QueryConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RSPEngineImpl implements RSPEngine<Stream> {

    private final long t0;
    private Report report;
    private Tick tick;
    protected EngineConfiguration rsp_config;

    protected Map<String, SDS> assignedSDS;
    protected Map<String, ContinuousQueryExecution> queryExecutions;
    protected Map<String, ContinuousQuery> registeredQueries;
    protected Map<String, List<QueryResponseFormatter>> queryObservers;
    protected Map<String, Stream> registeredStreams;

    public RSPEngineImpl(long t0, EngineConfiguration rsp_config) {
        this.rsp_config = rsp_config;
        this.assignedSDS = new HashMap<>();
        this.registeredQueries = new HashMap<>();
        this.registeredStreams = new HashMap<>();
        this.queryObservers = new HashMap<>();
        this.queryExecutions = new HashMap<>();
        this.t0 = t0;
        this.report = new ReportImpl();
        this.report.add(new OnWindowClose());
    }

    @Override
    public Stream register(Stream s) {
        registeredStreams.put(s.getURI(), s);
        return s;
    }

    @Override
    public void unregister(Stream s) {
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
        SDSBuilder builder = new SDSBuilderImpl(registeredStreams, rsp_config, c, report, ReportGrain.SINGLE, Tick.TIME_DRIVEN);
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
