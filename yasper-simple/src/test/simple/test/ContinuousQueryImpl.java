package simple.test;


import it.polimi.yasper.core.quering.AbstractContinuousQuery;
import it.polimi.yasper.core.spe.windowing.operator.CSPARQLTimeWindowOperator;
import it.polimi.yasper.core.spe.windowing.operator.WindowOperator;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.stream.rdf.RDFStream;
import it.polimi.yasper.core.utils.RDFUtils;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ContinuousQueryImpl extends AbstractContinuousQuery {
    private final String id;
    private Map<WindowOperator, Stream> windowMap = new HashMap<>();
    private List<String> graphURIs = new ArrayList<>();
    private List<String> namedwindowsURIs = new ArrayList<>();
    private List<String> namedGraphURIs = new ArrayList<>();

    @Override
    public void addNamedWindow(String windowUri, Object streamUri, Duration range, Duration step) {
        WindowOperator w = new CSPARQLTimeWindowOperator(RDFUtils.createIRI(windowUri), range.toMillis(), step.toMillis(), 0);
        Stream s = new RDFStream((String) streamUri);
        windowMap.put(w, s);
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
    public Map<WindowOperator, Stream> getWindowMap() {
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
    public String getSPARQL() {
        return "";
    }
}
