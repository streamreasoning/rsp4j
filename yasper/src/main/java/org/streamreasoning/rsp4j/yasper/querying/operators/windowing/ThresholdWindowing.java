package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;

import java.util.Comparator;

public class ThresholdWindowing<I extends EventBean<V>,V extends Comparable<V>,O> extends FrameWindowing<I,V,O>{

    private final int min = 0;

    protected ThresholdWindowing(String attribute, Comparator<V> comparator, V threshold) {
        super();
        this.openPred = i -> comparator.compare(i.getValue(attribute), threshold)>0 &&
                frameState.getCount() == 0;
        this.updatePred = i -> comparator.compare(i.getValue(attribute), threshold)>0 &&
                frameState.getCount()>0;
        this.closePred = i -> comparator.compare(i.getValue(attribute), threshold)<0 &&
                frameState.getCount()>0;
    }

    @Override
    public void close(long ts) {
        if(frameState.getCount()>min){
            candidateWindow.close();
            windows.put(candidateWindow.getC(), candidateWindow);
        }
        frameState.resetCounter();
        frameState.setTsStart(0L);

    }

    @Override
    public void open(long ts, I arg) {
        candidateWindow = new CandidateWindow(ts,ts);
        frameState.setTsStart(ts);
        frameState.incrementCounter();
    }

    @Override
    public void update(long ts, I arg) {
        candidateWindow.extend(ts);
        frameState.incrementCounter();
    }

}
