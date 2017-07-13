package it.polimi.jasper.engine.stream;

import com.espertech.esper.client.EPStatement;
import it.polimi.yasper.core.stream.StreamImpl;
import lombok.Getter;

/**
 * Created by riccardo on 10/07/2017.
 */
@Getter
public abstract class RDFStream extends StreamImpl implements Runnable {

    protected String name;

    public RDFStream(String name, String stream_uri) {
        super(stream_uri);
        this.name = name;
    }

    public RDFStream(String stream_uri, String name, EPStatement s) {
        super(stream_uri, s);
        this.name = name;
    }

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
