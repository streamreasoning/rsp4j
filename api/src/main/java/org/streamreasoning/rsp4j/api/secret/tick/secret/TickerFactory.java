package org.streamreasoning.rsp4j.api.secret.tick.secret;

import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.secret.tick.Ticker;

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
