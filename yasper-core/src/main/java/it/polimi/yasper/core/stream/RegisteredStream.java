package it.polimi.yasper.core.stream;

import com.espertech.esper.client.EPStatement;
import it.polimi.rspql.RSPEngine;
import it.polimi.rspql.Stream;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegisteredStream implements Stream {

    private Stream s;
    private EPStatement e;
    private String stream;
    private String uri;

    @Override
    public String getURI() {
        return uri;
    }

    @Override
    public void setRSPEngine(RSPEngine e) {
        s.setRSPEngine(e);
    }

    @Override
    public RSPEngine getRSPEngine() {
        return s.getRSPEngine();
    }

    @Override
    public String toEPLSchema() {
        return s.toEPLSchema();
    }
}
