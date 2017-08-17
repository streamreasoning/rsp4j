package it.polimi.yasper.core.stream;

import com.espertech.esper.client.EPStatement;
import it.polimi.yasper.core.engine.RSPEngine;
import it.polimi.yasper.core.query.ContinuousQuery;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by riccardo on 10/07/2017.
 */
public class QueryStream extends StreamImpl {

    protected RSPEngine e;
    @NonNull
    protected String query_id;
    @Setter
    @Getter
    protected EPStatement streamStatemnt;
    protected ContinuousQuery q;

    public QueryStream(RSPEngine e, String stream_uri, EPStatement streamStatemnt) {
        super(stream_uri);
        this.e = e;
        this.streamStatemnt = streamStatemnt;
    }

    public QueryStream(RSPEngine e, String stream_uri) {
        super(stream_uri);
        this.query_id = stream_uri;
        this.e = e;
    }

    public void setRSPEngine(RSPEngine e) {
        this.e = e;
    }

    public RSPEngine getRSPEngine() {
        return e;
    }

}
