package org.streamreasoning.rsp4j.reasoning.datalog;

import org.streamreasoning.rsp4j.yasper.querying.PrefixMap;

public class TripleGenerator {
    private final PrefixMap prefixes;

    public TripleGenerator(PrefixMap prefixes) {
        this.prefixes = prefixes;
    }

    public ReasonerTriple createReasonerTriple(String s, String p ,String o){
        return new ReasonerTriple(s, p, o, prefixes);
    }
}
