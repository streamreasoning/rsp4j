package it.polimi.yasper.core.quering.formatter;

import lombok.RequiredArgsConstructor;

import java.util.Observer;

/**
 * Created by riccardo on 03/07/2017.
 */
@RequiredArgsConstructor
public abstract class QueryResponseFormatter implements Observer {

    protected final String format;
    protected final boolean distinct;

}
