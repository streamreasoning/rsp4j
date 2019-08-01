package it.polimi.yasper.core.operators.s2r.execution.assigner;


import it.polimi.yasper.core.sds.timevarying.TimeVarying;
import it.polimi.yasper.core.secret.content.Content;
import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.operators.s2r.execution.instance.Window;
import it.polimi.yasper.core.secret.report.Report;
import it.polimi.yasper.core.enums.Tick;
import it.polimi.yasper.core.secret.time.Time;

import java.util.List;

/*
 * I represents the variable type of the input, e.g., RDF TRIPLE, RDF GRAPHS OR TUPLE
 * O represents the variable type of the maintained status, e.g., BAG of RDF Triple, RDF Graph (set) or RELATION
 * */
public interface Assigner<I, O> extends Consumer<I> {

    Report report();

    Tick tick();

    Time time();

    Content<O> getContent(long now);

    List<Content<O>> getContents(long now);

    TimeVarying<O> set(ContinuousQueryExecution content);

    String iri();

    boolean named();

    Content<O> compute(long t_e, Window w);

}
