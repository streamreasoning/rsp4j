package simple.test.examples;

import it.polimi.yasper.core.engine.features.QueryRegistrationFeature;
import it.polimi.yasper.core.engine.features.StreamRegistrationFeature;
import it.polimi.yasper.core.spe.operators.r2r.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.spe.operators.r2r.ContinuousQuery;
import it.polimi.yasper.core.rspql.sds.SDS;
import it.polimi.yasper.core.rspql.sds.SDSManager;
import it.polimi.yasper.core.spe.operators.r2s.result.QueryResultFormatter;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.report.ReportImpl;
import it.polimi.yasper.core.spe.report.strategies.OnWindowClose;
import it.polimi.yasper.core.spe.tick.Tick;
import it.polimi.yasper.core.stream.RegisteredStream;
import it.polimi.yasper.core.stream.rdf.RDFStream;
import it.polimi.yasper.core.stream.rdf.RegisteredRDFStream;
import it.polimi.yasper.core.engine.EngineConfiguration;
import it.polimi.yasper.core.spe.operators.r2r.QueryConfiguration;
import org.apache.commons.rdf.api.Graph;
import simple.sds.SDSManagerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CSPARQLImpl implements QueryRegistrationFeature, StreamRegistrationFeature<RegisteredRDFStream, RDFStream> {

    private final long t0;
    private Report report;
    private Tick tick;
    protected EngineConfiguration rsp_config;

    protected Map<String, SDS> assignedSDS;
    protected Map<String, ContinuousQueryExecution> queryExecutions;
    protected Map<String, ContinuousQuery> registeredQueries;
    protected Map<String, List<QueryResultFormatter>> queryObservers;
    protected Map<String, RegisteredStream<Graph>> registeredStreams;
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
    public ContinuousQueryExecution register(ContinuousQuery q, QueryConfiguration c) {
        SDSManager builder = new SDSManagerImpl(q, c, registeredStreams, report, report_grain, tick, t0);
        SDS build = builder.build();
        return builder.getContinuousQueryExecution();
    }

    @Override
    public ContinuousQueryExecution register(ContinuousQuery q) {
        return register(q, null);
    }


}
