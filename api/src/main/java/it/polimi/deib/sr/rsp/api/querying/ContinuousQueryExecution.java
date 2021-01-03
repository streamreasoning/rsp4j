package it.polimi.deib.sr.rsp.api.querying;

import it.polimi.deib.sr.rsp.api.operators.r2r.RelationToRelationOperator;
import it.polimi.deib.sr.rsp.api.operators.r2s.RelationToStreamOperator;
import it.polimi.deib.sr.rsp.api.operators.s2r.execution.assigner.StreamToRelationOp;
import it.polimi.deib.sr.rsp.api.querying.result.SolutionMapping;
import it.polimi.deib.sr.rsp.api.sds.SDS;
import it.polimi.deib.sr.rsp.api.stream.data.WebDataStream;

import java.util.stream.Stream;

/**
 * Created by Riccardo on 12/08/16.
 */

public interface ContinuousQueryExecution<I, E1, E2> {

    WebDataStream<E2> outstream();

    ContinuousQuery query();

    SDS<E1> sds();

    StreamToRelationOp<I, E1>[] s2rs();

    RelationToRelationOperator<E2> r2r();

    RelationToStreamOperator<E2> r2s();

    void add(StreamToRelationOp<I, E1> op);

    Stream<SolutionMapping<E2>> eval(Long now);
}

