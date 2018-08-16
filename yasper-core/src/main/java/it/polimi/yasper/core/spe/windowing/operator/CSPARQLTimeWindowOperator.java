package it.polimi.yasper.core.spe.windowing.operator;

import it.polimi.yasper.core.spe.time.TimeFactory;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.spe.windowing.assigner.CSPARQLWindowAssigner;
import it.polimi.yasper.core.stream.RegisteredStream;
import it.polimi.yasper.core.stream.Stream;
import org.apache.commons.rdf.api.IRI;

public class CSPARQLTimeWindowOperator implements WindowOperator {

    private final long a, b, t0;
    private final IRI iri;

    public CSPARQLTimeWindowOperator(IRI iri, long a, long b, long t0) {
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
    public WindowAssigner apply(RegisteredStream s) {
        WindowAssigner windowAssigner = new CSPARQLWindowAssigner(iri, a, b, 0, 0, TimeFactory.getInstance());
        s.addWindowAssiger(windowAssigner);
        return windowAssigner;
    }
}
