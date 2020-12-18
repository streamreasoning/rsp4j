package it.polimi.deib.rsp.test.examples;

import it.polimi.yasper.core.engine.config.EngineConfiguration;
import it.polimi.yasper.core.engine.features.QueryRegistrationFeature;
import it.polimi.yasper.core.engine.features.StreamRegistrationFeature;
import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.sds.SDSManager;
import it.polimi.yasper.core.querying.ContinuousQuery;
import it.polimi.yasper.core.sds.SDSConfiguration;
import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.format.QueryResultFormatter;
import it.polimi.yasper.core.secret.report.Report;
import it.polimi.yasper.core.enums.ReportGrain;
import it.polimi.yasper.core.secret.report.ReportImpl;
import it.polimi.yasper.core.secret.report.strategies.OnWindowClose;
import it.polimi.yasper.core.enums.Tick;
import it.polimi.yasper.core.stream.data.WebDataStream;
import it.polimi.yasper.core.stream.web.WebStreamImpl;
import it.polimi.yasper.core.stream.data.DataStreamImpl;
import org.apache.commons.rdf.api.Graph;
import it.polimi.deib.rsp.simple.sds.SDSManagerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CSPARQLImpl implements QueryRegistrationFeature, StreamRegistrationFeature<DataStreamImpl, WebStreamImpl> {

    private final long t0;
    private Report report;
    private Tick tick;
    protected EngineConfiguration rsp_config;

    protected Map<String, SDS> assignedSDS;
    protected Map<String, ContinuousQueryExecution> queryExecutions;
    protected Map<String, ContinuousQuery> registeredQueries;
    protected Map<String, List<QueryResultFormatter>> queryObservers;
    protected Map<String, WebDataStream<Graph>> registeredStreams;
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
    public DataStreamImpl register(WebStreamImpl s) {
        DataStreamImpl rs = new DataStreamImpl(s.getURI());
        registeredStreams.put(s.getURI(), rs);
        return rs;
    }

    @Override
    public ContinuousQueryExecution register(ContinuousQuery q, SDSConfiguration c) {
        SDSManager builder = new SDSManagerImpl(q, c, registeredStreams, report, report_grain, tick, t0);
        SDS build = builder.build();
        return builder.getContinuousQueryExecution();
    }

    @Override
    public ContinuousQueryExecution register(ContinuousQuery q) {
        return register(q, null);
    }


}
