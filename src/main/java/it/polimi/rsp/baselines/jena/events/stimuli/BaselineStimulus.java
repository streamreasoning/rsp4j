package it.polimi.rsp.baselines.jena.events.stimuli;

import it.polimi.heaven.core.teststand.data.RDFLine;
import it.polimi.heaven.core.teststand.rspengine.events.Stimulus;

import java.util.HashMap;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import org.apache.jena.graph.Graph;


public abstract class BaselineStimulus extends HashMap<String, Object> implements Stimulus {

	private static final long serialVersionUID = 1L;

	protected final String appTimestamp = "app_timestamp";
	protected final String sysTimestamp = "sys_timestamp";
	protected final String content = "content";

	@Setter
	@Getter
	private String stream_name;

	public BaselineStimulus(Class<?> type) {
		this.put(appTimestamp, long.class);
		this.put(sysTimestamp, long.class);
		this.put("content", type);
	}

	public BaselineStimulus(long appTimestamp1, Object content1, String stream_name) {
		this.put(appTimestamp, appTimestamp1);
		this.put(content, content1);
		this.put(sysTimestamp, System.currentTimeMillis());
		this.stream_name = stream_name;
	}

	public BaselineStimulus(long sysTimestamp1, Object content1) {
		this.put(sysTimestamp, sysTimestamp1);
		this.put(sysTimestamp, System.currentTimeMillis());
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
		return this.get(content);
	}

	public abstract Graph addTo(Graph abox);

	public abstract Graph removeFrom(Graph abox);

	public abstract Set<RDFLine> serialize();

}
