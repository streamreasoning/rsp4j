package it.polimi.sr.rsp.onsper.rspql;

import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.Relation;
import it.polimi.yasper.core.operators.s2r.execution.assigner.Assigner;
import it.polimi.yasper.core.sds.timevarying.TimeVarying;
import it.polimi.yasper.core.secret.content.Content;
import it.polimi.yasper.core.stream.metadata.StreamSchema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.IRI;
import org.jooq.lambda.tuple.Tuple;

@Log4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimeVaryingRelation implements TimeVarying<Relation<Tuple>> {

    private IRI iri;
    private StreamSchema schema;
    private Relation<Tuple> relation;
    private Assigner<Tuple, Relation<Tuple>> wa;

    @Override
    public void materialize(long l) {
        //TODO naive, because I do not update the remove content
        Content<Tuple,Relation<Tuple>> content = wa.getContent(l);
        relation.clear();
        Relation<Tuple> coalesce = content.coalesce();
        coalesce.getCollection().forEach(relation::add);
    }

    @Override
    public Relation<Tuple> get() {
        return relation;
    }

    @Override
    public String iri() {
        return iri.getIRIString();
    }

    @Override
    public boolean named() {
        return iri != null;
    }
}