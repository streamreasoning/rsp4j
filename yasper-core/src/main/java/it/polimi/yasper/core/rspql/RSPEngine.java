package it.polimi.yasper.core.rspql;


import it.polimi.yasper.core.rspql.features.*;
import it.polimi.yasper.core.spe.stream.StreamElement;
import it.polimi.yasper.core.spe.stream.rdf.RDFStream;

import java.lang.reflect.InvocationTargetException;


/**
 * @author Riccardo
 */
public interface RSPEngine<T> extends QueryParsingFeature, QueryRegistrationFeature, QueryDeletionFeature, QueryObserverRegistrationFeature, QueryObserverDeletionFeature, StreamRegistrationFeature<RDFStream>, StreamDeletionFeature<RDFStream> {

    boolean process(T var1);

    @Override
    default String toVocals() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        String ttl = "<> a vocals:RSPEngine; \n";
        ttl += "\t " + StreamRegistrationFeature.super.toVocals() + ";\n";
        ttl += "\t " + StreamDeletionFeature.super.toVocals() + ";\n";
        ttl += "\t " + QueryParsingFeature.super.toVocals() + ";\n";
        ttl += "\t " + QueryRegistrationFeature.super.toVocals() + ";\n";
        ttl += "\t " + QueryDeletionFeature.super.toVocals() + ";\n";
        ttl += "\t " + QueryObserverRegistrationFeature.super.toVocals() + ";\n";
        ttl += "\t " + QueryObserverDeletionFeature.super.toVocals() + ";\n";

        return ttl;
    }


    // TODO is reasoning enabled
    // TODO is external time control enabled
}
