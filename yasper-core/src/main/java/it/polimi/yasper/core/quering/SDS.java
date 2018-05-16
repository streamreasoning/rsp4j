package it.polimi.yasper.core.quering;

import org.apache.commons.rdf.api.IRI;

/**
 * Created by riccardo on 01/07/2017.
 */
public interface SDS<Graph> {

    void beforeEval();

    void afterEval();

    <T extends TimeVarying<Graph>> void add(IRI iri, T tvg);

    <T extends TimeVarying<Graph>> void add(T tvg);

    void eval(long ts);
}
