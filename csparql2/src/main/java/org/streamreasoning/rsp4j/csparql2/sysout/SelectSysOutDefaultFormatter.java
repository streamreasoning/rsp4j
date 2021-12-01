package org.streamreasoning.rsp4j.csparql2.sysout;

public class SelectSysOutDefaultFormatter extends SelectResponseDefaultFormatter {

    public SelectSysOutDefaultFormatter(String format, boolean distinct) {
        super(format, distinct);
    }

    @Override
    public void out(String s) {
        System.out.println(s);
    }

    @Override
    public void notify(Object arg, long ts) {
        throw new UnsupportedOperationException();

    }
}
