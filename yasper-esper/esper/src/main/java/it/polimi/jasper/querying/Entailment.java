package it.polimi.jasper.querying;

public enum Entailment {

    NONE("NONE"), RHODF("RHODF"), RDFS("RDFS"), OWL2RL("OWL2RL"), OWL2QL("OWL2QL"), OWL2EL("OWL2EL"),
    OWL2DL("OWL2DL"), PELLET("PELLET"), OWL("OWL"), OWLMicro("OWLMicro"), RDFox("RDFox"), Hermit("Hermit"), CUSTOM("CUSTOM");

    private final String ent;

    Entailment(String ent) {
        this.ent=ent;
    }
}
