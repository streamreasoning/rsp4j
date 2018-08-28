package it.polimi.yasper.core.spe.tick.secret;

import it.polimi.yasper.core.spe.operators.s2r.execution.assigner.WindowAssigner;
import it.polimi.yasper.core.spe.tick.Tick;
import it.polimi.yasper.core.spe.tick.Ticker;

public class TickerFactory {

    public static Ticker tick(Tick t, WindowAssigner<?, ?> wa) {
        switch (t) {
            case TUPLE_DRIVEN:
                return new TupleTicker(wa);
            case BATCH_DRIVEN:
                return new BatchTicker(wa);
            case TIME_DRIVEN:
            default:
                return new TimeTicker(wa, wa.time());
        }
    }

}
