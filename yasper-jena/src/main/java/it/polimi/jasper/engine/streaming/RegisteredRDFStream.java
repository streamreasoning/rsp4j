package it.polimi.jasper.engine.streaming;

import com.espertech.esper.client.EPStatement;
import it.polimi.yasper.core.engine.RSPEngine;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.stream.rdf.RDFStream;
import lombok.Getter;

/**
 * Created by riccardo on 10/07/2017.
 */
@Getter
public class RegisteredRDFStream extends RDFStream implements Stream {

    protected RDFStream stream;
    protected EPStatement e;
    protected RSPEngine engine;

    public RegisteredRDFStream(String uri, RDFStream s, EPStatement epl, RSPEngine engine) {
        super(uri);
        this.stream = s;
        this.engine = engine;
    }


}
