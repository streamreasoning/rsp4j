package it.polimi.yasper.core.query.operators.s2r;

/**
 * Created by riccardo on 16/07/2017.
 */
public abstract class WindowOperatorImpl implements WindowOperator {

    private final long t0;
    private final long range;
    private final long step;

    public WindowOperatorImpl(long t0, long range, long step) {
        this.t0 = t0;
        this.range = range;
        this.step = step;
    }

    @Override
    public long getT0() {
        return t0;
    }

    @Override
    public long getRange() {
        return range;
    }

    @Override
    public long getStep() {
        return step;
    }
}
