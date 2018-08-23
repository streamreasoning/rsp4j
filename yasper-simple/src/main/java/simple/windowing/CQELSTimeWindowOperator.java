package simple.windowing;

import it.polimi.yasper.core.spe.time.Time;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.spe.windowing.operator.WindowOperator;
import it.polimi.yasper.core.stream.RegisteredStream;
import org.apache.commons.rdf.api.IRI;

public class CQELSTimeWindowOperator implements WindowOperator {

    private final long a, t0;
    private final IRI iri;
    private final Time time;

    public CQELSTimeWindowOperator(IRI iri, long a, long t0, Time time) {
        this.iri = iri;
        this.a = a;
        this.t0 = t0;
        this.time = time;
    }

    @Override
    public String iri() {
        return iri.getIRIString();
    }

    @Override
    public boolean named() {
        return iri != null;
    }

    @Override
    public WindowAssigner apply(RegisteredStream s) {
        WindowAssigner windowAssigner = new CQELSWindowAssigner(iri, a, t0, time);
        s.addWindowAssiger(windowAssigner);
        return windowAssigner;
    }
}
