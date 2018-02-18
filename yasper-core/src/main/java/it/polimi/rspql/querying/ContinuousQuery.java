package it.polimi.rspql.querying;

import it.polimi.rspql.Stream;
import it.polimi.rspql.Visitable;
import it.polimi.spe.windowing.WindowOperator;
import it.polimi.yasper.core.enums.StreamOperator;

import java.util.Map;
import java.util.Set;

public interface ContinuousQuery extends Visitable {

    String getID();

    StreamOperator getR2S();

    boolean isRecursive();

    Set<? extends WindowOperator> getWindowsSet();

    Set<? extends WindowOperator> getNamedWindowsSet();

    Map<? extends WindowOperator, Stream> getWindowMap();

    Set<Stream> getStreamSet();

}
