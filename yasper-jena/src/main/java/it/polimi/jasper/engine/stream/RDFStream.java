package it.polimi.jasper.engine.stream;

import it.polimi.jasper.engine.stream.items.RDFStreamSchema;
import it.polimi.yasper.core.stream.StreamImpl;
import it.polimi.yasper.core.stream.StreamSchema;
import lombok.Getter;
import lombok.NonNull;

/**
 * Created by riccardo on 10/07/2017.
 */
@Getter
public class RDFStream extends StreamImpl {

    @NonNull
    private RDFStreamSchema schema;

    public RDFStream(String stream_uri, RDFStreamSchema schema) {
        super(stream_uri);
        this.schema=schema;
    }

    @Override
    public StreamSchema getSchema() {
        return schema;
    }

    @Override
    public String getURI() {
        return stream_uri;
    }


}
