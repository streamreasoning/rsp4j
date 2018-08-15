package simple.sds;

import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.quering.querying.ContinuousQuery;
import it.polimi.yasper.core.quering.rspql.sds.SDS;
import it.polimi.yasper.core.quering.rspql.sds.SDSBuilder;
import it.polimi.yasper.core.quering.rspql.tvg.TimeVarying;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.scope.Tick;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.spe.windowing.operator.WindowOperator;
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
    private Map<String, Stream> registeredStreams;
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
        this.cqe = new ContinuousQueryExecutionImpl(RDFUtils.createIRI(query.getID()), sds, sds, query);

        query.getWindowMap().forEach((WindowOperator wo, Stream s) -> {
            IRI iri = RDFUtils.createIRI(wo.getName());
            Stream s1 = registeredStreams.get(s.getURI());
            WindowAssigner wa = wo.apply(s1);
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
