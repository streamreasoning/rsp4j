package it.polimi.spe.windowing.assigner;

import it.polimi.rspql.Stream;
import it.polimi.spe.windowing.WindowOperator;

public class TimeWindowOperator implements WindowOperator {

    private final long a, b, t0;
    private final String iri;

    public TimeWindowOperator(String iri, long a, long b, long t0) {
        this.iri = iri;
        this.a = a;
        this.b = b;
        this.t0 = t0;
    }

    @Override
    public String getName() {
        return iri;
    }

    @Override
    public boolean isNamed() {
        return iri != null;
    }

    @Override
    public WindowAssigner apply(Stream s) {
        WindowAssignerImpl windowAssigner = new WindowAssignerImpl(s, a, b, 0, 0);
        return windowAssigner;
    }
}
