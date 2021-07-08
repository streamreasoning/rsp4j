package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;

public class DeltaWindowing<I extends EventBean<Long>> extends FrameWindowing<I,Long>{

    private final String attribute;

    public DeltaWindowing(String attribute, long threshold) {
        super();
        this.openPred = i -> frameState.isTsStartNull();
        this.updatePred = i -> {
            if (frameState.getAuxiliaryValue() == null) {
                return true;
            } else return Math.abs(frameState.getAuxiliaryValue()-i.getValue(attribute)) > threshold;
        };
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
