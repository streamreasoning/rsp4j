package it.polimi.jasper.engine.stream;

import com.espertech.esper.client.EPStatement;
import it.polimi.yasper.core.stream.StreamImpl;
import lombok.Getter;

/**
 * Created by riccardo on 10/07/2017.
 */
@Getter
public class RDFStream extends StreamImpl {

    protected String name;

    public RDFStream(String name, String stream_uri) {
        super(stream_uri);
        this.name = name;
    }

    @Override
    public String getURI() {
        return stream_uri;
    }
}
