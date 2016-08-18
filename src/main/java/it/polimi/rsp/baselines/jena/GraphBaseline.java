package it.polimi.rsp.baselines.jena;

import com.espertech.esper.client.time.CurrentTimeEvent;
import it.polimi.heaven.core.teststand.EventProcessor;
import it.polimi.heaven.core.teststand.rsp.data.Response;
import it.polimi.heaven.core.teststand.rsp.data.Stimulus;
import it.polimi.rsp.baselines.jena.events.stimuli.GraphStimulus;
import lombok.extern.log4j.Log4j;

@Log4j
public class GraphBaseline extends JenaEngine {

    public GraphBaseline(EventProcessor<Response> collector,String provider) {
        super(new GraphStimulus(), collector, 0,provider);
    }

    @Override
    public boolean process(Stimulus e) {
        log.info("Current runtime is  [" + cepRT.getCurrentTime() + "]");
        this.currentEvent = e;
        GraphStimulus g = (GraphStimulus) e;
        if (!this.internalTimerEnabled && cepRT.getCurrentTime() < g.getAppTimestamp()) {
            log.info("Sent time event with current [" + (g.getAppTimestamp()) + "]");
            cepRT.sendEvent(new CurrentTimeEvent(g.getAppTimestamp()));
            currentTimestamp = g.getAppTimestamp();// TODO
            log.info("Current runtime is now [" + cepRT.getCurrentTime() + "]");
        }
        cepRT.sendEvent(g, g.getStream_name());
        log.info("Received Stimulus [" + g + "]");
        rspEventsNumber++;
        log.info("Current runtime is  [" + cepRT.getCurrentTime() + "]");
        return true;
    }
}
