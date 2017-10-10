package it.polimi.yasper.esper.rsp.internals.esper.internals.view.iterator;

import com.espertech.esper.event.map.MapEventType;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by riccardo on 01/09/2017.
 */
/*
 ***************************************************************************************
 *  Copyright (C) 2006 EsperTech, Inc. All rights reserved.                            *
 *  http://www.espertech.com/esper                                                     *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 ***************************************************************************************
 */

public abstract class MixedMapEventIterator implements Iterator<MapEventType> {

    private final Iterator keyIterator;
    private Iterator<MapEventType> currentListIterator;
    private MapEventType currentItem;

    protected abstract Object getValue(Object iteratorKeyValue);

    protected MixedMapEventIterator(Iterator keyIterator) {
        this.keyIterator = keyIterator;
    }

    protected void init() {
        if (keyIterator.hasNext()) {
            goToNext();
        }
    }

    public final MapEventType next() {
        if (currentListIterator == null && currentItem == null) {
            throw new NoSuchElementException();
        }

        MapEventType eventBean;
        if (currentListIterator != null) {
            eventBean = currentListIterator.next();

            if (!currentListIterator.hasNext()) {
                currentListIterator = null;
                currentItem = null;
                if (keyIterator.hasNext()) {
                    goToNext();
                }
            }
        } else {
            eventBean = currentItem;
            currentItem = null;
            if (keyIterator.hasNext()) {
                goToNext();
            }
        }

        return eventBean;
    }

    public final boolean hasNext() {
        if (currentListIterator == null && currentItem == null) {
            return false;
        }

        if (currentItem != null) {
            return true;
        }

        if (currentListIterator.hasNext()) {
            return true;
        }

        currentListIterator = null;
        currentItem = null;

        return keyIterator.hasNext();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    private void goToNext() {
        Object nextKey = keyIterator.next();
        Object entry = getValue(nextKey);
        while (true) {
            if (entry instanceof Collection) {
                currentListIterator = ((Collection<MapEventType>) entry).iterator();
                if (currentListIterator.hasNext()) {
                    break;
                } else {
                    currentListIterator = null;
                }
            } else if (entry instanceof MapEventType) {
                currentItem = (MapEventType) entry;
                break;
            }

            // next key
            if (keyIterator.hasNext()) {
                nextKey = keyIterator.next();
                entry = getValue(nextKey);
            } else {
                break;
            }
        }
    }
}
