package it.polimi.rsp.yasper.csparql;

import eu.larkc.csparql.cep.api.RdfStream;
import it.polimi.yasper.core.stream.Stream;
import lombok.Getter;

@Getter
public class CSRDFStream implements Stream {

    private final RdfStream stream;

    public CSRDFStream(RdfStream stream) {
        this.stream = stream;
    }

    @Override
    public String getURI() {
        return stream.getIRI();
    }
}
