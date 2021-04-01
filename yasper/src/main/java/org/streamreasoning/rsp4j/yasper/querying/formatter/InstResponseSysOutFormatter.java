package org.streamreasoning.rsp4j.yasper.querying.formatter;

import org.streamreasoning.rsp4j.api.format.QueryResultFormatter;
import org.apache.commons.rdf.api.Triple;

/**
 * Created by riccardo on 03/07/2017.
 */

public class InstResponseSysOutFormatter extends QueryResultFormatter<Triple> {

    public InstResponseSysOutFormatter(String format, boolean distinct) {
        super(format, distinct);
    }

    @Override
    public void notify(Triple t, long ts) {
        System.out.println(t);
    }
}
