package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;


/**
 *
 * @param <I>
 * @param <V>
 */
public class BoundaryWindowing<I extends EventBean<V>, V extends Comparable<V>> extends FrameWindowing<I,V>{

    public BoundaryWindowing(V[] boundaries, String attribute) {
        super();
        this.frameState.initBoundaries(boundaries);
        this.openPred = i -> {
            for (int j = 0; j < boundaries.length; j++) {
                if (j == boundaries.length - 1) {
                    if (i.getValue(attribute).compareTo(frameState.getBoundary(j)) > 0
                            && (!frameState.compareCurrentBoundary(j) || frameState.isFirst())) {
                        frameState.setCurrentBoundary(j);
                        return true;
                    }
                } else if (i.getValue(attribute).compareTo(frameState.getBoundary(j)) > 0 &&
                        i.getValue(attribute).compareTo(frameState.getBoundary(j + 1)) < 0
                        && (!frameState.compareCurrentBoundary(j) || frameState.isFirst())) {
                    frameState.setCurrentBoundary(j);
                    return true;
                }
            }
            return false;
        };
        this.updatePred = i -> i.getValue(attribute).compareTo(frameState.getBoundary(frameState.getCurrentBoundary())) > 0 &&
                i.getValue(attribute).compareTo(frameState.getBoundary(frameState.getCurrentBoundary() + 1)) < 0 && !frameState.isFirst();
        this.closePred = i -> {
            if (frameState.getCurrentBoundary() + 1 == boundaries.length - 1)
                return i.getValue(attribute).compareTo(frameState.getBoundary(frameState.getCurrentBoundary())) < 0 && !frameState.isFirst();
            else
                return (i.getValue(attribute).compareTo(frameState.getBoundary(frameState.getCurrentBoundary() + 1)) >= 0 ||
                        i.getValue(attribute).compareTo(frameState.getBoundary(frameState.getCurrentBoundary())) < 0) && !frameState.isFirst();
        };
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
    }


    @Override
    public void update(long ts, I arg) {
        candidateWindow.extend(ts);
    }

}
