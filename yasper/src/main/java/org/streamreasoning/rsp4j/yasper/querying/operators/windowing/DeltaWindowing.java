package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;

public class DeltaWindowing<I extends EventBean<Long>,O> extends FrameWindowing<I,Long,O>{

    private final String attribute;

    protected DeltaWindowing(I arg, String attribute, long threshold) {
        super();
        this.openPred = i -> frameState.isTsStartNull();
        this.updatePred = i -> Math.abs(frameState.getAuxiliaryValue()-arg.getValue(attribute)) > threshold;
        this.closePred = this.updatePred.negate();
        this.attribute = attribute;
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
        frameState.setAuxiliaryValue(arg.getValue(attribute));
    }

    @Override
    public void update(long ts, I arg) {
        candidateWindow.extend(ts);
    }

}
