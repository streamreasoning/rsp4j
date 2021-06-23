package org.streamreasoning.rsp4j.abstraction.functions;

import org.streamreasoning.rsp4j.abstraction.table.TableRow;

import java.util.Collection;
import java.util.Optional;

public class CountFunction implements AggregationFunction<TableRow> {


    @Override
    public TableRow evaluate(String variableName, String outputname, Collection<TableRow> collection) {
        int counter = 0;
        for (TableRow solutionMapping : collection) {
            Optional<String> variableBinding = solutionMapping.getDataForVariable(variableName);
            if (variableBinding.isPresent()) {
                counter++;
            }
        }
        TableRow countResponse = new TableRow();
        countResponse.add(outputname, counter + "");
//        return new TableResponse(outputname, System.currentTimeMillis(), countResponse);
        return countResponse;

    }
}
