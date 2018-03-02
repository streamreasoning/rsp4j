package it.polimi.yasper.core.query.response;

import it.polimi.rspql.ContinuousQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class InstantaneousResponse implements Response {

    private String id;
    private long creation_timestamp, cep_timestamp;
    private ContinuousQuery query;

    @Override
    public long getCreationTime() {
        return creation_timestamp;
    }

    @Override
    public String getQueryString() {
        return query.toString();
    }

    public abstract InstantaneousResponse difference(InstantaneousResponse r);

    public abstract InstantaneousResponse intersection(InstantaneousResponse new_response);
}
