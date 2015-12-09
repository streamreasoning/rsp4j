package it.polimi.heaven.baselines.jena;

import it.polimi.heaven.baselines.jena.events.stimuli.GraphStimulus;
import it.polimi.heaven.core.teststand.EventProcessor;
import it.polimi.heaven.core.teststand.rspengine.events.Response;
import it.polimi.heaven.core.teststand.rspengine.events.Stimulus;
import lombok.extern.log4j.Log4j;

import com.espertech.esper.client.time.CurrentTimeEvent;

@Log4j
public class GraphBaseline extends JenaEngine {

<<<<<<< HEAD
	public GraphBaseline(RSPListener listener, EventProcessor<Response> collector) {
=======
	private long processing_duration;

	public GraphBaseline(EventProcessor<Response> collector) {
>>>>>>> 0545c1c... fixed some minors in event representation
		super(new GraphStimulus(), collector);
	}

	@Override
	public boolean process(Stimulus e) {
		this.currentEvent = e;
		GraphStimulus g = (GraphStimulus) e;
		cepRT.sendEvent(g, g.getStream_name());
		log.debug("Received Stimulus [" + g + "]");
		rspEventsNumber++;
		if (!this.internalTimerEnabled && currentTimestamp != g.getAppTimestamp()) {
			cepRT.sendEvent(new CurrentTimeEvent(currentTimestamp = g.getAppTimestamp()));
			log.debug("Sent time Event current runtime ts [" + currentTimestamp + "]");
		}
		return true;
	}
}
