package it.polimi.jasper.engine.windowing;

import com.espertech.esper.client.EventBean;
import it.polimi.jasper.engine.spe.content.ContentBean;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j
public class StreamEsperView extends EsperStatementView {

    private Set<WindowAssigner> windowAssigners;

    public StreamEsperView(Graph g) {
        this.setContent(g);
        this.windowAssigners = new HashSet<>();
    }

    public StreamEsperView(Maintenance maintenance, WindowAssigner<Graph> wa) {

    }

    @Override
    public void update(long t) {
        List<EventBean> beans = windowAssigners.stream()
                .map(windowAssigner -> (ContentBean) windowAssigner.getContent(t))
                .flatMap(contentBean -> Arrays.stream(contentBean.asArray()))
                .collect(Collectors.toList());

        eval(null, beans.toArray(new EventBean[beans.size()]), t);
    }


}