package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;


import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.WindowImpl;

public class CandidateWindow extends WindowImpl {

    public CandidateWindow(long o, long c) {
        super(o, c);
        this.closed = false;
    }

    public void extend(long newEnd){
        this.c = newEnd;
    }

    public void close(){
        this.closed = true;
    }
}
