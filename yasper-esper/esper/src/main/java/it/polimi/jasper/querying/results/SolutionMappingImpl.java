package it.polimi.jasper.querying.results;

import it.polimi.yasper.core.querying.result.SolutionMapping;
import it.polimi.yasper.core.querying.result.SolutionMappingBase;
import lombok.Getter;
import lombok.extern.java.Log;

import java.util.List;

@Log
@Getter
public final class SolutionMappingImpl<T> extends SolutionMappingBase<T> {

    private final List<String> result_vars;

    public SolutionMappingImpl(String id, T results, List<String> resultVars, long cep_timestamp) {
        super(id, System.currentTimeMillis(), cep_timestamp, results);
        this.result_vars = resultVars;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolutionMappingImpl<T> response = (SolutionMappingImpl<T>) o;
        return this.get().equals(response.get());
    }

    @Override
    public int hashCode() {
        return result_vars != null ? result_vars.hashCode() : 0;
    }

    @Override
    public SolutionMapping<T> difference(SolutionMapping<T> r) {
        //todo
        return null;
    }

    @Override
    public SolutionMapping<T> intersection(SolutionMapping<T> new_response) {
        //todo
        return null;
    }
}
