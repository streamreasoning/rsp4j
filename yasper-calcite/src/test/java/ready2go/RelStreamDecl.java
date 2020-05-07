package ready2go;

import it.polimi.yasper.core.stream.metadata.StreamSchema;
import it.polimi.yasper.core.stream.web.WebStream;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RelStreamDecl implements WebStream {

    @NonNull
    protected String stream_uri;

    @NonNull
    StreamSchema schema;

    public StreamSchema getSchema() {
        return schema;
    }

    @Override
    public String getURI() {
        return stream_uri;
    }
}