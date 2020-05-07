package it.polimi.sr.rsp.onsper.streams;

import it.polimi.yasper.core.stream.data.DataStreamImpl;
import it.polimi.yasper.core.stream.metadata.StreamSchema;
import org.apache.commons.rdf.api.Graph;
import org.jooq.lambda.tuple.Tuple;

/**
 * Created by riccardo on 14/08/2017.
 */

public class RegisteredVirtualRDFStream extends DataStreamImpl<Tuple> implements OBDAStream {

    private StreamSchema schema;
    private Graph mappings;

    public RegisteredVirtualRDFStream(String stream_uri, StreamSchema schema, Graph mappings) {
        super(stream_uri);
        this.schema = schema;
        this.mappings = mappings;
    }

    public StreamSchema getSchema() {
        return schema;
    }

    @Override
    public Graph mappings() {
        return mappings;
    }

    @Override
    public void mappings(Graph mappings) {
        this.mappings = mappings;
    }

}
