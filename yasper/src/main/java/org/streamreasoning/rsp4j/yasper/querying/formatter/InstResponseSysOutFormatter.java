package org.streamreasoning.rsp4j.yasper.querying.formatter;

import org.streamreasoning.rsp4j.api.format.QueryResultFormatter;

/**
 * Created by riccardo on 03/07/2017.
 */

public class InstResponseSysOutFormatter<O> extends QueryResultFormatter<O> {

    public InstResponseSysOutFormatter(String format, boolean distinct) {
        super(format, distinct);
    }

    @Override
    public void notify(O t, long ts) {
        System.out.println(t);
    }
}
