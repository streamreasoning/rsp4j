package it.polimi.yasper.core.quering;

import java.util.List;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.spe.windowing.operator.WindowOperator;
import it.polimi.yasper.core.enums.StreamOperator;

import java.util.Map;

public interface ContinuousQuery {

    String getID();

    StreamOperator getR2S();

    boolean isRecursive();

    Map<? extends WindowOperator, Stream> getWindowMap();

    boolean isSelectType();

    boolean isConstructType();

    int getQueryType();

    List<String> getGraphURIs();

    List<String> getNamedwindowsURIs();

    List<String> getNamedGraphURIs();

    String getSPARQL();
}
