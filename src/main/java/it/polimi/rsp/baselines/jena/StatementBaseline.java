package it.polimi.rsp.baselines.jena;

import com.espertech.esper.client.time.CurrentTimeEvent;
import it.polimi.heaven.core.teststand.EventProcessor;
import it.polimi.heaven.core.teststand.rsp.data.Response;
import it.polimi.heaven.core.teststand.rsp.data.Stimulus;
import it.polimi.rsp.baselines.jena.events.stimuli.StatementStimulus;
import lombok.extern.log4j.Log4j;

@Log4j
public class StatementBaseline extends JenaEngine {

    public StatementBaseline(EventProcessor<Response> collector,String provider) {
        super(new StatementStimulus(), collector, 0, provider);
    }

    @Override
    public boolean process(Stimulus e) {
        this.currentEvent = e;
        StatementStimulus s = (StatementStimulus) e;
        cepRT.sendEvent(s, s.getStream_name());
        log.debug("Received Stimulus [" + s + "]");
        rspEventsNumber++;
        if (!this.internalTimerEnabled && currentTimestamp != s.getAppTimestamp()) {
            cepRT.sendEvent(new CurrentTimeEvent(currentTimestamp = s.getAppTimestamp()));
            log.debug("Sent time Event current runtime ts [" + currentTimestamp + "]");
        }
        return true;
    }

}
