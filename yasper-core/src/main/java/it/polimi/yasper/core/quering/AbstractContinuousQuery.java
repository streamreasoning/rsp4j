package it.polimi.yasper.core.quering;

import it.polimi.yasper.core.enums.StreamOperator;
import it.polimi.yasper.core.spe.windowing.operator.WindowOperator;
import it.polimi.yasper.core.stream.Stream;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.simple.SimpleRDF;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class AbstractContinuousQuery implements ContinuousQuery {
    private RDF rdf = new SimpleRDF();
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
    public void setOutputStream(String iri) {
        outputStreamIri = iri;
    }

    @Override
    public String getOutputStream() {
        return outputStreamIri;
    }


    @Override
    public void addNamedWindow(Object windowUri, Object streamUri, Duration range, Duration step) {
        throw new NotImplementedException();
    }

    @Override
    public String getID() {
        throw new NotImplementedException();
    }

    @Override
    public StreamOperator getR2S() {
        throw new NotImplementedException();
    }

    @Override
    public boolean isRecursive() {
        throw new NotImplementedException();
    }

    @Override
    public Map<? extends WindowOperator, Stream> getWindowMap() {
        throw new NotImplementedException();
    }

    @Override
    public List<String> getGraphURIs() {
        throw new NotImplementedException();
    }

    @Override
    public List<String> getNamedwindowsURIs() {
        throw new NotImplementedException();
    }

    @Override
    public List<String> getNamedGraphURIs() {
        throw new NotImplementedException();
    }

    @Override
    public String getSPARQL() {
        throw new NotImplementedException();
    }
}
