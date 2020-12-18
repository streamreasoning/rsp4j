package it.polimi.deib.sr.rsp.yasper.engines;

import it.polimi.deib.sr.rsp.yasper.examples.RDFStream;
import it.polimi.deib.sr.rsp.api.engine.config.EngineConfiguration;
import it.polimi.deib.sr.rsp.api.engine.features.QueryRegistrationFeature;
import it.polimi.deib.sr.rsp.api.engine.features.StreamRegistrationFeature;
import it.polimi.deib.sr.rsp.api.sds.SDS;
import it.polimi.deib.sr.rsp.api.sds.SDSManager;
import it.polimi.deib.sr.rsp.api.querying.ContinuousQuery;
import it.polimi.deib.sr.rsp.api.sds.SDSConfiguration;
import it.polimi.deib.sr.rsp.api.querying.ContinuousQueryExecution;
import it.polimi.deib.sr.rsp.api.format.QueryResultFormatter;
import it.polimi.deib.sr.rsp.api.secret.report.Report;
import it.polimi.deib.sr.rsp.api.enums.ReportGrain;
import it.polimi.deib.sr.rsp.api.secret.report.ReportImpl;
import it.polimi.deib.sr.rsp.api.secret.report.strategies.OnContentChange;
import it.polimi.deib.sr.rsp.api.enums.Tick;
import it.polimi.deib.sr.rsp.api.stream.data.WebDataStream;
import it.polimi.deib.sr.rsp.api.stream.web.WebStreamImpl;
import org.apache.commons.rdf.api.Graph;
import it.polimi.deib.sr.rsp.yasper.sds.SDSManagerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CQELSmpl implements QueryRegistrationFeature, StreamRegistrationFeature<RDFStream, WebStreamImpl> {

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


    public CQELSmpl(long t0, EngineConfiguration rsp_config) {
        this.rsp_config = rsp_config;
        this.assignedSDS = new HashMap<>();
        this.registeredQueries = new HashMap<>();
        this.registeredStreams = new HashMap<>();
        this.queryObservers = new HashMap<>();
        this.queryExecutions = new HashMap<>();
        this.t0 = t0;
        this.report = new ReportImpl();
        this.report.add(new OnContentChange());
        this.report_grain = ReportGrain.SINGLE;
        this.tick = Tick.TUPLE_DRIVEN;
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

    @Override
    public RDFStream register(WebStreamImpl s) {
        RDFStream rs = new RDFStream(s.getURI());
        registeredStreams.put(s.getURI(), rs);
        return rs;
    }
}
