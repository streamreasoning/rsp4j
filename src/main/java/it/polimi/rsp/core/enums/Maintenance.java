package it.polimi.rsp.core.enums;

public enum Maintenance {
    INCREMENTAL("RSTREAM_ISTREAM_BOTH"), NAIVE("ISTREAM_ONLY");

    private final String epl;

    Maintenance(String epl) {
        this.epl = epl;
    }

    /**
     * Returns syntactic text
     *
     * @return epl text
     */
    public String selector() {
        return epl;
    }
}
