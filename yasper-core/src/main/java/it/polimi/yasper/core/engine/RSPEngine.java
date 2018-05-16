package it.polimi.yasper.core.engine;


import it.polimi.yasper.core.engine.features.*;
import it.polimi.yasper.core.stream.rdf.RDFStream;


/**
 * @author Riccardo
 */
public interface RSPEngine<T> extends QueryParsingFeature, QueryRegistrationFeature, QueryDeletionFeature, QueryObserverRegistrationFeature, QueryExecutionObserverRegistrationFeature, QueryObserverDeletionFeature, StreamRegistrationFeature<RDFStream>, StreamDeletionFeature<RDFStream> {

    boolean process(T var1);

    // TODO is reasoning enabled
    // TODO is external time control enabled
}
