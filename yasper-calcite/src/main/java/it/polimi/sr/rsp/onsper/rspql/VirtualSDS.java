package it.polimi.sr.rsp.onsper.rspql;


import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.sds.SDSManager;
import it.polimi.yasper.core.sds.timevarying.TimeVarying;
import lombok.RequiredArgsConstructor;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.Relation;
import org.apache.commons.rdf.api.IRI;
import org.jooq.lambda.tuple.Tuple;

import java.util.*;

/**
 * Created by riccardo on 05/09/2017.
 */
@RequiredArgsConstructor
public class VirtualSDS implements SDS<Relation<Tuple>>, Observer {

    public VirtualSDS(SDSManager manager) {
        this.manager = manager;
    }

    private SDSManager manager;

    private Map<IRI, TimeVarying<Relation<Tuple>>> named = new HashMap<>();
    private Set<TimeVarying<Relation<Tuple>>> defs = new HashSet<>();

    @Override
    public Collection<TimeVarying<Relation<Tuple>>> asTimeVaryingEs() {
        Set<TimeVarying<Relation<Tuple>>> res = new HashSet<>();
        res.addAll(defs);
        Set<Map.Entry<IRI, TimeVarying<Relation<Tuple>>>> entries = named.entrySet();
        entries.stream().map(Map.Entry::getValue).forEach(res::add);
        return res;
    }

    @Override
    public void add(IRI iri, TimeVarying<Relation<Tuple>> timeVarying) {
        this.named.put(iri, timeVarying);
    }

    @Override
    public void add(TimeVarying<Relation<Tuple>> tvg) {
        this.defs.add(tvg);
    }

    @Override
    public void materialize(long ts) {
        named.values().forEach(named_tvr -> named_tvr.materialize(ts));
        defs.forEach(tvr -> tvr.materialize(ts));
    }

    @Override
    public void update(Observable o, Object arg) {
        materialize((Long) arg);
    }
}
