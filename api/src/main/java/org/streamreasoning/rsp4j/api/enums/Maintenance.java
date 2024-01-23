package org.streamreasoning.rsp4j.api.enums;

public enum Maintenance {
    INCREMENTAL("INCREMENTAL"), NAIVE("NAIVE");

    private final String name;

    Maintenance(String name) {
        this.name = name;
    }
}
