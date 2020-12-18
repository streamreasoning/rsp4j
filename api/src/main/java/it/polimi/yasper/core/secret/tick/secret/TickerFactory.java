package it.polimi.yasper.core.secret.tick.secret;

import it.polimi.yasper.core.operators.s2r.execution.assigner.Assigner;
import it.polimi.yasper.core.enums.Tick;
import it.polimi.yasper.core.secret.tick.Ticker;

public class TickerFactory {

    public static Ticker tick(Tick t, Assigner<?, ?> wa) {
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
