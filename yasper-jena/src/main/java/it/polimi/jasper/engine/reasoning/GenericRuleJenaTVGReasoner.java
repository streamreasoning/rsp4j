package it.polimi.jasper.engine.reasoning;

import it.polimi.jasper.engine.reasoning.rulesys.BasicForwardRuleInfTVGraph;
import it.polimi.jasper.engine.reasoning.rulesys.FBRuleInfTVGraph;
import it.polimi.jasper.engine.instantaneous.InstantaneousGraph;
import it.polimi.jasper.engine.instantaneous.InstantaneousModel;
import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.InfGraph;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerException;
import org.apache.jena.reasoner.ReasonerFactory;
import org.apache.jena.reasoner.rulesys.*;

import java.util.List;

/**
 * Created by riccardo on 05/07/2017.
 */
public class GenericRuleJenaTVGReasoner extends GenericRuleReasoner implements JenaTVGReasoner {


    public GenericRuleJenaTVGReasoner(List<Rule> rules) {
        super(rules);
    }

    public GenericRuleJenaTVGReasoner(ReasonerFactory factory, Resource configuration) {
        super(factory, configuration);
    }

    public GenericRuleJenaTVGReasoner(List<Rule> rules, ReasonerFactory factory) {
        super(rules, factory);
    }

    protected GenericRuleJenaTVGReasoner(List<Rule> rules, Graph schemaGraph, ReasonerFactory factory, RuleMode mode) {
        super(rules, schemaGraph, factory, mode);
    }

    public InfGraph bind(InstantaneousModel w) {
        return bind(w.getGraph());
    }

    @Override
    public InfGraph bind(Graph data) throws ReasonerException {
        return bind((InstantaneousGraph) data);
    }

    /**
     * Attach the reasoner to a set of RDF data to process.
     * The reasoner may already have been bound to specific rules or ontology
     * axioms (encoded in RDF) through earlier bindRuleset calls.
     *
     * @param data the RDF data to be processed, some reasoners may restrict
     *             the range of RDF which is legal here (e.g. syntactic restrictions in OWL).
     * @return an inference graph through which the data+reasoner can be queried.
     * @throws ReasonerException if the data is ill-formed according to the
     *                           constraints imposed by this reasoner.
     */


    public InfGraph bind(InstantaneousGraph data) throws ReasonerException {
        Graph schemaArg = schemaGraph == null ? getPreload() : schemaGraph;
        InfGraph graph = null;
        if (mode == FORWARD) {
            graph = new BasicForwardRuleInfTVGraph(this, rules, schemaArg, data.getTimestamp(), data.getWindowOperator());
            ((BasicForwardRuleInfGraph) graph).setTraceOn(super.isTraceOn());
        } else if (mode == FORWARD_RETE) {
            graph = new RETERuleInfGraph(this, rules, schemaArg);
            ((BasicForwardRuleInfGraph) graph).setTraceOn(super.isTraceOn());
            ((BasicForwardRuleInfGraph) graph).setFunctorFiltering(filterFunctors);
        } else if (mode == BACKWARD) {
            graph = new LPBackwardRuleInfGraph(this, getBruleStore(), data, schemaArg);
            ((LPBackwardRuleInfGraph) graph).setTraceOn(super.isTraceOn());
        } else {
            List<Rule> ruleSet = ((FBRuleInfGraph) schemaArg).getRules();
            FBRuleInfTVGraph fbgraph = new FBRuleInfTVGraph(this, ruleSet, schemaArg, data.getTimestamp(), data.getWindowOperator());
            graph = fbgraph;
            if (enableTGCCaching) fbgraph.setUseTGCCache();
            fbgraph.setTraceOn(super.isTraceOn());
            fbgraph.setFunctorFiltering(filterFunctors);
            if (preprocessorHooks != null) {
                for (RulePreprocessHook preprocessorHook : preprocessorHooks) {
                    fbgraph.addPreprocessingHook(preprocessorHook);
                }
            }
        }
        graph.setDerivationLogging(recordDerivations);
        graph.rebind(data);
        return graph;
    }

    /**
     * Precompute the implications of a schema graph. The statements in the graph
     * will be combined with the data when the final InfGraph is created.
     */
    @Override
    public Reasoner bindSchema(Graph tbox) throws ReasonerException {
        if (schemaGraph != null) {
            throw new ReasonerException("Can only bind one schema at a time to a GenericRuleReasoner");
        }
        Graph graph = null;
        if (mode == FORWARD) {
            graph = new BasicForwardRuleInfTVGraph(this, rules, null, tbox, -1, null);
            ((InfGraph) graph).prepare();
        } else if (mode == FORWARD_RETE) {
            graph = new RETERuleInfGraph(this, rules, null, tbox);
            ((InfGraph) graph).prepare();
        } else if (mode == BACKWARD) {
            graph = tbox;
        } else {
            List<Rule> ruleSet = rules;
            graph = new FBRuleInfGraph(this, ruleSet, getPreload(), tbox);
            if (enableTGCCaching) ((FBRuleInfGraph) graph).setUseTGCCache();
            ((FBRuleInfGraph) graph).prepare();
        }
        GenericRuleJenaTVGReasoner grr = new GenericRuleJenaTVGReasoner(rules, graph, factory, mode);
        grr.setDerivationLogging(recordDerivations);
        grr.setTraceOn(super.isTraceOn());
        grr.setTransitiveClosureCaching(enableTGCCaching);
        grr.setFunctorFiltering(filterFunctors);
        if (preprocessorHooks != null) {
            for (RulePreprocessHook preprocessorHook : preprocessorHooks) {
                grr.addPreprocessingHook(preprocessorHook);
            }
        }
        return grr;
    }
}
