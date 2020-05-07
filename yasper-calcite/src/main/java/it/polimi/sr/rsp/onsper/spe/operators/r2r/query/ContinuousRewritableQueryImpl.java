package it.polimi.sr.rsp.onsper.spe.operators.r2r.query;

import it.polimi.yasper.core.enums.StreamOperator;
import it.polimi.yasper.core.operators.s2r.syntax.StreamNode;
import it.polimi.yasper.core.operators.s2r.syntax.WindowNode;
import it.polimi.yasper.core.querying.ContinuousQuery;
import it.polimi.yasper.core.secret.time.Time;
import it.polimi.yasper.core.stream.web.WebStream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.semanticweb.owlapi.model.OWLOntology;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ContinuousRewritableQueryImpl implements ContinuousQuery {
    @Getter
    private final String qid;

    private final String query;

    @Getter
    private RDF rdf = new RDF4J();
    @Getter
    private OWLOntology tbox;
    @Getter
    private Map<String, Graph> mapping_map = new HashMap<>();
    private Map<WindowNode, WebStream> windowMap = new HashMap<>();

    @Override
    public void addNamedWindow(String streamUri, WindowNode wo) {
        WebStream s = new StreamNode(streamUri);
        windowMap.put(wo, s);
    }

    @Override
    public void setIstream() {

    }

    @Override
    public void setRstream() {

    }

    @Override
    public void setDstream() {

    }

    @Override
    public boolean isIstream() {
        return false;
    }

    @Override
    public boolean isRstream() {
        return false;
    }

    @Override
    public boolean isDstream() {
        return false;
    }

    @Override
    public void setSelect() {

    }

    @Override
    public void setConstruct() {

    }

    @Override
    public boolean isSelectType() {
        return false;
    }

    @Override
    public boolean isConstructType() {
        return false;
    }

    @Override
    public void setOutputStream(String uri) {

    }

    @Override
    public WebStream getOutputStream() {
        return null;
    }

    @Override
    public String getID() {
        return qid;
    }

    @Override
    public StreamOperator getR2S() {
        return null;
    }

    @Override
    public boolean isRecursive() {
        return false;
    }

    @Override
    public Map<? extends WindowNode, WebStream> getWindowMap() {
        return windowMap;
    }

    @Override
    public List<String> getGraphURIs() {
        return null;
    }

    @Override
    public List<String> getNamedwindowsURIs() {
        return null;
    }

    @Override
    public List<String> getNamedGraphURIs() {
        return null;
    }

    @Override
    public List<String> getResultVars() {
        return null;
    }

    @Override
    public String getSPARQL() {
        return null;
    }

    @Override
    public Time getTime() {
        return null;
    }

    @Override
    public String toString() {
        return query;
    }
}
