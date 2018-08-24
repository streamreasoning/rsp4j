package it.polimi.yasper.core.spe.operators.r2s.result;

import it.polimi.yasper.core.spe.operators.r2r.ContinuousQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class InstantaneousResult implements Result {

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

    public abstract InstantaneousResult difference(InstantaneousResult r);

    public abstract InstantaneousResult intersection(InstantaneousResult new_response);
}
