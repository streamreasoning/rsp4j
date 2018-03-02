package it.polimi.runtime;

import it.polimi.rspql.SDSBuilder;
import it.polimi.rspql.Stream;
import it.polimi.rspql.ContinuousQueryExecution;
import it.polimi.rspql.SDS;
import it.polimi.spe.report.Report;
import it.polimi.spe.report.ReportGrain;
import it.polimi.spe.scope.Tick;
import it.polimi.spe.windowing.WindowOperator;
import it.polimi.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.enums.StreamOperator;
import it.polimi.yasper.core.utils.EngineConfiguration;
import it.polimi.yasper.core.utils.QueryConfiguration;
import lombok.AllArgsConstructor;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.simple.SimpleRDF;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class SDSBuilderImpl implements SDSBuilder<ContinuousQueryImpl> {

    private Map<String, Stream> registeredStreams;
    private EngineConfiguration engine_config;
    private QueryConfiguration query_config;
    private Report report;
    private ReportGrain reportGrain;
    private Tick tick;

    @Override
    public void visit(ContinuousQueryImpl query) {

        String id = query.getID();

        RDF rdf = new SimpleRDF();

        Set<DefaultStreamView> defaultStreamViews = new HashSet<>();

        Map<WindowOperator, Stream> windows = query.getWindowMap();
        Map<IRI, NamedStreamView> nametvgs = new HashMap<>();

        windows.forEach((wo, s) -> {

            Stream s1 = registeredStreams.get(s.getURI());
            WindowAssigner wa = wo.apply(s1);

            wa.setReport(report);
            wa.setTick(tick);
            wa.setReportGrain(reportGrain);

            if (wo.isNamed()) {
                NamedStreamView g = new NamedStreamView();
                nametvgs.put(rdf.createIRI(wo.getName()), g);
                wa.setView(g);
            } else {
                DefaultStreamView dg = new DefaultStreamView();
                defaultStreamViews.add(dg);
                wa.setView(dg);
            }
        });

        Map<IRI, Graph> namedGraphs = query.getNamedGraphs();
        Set<Graph> graphs = query.getGraphs();

        SDS sds = new SDSImpl(graphs, namedGraphs, defaultStreamViews, nametvgs);

        StreamOperator r2S = query.getR2S();
        ContinuousQueryExecution cqe = new ContinuousQueryExecutionImpl(sds, query, r2S);

        nametvgs.forEach((k,v) -> cqe.add(v));
        boolean recursive = query.isRecursive();

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
        return null;
    }
}
