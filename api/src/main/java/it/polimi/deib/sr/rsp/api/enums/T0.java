package it.polimi.deib.sr.rsp.api.enums;

public enum T0 {
    SYSTEM("system"), PROVIDED("provided"), UNIX("unix-time"), ZERO("zero");

    private final String name;

    T0(String name) {
        this.name = name;
    }
}
