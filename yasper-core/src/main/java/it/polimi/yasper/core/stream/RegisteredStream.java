package it.polimi.yasper.core.stream;

import it.polimi.esper.wrapping.SchemaAssigner;
import it.polimi.rspql.RSPEngine;
import it.polimi.rspql.Stream;
import it.polimi.spe.windowing.assigner.WindowAssigner;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisteredStream implements Stream {

    protected Stream stream;
    protected SchemaAssigner e;
    protected String uri;
    protected RSPEngine engine;

    @Override
    public String getURI() {
        return uri;
    }

    @Override
    public void addObserver(WindowAssigner windowAssigner) {
        stream.addObserver(windowAssigner);
    }

    public void setRSPEngine(RSPEngine e) {
        this.engine = e;
    }

    public RSPEngine getRSPEngine() {
        return engine;
    }

}
