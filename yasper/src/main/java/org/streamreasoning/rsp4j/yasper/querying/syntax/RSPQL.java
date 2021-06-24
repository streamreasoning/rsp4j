package org.streamreasoning.rsp4j.yasper.querying.syntax;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;

public interface RSPQL<O> extends ContinuousQuery<Graph, Graph, Binding, O> {
}
