package it.polimi.yasper.core.engine;


import it.polimi.yasper.core.engine.features.*;
import it.polimi.yasper.core.stream.rdf.RDFStream;
import it.polimi.yasper.core.stream.rdf.RegisteredRDFStream;


/**
 * @author Riccardo
 */
public interface RSPEngine<S2 extends RDFStream, S1 extends RegisteredRDFStream, T> extends QueryParsingFeature, QueryRegistrationFeature, QueryDeletionFeature, QueryObserverRegistrationFeature, QueryExecutionObserverRegistrationFeature, QueryObserverDeletionFeature, StreamRegistrationFeature<S1, S2>, StreamDeletionFeature<S1> {

    boolean process(T var1);

}
