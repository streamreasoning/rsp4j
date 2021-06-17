package org.streamreasoning.rsp4j.api.querying;

import org.streamreasoning.rsp4j.api.querying.syntax.RSPQLQuery;
import org.streamreasoning.rsp4j.api.stream.web.WebStream;
import org.streamreasoning.rsp4j.api.stream.web.WebStreamImpl;

public abstract class AbstractContinuousQuery implements RSPQLQuery {
    private WebStreamImpl outputStream;

    @Override
    public void setOutputStream(String iri) {
        outputStream = new WebStreamImpl(iri);
    }

    @Override
    public WebStream getOutputStream() {
        return outputStream;
    }

}
