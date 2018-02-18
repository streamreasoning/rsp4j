package it.polimi.jasper.engine.query;

import com.espertech.esper.client.EventBean;
import it.polimi.esper.EsperStatementView;
import it.polimi.jasper.engine.instantaneous.GraphBase;
import it.polimi.jasper.engine.instantaneous.JenaGraph;
import it.polimi.spe.content.Content;
import it.polimi.spe.windowing.assigner.WindowAssigner;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.util.*;

@Log4j
@Getter
public class DefaultTVG extends EsperStatementView<JenaGraph> {

    private Set<WindowAssigner> windowAssigners;

    private JenaGraph graph = new GraphBase();

    public DefaultTVG(JenaGraph graph) {
        this.graph = graph;
        this.windowAssigners = new HashSet<>();
    }

    @Override
    public void update(long t) {
        List<Content> events = new ArrayList<>();
        windowAssigners.forEach(woa -> {
            Content content = woa.getContent(t);
            events.add(content);
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
    public void addWindowAssigner(WindowAssigner w) {
        windowAssigners.add(w);
    }

    @Override
    public void addObservable(Observable windowAssigner) {

    }
}