package it.polimi.deib.sr.rsp.api.format;

import it.polimi.deib.sr.rsp.api.querying.ContinuousQueryExecution;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Observer;

/**
 * Created by riccardo on 03/07/2017.
 */

@Getter
@RequiredArgsConstructor
public abstract class QueryResultFormatter implements Observer {

    protected final String format;
    protected final boolean distinct;
    protected ContinuousQueryExecution cqe;

}
