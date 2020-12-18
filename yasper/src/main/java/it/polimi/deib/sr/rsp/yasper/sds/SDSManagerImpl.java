package it.polimi.deib.sr.rsp.yasper.sds;

import it.polimi.deib.sr.rsp.yasper.querying.operators.R2RImpl;
import it.polimi.deib.sr.rsp.yasper.querying.operators.windowing.CQELSTimeWindowOperator;
import it.polimi.deib.sr.rsp.yasper.querying.operators.windowing.CSPARQLTimeWindowOperator;
import it.polimi.deib.sr.rsp.api.RDFUtils;
import it.polimi.deib.sr.rsp.api.enums.ReportGrain;
import it.polimi.deib.sr.rsp.api.enums.Tick;
import it.polimi.deib.sr.rsp.api.operators.s2r.StreamToRelationOperatorFactory;
import it.polimi.deib.sr.rsp.api.operators.s2r.syntax.WindowNode;
import it.polimi.deib.sr.rsp.api.querying.ContinuousQuery;
import it.polimi.deib.sr.rsp.api.querying.ContinuousQueryExecution;
import it.polimi.deib.sr.rsp.api.sds.SDS;
import it.polimi.deib.sr.rsp.api.sds.SDSConfiguration;
import it.polimi.deib.sr.rsp.api.sds.SDSManager;
import it.polimi.deib.sr.rsp.api.sds.timevarying.TimeVarying;
import it.polimi.deib.sr.rsp.api.secret.report.Report;
import it.polimi.deib.sr.rsp.api.stream.data.WebDataStream;
import it.polimi.deib.sr.rsp.api.stream.web.WebStream;
import it.polimi.deib.sr.rsp.yasper.querying.operators.Rstream;
import lombok.RequiredArgsConstructor;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import it.polimi.deib.sr.rsp.yasper.ContinuousQueryExecutionImpl;

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

            StreamToRelationOperatorFactory<Graph, Graph> w;
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
