package it.polimi.yasper.core.stream;

import it.polimi.rspql.Stream;
import lombok.NonNull;

/**
 * Created by riccardo on 10/07/2017.
 */
public class StreamImpl implements Stream {

    @NonNull
    protected String stream_uri;

    public StreamImpl(String stream_uri) {
        this.stream_uri = stream_uri;
    }

    @Override
    public String getURI() {
        return stream_uri;
    }

}
