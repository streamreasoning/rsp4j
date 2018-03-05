package it.polimi.yasper.core.simple.sds;

import it.polimi.yasper.core.rspql.ContinuousQueryExecution;
import it.polimi.yasper.core.rspql.SDS;
import it.polimi.yasper.core.rspql.SDSBuilder;
import it.polimi.yasper.core.rspql.Stream;
import it.polimi.yasper.core.simple.querying.ContinuousQueryExecutionImpl;
import it.polimi.yasper.core.simple.querying.ContinuousQueryImpl;
import it.polimi.yasper.core.simple.windowing.DefaultStreamView;
import it.polimi.yasper.core.simple.windowing.NamedStreamView;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.scope.Tick;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.utils.EngineConfiguration;
import it.polimi.yasper.core.utils.QueryConfiguration;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;

import java.util.Map;

@RequiredArgsConstructor
public class SDSBuilderImpl implements SDSBuilder<ContinuousQueryImpl> {

    @NonNull
    private final RDF rdf;
    @NonNull
    private Map<String, Stream> registeredStreams;
    @NonNull
    private EngineConfiguration engine_config;
    @NonNull
    private QueryConfiguration query_config;
    @NonNull
    private Report report;
    @NonNull
    private ReportGrain reportGrain;
    @NonNull
    private Tick tick;
    private ContinuousQueryExecutionImpl cqe;

    @Override
    public void visit(ContinuousQueryImpl query) {
        SDSImpl sds = new SDSImpl(rdf);
        this.cqe = new ContinuousQueryExecutionImpl(rdf, rdf.createIRI(query.getID()), sds, sds, query, query.getR2S());
        query.getWindowMap().forEach((wo, s) -> {
            IRI iri = rdf.createIRI(wo.getName());
            Stream s1 = registeredStreams.get(s.getURI());
            WindowAssigner wa = wo.apply(s1);
            wa.setReport(report);
            wa.setTick(tick);
            wa.setReportGrain(reportGrain);
            if (wo.isNamed()) {
                NamedStreamView v = new NamedStreamView(wa);
                sds.add(iri, wa.setView(v));
                cqe.add(v);
            } else {
                DefaultStreamView v = new DefaultStreamView();
                sds.add(wa.setView(v));
                cqe.add(v);
            }
        });
    }


    @Override
    public SDS getSDS() {
        return null;
    }

    @Override
    public ContinuousQueryImpl getContinuousQuery() {
        return null;
    }

    @Override
    public ContinuousQueryExecution getContinuousQueryExecution() {
        return cqe;
    }
}
