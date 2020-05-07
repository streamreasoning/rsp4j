package it.polimi.jasper.streams;

import com.espertech.esper.client.EPStatement;
import it.polimi.yasper.core.stream.data.DataStreamImpl;
import it.polimi.yasper.core.stream.web.WebStream;
import lombok.Getter;

/**
 * Created by riccardo on 10/07/2017.
 */
@Getter
public class EPLStream<T> extends DataStreamImpl<T> {

    protected WebStream stream;
    protected EPStatement e;

    public EPLStream(String uri, WebStream s, EPStatement epl) {
        super(uri);
        this.stream = s;
        this.e = epl;
    }

}
