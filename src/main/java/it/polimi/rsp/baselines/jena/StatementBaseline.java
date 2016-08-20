package it.polimi.rsp.baselines.jena;

import com.espertech.esper.client.time.CurrentTimeEvent;
import it.polimi.heaven.core.teststand.EventProcessor;
import it.polimi.heaven.core.teststand.rsp.Receiver;
import it.polimi.heaven.core.teststand.rsp.data.Response;
import it.polimi.heaven.core.teststand.rsp.data.Stimulus;
import it.polimi.rsp.baselines.jena.events.stimuli.StatementStimulus;
import lombok.extern.log4j.Log4j;

@Log4j
public class StatementBaseline extends JenaEngine {

    public StatementBaseline(Receiver collector, String provider) {
        super(new StatementStimulus(), collector, 0, provider);
    }

    public boolean process(Stimulus e) {
        log.info("Current runtime is  [" + cepRT.getCurrentTime() + "]");
        this.currentEvent = e;
        StatementStimulus s = (StatementStimulus) e;
        if (!this.internalTimerEnabled && cepRT.getCurrentTime() < s.getAppTimestamp()) {
            log.info("Sent time event with current [" + (s.getAppTimestamp()) + "]");
            cepRT.sendEvent(new CurrentTimeEvent(s.getAppTimestamp()));
            currentTimestamp = s.getAppTimestamp();// TODO
            log.info("Current runtime is now [" + cepRT.getCurrentTime() + "]");
        }
        cepRT.sendEvent(s, s.getStream_uri());
        log.info("Received Stimulus [" + s + "]");
        rspEventsNumber++;
        log.info("Current runtime is  [" + cepRT.getCurrentTime() + "]");
        return true;
    }
}
