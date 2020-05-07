package it.polimi.sr.rsp.onsper.spe.operators.r2s.responses;

import it.polimi.yasper.core.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.querying.result.SolutionMapping;

public class JRStream<T> implements RelationToStreamOperator<T> {

    @Override
    public T eval(SolutionMapping<T> last_response, long ts) {
        return last_response.get();
    }
}