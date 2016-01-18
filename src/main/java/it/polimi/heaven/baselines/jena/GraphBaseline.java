package it.polimi.heaven.baselines.jena;

import it.polimi.heaven.baselines.jena.events.stimuli.GraphStimulus;
import it.polimi.heaven.core.teststand.EventProcessor;
import it.polimi.heaven.core.teststand.rspengine.events.Response;
import it.polimi.heaven.core.teststand.rspengine.events.Stimulus;
import lombok.extern.log4j.Log4j;

import com.espertech.esper.client.time.CurrentTimeEvent;

@Log4j
public class GraphBaseline extends JenaEngine {

	public GraphBaseline(EventProcessor<Response> collector) {
		super(new GraphStimulus(), collector);
	}

	@Override
	public boolean process(Stimulus e) {
		this.currentEvent = e;
		GraphStimulus g = (GraphStimulus) e;
		if (!this.internalTimerEnabled && cepRT.getCurrentTime() < g.getAppTimestamp()) {
			log.info("Sent time Event current runtime ts [" + g.getAppTimestamp() + "]");
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
