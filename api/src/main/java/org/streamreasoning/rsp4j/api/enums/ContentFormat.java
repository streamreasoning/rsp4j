package org.streamreasoning.rsp4j.api.enums;

public enum ContentFormat {

    GRAPH("GRAPH"), BINDING("BINDING"), RELATION("RELATION");

    private final String name;

    ContentFormat(String single) {

        this.name = single;
    }

    }
