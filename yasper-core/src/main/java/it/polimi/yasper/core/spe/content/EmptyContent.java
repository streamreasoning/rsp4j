package it.polimi.yasper.core.spe.content;

import it.polimi.yasper.core.stream.StreamElement;
import org.apache.commons.rdf.api.Graph;

public class EmptyContent implements Content {

    long ts = System.currentTimeMillis();

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void add(StreamElement e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long getTimeStampLastUpdate() {
        return ts;
    }

    @Override
    public Graph coalese() {
        return null;
    }
}
