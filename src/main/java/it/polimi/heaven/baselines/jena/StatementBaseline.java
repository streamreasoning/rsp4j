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
		StatementStimulus s = (StatementStimulus) e;
		s.setAppTimestamp(cepRT.getCurrentTime());
		cepRT.sendEvent(s);
		this.currentEvent = e;
		currentTimestamp = s.getAppTimestamp();
		rspEventsNumber++;
		if (!this.internalTimerEnabled) {
			log.info("Sent time Event current runtime ts [" + currentTimestamp + "]");
			cepRT.sendEvent(new CurrentTimeEvent(currentTimestamp));
		}
		return true;
	}

}
