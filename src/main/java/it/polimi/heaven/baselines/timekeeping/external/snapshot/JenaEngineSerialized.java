package it.polimi.heaven.baselines.timekeeping.external.snapshot;

import it.polimi.heaven.baselines.JenaEngine;
import it.polimi.heaven.baselines.RSPListener;
import it.polimi.heaven.baselines.events.jena.SerializedEvent;
import it.polimi.heaven.core.ts.EventProcessor;
import it.polimi.heaven.core.ts.events.Stimulus;
import it.polimi.heaven.core.ts.events.TripleContainer;
import it.polimi.utils.WindowUtils;
import lombok.extern.log4j.Log4j;

import com.espertech.esper.client.Configuration;

/**
 * In this example rdfs property of subclass of is exploited by external static
 * functions which can be called form EPL No data or time windows are considered
 * in event consuming, se the related example for that time is externally
 * controlled all event are sent in the samte time interval
 * 
 * the query doesn't include joins
 * 
 * events are pushed, on incoming events, in 3 differents queue which are pulled
 * by refering statements
 * 
 * **/
@Log4j
public class JenaEngineSerialized extends JenaEngine {

	public JenaEngineSerialized(String name, EventProcessor<Stimulus> collector, RSPListener listener) {
		super(name, collector, listener, WindowUtils.JENA_INPUT_QUERY_SNAPTSHOT);

		cepConfig = new Configuration();
		cepConfig.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
		log.info("Added [" + SerializedEvent.class + "] as TEvent");
		cepConfig.addEventType("TEvent", SerializedEvent.class.getName());

	}

	@Override
	protected void handleEvent(Stimulus e) {
		super.handleEvent(e);
		for (TripleContainer tc : e.getEventTriples()) {
			String[] t = tc.getTriple();
			esperEventsNumber++;
			cepRT.sendEvent(new SerializedEvent(t[0], t[1], t[2], cepRT.getCurrentTime(), System.currentTimeMillis()));
		}
	}

}
