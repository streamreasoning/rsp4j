package it.polimi.jasper.engine.stream;

import com.espertech.esper.client.EPStatement;
import it.polimi.rspql.RSPEngine;
import it.polimi.yasper.core.stream.RegisteredStream;
import it.polimi.yasper.core.stream.StreamSchema;
import lombok.Getter;

/**
 * Created by riccardo on 10/07/2017.
 */
@Getter
public class RegisteredRDFStream extends RegisteredStream {

    private String resolved_uri;

    public RegisteredRDFStream(String resolved_uri, RDFStream s, EPStatement e, RSPEngine engine) {
        super(s, e, s.getURI(), engine);
        this.resolved_uri=resolved_uri;
    }

    @Override
    public StreamSchema getSchema() {
        return super.getStream().getSchema();
    }

    @Override
    public String getURI() {
        return resolved_uri;
    }


}
