package org.streamreasoning.rsp4j.api.format;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;

/**
 * Created by riccardo on 03/07/2017.
 */

public abstract class QueryResultFormatter<O> implements Consumer<O> {

    protected final String format;
    protected final boolean distinct;
    protected ContinuousQueryExecution cqe;

    public QueryResultFormatter(String format, boolean distinct) {
        this.format = format;
        this.distinct = distinct;
    }

    public String getFormat() {
        return this.format;
    }

    public boolean isDistinct() {
        return this.distinct;
    }

    public ContinuousQueryExecution getCqe() {
        return this.cqe;
    }
}
