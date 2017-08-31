package it.polimi.rspql.querying;

import it.polimi.rspql.Stream;
import it.polimi.rspql.Visitable;
import it.polimi.rspql.cql.s2_.WindowOperator;
import it.polimi.yasper.core.enums.StreamOperator;

import java.util.Map;
import java.util.Set;

public interface ContinuousQuery extends Visitable {

    String getID();

    StreamOperator getR2S();

    boolean isRecursive();

    Set<? extends WindowOperator> getWindowsSet();

    Set<? extends WindowOperator> getNamedWindowsSet();

    Map<WindowOperator, Stream> getWindowMap();

    Set<Stream> getStreamSet();

}
