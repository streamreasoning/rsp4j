package it.polimi.heaven.baselines;

import it.polimi.heaven.core.enums.ExecutionState;
import it.polimi.heaven.core.ts.EventProcessor;
import it.polimi.heaven.core.ts.events.Stimulus;
import it.polimi.heaven.core.ts.rspengine.RSPEngine;
import it.polimi.utils.WindowUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.ConfigurationMethodRef;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.time.CurrentTimeEvent;

@Getter
@Log4j
public abstract class RSPEsperEngine implements RSPEngine {

	protected Configuration cepConfig;
	protected EPServiceProvider cep;
	protected EPRuntime cepRT;
	protected EPAdministrator cepAdm;
	protected ConfigurationMethodRef ref;

	protected ExecutionState status;
	protected EventProcessor<Stimulus> next;

	protected String name;

	@Setter
	protected Stimulus currentEvent = null;
	protected long sentTimestamp;

	protected int windowShots = 0, snapshots = 0, time = 1, registrationTime = 0, rspEventsNumber = 0, esperEventsNumber = 0;

	public RSPEsperEngine(String name, EventProcessor<Stimulus> next) {
		this.next = next;
		this.name = name;
	}

	/**
	 * 
	 * Moves time forward of an given amount of time
	 * 
	 * @param delta
	 *            , Must be greater than zero
	 */
	public void moveTime(long delta) {
		this.time += delta;
		cepRT.sendEvent(new CurrentTimeEvent(time));
		log.debug("Sent time Event current runtime ts [" + time + "]");
	}

	/**
	 * Moves time forward of exactly one time slot, represented by the time value of the Output
	 * clause of Esper
	 */
	public void moveTime() {
		this.time += WindowUtils.beta;
		snapshots++;
		cepRT.sendEvent(new CurrentTimeEvent(time));
		windowShots += time % WindowUtils.omega == 0 ? 1 : 0;
		log.debug("Sent time Event current runtime ts [" + time + "]");
	}

	/**
	 * Moves time forward of exactly n time slots, represented by the time value of the Output
	 * clause of Esper
	 */
	public void moveTiveOfSlot(int n) {
		time += n * WindowUtils.beta;
		cepRT.sendEvent(new CurrentTimeEvent(time));
		windowShots += time % WindowUtils.omega == 0 ? 1 : 0;
		log.debug("Sent time Event current runtime ts [" + time + "]");
	}

	/**
	 * Moves time forward of exactly one window
	 */
	public void moveTimeWindow() {
		time += WindowUtils.omega;
		windowShots++;
		cepRT.sendEvent(new CurrentTimeEvent(time));
		log.debug("Sent time Event current runtime ts [" + time + "]");
	}

	/**
	 * Initialize Esper internal clock
	 */
	protected void resetTime() {
		cepRT.sendEvent(new CurrentTimeEvent(registrationTime));
	}

	protected boolean isStartable() {
		return ExecutionState.READY.equals(status) || ExecutionState.CLOSED.equals(status);
	}

	protected boolean isOn() {
		return ExecutionState.READY.equals(status);
	}

	protected boolean isReady() {
		return ExecutionState.READY.equals(status);
	}

	@Override
	public int getEventNumber() {
		return rspEventsNumber;
	}

	@Override
	public abstract void timeProgress();
}
