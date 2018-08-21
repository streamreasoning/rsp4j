package simple.test.examples;

import it.polimi.yasper.core.engine.RSPEngine;
import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.quering.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.quering.querying.ContinuousQuery;
import it.polimi.yasper.core.quering.rspql.sds.SDS;
import it.polimi.yasper.core.quering.rspql.sds.SDSBuilder;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.report.ReportImpl;
import it.polimi.yasper.core.spe.report.strategies.OnWindowClose;
import it.polimi.yasper.core.spe.scope.Tick;
import it.polimi.yasper.core.stream.RegisteredStream;
import it.polimi.yasper.core.stream.StreamElement;
import it.polimi.yasper.core.stream.rdf.RDFStream;
import it.polimi.yasper.core.stream.rdf.RegisteredRDFStream;
import it.polimi.yasper.core.utils.EngineConfiguration;
import it.polimi.yasper.core.utils.QueryConfiguration;
import org.apache.commons.rdf.api.Graph;
import simple.sds.SDSBuilderImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CSPARQLImpl implements RSPEngine<RDFStream, RegisteredRDFStream, Graph> {

    private final long t0;
    private Report report;
    private Tick tick;
    protected EngineConfiguration rsp_config;

    protected Map<String, SDS> assignedSDS;
    protected Map<String, ContinuousQueryExecution> queryExecutions;
    protected Map<String, ContinuousQuery> registeredQueries;
    protected Map<String, List<QueryResponseFormatter>> queryObservers;
    protected Map<String, RegisteredStream> registeredStreams;
    private ReportGrain report_grain;

    public CSPARQLImpl(long t0, EngineConfiguration rsp_config) {
        this.rsp_config = rsp_config;
        this.assignedSDS = new HashMap<>();
        this.registeredQueries = new HashMap<>();
        this.registeredStreams = new HashMap<>();
        this.queryObservers = new HashMap<>();
        this.queryExecutions = new HashMap<>();
        this.t0 = t0;
        this.report = new ReportImpl();
        this.report.add(new OnWindowClose());
        this.report_grain = ReportGrain.SINGLE;
        this.tick = Tick.TIME_DRIVEN;
    }

    @Override
    public RegisteredRDFStream register(RDFStream s) {
        RegisteredRDFStream rs = new RegisteredRDFStream(s.getURI());
        registeredStreams.put(s.getURI(), rs);
        return rs;
    }

    @Override
    public void unregister(RegisteredRDFStream s) {
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
        report_grain = ReportGrain.SINGLE;
        SDSBuilder builder = new SDSBuilderImpl(registeredStreams, report, report_grain, tick, t0);
        builder.visit(q);
        return builder.getContinuousQueryExecution();
    }

    @Override
    public void unregister(ContinuousQuery qId) {

    }

    @Override
    public void register(ContinuousQuery q, QueryResponseFormatter o) {

    }

    @Override
    public void register(ContinuousQueryExecution cqe, QueryResponseFormatter o) {

    }

    @Override
    public boolean process(Graph var1) {
        return false;
    }

    @Override
    public void removeQueryResponseFormatter(QueryResponseFormatter o) {

    }
}
