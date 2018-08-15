package it.polimi.yasper.core.quering.rspql.tvg;

import it.polimi.yasper.core.stream.rdf.Named;

public interface TimeVarying extends Named {

    <T> T materialize(long ts);

}
