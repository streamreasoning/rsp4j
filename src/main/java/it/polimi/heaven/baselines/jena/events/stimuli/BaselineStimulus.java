package it.polimi.heaven.baselines.jena.events.stimuli;

import it.polimi.heaven.core.ts.data.TripleContainer;
import it.polimi.heaven.core.ts.events.engine.Stimulus;

import java.util.HashMap;
import java.util.Set;

import lombok.extern.log4j.Log4j;

import com.hp.hpl.jena.graph.Graph;

@Log4j
public abstract class BaselineStimulus extends HashMap<String, Object> implements Stimulus {

	private static final long serialVersionUID = 1L;

	protected final String appTimestamp = "app_timestamp";
	protected final String sysTimestamp = "sys_timestamp";
	protected final String content = "content";

	public BaselineStimulus(Class<?> type) {
		this.put(appTimestamp, long.class);
		this.put(sysTimestamp, long.class);
		this.put("content", type);
	}

	public BaselineStimulus(long appTimestamp1, long sysTimestamp1, Object content1) {
		this.put(appTimestamp, appTimestamp1);
		this.put(sysTimestamp, sysTimestamp1);
		this.put(content, content1);
	}

	public BaselineStimulus(long sysTimestamp1, Object content1) {
		this.put(sysTimestamp, sysTimestamp1);
		this.put(content, content1);
	}

	public void setAppTimestamp(long ts) {
		this.put(appTimestamp, ts);
	}

	public long getAppTimestamp() {
		return (long) this.get(appTimestamp);
	}

	public long getSysTimestamp() {
		return (long) this.get(sysTimestamp);
	}

	public Object getContent() {
		log.info(this.get(content));
		return this.get(content);
	}

	public abstract Graph addTo(Graph abox);

	public abstract Graph removeFrom(Graph abox);

	public abstract Set<TripleContainer> serialize();

}
