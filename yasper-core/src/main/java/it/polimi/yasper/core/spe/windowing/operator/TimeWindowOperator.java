package it.polimi.yasper.core.spe.windowing.operator;

import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssignerImpl;
import it.polimi.yasper.core.stream.Stream;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;

public class TimeWindowOperator implements WindowOperator {

    private final long a, b, t0;
    private final IRI iri;
    private RDF rdf;

    public TimeWindowOperator(RDF rdf, IRI iri, long a, long b, long t0) {
        this.rdf = rdf;
        this.iri = iri;
        this.a = a;
        this.b = b;
        this.t0 = t0;
    }

    @Override
    public String getName() {
        return iri.getIRIString();
    }

    @Override
    public boolean isNamed() {
        return iri != null;
    }

    @Override
    public WindowAssigner apply(Stream s) {
        WindowAssignerImpl windowAssigner = new WindowAssignerImpl(rdf, iri, s, a, b, 0, 0);
        return windowAssigner;
    }
}
