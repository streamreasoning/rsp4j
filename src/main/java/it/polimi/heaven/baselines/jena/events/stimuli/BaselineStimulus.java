package it.polimi.heaven.baselines.jena.events;

import it.polimi.heaven.core.ts.events.TripleContainer;

import java.util.HashMap;
import java.util.Set;

import com.hp.hpl.jena.graph.Graph;

public abstract class BaselineEvent extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;
	protected final String appTimestamp = "app_timestamp";
	protected final String sysTimestamp = "sys_timestamp";
	protected final String content = "content";

	public BaselineEvent(Class<?> type) {
		this.put(appTimestamp, long.class);
		this.put(sysTimestamp, long.class);
		this.put("content", type);
	}

	public BaselineEvent(long appTimestamp1, long sysTimestamp1, Object content1) {
		this.put(appTimestamp, appTimestamp1);
		this.put(sysTimestamp, sysTimestamp1);
		this.put(content, content1);
	}

	public long getAppTimestamp() {
		return (long) this.get(appTimestamp);
	}

	public long getSysTimestamp() {
		return (long) this.get(sysTimestamp);
	}

	public abstract Graph addTo(Graph abox);

	public abstract Graph removeFrom(Graph abox);

	public abstract Set<TripleContainer> serialize();

}
