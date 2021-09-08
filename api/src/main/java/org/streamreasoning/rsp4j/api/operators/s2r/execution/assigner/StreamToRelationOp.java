package org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner;


import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.Window;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;

import java.util.List;

/*
 * I represents the variable type of the input, e.g., RDF TRIPLE, RDF GRAPHS OR TUPLE
 * O represents the variable type of the maintained status, e.g., BAG of RDF Triple, RDF Graph (set) or RELATION
 * */

public interface StreamToRelationOp<I, W> extends Consumer<I> {

    Report report();

    Tick tick();

    Time time();

    ReportGrain grain();

    Content<I, W> content(long now);

    List<Content<I, W>> getContents(long now);

    TimeVarying<W> get();

    String iri();

    default boolean named() {
        return iri() != null;
    }

    Content<I, W> compute(long t_e, Window w);

    StreamToRelationOp<I, W> link(ContinuousQueryExecution<I, W, ?, ?> context);

    TimeVarying<W> apply(DataStream<I> s);
}
