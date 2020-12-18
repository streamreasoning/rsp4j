package it.polimi.deib.sr.rsp.yasper.querying.formatter;

import it.polimi.deib.sr.rsp.api.format.QueryResultFormatter;
import org.apache.commons.rdf.api.Triple;

import java.util.Observable;

/**
 * Created by riccardo on 03/07/2017.
 */

public class InstResponseSysOutFormatter extends QueryResultFormatter {

    public InstResponseSysOutFormatter(String format, boolean distinct) {
        super(format, distinct);
    }

    @Override
    public void update(Observable o, Object arg) {
        Triple t = (Triple) arg;
        System.out.println(t);
    }
}
