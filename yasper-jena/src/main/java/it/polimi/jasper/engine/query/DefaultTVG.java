package it.polimi.jasper.engine.query;

import com.espertech.esper.client.EventBean;
import it.polimi.jasper.engine.instantaneous.GraphBase;
import it.polimi.jasper.engine.instantaneous.JenaGraph;
import it.polimi.jasper.esper.ContentBean;
import it.polimi.jasper.esper.EsperStatementView;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import lombok.extern.log4j.Log4j;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j
public class DefaultTVG extends EsperStatementView {

    private Set<WindowAssigner> windowAssigners;

    public DefaultTVG(JenaGraph g) {
        this.setContent(g);
        this.windowAssigners = new HashSet<>();
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