package org.streamreasoning.rsp4j.yasper.querying.syntax;

import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.querying.AbstractContinuousQuery;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.stream.web.WebStream;

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