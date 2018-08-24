package it.polimi.yasper.core.spe.content;

public enum Maintenance {
    INCREMENTAL("INCREMENTAL"), NAIVE("NAIVE");

    private final String name;

    Maintenance(String name) {
        this.name=name;
    }
}
