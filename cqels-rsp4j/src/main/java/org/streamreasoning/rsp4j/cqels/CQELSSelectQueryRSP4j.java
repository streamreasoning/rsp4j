package org.streamreasoning.rsp4j.cqels;

import com.hp.hpl.jena.query.Query;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;


public class CQELSSelectQueryRSP4j extends CQELSAbstractQuery<Binding> {


    public CQELSSelectQueryRSP4j(Query query){
        super(query);
    }



}
