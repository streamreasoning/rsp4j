package it.polimi.heaven.baselines.jena;

import it.polimi.heaven.baselines.esper.RSPListener;
import it.polimi.heaven.baselines.jena.events.stimuli.GraphStimulus;
import it.polimi.heaven.core.ts.EventProcessor;
import it.polimi.heaven.core.ts.events.engine.Response;
import it.polimi.heaven.core.ts.events.engine.Stimulus;
import lombok.extern.log4j.Log4j;

import com.espertech.esper.client.time.CurrentTimeEvent;

@Log4j
public class GraphBaseline extends JenaEngine {

	public GraphBaseline(RSPListener listener, EventProcessor<Response> collector) {
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
