package org.streamreasoning.rsp4j.abstraction.table;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TableRow {

    private Map<String,String> tableData;

    public TableRow(){
        tableData = new HashMap<>();
    }

    public TableRow add(String key, String value){
        tableData.put(key,value);
        return this;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TableRow: {");
        tableData.entrySet().forEach((entry) ->stringBuilder.append(entry.getKey())
                .append(" -> ").append(entry.getValue())
                .append(", "));
        stringBuilder.append("}");
        return stringBuilder.toString();

    }
    public Optional<String> getDataForVariable(String variableName){
        return Optional.ofNullable(tableData.get(variableName));
    }

}
