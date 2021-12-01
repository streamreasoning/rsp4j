package org.streamreasoning.rsp4j.csparql2.stream;

import org.streamreasoning.rsp4j.esper.secret.content.ContentEventBean;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.GraphUtil;
import org.apache.jena.mem.GraphMem;

@Log4j
public class JenaGraphContent extends ContentEventBean<Graph, Graph> {

    private Graph content;

    public JenaGraphContent() {
        content = new GraphMem();
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
