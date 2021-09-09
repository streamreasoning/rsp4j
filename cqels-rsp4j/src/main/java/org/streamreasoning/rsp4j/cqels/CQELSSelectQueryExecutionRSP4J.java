package org.streamreasoning.rsp4j.cqels;

import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;

public class CQELSSelectQueryExecutionRSP4J extends CQELSAbstractQueryExecution<Binding> {



    public CQELSSelectQueryExecutionRSP4J(ContinuousQuery q, DataStream<Binding> outputStream){
        super(q,outputStream);
    }


}
