package it.polimi.yasper.core.spe.windowing.operator;

import it.polimi.yasper.core.spe.time.TimeFactory;
import it.polimi.yasper.core.spe.windowing.assigner.CQELSWindowAssigner;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.RegisteredStream;
import it.polimi.yasper.core.stream.Stream;
import org.apache.commons.rdf.api.IRI;

public class CQELSTimeWindowOperator implements WindowOperator {

    private final long a, t0;
    private final IRI iri;

    public CQELSTimeWindowOperator(IRI iri, long a, long t0) {
        this.iri = iri;
        this.a = a;
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
        WindowAssigner windowAssigner = new CQELSWindowAssigner(iri, a, 0, TimeFactory.getInstance());
        s.addWindowAssiger(windowAssigner);
        return windowAssigner;
    }
}
