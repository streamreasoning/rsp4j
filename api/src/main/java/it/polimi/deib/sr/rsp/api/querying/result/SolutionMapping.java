package it.polimi.deib.sr.rsp.api.querying.result;

public interface SolutionMapping<I> {
    long getCreationTime();

    I get();

    SolutionMapping<I> difference(SolutionMapping<I> r);

    SolutionMapping<I> intersection(SolutionMapping<I> new_response);

}
