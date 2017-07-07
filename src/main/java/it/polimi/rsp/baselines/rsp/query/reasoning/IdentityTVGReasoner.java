package it.polimi.rsp.baselines.rsp.query.reasoning;

import it.polimi.rsp.baselines.rsp.query.reasoning.jena.BasicForwardRuleInfTVGraph;
import org.apache.jena.graph.Capabilities;
import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.BaseInfGraph;
import org.apache.jena.reasoner.InfGraph;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerException;

/**
 * Created by riccardo on 06/07/2017.
 */
public class IdentityTVGReasoner implements TVGReasoner {
    @Override
    public Reasoner bindSchema(Graph tbox) throws ReasonerException {
        return this;
    }

    @Override
    public Reasoner bindSchema(Model tbox) throws ReasonerException {
        return this;
    }

    @Override
    public InfGraph bind(Graph data) throws ReasonerException {
        return new BasicForwardRuleInfTVGraph(this, data, -1, null);
    }

    @Override
    public void setDerivationLogging(boolean logOn) {

    }

    @Override
    public void setParameter(Property parameterUri, Object value) {

    }

    @Override
    public Model getReasonerCapabilities() {
        return ModelFactory.createDefaultModel();
    }

    @Override
    public void addDescription(Model configSpec, Resource base) {

    }

    @Override
    public boolean supportsProperty(Property property) {
        return false;
    }

    @Override
    public Capabilities getGraphCapabilities() {
        return new BaseInfGraph.InfCapabilities();
    }
}
