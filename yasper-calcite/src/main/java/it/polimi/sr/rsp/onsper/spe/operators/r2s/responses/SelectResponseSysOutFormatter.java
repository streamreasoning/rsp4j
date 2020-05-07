package it.polimi.sr.rsp.onsper.spe.operators.r2s.responses;


import it.polimi.yasper.core.format.QueryResultFormatter;

import java.io.OutputStream;
import java.util.Observable;

/**
 * Created by riccardo on 03/07/2017.
 */
public class SelectResponseSysOutFormatter extends QueryResultFormatter {

    private final OutputStream os;
    long last_result = -1L;

    public SelectResponseSysOutFormatter(String format, boolean distinct, OutputStream os) {
        super(format, distinct);
        this.os = os;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

//    @Override
//    public void update(Observable o, Object arg) {
//        if (arg instanceof RelationalSolution) {
//            RelationalSolution sr = (RelationalSolution) arg;
//            if (sr.getCep_timestamp() != last_result && distinct) {
//                last_result = sr.getCep_timestamp();
//                System.out.println("[" + System.currentTimeMillis() + "] Result at [" + last_result + "]");
//                ResultSet res = sr.getRs();
//                try {
//                    while (res.next()) {
//                        ResultSetMetaData metaData = res.getMetaData();
//                        for (int i = 0; i < metaData.getColumnCount(); i++) {
//                            String columnLabel = metaData.getColumnLabel(i);
//                            Object object = res.getObject(columnLabel);
//                            System.out.println(columnLabel + "===" + object.toString());
//                        }
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//
//    }
}
