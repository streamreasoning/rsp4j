package it.polimi.deib.sr.rsp.api.format;

import it.polimi.deib.sr.rsp.api.operators.s2r.execution.assigner.Consumer;
import it.polimi.deib.sr.rsp.api.querying.ContinuousQueryExecution;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Observer;

/**
 * Created by riccardo on 03/07/2017.
 */

@Getter
@RequiredArgsConstructor
public abstract class QueryResultFormatter<O> implements Consumer<O> {

    protected final String format;
    protected final boolean distinct;
    protected ContinuousQueryExecution cqe;

}
