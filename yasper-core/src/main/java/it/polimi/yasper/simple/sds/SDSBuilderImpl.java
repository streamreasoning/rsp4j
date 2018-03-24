package it.polimi.yasper.simple.sds;

import it.polimi.yasper.core.quering.ContinuousQuery;
import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.quering.SDS;
import it.polimi.yasper.core.quering.SDSBuilder;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.simple.querying.ContinuousQueryExecutionImpl;
import it.polimi.yasper.simple.querying.ContinuousQueryImpl;
import it.polimi.yasper.simple.windowing.DefaultStreamView;
import it.polimi.yasper.simple.windowing.NamedStreamView;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.scope.Tick;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.spe.windowing.operator.WindowOperator;
import it.polimi.yasper.core.utils.EngineConfiguration;
import it.polimi.yasper.core.utils.QueryConfiguration;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;

import java.util.Map;

@RequiredArgsConstructor
public class SDSBuilderImpl implements SDSBuilder {

    @NonNull
    private final RDF rdf;
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
        SDSImpl sds = new SDSImpl(rdf);
        this.cqe = new ContinuousQueryExecutionImpl(rdf, rdf.createIRI(query.getID()), sds, sds, query);
        query.getWindowMap().forEach((WindowOperator wo, Stream s) -> {
            IRI iri = rdf.createIRI(wo.getName());
            Stream s1 = registeredStreams.get(s.getURI());
            WindowAssigner wa = wo.apply(s1);
            wa.setReport(report);
            wa.setTick(tick);
            wa.setReportGrain(reportGrain);
            if (wo.isNamed()) {
                NamedStreamView v = new NamedStreamView(wa);
                sds.add(iri, wa.setView(v));
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
