package it.polimi.jasper.engine.stream;

import com.espertech.esper.client.EPStatement;
import it.polimi.rspql.Stream;
import it.polimi.yasper.core.stream.RegisteredStream;
import it.polimi.yasper.core.stream.StreamSchema;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by riccardo on 10/07/2017.
 */
@Getter
@RequiredArgsConstructor
public class RegisteredRDFStream implements Stream, RegisteredStream {

    @NonNull
    private RDFStream stream;
    @NonNull
    private EPStatement epl_schema;
    @NonNull
    private StreamSchema schema;

    @Override
    public StreamSchema getSchema() {
        return schema;
    }

    @Override
    public String getTboxUri() {
        return null;
    }

    @Override
    public String getURI() {
        return stream.getURI();
    }


}
