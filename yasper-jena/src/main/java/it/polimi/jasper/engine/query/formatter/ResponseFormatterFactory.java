package it.polimi.jasper.engine.query.formatter;

import it.polimi.yasper.core.quering.formatter.QueryResponseFormatter;

/**
 * Created by riccardo on 10/07/2017.
 */
public class ResponseFormatterFactory {

    public static QueryResponseFormatter getSelectResponseSysOutFormatter(boolean distinct) {
        return new SelectResponseSysOutFormatter(distinct);
    }

    public static QueryResponseFormatter getConstructResponseSysOutFormatter(boolean distinct) {
        return new ConstructResponseSysOutFormatter(distinct);
    }

    public static QueryResponseFormatter getGenericResponseSysOutFormatter(boolean distinct) {
        return new GenericResponseSysOutFormatter(distinct, System.out);
    }
}
