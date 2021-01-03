package it.polimi.deib.sr.rsp.yasper.querying.formatter;

import it.polimi.deib.sr.rsp.api.format.QueryResultFormatter;
import org.apache.commons.rdf.api.Triple;

import java.util.Observable;

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
