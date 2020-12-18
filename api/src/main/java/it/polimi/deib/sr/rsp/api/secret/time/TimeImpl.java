package it.polimi.deib.sr.rsp.api.secret.time;

/**
 * Central control time. This implementation of time, represents
 * a global clock, streams must be aligned w.r.t. to app_time.
 **/
public class TimeImpl implements Time {

    private Long app_time = 0L;
    public final long tc0;

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
        this.app_time = this.app_time < now ? now : this.app_time;
    }

    @Override
    public ET getEvaluationTimeInstants() {
        return TimeFactory.getEvaluationTimeInstants();
    }

    @Override
    public void addEvaluationTimeInstants(TimeInstant et) {
        TimeFactory.getEvaluationTimeInstants().add(et);
    }
}
