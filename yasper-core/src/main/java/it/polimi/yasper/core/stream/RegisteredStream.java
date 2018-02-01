package it.polimi.yasper.core.stream;

import com.espertech.esper.client.EPStatement;
import it.polimi.rspql.RSPEngine;
import it.polimi.rspql.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisteredStream implements Stream {

    protected Stream stream;
    protected EPStatement e;
    protected String uri;
    protected RSPEngine engine;

    @Override
    public String getURI() {
        return uri;
    }

    public void setRSPEngine(RSPEngine e) {
        this.engine=e;
    }

    public RSPEngine getRSPEngine() {
        return engine;
    }

}
