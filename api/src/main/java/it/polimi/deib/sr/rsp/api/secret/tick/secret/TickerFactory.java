package it.polimi.deib.sr.rsp.api.secret.tick.secret;

import it.polimi.deib.sr.rsp.api.secret.tick.Ticker;
import it.polimi.deib.sr.rsp.api.operators.s2r.execution.assigner.StreamToRelationOp;
import it.polimi.deib.sr.rsp.api.enums.Tick;

public class TickerFactory {

    public static Ticker tick(Tick t, StreamToRelationOp<?, ?> wa) {
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
