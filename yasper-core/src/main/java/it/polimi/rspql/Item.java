package it.polimi.rspql;

public interface Item {

    <T> Updatable<T> asUpdatable();

    Instantaneous asInstantaneous();
}
