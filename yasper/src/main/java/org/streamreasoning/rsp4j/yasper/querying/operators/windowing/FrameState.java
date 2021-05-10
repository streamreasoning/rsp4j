package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;

public class FrameState<V> {

    private long count;
    private long tsStart;
    private V[] boundaries;
    private int boundary = 0;
    private V auxiliaryValue;
    private boolean first = true;
    private boolean tsStartNull = true;


    public V getBoundary(int i) {
        return boundaries[i];
    }

    public void initBoundaries(V[] boundaries) {
        this.boundaries = boundaries;
    }

    public void setCurrentBoundary(int boundary) {
        this.boundary = boundary;
    }

    public boolean compareCurrentBoundary(int i) {
        return i == boundary || first;
    }

    public int getCurrentBoundary() {
        return boundary;
    }

    public long getCount() {
        return count;
    }

    public long getTsStart() {
        return tsStart;
    }

    public V getAuxiliaryValue() {
        return auxiliaryValue;
    }

    public boolean isTsStartNull() {
        return tsStartNull;
    }

    public void setTsStart(long tsStart) {
        this.tsStart = tsStart;
        this.tsStartNull = false;
    }

    public void resetTsStartNull(){
        this.tsStartNull = true;
    }

    public void setAuxiliaryValue(V auxiliaryValue) {
        this.auxiliaryValue = auxiliaryValue;
    }

    public void incrementCounter(){
        this.count++;
    }

    public void resetCounter(){
        this.count = 0L;
    }

}
