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

    @Override
    public String toString() {
        return "{" +
                "appTimestamp='" + appTimestamp + '\'' +
                ", sysTimestamp='" + sysTimestamp + '\'' +
                ", content='" + getContent() + '\'' +
                ", stream_uri='" + stream_uri + '\'' +
                '}';
    }

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
