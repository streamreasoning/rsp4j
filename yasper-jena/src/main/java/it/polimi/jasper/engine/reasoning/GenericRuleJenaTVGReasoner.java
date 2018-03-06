package it.polimi.jasper.engine.reasoning;

import it.polimi.jasper.engine.reasoning.rulesys.BasicForwardRuleInstantaneousInfTVGraph;
import it.polimi.jasper.engine.reasoning.rulesys.FBRuleInstantaneousInfTVGraph;
import it.polimi.jasper.engine.reasoning.rulesys.LPBackwardRuleInstantaneousInfTvGraph;
import it.polimi.jasper.engine.reasoning.rulesys.RETERuleInstantaneousInfTVGraph;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import it.polimi.yasper.simple.windowing.TimeVarying;
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
public class GenericRuleJenaTVGReasoner extends GenericRuleReasoner implements TVGReasoner<TimeVarying<InfGraph>, TimeVarying<Graph>> {


    private GenericRuleJenaTVGReasoner grr;

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


    /**
     * Attach the reasoner to a set of RDF data to process.
     * The reasoner may already have been bound to specific rules or ontology
     * axioms (encoded in RDF) through earlier bindRuleset calls.
     *
     * @param data the RDF data to be processed, some reasoners may restrict
     *             the range of RDF which is legal here (sds.g. syntactic restrictions in OWL).
     * @return an inference graph through which the data+reasoner can be queried.
     * @throws ReasonerException if the data is ill-formed according to the
     *                           constraints imposed by this reasoner.
     */

    public InfGraph bind(Graph data) throws ReasonerException {
        Graph schemaArg = schemaGraph == null ? getPreload() : schemaGraph;

        InfGraph graph = null;
        if (mode == FORWARD) {
            graph = new BasicForwardRuleInstantaneousInfTVGraph(this, rules, schemaArg, data);
            ((BasicForwardRuleInfGraph) graph).setTraceOn(super.isTraceOn());
        } else if (mode == FORWARD_RETE) {
            Reasoner r = this;
            graph = new RETERuleInstantaneousInfTVGraph(r, rules, schemaArg, data);
            ((BasicForwardRuleInfGraph) graph).setTraceOn(super.isTraceOn());
            ((BasicForwardRuleInfGraph) graph).setFunctorFiltering(filterFunctors);
        } else if (mode == BACKWARD) {
            graph = new LPBackwardRuleInstantaneousInfTvGraph(this, getBruleStore(), data, schemaArg, data);
            ((LPBackwardRuleInfGraph) graph).setTraceOn(super.isTraceOn());
        } else {
            List<Rule> ruleSet = ((FBRuleInfGraph) schemaArg).getRules();
            FBRuleInstantaneousInfTVGraph fbgraph = new FBRuleInstantaneousInfTVGraph(this, ruleSet, schemaArg, data);
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
     * will be combined with the data when the final InstantaneousInfGraph is created.
     */
    @Override
    public Reasoner bindSchema(Graph tbox) throws ReasonerException {
        if (schemaGraph != null) {
            throw new ReasonerException("Can only bindTVG one schema at a time to a GenericRuleReasoner");
        }
        Graph graph = null;
        if (mode == FORWARD) {
            graph = new BasicForwardRuleInstantaneousInfTVGraph(this, rules, null, tbox);
            ((org.apache.jena.reasoner.InfGraph) graph).prepare();
        } else if (mode == FORWARD_RETE) {
            graph = new RETERuleInfGraph(this, rules, null, tbox);
            ((org.apache.jena.reasoner.InfGraph) graph).prepare();
        } else if (mode == BACKWARD) {
            graph = tbox;
        } else {
            List<Rule> ruleSet = rules;
            graph = new FBRuleInfGraph(this, ruleSet, getPreload(), tbox);
            if (enableTGCCaching) ((FBRuleInfGraph) graph).setUseTGCCache();
            ((FBRuleInfGraph) graph).prepare();
        }
        this.grr = new GenericRuleJenaTVGReasoner(rules, graph, factory, mode);
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

    @Override
    public TimeVarying<InfGraph> bindTVG(TimeVarying<Graph> data) {
        return new TimeVaryingInfGraphImpl(data, bind(data.asT()));
    }

    @Override
    public TVGReasoner<TimeVarying<InfGraph>, TimeVarying<Graph>> bindSchemaTVG(TimeVarying<Graph> g) {
        bindSchema(g.asT());
        return grr;
    }
}
