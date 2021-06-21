package org.streamreasoning.rsp4j.yasper.querying.operators.r2r;

import org.apache.commons.rdf.api.RDFTerm;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TermImpl compTerm = (TermImpl) o;
        return Objects.equals(this.term, compTerm.term);
    }
}