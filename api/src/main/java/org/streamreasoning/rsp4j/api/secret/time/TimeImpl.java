package org.streamreasoning.rsp4j.api.secret.time;

import java.util.ArrayList;
import java.util.List;

/**
 * Central control time. This implementation of time, represents
 * a global clock, streams must be aligned w.r.t. to app_time.
 **/
public class TimeImpl implements Time {

    public final long tc0;
    List<Long> et = new ArrayList<>();
    private Long app_time = 0L;
    ET timeInstants = new ET();

    public TimeImpl(long tc0) {
        this.tc0 = tc0;
    }

    @Override
    public long getScope() {
        return tc0;
    }

    @Override
    public synchronized long getAppTime() {
        return this.app_time;
    }

    @Override
    public synchronized void setAppTime(long now) {
        et.add(now);
        this.app_time = this.app_time < now ? now : this.app_time;
    }

    @Override
    public ET getEvaluationTimeInstants() {
        return timeInstants;
    }

    @Override
    public void addEvaluationTimeInstants(TimeInstant et) {
        timeInstants.add(et);
    }

    public static Time forStartTime(long startTime){
        return new TimeImpl(startTime);
    }
}
