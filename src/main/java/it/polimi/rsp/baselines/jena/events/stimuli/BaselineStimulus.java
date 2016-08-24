package it.polimi.rsp.baselines.jena.events.stimuli;

import it.polimi.rdf.RDFLine;
import it.polimi.streaming.Stimulus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jena.graph.Graph;

import java.util.HashMap;
import java.util.Set;

@NoArgsConstructor
public abstract class BaselineStimulus extends HashMap<String, Object> implements Stimulus {

	private static final long serialVersionUID = 1L;

	protected final String appTimestamp = "app_timestamp";
	protected final String sysTimestamp = "sys_timestamp";
	protected final String content = "content";

	@Setter
	@Getter
	private String stream_uri;

	public BaselineStimulus(long appTimestamp1, Object content1, String stream_uri) {
		this.put(appTimestamp, appTimestamp1);
		this.put(content, content1);
		this.put(sysTimestamp, System.currentTimeMillis());
		this.stream_uri = stream_uri;
	}

	public void setAppTimestamp(long ts) {
		this.put(appTimestamp, ts);
	}

	public long getAppTimestamp() {
		return this.containsKey(appTimestamp) && this.get(appTimestamp) != null ? (long) this.get(appTimestamp) : null;
	}

	public long getSysTimestamp() {
		return this.containsKey(sysTimestamp) && this.get(sysTimestamp) != null ? (long) this.get(sysTimestamp) : null;
	}

	public Object getContent() {
		return this.containsKey(content) && this.getContent() != null ? this.get(content) : null;
	}

	public abstract Graph addTo(Graph abox);

	public abstract Graph removeFrom(Graph abox);

	public abstract Set<RDFLine> serialize();

}
