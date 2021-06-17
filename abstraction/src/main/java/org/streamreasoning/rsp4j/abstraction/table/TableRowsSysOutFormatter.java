package org.streamreasoning.rsp4j.abstraction.table;

import org.streamreasoning.rsp4j.api.format.QueryResultFormatter;

public class TableRowsSysOutFormatter extends QueryResultFormatter<TableRow> {

    public TableRowsSysOutFormatter(String format, boolean distinct) {
        super(format, distinct);
    }

    @Override
    public void notify(TableRow t, long ts) {
        System.out.println(t);
    }
}