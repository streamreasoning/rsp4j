package it.polimi.jasper.engine.query;

import it.polimi.jasper.engine.instantaneous.GraphBase;
import it.polimi.jasper.engine.instantaneous.JenaGraph;
import it.polimi.rspql.Window;
import it.polimi.rspql.cql.s2_.WindowOperator;
import it.polimi.rspql.instantaneous.Instantaneous;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.query.operators.s2r.windows.TimeVaryingItemImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NamedTVG extends TimeVaryingItemImpl<JenaGraph> {

    private WindowOperator woa;
    private JenaGraph graph;

    public NamedTVG(Maintenance maintenance, WindowOperator wo) {
        super(maintenance);
        this.woa = wo;
        this.graph = new GraphBase();
    }

    @Override
    public void update(long t) {
        Window windowContent = woa.getWindowContent(t);
        //FIXME eval(null, null, t);
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

    @Override
    public void setWindowOperator(WindowOperator w) {
        woa = w;
    }
}