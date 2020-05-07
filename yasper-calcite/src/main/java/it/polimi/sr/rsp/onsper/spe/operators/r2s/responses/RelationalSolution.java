package it.polimi.sr.rsp.onsper.spe.operators.r2s.responses;


import it.polimi.yasper.core.querying.result.SolutionMapping;
import it.polimi.yasper.core.querying.result.SolutionMappingBase;

public class RelationalSolution extends SolutionMappingBase<RelationalSolution.Result> {


    public RelationalSolution(String id, long cep_timestamp, String label, String value) {
        super(id, System.currentTimeMillis(), cep_timestamp, wrap(label, value));
    }


    @Override
    public SolutionMapping<Result> difference(SolutionMapping<Result> r) {
        return null;
    }

    @Override
    public SolutionMapping<Result> intersection(SolutionMapping<Result> new_response) {
        return null;
    }

    public static class Result {
        private final String label, value;

        public Result(String label, String value) {
            this.label = label;
            this.value = value;
        }
    }

    public static Result wrap(String k, String v) {
        return new Result(k, v);
    }
}
