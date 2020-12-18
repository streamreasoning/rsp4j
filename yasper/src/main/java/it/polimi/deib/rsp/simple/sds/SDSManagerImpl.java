package it.polimi.deib.rsp.simple.sds;

import it.polimi.deib.rsp.simple.querying.operators.R2RImpl;
import it.polimi.deib.rsp.simple.querying.operators.windowing.CQELSTimeWindowOperator;
import it.polimi.deib.rsp.simple.querying.operators.windowing.CSPARQLTimeWindowOperator;
import it.polimi.yasper.core.RDFUtils;
import it.polimi.yasper.core.enums.ReportGrain;
import it.polimi.yasper.core.enums.Tick;
import it.polimi.yasper.core.operators.s2r.StreamToRelationOperator;
import it.polimi.yasper.core.operators.s2r.syntax.WindowNode;
import it.polimi.yasper.core.querying.ContinuousQuery;
import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.sds.SDSConfiguration;
import it.polimi.yasper.core.sds.SDSManager;
import it.polimi.yasper.core.sds.timevarying.TimeVarying;
import it.polimi.yasper.core.secret.report.Report;
import it.polimi.yasper.core.stream.data.WebDataStream;
import it.polimi.yasper.core.stream.web.WebStream;
import lombok.RequiredArgsConstructor;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import it.polimi.deib.rsp.simple.ContinuousQueryExecutionImpl;
import it.polimi.deib.rsp.simple.querying.operators.Rstream;

import java.util.Map;

@RequiredArgsConstructor
public class SDSManagerImpl implements SDSManager {

    private final ContinuousQuery query;
    private final SDSConfiguration config;
    private final Map<String, WebDataStream<Graph>> registeredStreams;
    private final Report report;
    private final ReportGrain reportGrain;
    private final Tick tick;
    private final long t0;

    private ContinuousQueryExecution cqe;
    private SDSImpl sds;

    public SDS build() {
        this.sds = new SDSImpl(this);

        this.cqe = new ContinuousQueryExecutionImpl(sds, query, new R2RImpl(sds, query), new Rstream());

        query.getWindowMap().forEach((WindowNode wo, WebStream s) -> {

            StreamToRelationOperator<Graph, Graph> w;
            IRI iri = RDFUtils.createIRI(wo.iri());

            if (wo.getStep() == -1) {
                w = new CQELSTimeWindowOperator(iri, wo.getRange(), wo.getT0(), query.getTime(), tick, report, reportGrain, sds);
            } else
                w = new CSPARQLTimeWindowOperator(iri, wo.getRange(), wo.getStep(), wo.getT0(), query.getTime(), tick, report, reportGrain, sds);


            TimeVarying<Graph> tvg = w.apply(registeredStreams.get(s.getURI()));

            if (wo.named()) {
                sds.add(iri, tvg);
            } else {
                sds.add(tvg);
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
