package it.polimi.yasper.core.spe.content;

import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.stream.StreamElement;

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
}
