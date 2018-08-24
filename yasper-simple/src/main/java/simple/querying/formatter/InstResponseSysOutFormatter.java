package simple.querying.formatter;

import it.polimi.yasper.core.spe.operators.r2s.result.QueryResultFormatter;
import simple.querying.SelectInstResponse;

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
        super.update(o, arg);
        SelectInstResponse sr = (SelectInstResponse) arg;
        System.err.println("[" + sr.getTriples() + "] Result");

    }
}
