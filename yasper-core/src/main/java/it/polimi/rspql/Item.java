package it.polimi.rspql;

import it.polimi.rspql.instantaneous.Instantaneous;
import it.polimi.yasper.core.query.Updatable;

public interface Item {

    <T> Updatable<T> asUpdatable();

    Instantaneous asInstantaneous();
}
