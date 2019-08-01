package simple.sds;

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
import simple.querying.ContinuousQueryExecutionImpl;
import simple.querying.R2RImpl;
import simple.querying.Rstream;
import simple.windowing.CQELSTimeWindowOperator;
import simple.windowing.CSPARQLTimeWindowOperator;

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
                w = new CQELSTimeWindowOperator(iri, wo.getRange(), wo.getT0(), query.getTime(), tick, report, reportGrain, cqe);
            } else
                w = new CSPARQLTimeWindowOperator(iri, wo.getRange(), wo.getStep(), wo.getT0(), query.getTime(), tick, report, reportGrain, cqe);


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
