package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;

import java.util.Comparator;

public class FrameState<V> {

    private long count;
    private long tsStart;
    private V[] boundaries;
    private int boundary = -1;
    private V auxiliaryValue;
    private boolean first = true;
    private boolean tsStartNull = true;

    public boolean isFirst() {
        return first;
    }

    public V getBoundary(int i) {
        if(i<0 || i>boundaries.length-1)
            return boundaries[0];
        else return boundaries[i];
    }

    public void initBoundaries(V[] boundaries) {
        this.boundaries = boundaries;
    }

    public void setCurrentBoundary(int boundary) {
        this.boundary = boundary;
        first = false;
    }

    public boolean compareCurrentBoundary(int i) {
        return i == boundary;
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
        this.first = false;
    }

    public void incrementCounter(){
        this.count++;
    }

    public void resetCounter(){
        this.count = 0L;
    }

}
