package org.streamreasoning.rsp4j.api.querying;


import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;

import java.util.Map;

/**
 * TODO: This interface needs to be updated to contain setter and getters for all relevant query parts.
 */
public interface ContinuousQuery<I, W, R, O> {//extends Task<I, Binding, O> {

    void addNamedWindow(String streamUri, WindowNode wo);

    void setIstream();

    void setRstream();

    void setDstream();

    boolean isIstream();

    boolean isRstream();

    boolean isDstream();

    void setSelect();

    void setConstruct();

    boolean isSelectType();

    boolean isConstructType();

    void setOutputStream(String uri);

    DataStream<O> getOutputStream();

    String getID();

    Map<? extends WindowNode, DataStream<I>> getWindowMap();

    Time getTime();

    RelationToRelationOperator<W, R> r2r();

    StreamToRelationOp<I, W>[] s2r();

    RelationToStreamOperator<R,O> r2s();

}
