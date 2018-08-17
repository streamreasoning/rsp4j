package it.polimi.yasper.core.quering.querying;

import it.polimi.yasper.core.enums.StreamOperator;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.simple.SimpleRDF;

public abstract class AbstractContinuousQuery implements ContinuousQuery {
    private int queryType;
    private String outputStreamIri;
    private int outputStreamType;

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
        outputStreamIri = iri;
    }

    @Override
    public String getOutputStream() {
        return outputStreamIri;
    }

}
