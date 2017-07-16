package it.polimi.yasper.core.stream;

import it.polimi.rdf.RDFLine;
import it.polimi.yasper.core.query.InstantaneousItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Set;

@NoArgsConstructor
public abstract class StreamItem<T> extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    protected final String appTimestamp = "app_timestamp";
    protected final String sysTimestamp = "sys_timestamp";
    protected final String content = "content";

    @Setter
    @Getter
    private String stream_uri;

    public StreamItem(long appTimestamp1, Object content1, String stream_uri) {
        this.put(appTimestamp, appTimestamp1);
        this.put(content, content1);
        this.put(sysTimestamp, System.currentTimeMillis());
        this.stream_uri = stream_uri;
    }

    public long getAppTimestamp() {
        return this.containsKey(appTimestamp) && this.get(appTimestamp) != null ? (long) this.get(appTimestamp) : null;
    }

    public void setAppTimestamp(long ts) {
        this.put(appTimestamp, ts);
    }

    public long getSysTimestamp() {
        return this.containsKey(sysTimestamp) && this.get(sysTimestamp) != null ? (long) this.get(sysTimestamp) : null;
    }

    public Object getContent() {
        return this.containsKey(content) ? this.get(content) : null;
    }

    public T getTypedContent() {
        return this.containsKey(content) ? (T) this.get(content) : null;
    }

    public abstract InstantaneousItem addTo(InstantaneousItem abox);

    public abstract InstantaneousItem removeFrom(InstantaneousItem abox);

    public abstract Set<RDFLine> serialize();

    public abstract String getStreamURI();
}
