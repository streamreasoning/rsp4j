package org.streamreasoning.rsp4j.esper.secret;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.time.CurrentTimeEvent;
import org.streamreasoning.rsp4j.api.secret.time.ET;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.secret.time.TimeFactory;
import org.streamreasoning.rsp4j.api.secret.time.TimeInstant;


public class EsperTime implements Time {

    private final EPRuntime cepRT;
    private long t0;

    public EsperTime(EPRuntime cepRT, long t0) {
        this.cepRT = cepRT;
        this.t0=t0;
        setAppTime(this.t0);
    }

    @Override
    public long getScope() {
        return this.t0;
    }

    @Override
    public long getAppTime() {
        return cepRT.getCurrentTime();
    }

    @Override
    public void setAppTime(long now) {
        cepRT.sendEvent(new CurrentTimeEvent(now));
    }

    @Override
    public ET getEvaluationTimeInstants() {
        return TimeFactory.getEvaluationTimeInstants();
    }

    @Override
    public void addEvaluationTimeInstants(TimeInstant i) {
        TimeFactory.getEvaluationTimeInstants().add(i);
    }
}
