package simple.sds;

import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.quering.querying.ContinuousQuery;
import it.polimi.yasper.core.quering.rspql.sds.SDS;
import it.polimi.yasper.core.quering.rspql.sds.SDSBuilder;
import it.polimi.yasper.core.quering.rspql.tvg.TimeVarying;
import it.polimi.yasper.core.quering.rspql.window.WindowNode;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.scope.Tick;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.spe.windowing.operator.CQELSTimeWindowOperator;
import it.polimi.yasper.core.spe.windowing.operator.CSPARQLTimeWindowOperator;
import it.polimi.yasper.core.spe.windowing.operator.WindowOperator;
import it.polimi.yasper.core.stream.RegisteredStream;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.utils.RDFUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.rdf.api.IRI;
import simple.querying.ContinuousQueryExecutionImpl;

import java.util.Map;

@RequiredArgsConstructor
public class SDSBuilderImpl implements SDSBuilder {

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

    @Override
    public void visit(ContinuousQuery query) {
        this.sds = new SDSImpl();
        this.cqe = new ContinuousQueryExecutionImpl(sds, sds, query);

        query.getWindowMap().forEach((WindowNode wo, Stream s) -> {

            WindowOperator w;
            if (wo.getStep() == -1) {
                w = new CQELSTimeWindowOperator(RDFUtils.createIRI(wo.getName()), wo.getRange(), wo.getT0());
            } else
                w = new CSPARQLTimeWindowOperator(RDFUtils.createIRI(wo.getName()), wo.getRange(), wo.getStep(), wo.getT0());

            IRI iri = RDFUtils.createIRI(w.getName());
            RegisteredStream s1 = registeredStreams.get(s.getURI());
            WindowAssigner wa = w.apply(s1);
            wa.report(report);
            wa.tick(tick);
            wa.report_grain(reportGrain);
            if (wo.isNamed()) {
                TimeVarying tvg = wa.set(cqe);
                sds.add(iri, tvg);
            } else {
                sds.add(wa.set(cqe));
            }
        });
    }


    @Override
    public SDS getSDS() {
        return sds;
    }

    @Override
    public ContinuousQueryExecution getContinuousQueryExecution() {
        return cqe;
    }
}
