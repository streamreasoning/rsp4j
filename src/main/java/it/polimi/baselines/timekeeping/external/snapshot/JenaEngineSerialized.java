package it.polimi.baselines.timekeeping.external.snapshot;

import it.polimi.baselines.JenaEngine;
import it.polimi.processing.EventProcessor;
import it.polimi.processing.events.CTEvent;
import it.polimi.processing.events.TripleContainer;
import it.polimi.processing.rspengine.abstracts.RSPListener;
import it.polimi.processing.rspengine.rspevents.jena.SerializedEvent;
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

	public JenaEngineSerialized(String name, EventProcessor<CTEvent> collector, RSPListener listener) {
		super(name, collector, listener, WindowUtils.JENA_INPUT_QUERY_SNAPTSHOT);

		cepConfig = new Configuration();
		cepConfig.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
		log.info("Added [" + SerializedEvent.class + "] as TEvent");
		cepConfig.addEventType("TEvent", SerializedEvent.class.getName());

	}

	@Override
	protected void handleEvent(CTEvent e) {
		super.handleEvent(e);
		for (TripleContainer tc : e.getEventTriples()) {
			String[] t = tc.getTriple();
			esperEventsNumber++;
			cepRT.sendEvent(new SerializedEvent(t[0], t[1], t[2], cepRT.getCurrentTime(), System.currentTimeMillis()));
		}
	}

}
