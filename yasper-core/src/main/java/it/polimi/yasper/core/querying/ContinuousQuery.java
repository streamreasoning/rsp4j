package it.polimi.yasper.core.querying;

import it.polimi.yasper.core.enums.StreamOperator;
import it.polimi.yasper.core.operators.s2r.syntax.WindowNode;
import it.polimi.yasper.core.secret.time.Time;
import it.polimi.yasper.core.stream.web.WebStream;

import java.util.List;
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

    void addNamedWindow(String streamUri, WindowNode wo);

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

    WebStream getOutputStream();

    String getID();

    StreamOperator getR2S();

    boolean isRecursive();

    Map<? extends WindowNode, WebStream> getWindowMap();

    List<String> getGraphURIs();

    List<String> getNamedwindowsURIs();

    List<String> getNamedGraphURIs();

    List<String> getResultVars();

    String getSPARQL();

    Time getTime();
}
