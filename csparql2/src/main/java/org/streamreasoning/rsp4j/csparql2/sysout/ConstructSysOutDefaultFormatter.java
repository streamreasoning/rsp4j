package org.streamreasoning.rsp4j.csparql2.sysout;

public class ConstructSysOutDefaultFormatter extends ConstructResponseDefaultFormatter {

    public ConstructSysOutDefaultFormatter(String format, boolean distinct) {
        super(format, distinct);
    }

    @Override
    protected void out(String s) {
        System.out.println(s);
    }

    @Override
    public void notify(Object arg, long ts) {
        throw new UnsupportedOperationException();
    }
}
