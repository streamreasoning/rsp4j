package it.polimi.sr.rsp.onsper.spe.operators.s2r.operator;

import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.Relation;
import it.polimi.yasper.core.enums.ReportGrain;
import it.polimi.yasper.core.enums.Tick;
import it.polimi.yasper.core.operators.s2r.StreamToRelationOperator;
import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.sds.timevarying.TimeVarying;
import it.polimi.yasper.core.secret.report.Report;
import it.polimi.yasper.core.secret.time.TimeFactory;
import it.polimi.yasper.core.stream.data.WebDataStream;
import it.polimi.yasper.core.stream.metadata.StreamSchema;
import it.polimi.sr.rsp.onsper.spe.operators.s2r.assigner.VCSPARQLWindowAssigner;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.jooq.lambda.tuple.Tuple;

public class OBDAWindowOperator1 implements StreamToRelationOperator<Tuple, Relation<Tuple>> {
    private final long a, b, t0;
    private final IRI iri;
    private final Graph mapping;
    private final Relation<Tuple> relation;
    private final StreamSchema schema;
    private final Tick tick;
    private final Report report;
    private final ReportGrain grain;
    private final SDS<Relation<Tuple>> context;

    public OBDAWindowOperator1(IRI iri, long a, long b, long t0, StreamSchema schema, Graph mapping, Relation<Tuple> relation, Tick tick, Report report, ReportGrain grain, SDS<Relation<Tuple>> context) {
        this.iri = iri;
        this.a = a;
        this.b = b;
        this.t0 = t0;
        this.schema = schema;
        this.mapping = mapping;
        this.relation = relation;
        this.tick = tick;
        this.report = report;
        this.grain = grain;
        this.context = context;
    }

    @Override
    public String iri() {
        return iri.getIRIString();
    }

    @Override
    public boolean named() {
        return iri != null;
    }

    @Override
    public TimeVarying<Relation<Tuple>> apply(WebDataStream<Tuple> s) {
        VCSPARQLWindowAssigner windowAssigner = new VCSPARQLWindowAssigner(iri, a, b, 0, 0, TimeFactory.getInstance(), schema, mapping, relation, tick, report, grain);
        s.addConsumer(windowAssigner);
        return windowAssigner.set( context);
    }

}
