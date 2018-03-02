package it.polimi.jasper.engine.query;

import it.polimi.jasper.engine.instantaneous.GraphBase;
import it.polimi.jasper.engine.instantaneous.JenaGraph;
import it.polimi.jasper.esper.ContentBean;
import it.polimi.jasper.esper.EsperStatementView;
import it.polimi.yasper.core.rspql.Instantaneous;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.enums.Maintenance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NamedTVG extends EsperStatementView<JenaGraph> {

    private WindowAssigner woa;
    private JenaGraph graph;
    private String uri;

    public NamedTVG(String uri, Maintenance maintenance, WindowAssigner wo) {
        super(maintenance);
        this.woa = wo;
        this.graph = new GraphBase();
        this.uri = uri;
    }

    @Override
    public void update(long t) {
        ContentBean content = (ContentBean) woa.getContent(t);
        eval(null, content.asArray(), t);
    }

    @Override
    public Instantaneous eval(long t) {
        graph.setTimestamp(t);
        return graph;
    }

    public void setContent(JenaGraph jenaGraph) {
        this.graph = jenaGraph;
    }

    @Override
    public JenaGraph getContent(long now) {
        return graph;
    }

    public String getUri() {
        return uri;
    }



}