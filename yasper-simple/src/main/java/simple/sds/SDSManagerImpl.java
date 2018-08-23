package simple.sds;

import it.polimi.yasper.core.rspql.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.rspql.querying.ContinuousQuery;
import it.polimi.yasper.core.rspql.sds.SDS;
import it.polimi.yasper.core.rspql.sds.SDSManager;
import it.polimi.yasper.core.rspql.tvg.TimeVarying;
import it.polimi.yasper.core.rspql.window.WindowNode;
import it.polimi.yasper.core.spe.Tick;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.spe.windowing.operator.WindowOperator;
import it.polimi.yasper.core.stream.RegisteredStream;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.utils.QueryConfiguration;
import it.polimi.yasper.core.utils.RDFUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.rdf.api.IRI;
import simple.querying.ContinuousQueryExecutionImpl;
import simple.windowing.CQELSTimeWindowOperator;
import simple.windowing.CSPARQLTimeWindowOperator;

import java.util.Map;

@RequiredArgsConstructor
public class SDSManagerImpl implements SDSManager {

    private final ContinuousQuery query;
    private final QueryConfiguration config;
    @NonNull
    private Map<String, RegisteredStream> registeredStreams;
    @NonNull
    private Report report;
    @NonNull
    private ReportGrain reportGrain;
    @NonNull
    private Tick tick;
    @NonNull
    private long t0;

    private ContinuousQueryExecution cqe;
    private SDSImpl sds;

    public SDS build() {
        this.sds = new SDSImpl(this);
        this.cqe = new ContinuousQueryExecutionImpl(sds, sds, query);

        query.getWindowMap().forEach((WindowNode wo, Stream s) -> {

            WindowOperator w;
            if (wo.getStep() == -1) {
                w = new CQELSTimeWindowOperator(RDFUtils.createIRI(wo.iri()), wo.getRange(), wo.getT0(), query.getTime());
            } else
                w = new CSPARQLTimeWindowOperator(RDFUtils.createIRI(wo.iri()), wo.getRange(), wo.getStep(), wo.getT0(), query.getTime());

            IRI iri = RDFUtils.createIRI(w.iri());
            RegisteredStream s1 = registeredStreams.get(s.getURI());
            WindowAssigner wa = w.apply(s1);
            wa.report(report);
            wa.tick(tick);
            wa.report_grain(reportGrain);
            if (wo.named()) {
                TimeVarying tvg = wa.set(cqe);
                sds.add(iri, tvg);
            } else {
                sds.add(wa.set(cqe));
            }
        });

        return sds;
    }

    public SDS sds() {
        return sds;
    }

    @Override
    public ContinuousQueryExecution getContinuousQueryExecution() {
        return cqe;
    }
}
