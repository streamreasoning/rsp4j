package it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.rewriting;


import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.Relation;
import org.jooq.lambda.tuple.Tuple;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SingletonTupleRelation implements Relation<Tuple> {

    private List<Tuple> list;

    public SingletonTupleRelation(Tuple t) {
        this.list = Collections.singletonList(t);
    }

    @Override
    public Collection<Tuple> getCollection() {
        return list;
    }

    @Override
    public void add(Tuple o) {
    }

    @Override
    public void remove(Tuple o) {

    }

    @Override
    public void clear() {

    }
}
