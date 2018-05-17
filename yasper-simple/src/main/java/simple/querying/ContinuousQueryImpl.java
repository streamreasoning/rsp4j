package simple.querying;


import it.polimi.yasper.core.quering.AbstractContinuousQuery;
import it.polimi.yasper.core.quering.ContinuousQuery;
import it.polimi.yasper.core.spe.windowing.operator.TimeWindowOperator;
import it.polimi.yasper.core.spe.windowing.operator.WindowOperator;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.stream.rdf.RDFStream;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.simple.SimpleRDF;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.Map;

public class ContinuousQueryImpl extends AbstractContinuousQuery {
    private RDF rdf = new SimpleRDF();
    private Map<WindowOperator, Stream> windowMap = new HashMap<>();

    @Override
    public void addNamedWindow(Object windowUri, Object streamUri, Duration range, Duration step) {
        WindowOperator w = new TimeWindowOperator(rdf, rdf.createIRI((String) windowUri),
                range.toMillis(), step.toMillis(), 0);
        Stream s = new RDFStream((String) streamUri);
        windowMap.put(w, s);
    }

    @Override
    public Map<WindowOperator, Stream> getWindowMap(){
        return windowMap;
    }
}
