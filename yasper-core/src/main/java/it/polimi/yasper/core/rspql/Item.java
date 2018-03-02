package it.polimi.yasper.core.rspql;

public interface Item {

    <T> Updatable<T> asUpdatable();

}
