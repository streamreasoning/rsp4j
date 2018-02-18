package it.polimi.sr.onsper.streams;

import com.espertech.esper.client.EPStatement;
import it.polimi.esper.wrapping.SchemaAssigner;
import it.polimi.rspql.RSPEngine;
import it.polimi.rspql.Stream;
import it.polimi.yasper.core.stream.StreamItem;
import it.polimi.yasper.core.stream.StreamSchema;
import lombok.NonNull;

/**
 * Created by riccardo on 14/08/2017.
 */

public class RegisteredRelStream implements RelStream {


    protected Stream stream;
    protected SchemaAssigner e;
    protected String uri;
    protected RSPEngine engine;

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

    public String getTboxUri() {
        return null;
    }

    public void put(StreamItem i) {

        //TODO nice to have here the sending of the data to the RSPEngineImpl, since in this way I can
        //check if the schema is compliant BEFORE sending it in.

        //TODO I can also deliver it on the right channel already.

    }
}
