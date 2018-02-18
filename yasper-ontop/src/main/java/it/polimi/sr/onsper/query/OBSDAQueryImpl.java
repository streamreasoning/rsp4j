package it.polimi.sr.onsper.query;

import it.polimi.rspql.SDSBuilder;
import it.polimi.rspql.Stream;
import it.polimi.rspql.cql.s2_.WindowOperatorNode;
import it.polimi.yasper.core.enums.StreamOperator;
import it.unibz.inf.ontop.answering.reformulation.input.InputQuery;
import org.semanticweb.owlapi.model.IRI;

import java.util.Map;
import java.util.Set;

/**
 * Created by riccardo on 05/09/2017.
 */
public class OBSDAQueryImpl implements OBDAQuery {

    private IRI TBox;

    @Override
    public InputQuery getQ() {
        return null;
    }

    @Override
    public String getID() {
        return null;
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
    public Set<? extends WindowOperatorNode> getWindowsSet() {
        return null;
    }

    @Override
    public Set<? extends WindowOperatorNode> getNamedWindowsSet() {
        return null;
    }

    @Override
    public Map<WindowOperatorNode, Stream> getWindowMap() {
        return null;
    }

    @Override
    public Set<Stream> getStreamSet() {
        return null;
    }

    @Override
    public void accept(SDSBuilder v) {

    }

    @Override
    public IRI getTBox() {
        return TBox;
    }
}
