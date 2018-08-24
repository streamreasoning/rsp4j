package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.spe.operators.r2r.ContinuousQuery;

public interface QueryParsingFeature {

    ContinuousQuery parseQuery(String input);

}
