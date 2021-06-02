package org.streamreasoning.rsp4j.abstraction.functions;

import org.streamreasoning.rsp4j.abstraction.table.TableResponse;
import org.streamreasoning.rsp4j.abstraction.table.TableRow;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;

import java.util.Collection;
import java.util.Optional;

public class CountFunction implements AggregationFunction<TableRow> {


    @Override
    public SolutionMapping<TableRow> evaluate(String variableName,String outputname,Collection<SolutionMapping<TableRow>> collection) {
        int counter = 0;
        for(SolutionMapping<TableRow> solutionMapping: collection){
            Optional<String> variableBinding = solutionMapping.get().getDataForVariable(variableName);
            if(variableBinding.isPresent()){
                counter++;
            }
        }
        TableRow countResponse = new TableRow();
        countResponse.add(outputname,counter+"");
        return new TableResponse(outputname, System.currentTimeMillis(), countResponse);

    }
}
