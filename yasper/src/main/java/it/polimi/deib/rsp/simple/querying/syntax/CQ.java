package it.polimi.deib.rsp.simple.querying.syntax;

import it.polimi.yasper.core.operators.s2r.syntax.WindowNode;
import it.polimi.yasper.core.querying.AbstractContinuousQuery;
import it.polimi.yasper.core.secret.time.Time;
import it.polimi.yasper.core.stream.web.WebStream;

import java.util.List;
import java.util.Map;

public class CQ extends AbstractContinuousQuery {


    @Override
    public void addNamedWindow(String streamUri, WindowNode wo) {

    }

    @Override
    public String getID() {
        return null;
    }

    @Override
    public boolean isRecursive() {
        return false;
    }

    @Override
    public Map<? extends WindowNode, WebStream> getWindowMap() {
        return null;
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
}