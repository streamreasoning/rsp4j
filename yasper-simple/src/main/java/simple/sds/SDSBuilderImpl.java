package simple.sds;

import it.polimi.yasper.core.quering.ContinuousQuery;
import it.polimi.yasper.core.quering.SDS;
import it.polimi.yasper.core.quering.SDSBuilder;
import it.polimi.yasper.core.quering.TimeVarying;
import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.scope.Tick;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.spe.windowing.operator.WindowOperator;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.utils.RDFUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import simple.querying.ContinuousQueryExecutionImpl;
import simple.windowing.DefaultStreamView;
import simple.windowing.NamedStreamView;

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
    private ContinuousQueryExecution cqe;

    @Override
    public void visit(ContinuousQuery query) {
        SDSImpl sds = new SDSImpl();
        this.cqe = new ContinuousQueryExecutionImpl(RDFUtils.createIRI(query.getID()), sds, sds, query);
        query.getWindowMap().forEach((WindowOperator wo, Stream s) -> {
            IRI iri = RDFUtils.createIRI(wo.getName());
            Stream s1 = registeredStreams.get(s.getURI());
            WindowAssigner wa = wo.apply(s1);
            wa.setReport(report);
            wa.setTick(tick);
            wa.setReportGrain(reportGrain);
            if (wo.isNamed()) {
                NamedStreamView v = new NamedStreamView(wa);
                TimeVarying<Graph> tvg = wa.setView(v);
                sds.add(iri, tvg);
                v.addObserver(cqe);
            } else {
                DefaultStreamView v = new DefaultStreamView();
                sds.add(wa.setView(v));
                v.addObserver(cqe);
            }
        });
    }


    @Override
    public SDS getSDS() {
        return null;
    }

    @Override
    public ContinuousQueryExecution getContinuousQueryExecution() {
        return cqe;
    }
}
