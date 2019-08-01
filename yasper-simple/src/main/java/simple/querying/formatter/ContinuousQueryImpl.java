package simple.querying.formatter;


import it.polimi.yasper.core.querying.AbstractContinuousQuery;
import it.polimi.yasper.core.operators.s2r.syntax.WindowNode;
import it.polimi.yasper.core.secret.time.Time;
import it.polimi.yasper.core.secret.time.TimeFactory;
import it.polimi.yasper.core.stream.web.WebStream;
import it.polimi.yasper.core.stream.web.WebStreamImpl;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ContinuousQueryImpl extends AbstractContinuousQuery {
    private final String id;
    private Map<WindowNode, WebStream> windowMap = new HashMap<>();
    private List<String> graphURIs = new ArrayList<>();
    private List<String> namedwindowsURIs = new ArrayList<>();
    private List<String> namedGraphURIs = new ArrayList<>();

    @Override
    public void addNamedWindow(String streamUri, WindowNode wo) {
        WebStream s = new WebStreamImpl(streamUri);
        windowMap.put(wo, s);
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public boolean isRecursive() {
        return false;
    }

    @Override
    public Map<WindowNode, WebStream> getWindowMap() {
        return windowMap;
    }

    @Override
    public List<String> getGraphURIs() {
        return graphURIs;
    }

    @Override
    public List<String> getNamedwindowsURIs() {
        return namedwindowsURIs;
    }

    @Override
    public List<String> getNamedGraphURIs() {
        return namedGraphURIs;
    }

    @Override
    public List<String> getResultVars() {
        return new ArrayList<>();
    }

    @Override
    public String getSPARQL() {
        return "";
    }

    @Override
    public Time getTime() {
        return TimeFactory.getInstance();
    }
}
