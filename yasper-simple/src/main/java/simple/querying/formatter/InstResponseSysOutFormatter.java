package simple.querying.formatter;

import it.polimi.yasper.core.format.QueryResultFormatter;
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
