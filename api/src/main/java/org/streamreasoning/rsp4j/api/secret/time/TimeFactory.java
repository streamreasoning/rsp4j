package org.streamreasoning.rsp4j.api.secret.time;

public class TimeFactory {

    protected static Time time = null;
    protected static ET et = null;

    public static Time getInstance() {
        if (time == null) {
            time = new TimeImpl(0);
        }
        return time;
    }

    public static ET getEvaluationTimeInstants() {
        if (et == null) {
            et = new ET();
        }
        return et;
    }


}
