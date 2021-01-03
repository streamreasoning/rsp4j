package it.polimi.deib.sr.rsp.yasper.engines;

import it.polimi.deib.sr.rsp.api.RDFUtils;
import it.polimi.deib.sr.rsp.api.engine.config.EngineConfiguration;
import it.polimi.deib.sr.rsp.api.engine.features.QueryRegistrationFeature;
import it.polimi.deib.sr.rsp.api.engine.features.StreamRegistrationFeature;
import it.polimi.deib.sr.rsp.api.enums.ReportGrain;
import it.polimi.deib.sr.rsp.api.enums.Tick;
import it.polimi.deib.sr.rsp.api.format.QueryResultFormatter;
import it.polimi.deib.sr.rsp.api.operators.s2r.StreamToRelationOperatorFactory;
import it.polimi.deib.sr.rsp.api.operators.s2r.syntax.WindowNode;
import it.polimi.deib.sr.rsp.api.querying.ContinuousQuery;
import it.polimi.deib.sr.rsp.api.querying.ContinuousQueryExecution;
import it.polimi.deib.sr.rsp.api.sds.SDS;
import it.polimi.deib.sr.rsp.api.sds.timevarying.TimeVarying;
import it.polimi.deib.sr.rsp.api.secret.report.Report;
import it.polimi.deib.sr.rsp.api.secret.time.Time;
import it.polimi.deib.sr.rsp.api.stream.data.WebDataStream;
import it.polimi.deib.sr.rsp.api.stream.web.WebStream;
import it.polimi.deib.sr.rsp.yasper.ContinuousQueryExecutionImpl;
import it.polimi.deib.sr.rsp.yasper.examples.RDFStream;
import it.polimi.deib.sr.rsp.yasper.examples.RDFTripleStream;
import it.polimi.deib.sr.rsp.yasper.querying.operators.R2RImpl;
import it.polimi.deib.sr.rsp.yasper.querying.operators.Rstream;
import it.polimi.deib.sr.rsp.yasper.sds.SDSImpl;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Triple;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Yasper implements QueryRegistrationFeature<ContinuousQuery>, StreamRegistrationFeature<RDFStream, RDFStream> {

    private final long t0;
    private final String baseUri;
    private final String windowOperatorFactory;
    private final String S2RFactory = "yasper.window_operator_factory";
    private Report report;
    private Tick tick;
    protected EngineConfiguration rsp_config;
    protected Map<String, SDS> assignedSDS;
    protected Map<String, ContinuousQueryExecution> queryExecutions;
    protected Map<String, ContinuousQuery> registeredQueries;
    protected Map<String, List<QueryResultFormatter>> queryObservers;
    protected Map<String, WebDataStream<Graph>> registeredStreams;
    private ReportGrain report_grain;


    public Yasper(EngineConfiguration rsp_config) {
        this.rsp_config = rsp_config;
        this.report = rsp_config.getReport();
        this.baseUri = rsp_config.getBaseIRI();
        this.report_grain = rsp_config.getReportGrain();
        this.tick = rsp_config.getTick();
        this.t0 = rsp_config.gett0();
        this.windowOperatorFactory = rsp_config.getString(S2RFactory);
        this.assignedSDS = new HashMap<>();
        this.registeredQueries = new HashMap<>();
        this.registeredStreams = new HashMap<>();
        this.queryObservers = new HashMap<>();
        this.queryExecutions = new HashMap<>();

    }

    @Override
    public ContinuousQueryExecution<Graph, Graph, Triple> register(ContinuousQuery q) {
//        return new ContinuousQueryExecutionFactoryImpl(q, windowOperatorFactory, registeredStreams, report, report_grain, tick, t0).build();

        SDS sds = new SDSImpl();

        RDFTripleStream out = new RDFTripleStream(q.getID());

        ContinuousQueryExecution<Graph, Graph, Triple> cqe = new ContinuousQueryExecutionImpl<Graph, Graph, Triple>(sds, q, out, new R2RImpl(sds, q), new Rstream());

        q.getWindowMap().forEach((WindowNode wo, WebStream s) -> {
            try {
                StreamToRelationOperatorFactory<Graph, Graph> w;
                IRI iri = RDFUtils.createIRI(wo.iri());

                Class<?> aClass = Class.forName(windowOperatorFactory);
                w = (StreamToRelationOperatorFactory<Graph, Graph>) aClass
                        .getConstructor(long.class,
                                long.class,
                                long.class,
                                Time.class,
                                Tick.class,
                                Report.class,
                                ReportGrain.class,
                                ContinuousQueryExecution.class)
                        .newInstance(wo.getRange(),
                                wo.getStep(),
                                wo.getT0(),
                                q.getTime(),
                                tick,
                                report,
                                report_grain,
                                cqe);

//            if (wo.getStep() == -1) {
//                w = new
//                (wo.getRange(), wo.getT0(), query.getTime(), tick, report, reportGrain, cqe);
//            } else
//                w = new CSPARQLTimeWindowOperatorFactory(wo.getRange(), wo.getStep(), wo.getT0(), query.getTime(), tick, report, reportGrain, cqe);

                TimeVarying<Graph> tvg = w.apply(registeredStreams.get(s.uri()), iri);

                if (wo.named()) {
                    sds.add(iri, tvg);
                } else {
                    sds.add(tvg);
                }

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return cqe;
    }

    @Override
    public RDFStream register(RDFStream s) {
        registeredStreams.put(s.uri(), s);
        return s;
    }
}
