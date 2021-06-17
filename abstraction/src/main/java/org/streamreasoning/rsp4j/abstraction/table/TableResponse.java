package org.streamreasoning.rsp4j.abstraction.table;

import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMappingBase;

public class TableResponse extends SolutionMappingBase<TableRow> {

    public TableResponse(String id, long cep_timestamp, TableRow tableRow) {
        super(id, System.currentTimeMillis(), cep_timestamp, tableRow);
    }
    @Override
    public SolutionMapping<TableRow> difference(SolutionMapping<TableRow> r) {
        return null;
    }

    @Override
    public SolutionMapping<TableRow> intersection(SolutionMapping<TableRow> new_response) {
        return null;
    }
}
