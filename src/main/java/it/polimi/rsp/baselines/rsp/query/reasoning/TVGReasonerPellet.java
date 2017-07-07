package it.polimi.rsp.baselines.rsp.query.reasoning;


import it.polimi.rsp.baselines.rsp.sds.graphs.PelletInfTVGraph;
import it.polimi.rsp.baselines.rsp.sds.graphs.TimeVaryingGraph;
import it.polimi.rsp.baselines.rsp.sds.graphs.TimeVaryingInfGraph;
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
public class TVGReasonerPellet extends PelletReasoner implements TVGReasoner {

    @Override
    public Reasoner bindSchema(Model model) throws ReasonerException {
        return super.bindSchema(model);
    }

    @Override
    public PelletInfGraph bind(Graph graph) throws ReasonerException {
        _logger.fine("In bind!");
        return new PelletInfTVGraph(graph, this, new DefaultGraphLoader(), null, -1);
    }

    public TimeVaryingInfGraph bind(TimeVaryingGraph graph) {
        return bind(graph);
    }

    public TVGReasoner bindSchema(TimeVaryingGraph data) {
        return bindSchema(data);
    }
}
