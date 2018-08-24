package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.spe.operators.r2r.ContinuousQuery;

public interface QueryDeletionFeature {

    void unregister(ContinuousQuery q);

}
