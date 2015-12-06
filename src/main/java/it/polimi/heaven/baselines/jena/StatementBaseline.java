package it.polimi.heaven.baselines.jena;

import it.polimi.heaven.baselines.esper.RSPListener;
import it.polimi.heaven.baselines.jena.abstracts.JenaEngine;
import it.polimi.heaven.baselines.jena.events.stimuli.StatementStimulus;
import it.polimi.heaven.core.ts.EventProcessor;
import it.polimi.heaven.core.ts.events.engine.Response;
import it.polimi.heaven.core.ts.events.engine.Stimulus;
import lombok.extern.log4j.Log4j;

import com.espertech.esper.client.time.CurrentTimeEvent;

@Log4j
public class StatementBaseline extends JenaEngine {

	public StatementBaseline(RSPListener listener, EventProcessor<Response> collector) {
		super(new StatementStimulus(), collector);
	}

	@Override
	public boolean process(Stimulus e) {
		this.currentEvent = e;
		StatementStimulus s = (StatementStimulus) e;
		cepRT.sendEvent(s, s.getStream_name());
		log.info("Received Stimulus [" + s + "]");
		rspEventsNumber++;
		if (!this.internalTimerEnabled && currentTimestamp != s.getAppTimestamp()) {
			cepRT.sendEvent(new CurrentTimeEvent(currentTimestamp = s.getAppTimestamp()));
			log.info("Sent time Event current runtime ts [" + currentTimestamp + "]");
		}
		return true;
	}

}
