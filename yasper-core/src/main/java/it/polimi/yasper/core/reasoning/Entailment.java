package it.polimi.yasper.core.reasoning;

import it.polimi.yasper.core.enums.EntailmentType;

public interface Entailment {

    EntailmentType getType();

    Object getRules();
}
