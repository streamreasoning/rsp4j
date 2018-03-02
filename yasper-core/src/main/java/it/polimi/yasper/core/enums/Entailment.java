package it.polimi.yasper.core.engine;

import it.polimi.yasper.core.enums.EntailmentType;

public interface Entailment {

    EntailmentType getType();

    Object getRules();
}
