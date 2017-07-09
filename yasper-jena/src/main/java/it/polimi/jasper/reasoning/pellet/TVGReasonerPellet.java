package it.polimi.jasper.reasoning.pellet;


import it.polimi.jasper.reasoning.JenaTVGReasoner;
import it.polimi.jasper.reasoning.JenaTimeVaryingInfGraph;
import it.polimi.jasper.sds.JenaTimeVaryingGraph;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import openllet.jena.PelletInfGraph;
import openllet.jena.PelletReasoner;
import openllet.jena.graph.loader.DefaultGraphLoader;
import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerException;

/**
 * Created by riccardo on 06/07/2017.
 */
public class TVGReasonerPellet extends PelletReasoner implements JenaTVGReasoner {

    @Override
    public Reasoner bindSchema(Model model) throws ReasonerException {
        return super.bindSchema(model);
    }

    @Override
    public PelletInfGraph bind(Graph graph) throws ReasonerException {
        _logger.fine("In bind!");
        return new PelletInfTVGraph(graph, this, new DefaultGraphLoader(), null, -1);
    }

    public JenaTimeVaryingInfGraph bind(JenaTimeVaryingGraph graph) {
        return bind(graph);
    }

    public TVGReasoner bindSchema(JenaTimeVaryingGraph data) {
        return bindSchema(data);
    }
}
