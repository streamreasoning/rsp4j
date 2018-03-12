package it.polimi.jasper.engine.querying.formatter;

import it.polimi.yasper.core.quering.formatter.QueryResponseFormatter;

/**
 * Created by riccardo on 10/07/2017.
 */
public class ResponseFormatterFactory {

    public static QueryResponseFormatter getSelectResponseSysOutFormatter(String format, boolean distinct) {
        return new SelectResponseSysOutFormatter(format, distinct);
    }

    public static QueryResponseFormatter getConstructResponseSysOutFormatter(String format, boolean distinct) {
        return new ConstructResponseSysOutFormatter(format, distinct);
    }

    public static QueryResponseFormatter getGenericResponseSysOutFormatter(String format, boolean distinct) {
        return new GenericResponseSysOutFormatter(format, distinct, System.out);
    }
}
