package it.polimi.yasper.core.quering;

import java.time.Duration;
import java.util.List;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.spe.windowing.operator.WindowOperator;
import it.polimi.yasper.core.enums.StreamOperator;

import java.util.Map;

/**
 * TODO: This interface needs to be updated to contain setter and getters for all relevant query parts.
 */
public interface ContinuousQuery {
    int RSTREAM = 0;
    int ISTREAM = 1;
    int DSTREAM = 2;

    int SELECT = 10;
    int CONSTRUCT = 11;

    // Subset of methods

    void addNamedWindow(String windowUri, Object streamUri, Duration range, Duration step);

    void setIstream();
    void setRstream();
    void setDstream();
    boolean isIstream();
    boolean isRstream();
    boolean isDstream();

    void setSelect();
    void setConstruct();
    boolean isSelectType();
    boolean isConstructType();

    void setOutputStream(String uri);
    String getOutputStream();

    String getID();

    StreamOperator getR2S();

    boolean isRecursive();

    Map<? extends WindowOperator, Stream> getWindowMap();

    List<String> getGraphURIs();

    List<String> getNamedwindowsURIs();

    List<String> getNamedGraphURIs();

    String getSPARQL();
}
