package it.polimi.sr.rsp.csparql.sysout;

public class SelectSysOutDefaultFormatter extends SelectResponseDefaultFormatter {

    public SelectSysOutDefaultFormatter(String format, boolean distinct) {
        super(format, distinct);
    }

    @Override
    public void out(String s) {
        System.out.println(s);
    }
}
