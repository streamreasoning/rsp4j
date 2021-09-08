package org.streamreasoning.rsp4j.yasper.querying.syntax;

import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarOrTerm;

public class TripleHolder {

    public TripleHolder(){

    }
    public TripleHolder(VarOrTerm s, VarOrTerm p , VarOrTerm o){
        this.s = s;
        this.p = p;
        this.o = o;
    }
    public VarOrTerm s;
    public VarOrTerm p;
    public VarOrTerm o;
}
