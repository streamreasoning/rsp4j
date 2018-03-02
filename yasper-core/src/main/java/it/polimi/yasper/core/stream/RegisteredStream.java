package it.polimi.yasper.core.stream;

import it.polimi.yasper.core.rspql.RSPEngine;
import it.polimi.yasper.core.rspql.Stream;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisteredStream implements Stream {

    protected Stream stream;
    protected String uri;
    protected RSPEngine engine;

    @Override
    public String getURI() {
        return uri;
    }

    @Override
    public void addWindowAssiger(WindowAssigner windowAssigner) {
        stream.addWindowAssiger(windowAssigner);
    }

    public void setRSPEngine(RSPEngine e) {
        this.engine = e;
    }

    public RSPEngine getRSPEngine() {
        return engine;
    }

}
