package org.streamreasoning.rsp4j.abstraction.functions;

import org.apache.commons.rdf.api.RDFTerm;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMappingBase;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.BindingImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Var;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarImpl;

import java.util.Collection;

public class CountFunction implements AggregationFunction<Binding> {


    @Override
    public SolutionMapping<Binding> evaluate(String variableName, String outputname, Collection<SolutionMapping<Binding>> collection) {
        int counter = 0;
        for(SolutionMapping<Binding> solutionMapping: collection){
            Binding binding = solutionMapping.get();
            Var var = binding.variables().stream().filter(v->v.name().equals(variableName)).findFirst().get();
            RDFTerm variableBinding = solutionMapping.get().value(var);
            if(variableBinding != null){
                counter++;
            }
        }
        Binding countResponse = new BindingImpl();
        countResponse.add(new VarImpl(outputname), RDFUtils.createIRI(counter+""));
        return new SolutionMappingBase<>(countResponse,System.currentTimeMillis());

    }
}
