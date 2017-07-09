package it.polimi.yasper.core.query.operators.s2r;

import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.jena.TimeVaryingGraphBase;
import it.polimi.yasper.core.jena.JenaTimeVaryingGraph;
import it.polimi.yasper.core.engine.RSPListener;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.impl.ModelCom;

import java.util.Observable;

@Log4j
@Data
@Getter
@Setter
public abstract class WindowModel extends Observable implements RSPListener {

    protected Model model;
    protected JenaTimeVaryingGraph graph;
    protected Maintenance maintenance;

    public WindowModel(JenaTimeVaryingGraph base) {
        graph = base;
        ModelCom modelCom = new ModelCom(graph);
        model = modelCom;
    }

    public WindowModel() {
        graph = new TimeVaryingGraphBase(-1, this);
        ModelCom modelCom = new ModelCom(graph);
        model = modelCom;
    }


    public long getTimestamp() {
        return getGraph().getTimestamp();
    }
}