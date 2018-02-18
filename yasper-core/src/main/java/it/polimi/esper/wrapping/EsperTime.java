package it.polimi.esper.wrapping;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.time.CurrentTimeEvent;
import it.polimi.spe.time.ET;
import it.polimi.spe.time.Time;
import it.polimi.spe.time.TimeFactory;

public class EsperTime implements Time {

    private final EPRuntime cepRT;

    public EsperTime(EPRuntime cepRT) {
        this.cepRT = cepRT;
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
}
