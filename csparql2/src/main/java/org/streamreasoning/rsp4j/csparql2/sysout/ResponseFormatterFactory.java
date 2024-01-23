package org.streamreasoning.rsp4j.csparql2.sysout;


import org.streamreasoning.rsp4j.api.format.QueryResultFormatter;

/**
 * Created by riccardo on 10/07/2017.
 */
public class ResponseFormatterFactory {

    public static QueryResultFormatter getSelectResponseSysOutFormatter(String format, boolean distinct) {
        return new SelectSysOutDefaultFormatter(format, distinct);
    }

    public static QueryResultFormatter getConstructResponseSysOutFormatter(String format, boolean distinct) {
        return new ConstructSysOutDefaultFormatter(format, distinct);
    }

}
