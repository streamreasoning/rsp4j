package it.polimi.yasper.core.stream;

import com.espertech.esper.client.EPStatement;
import it.polimi.rspql.querying.ContinuousQuery;
import it.polimi.rspql.Stream;
import it.polimi.rspql.RSPEngine;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by riccardo on 10/07/2017.
 */

//TODO InstantaneousResponse
public class QueryStream extends StreamImpl {

    protected RSPEngine e;
    @NonNull
    protected String query_id;
    @Setter
    @Getter
    protected EPStatement streamStatemnt;
    protected ContinuousQuery q;

    public QueryStream(RSPEngine e, String stream_uri, EPStatement streamStatemnt) {
        super(stream_uri, streamStatemnt);
        this.e=e;
        this.query_id=stream_uri;
    }


    public void setRSPEngine(RSPEngine e) {
        this.e = e;
    }

    public RSPEngine getRSPEngine() {
        return e;
    }


    @Override
    public String getURI() {
        return query_id;
    }




}
