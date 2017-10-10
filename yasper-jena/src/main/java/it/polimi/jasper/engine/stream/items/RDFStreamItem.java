package it.polimi.jasper.engine.stream.items;

import it.polimi.yasper.core.stream.StreamItem;
import lombok.NoArgsConstructor;

/**
 * Created by riccardo on 05/09/2017.
 */
public abstract class RDFStreamItem<T> extends StreamItem<T> {
    public RDFStreamItem(long appTimestamp1, T content1, String stream_uri) {
        super(appTimestamp1, content1, stream_uri);
    }
}
