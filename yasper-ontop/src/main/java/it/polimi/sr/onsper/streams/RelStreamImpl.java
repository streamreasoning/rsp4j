package it.polimi.sr.onsper.streams;

import it.polimi.yasper.core.query.Updatable;
import it.polimi.yasper.core.stream.StreamSchema;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by riccardo on 05/09/2017.
 */
public class RelStreamImpl implements RelStream {

    @Setter
    @Getter
    private final StreamSchema schema;

    public RelStreamImpl(StreamSchema schema) {
        this.schema = schema;
    }

    boolean isUpdatable() {
        return schema instanceof Updatable;
    }

    @Override
    public String getURI() {
        return null;
    }

    @Override
    public String getTboxUri() {
        return null;
    }

}
