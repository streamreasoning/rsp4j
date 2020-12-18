package it.polimi.yasper.core.querying;

import it.polimi.yasper.core.enums.StreamOperator;
import it.polimi.yasper.core.stream.web.WebStream;
import it.polimi.yasper.core.stream.web.WebStreamImpl;

public abstract class AbstractContinuousQuery implements ContinuousQuery {
    private int queryType;
    private int outputStreamType;
    private WebStreamImpl outputStream;

    @Override
    public void setSelect() {
        queryType = SELECT;
    }

    @Override
    public void setConstruct() {
        queryType = CONSTRUCT;
    }

    @Override
    public boolean isSelectType() {
        return queryType == SELECT;
    }

    @Override
    public boolean isConstructType() {
        return queryType == CONSTRUCT;
    }

    @Override
    public void setIstream() {
        outputStreamType = ISTREAM;
    }

    @Override
    public void setRstream() {
        outputStreamType = RSTREAM;
    }

    @Override
    public void setDstream() {
        outputStreamType = DSTREAM;
    }

    @Override
    public boolean isIstream() {
        return outputStreamType == ISTREAM;
    }

    @Override
    public boolean isRstream() {
        return outputStreamType == RSTREAM;
    }

    @Override
    public boolean isDstream() {
        return outputStreamType == DSTREAM;
    }

    @Override
    public StreamOperator getR2S() {
        switch (outputStreamType) {
            case ISTREAM:
                return StreamOperator.ISTREAM;
            case DSTREAM:
                return StreamOperator.DSTREAM;
            case RSTREAM:
            default:
                return StreamOperator.RSTREAM;
        }
    }

    @Override
    public void setOutputStream(String iri) {
        outputStream= new WebStreamImpl(iri);
    }

    @Override
    public WebStream getOutputStream() {
        return outputStream;
    }

}
