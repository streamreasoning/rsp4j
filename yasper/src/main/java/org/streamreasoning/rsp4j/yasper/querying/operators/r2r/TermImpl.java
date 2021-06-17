package org.streamreasoning.rsp4j.yasper.querying.operators.r2r;

import org.apache.commons.rdf.api.RDFTerm;

public class TermImpl implements VarOrTerm {

    private final RDFTerm term;

    public TermImpl(RDFTerm term) {
        this.term = term;
    }

    @Override
    public boolean bind(Binding b, RDFTerm t) {
        return this.term.equals(t);
    }

    @Override
    public String getIRIString() {
        return null;
    }

    @Override
    public String ntriplesString() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }
}
