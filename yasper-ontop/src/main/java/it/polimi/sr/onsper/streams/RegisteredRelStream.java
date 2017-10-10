package it.polimi.sr.onsper.streams;

import com.espertech.esper.client.EPStatement;
import it.polimi.yasper.core.stream.RegisteredStream;
import it.polimi.yasper.core.stream.StreamItem;
import it.polimi.yasper.core.stream.StreamSchema;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by riccardo on 14/08/2017.
 */

@RequiredArgsConstructor
public class RegisteredRelStream implements RelStream, RegisteredStream {
    @NonNull
    private RelStream s;
    @NonNull
    private EPStatement epl;
    @NonNull
    private StreamSchema schema;

    @Override
    public String getURI() {
        return s.getURI();
    }

    @Override
    public StreamSchema getSchema() {
        return schema;
    }

    @Override
    public String getTboxUri() {
        return null;
    }

    @Override
    public void put(StreamItem i) {

        //TODO nice to have here the sending of the data to the engine, since in this way I can
        //check if the schema is compliant BEFORE sending it in.

        //TODO I can also deliver it on the right channel already.

    }
}
