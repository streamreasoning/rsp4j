package org.streamreasoning.rsp4j.yasper.querying.operators.r2r;

import org.apache.commons.rdf.api.RDFTerm;
import org.streamreasoning.rsp4j.api.RDFUtils;

import java.util.Objects;

public class TermImpl implements VarOrTerm {

    private final RDFTerm term;

    public TermImpl(RDFTerm term) {
        this.term = term;
    }

    public TermImpl(String term) {
        this(RDFUtils.createIRI(term));
    }

    @Override
    public boolean bind(Binding b, RDFTerm t) {
        return this.term.equals(t);
    }

    @Override
    public String getIRIString() {
        return RDFUtils.trimTags(term.ntriplesString());
    }

    @Override
    public String ntriplesString() {
        return term.ntriplesString();
    }

    @Override
    public String name() {
        return term.ntriplesString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TermImpl compTerm = (TermImpl) o;
        return Objects.equals(this.term, compTerm.term);
    }

    @Override
    public int hashCode() {
        return Objects.hash(term);
    }

    @Override
    public String toString() {
        return term.toString();
    }
}
