package it.polimi.sr.onsper.engine;

import it.polimi.rspql.Item;
import it.polimi.yasper.core.query.Updatable;

import java.util.Collection;

public interface Relation<T> extends Item, Updatable<T> {

    Collection<T> getCollection();

}
