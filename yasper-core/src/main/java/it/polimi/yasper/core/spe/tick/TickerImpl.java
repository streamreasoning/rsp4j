package it.polimi.yasper.core.spe.tick;

import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.operators.s2r.execution.assigner.WindowAssigner;
import it.polimi.yasper.core.spe.operators.s2r.execution.instance.Window;
import it.polimi.yasper.core.spe.time.TimeFactory;
import it.polimi.yasper.core.spe.time.TimeInstant;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Tick dimension in our model defines the condition which drives an SPE
 * to take action on its input (also referred to as “window state change” or “window re-evaluation” [13]).
 * Like Report, Tick is also part of a system’s inter- nal execution model.
 * While some systems react to individual tuples as they arrive, others collectively
 * react to all or subsets of tuples with the same tapp value.
 * During our analysis, we have identified three main ways that di↵erent systems “tick”:
 * (a) tuple-driven, where each tuple arrival causes a system to react;
 * (b) time-driven, where the progress of tapp causes a system to react;
 * (c) batch-driven, where either a new batch arrival or the progress of tapp causes a system to react.
 **/

@NoArgsConstructor
public class TickerImpl<E> implements Ticker<E> {

    @Setter
    private WindowAssigner<E> wa;

    @Override
    public Content<E> tick(long t_e, Window w) {
        TimeFactory.getEvaluationTimeInstants().add(new TimeInstant(t_e));
        switch (wa.tick()) {
            case TIME_DRIVEN:
                if (t_e > wa.time().getAppTime()) {
                    return wa.compute(t_e, w);
                }
            case TUPLE_DRIVEN:
                return wa.compute(t_e, w);
            case BATCH_DRIVEN:
            default:
                return wa.compute(t_e, w);
        }
    }
}
