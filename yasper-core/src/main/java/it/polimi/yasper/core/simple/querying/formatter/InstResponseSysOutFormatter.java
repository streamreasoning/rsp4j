package it.polimi.yasper.core.simple.querying.formatter;

import it.polimi.yasper.core.query.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.simple.querying.SelectInstResponse;

import java.util.Observable;

/**
 * Created by riccardo on 03/07/2017.
 */

public class InstResponseSysOutFormatter extends QueryResponseFormatter {

    @Override
    public void update(Observable o, Object arg) {
        SelectInstResponse sr = (SelectInstResponse) arg;
        System.err.println("[" + sr.getTriples() + "] Result");

    }
}
