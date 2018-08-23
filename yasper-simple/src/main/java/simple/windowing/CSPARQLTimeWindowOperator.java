package simple.windowing;

import it.polimi.yasper.core.spe.time.Time;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.spe.windowing.operator.WindowOperator;
import it.polimi.yasper.core.stream.RegisteredStream;
import org.apache.commons.rdf.api.IRI;

public class CSPARQLTimeWindowOperator implements WindowOperator {

    private final long a, b, t0;
    private final IRI iri;
    private final Time time;

    public CSPARQLTimeWindowOperator(IRI iri, long a, long b, long t0, Time time) {
        this.iri = iri;
        this.a = a;
        this.b = b;
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
        WindowAssigner windowAssigner = new CSPARQLWindowAssigner(iri, a, b, t0, t0, time);
        s.addWindowAssiger(windowAssigner);
        return windowAssigner;
    }
}
