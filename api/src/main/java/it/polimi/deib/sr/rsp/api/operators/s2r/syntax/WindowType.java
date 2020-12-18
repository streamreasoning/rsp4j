package it.polimi.deib.sr.rsp.api.operators.s2r.syntax;

public enum WindowType {
    Logical(0), Physical(1), TIME_BASED(2), COUNT_BASED(3), SEMANTIC(4), LANDMARK(5), PARTIAL(6);

    private final int type;

    WindowType(int i) {
        this.type = i;
    }

    public static WindowType valueOf(int i) {
        switch (i) {
            case 0:
                return Logical;
            case 1:
                return Physical;
            default:
                return Logical;
        }
    }
}