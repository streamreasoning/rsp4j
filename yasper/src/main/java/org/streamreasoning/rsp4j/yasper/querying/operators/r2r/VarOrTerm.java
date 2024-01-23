package org.streamreasoning.rsp4j.yasper.querying.operators.r2r;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDFTerm;
import org.streamreasoning.rsp4j.api.operators.r2r.Var;

public interface VarOrTerm extends IRI, Var {
    static VarOrTerm create(String p) {
        if(p.startsWith("?")){
            return new VarImpl(p);
        }else{
            return new TermImpl(p);
        }
    }

    boolean bind(Binding b, RDFTerm t);

    boolean isVariable();

    boolean isTerm();

}
