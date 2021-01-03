package it.polimi.deib.sr.rsp.api.enums;

public enum Tick {
    TIME_DRIVEN("TIME_DRIVEN"), BATCH_DRIVEN("BATCH_DRIVEN"), TUPLE_DRIVEN("TUPLE_DRIVEN");

    private final String name;

    Tick(String name) {
        this.name = name;
    }
}
