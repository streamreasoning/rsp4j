package it.polimi.jasper.engine.stream;

import it.polimi.yasper.core.stream.StreamImpl;
import lombok.Getter;

/**
 * Created by riccardo on 10/07/2017.
 */
@Getter
public abstract class RDFStream extends StreamImpl implements Runnable {

    protected String name;

    public RDFStream(String name, String stream_uri, int grow_rate) {
        this.name = name;
        this.stream_uri = stream_uri;
        this.grow_rate = grow_rate;
    }

    protected int grow_rate;

    @Override
    public void run() {

        update();

    }

    protected abstract void update();

    @Override
    public String getURI() {
        return stream_uri;
    }
}
