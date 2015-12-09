package it.polimi.heaven.baselines.jena;

import it.polimi.heaven.baselines.jena.events.stimuli.StatementStimulus;
import it.polimi.heaven.core.teststand.EventProcessor;
import it.polimi.heaven.core.teststand.rspengine.events.Response;
import it.polimi.heaven.core.teststand.rspengine.events.Stimulus;
import lombok.extern.log4j.Log4j;

import com.espertech.esper.client.time.CurrentTimeEvent;

@Log4j
public class StatementBaseline extends JenaEngine {

<<<<<<< HEAD
	public StatementBaseline(RSPListener listener, EventProcessor<Response> collector) {
=======
	private long processing_duration;

	public StatementBaseline(EventProcessor<Response> collector) {
>>>>>>> 0545c1c... fixed some minors in event representation
		super(new StatementStimulus(), collector);
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
