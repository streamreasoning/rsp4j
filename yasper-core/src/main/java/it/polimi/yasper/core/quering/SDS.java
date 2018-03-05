package it.polimi.yasper.core.quering;

import it.polimi.yasper.core.simple.windowing.TimeVarying;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;

/**
 * Created by riccardo on 01/07/2017.
 */
public interface SDS {

    void beforeEval();

    void afterEval();

    <T extends TimeVarying<Graph>> void add(IRI iri, T tvg);

    <T extends TimeVarying<Graph>> void add(T tvg);

    void eval(long ts);
}
