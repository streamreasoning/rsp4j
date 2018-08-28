package it.polimi.yasper.core.spe.operators.s2r.execution.assigner;


import it.polimi.yasper.core.rspql.timevarying.TimeVarying;
import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.operators.r2r.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.spe.operators.s2r.execution.instance.Window;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.tick.Tick;
import it.polimi.yasper.core.spe.time.Time;

import java.util.List;

/*
 * I represents the variable type of the input, e.g., RDF TRIPLE, RDF GRAPHS OR TUPLE
 * O represents the variable type of the maintained status, e.g., BAG of RDF Triple, RDF Graph (set) or RELATION
 * */
public interface WindowAssigner<I, O> {

    Report report();

    Tick tick();

    Time time();

    Content<O> getContent(long now);

    List<Content<O>> getContents(long now);

    TimeVarying<O> set(ContinuousQueryExecution content);

    void notify(I arg, long ts);

    String iri();

    boolean named();

    Content<O> compute(long t_e, Window w);

}
