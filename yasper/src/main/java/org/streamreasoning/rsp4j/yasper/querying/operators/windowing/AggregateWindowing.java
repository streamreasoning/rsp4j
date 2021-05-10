package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;

import java.util.function.BiFunction;

public class AggregateWindowing<I extends EventBean<V>,V extends Comparable<V>,O> extends FrameWindowing<I,V, O>{

    private final BiFunction<I,V,V> agg;
    private final V startValue;

    protected AggregateWindowing(I arg, BiFunction<I,V,V> agg, V startValue, V threshold) {
        super();
        this.openPred = i -> frameState.isTsStartNull();
        this.updatePred = i -> frameState.getAuxiliaryValue().compareTo(threshold) < 0;
        this.closePred = this.updatePred.negate();
        this.agg = agg;
        this.startValue = startValue;
    }

    @Override
    public void close(long ts) {
        candidateWindow.close();
        windows.put(candidateWindow.getC(), candidateWindow);
        frameState.resetTsStartNull();
    }

    @Override
    public void open(long ts, I arg) {
        candidateWindow = new CandidateWindow(ts,ts);
        frameState.setTsStart(ts);
        frameState.setAuxiliaryValue(agg.apply(arg,startValue));
    }

    @Override
    public void update(long ts, I arg) {
        candidateWindow.extend(ts);
        frameState.setAuxiliaryValue(agg.apply(arg, frameState.getAuxiliaryValue()));
    }

}
