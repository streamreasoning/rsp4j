package org.streamreasoning.rsp4j.cqels;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;

public class CQELSConstructQueryExecutionRSP4J extends CQELSAbstractQueryExecution<Graph> {

    public CQELSConstructQueryExecutionRSP4J(ContinuousQuery q, DataStream<Graph> outputStream){
        super(q,outputStream);
    }

}
