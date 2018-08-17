package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.quering.formatter.QueryResponseFormatter;

public interface QueryObserverDeletionFeature {
    void removeQueryResponseFormatter(QueryResponseFormatter o);
}
