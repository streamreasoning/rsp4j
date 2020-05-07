package it.polimi.csparql2.jena.formatter.sysout;

public class SelectSysOutDefaultFormatter extends SelectResponseDefaultFormatter {

    public SelectSysOutDefaultFormatter(String format, boolean distinct) {
        super(format, distinct);
    }

    @Override
    protected void out(String s) {
        System.out.println(s);
    }
}
