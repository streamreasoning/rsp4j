package it.polimi.jasper.engine.query;

import com.espertech.esper.client.EventBean;
import it.polimi.jasper.engine.instantaneous.GraphBase;
import it.polimi.jasper.engine.instantaneous.JenaGraph;
import it.polimi.rspql.Window;
import it.polimi.rspql.cql.s2_.WindowOperator;
import it.polimi.yasper.core.query.operators.s2r.windows.TimeVaryingItemImpl;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j
@Getter
public class DefaultTVG extends TimeVaryingItemImpl<JenaGraph> {

    private Set<WindowOperator> windowOperatorSet;

    private JenaGraph graph = new GraphBase();

    public DefaultTVG(JenaGraph graph) {
        this.graph=graph;
        this.windowOperatorSet = new HashSet<>();
    }

    @Override
    public void update(long t) {
        List<Window> events = new ArrayList<>();
        windowOperatorSet.forEach(woa -> {
            Window windowContent = woa.getWindowContent(t);
            events.add(windowContent);
        });

        //FIXME
        eval(null, events.toArray(new EventBean[events.size()]), t);
    }

    @Override
    public JenaGraph eval(long t) {
        update(t);
        return graph;
    }


    @Override
    public void setContent(JenaGraph jenaGraph) {
        this.graph = jenaGraph;
    }

    @Override
    public JenaGraph getContent() {
        return graph;
    }


    @Override
    public void setWindowOperator(WindowOperator w) {
        windowOperatorSet.add(w);
    }

}