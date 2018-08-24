package it.polimi.yasper.core.spe.tick;

public enum Tick {
    TIME_DRIVEN("time"), BATCH_DRIVEN("batch"), TUPLE_DRIVEN("tuple");

    private final String name;

    Tick(String name) {
        this.name = name;
    }
}
