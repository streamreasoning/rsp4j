package it.polimi.jasper.engine.query;

import it.polimi.esper.EsperStatementView;
import it.polimi.jasper.engine.instantaneous.GraphBase;
import it.polimi.jasper.engine.instantaneous.JenaGraph;
import it.polimi.rspql.instantaneous.Instantaneous;
import it.polimi.spe.content.Content;
import it.polimi.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.enums.Maintenance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.Observable;

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
        Content content = woa.getContent(t);
    }

    @Override
    public Instantaneous eval(long t) {
        update(t);
        graph.setTimestamp(t);
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

    public String getUri() {
        return uri;
    }

    @Override
    public void addObservable(Observable windowAssigner) {

    }
}