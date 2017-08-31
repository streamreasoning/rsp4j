package it.polimi.rspql.querying;

import it.polimi.rspql.Visitable;

public interface Query<T, V> extends Visitable {

    T get();

}
