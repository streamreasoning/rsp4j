package org.streamreasoning.rsp4j.esper.streams;

import com.espertech.esper.client.EPStatement;
import lombok.Getter;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.DataStreamImpl;

/**
 * Created by riccardo on 10/07/2017.
 */
@Getter
public class EPLStream<T> extends DataStreamImpl<T> {

    protected DataStream stream;
    protected EPStatement e;

    public EPLStream(String uri, DataStream s, EPStatement epl) {
        super(uri);
        this.stream = s;
        this.e = epl;
    }

}
