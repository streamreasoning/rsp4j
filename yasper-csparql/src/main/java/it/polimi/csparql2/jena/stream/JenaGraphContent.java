package it.polimi.csparql2.jena.stream;

import it.polimi.csparql2.jena.engine.ContinuousQueryExecutionFactory;
import it.polimi.jasper.secret.content.ContentEventBean;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.GraphUtil;
import org.apache.jena.mem.GraphMem;
import org.apache.jena.reasoner.Reasoner;

@Log4j
public class JenaGraphContent extends ContentEventBean<Graph, Graph> {

    private Graph content;

    public JenaGraphContent(Graph content) {
        this.content = content;
    }

    public JenaGraphContent() {
        content = graph();
    }

    private static Graph graph() {
        Reasoner reasoner = ContinuousQueryExecutionFactory.getReasoner();
        return reasoner != null ? reasoner.bind(new GraphMem()) : new GraphMem();
    }

    @Override
    public Graph coalesce() {
        content.clear();
        elements.forEach(ig -> GraphUtil.addInto(this.content, ig));
        //        elements.stream().flatMap(ig->GraphUtil.findAll(ig).toList().stream()).forEach(this.graph::add);

        return this.content;
    }

    public void replace(Graph coalesce) {
        this.content.clear();
        GraphUtil.addInto(content, coalesce);
    }
}
