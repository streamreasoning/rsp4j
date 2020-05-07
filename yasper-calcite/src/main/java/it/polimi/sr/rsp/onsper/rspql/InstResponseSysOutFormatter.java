package it.polimi.sr.rsp.onsper.rspql;

import it.polimi.sr.rsp.onsper.spe.operators.r2s.responses.RelationalSolution;
import it.polimi.yasper.core.format.QueryResultFormatter;

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
        RelationalSolution sr = (RelationalSolution) arg;
//
//        ResultSet rs = sr.getRs();
//
//        if (rs != null) {
//            DBTablePrinter.printResultSet(rs);
//        } else {
//            TupleQueryResult result = sr.getTqr();
//            while (result.hasNext()) {
//                BindingSet solution = result.next();
//                Iterator<Binding> iterator = solution.iterator();
//                while (iterator.hasNext()) {
//                    System.out.println(iterator.next());
//                }
//            }

    }
}
