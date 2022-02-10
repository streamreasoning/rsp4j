package org.streamreasoning.rsp4j.operatorapi.functions;

import org.apache.commons.rdf.api.RDFTerm;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.BindingImpl;
import org.streamreasoning.rsp4j.api.operators.r2r.Var;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarImpl;

import java.util.Collection;

public class CountFunction implements AggregationFunction<Binding> {


    @Override
    public Binding evaluate(String variableName, String outputName, Collection<Binding> collection) {
        int counter = 0;
        for (Binding binding : collection) {
            Var var = binding.variables().stream().filter(v -> v.name().equals(variableName)).findFirst().get();
            RDFTerm variableBinding = binding.value(var);
            if (variableBinding != null) {
                counter++;
            }
        }
        Binding countResponse = new BindingImpl();
        countResponse.add(new VarImpl(outputName), RDFUtils.createIRI(counter + ""));
        return countResponse;
    }
}
