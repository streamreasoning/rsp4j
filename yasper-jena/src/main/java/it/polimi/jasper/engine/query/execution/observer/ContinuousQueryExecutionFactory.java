package it.polimi.jasper.engine.query.execution.observer;

import it.polimi.jasper.engine.BaselinesUtils;
import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.jasper.engine.query.execution.subscribers.ContinuouConstructSubscriber;
import it.polimi.jasper.engine.query.execution.subscribers.ContinuousSelectSubscriber;
import it.polimi.jasper.engine.reasoning.GenericRuleJenaTVGReasoner;
import it.polimi.jasper.engine.reasoning.JenaTVGReasoner;
import it.polimi.jasper.engine.reasoning.pellet.TVGReasonerPellet;
import it.polimi.rspql.cql._2s._ToStreamOperator;
import it.polimi.rspql.querying.SDS;
import it.polimi.yasper.core.enums.Entailment;
import it.polimi.yasper.core.enums.StreamOperator;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecutionObserver;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecutionSubscriber;
import it.polimi.yasper.core.query.operators.r2s.Dstream;
import it.polimi.yasper.core.query.operators.r2s.Istream;
import it.polimi.yasper.core.query.operators.r2s.Rstream;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.RDFSRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;

import java.util.List;


/**
 * Created by riccardo on 04/07/2017.
 */
public final class ContinuousQueryExecutionFactory extends QueryExecutionFactory {


    static public ContinuousQueryExecutionObserver createObserver(RSPQuery query, SDS sds, TVGReasoner r) {
        ContinuousQueryExecutionObserver cqe;
        StreamOperator r2S = query.getR2S();
        _ToStreamOperator s2r;
        switch (r2S) {
            case DSTREAM:
                s2r = new Dstream(1);
                break;
            case ISTREAM:
                s2r = new Istream(1);
                break;
            case RSTREAM:
                s2r = new Rstream();
                break;
            default:
                s2r = new Rstream();
                break;
        }

        if (query.getQ().isSelectType()) {
            cqe = new ContinuousSelect(query, sds, r, s2r);
        } else if (query.getQ().isConstructType()) {
            cqe = new ContinuouConstruct(query, sds, r, s2r);
        } else {
            throw new RuntimeException("Unsupported ContinuousQuery Type [" + query.getQ().getQueryType() + "]");
        }

        return cqe;
    }

    static public ContinuousQueryExecutionSubscriber createSubscriber(RSPQuery query, SDS sds, TVGReasoner r) {
        ContinuousQueryExecutionSubscriber cqe;
        StreamOperator r2S = query.getR2S();
        _ToStreamOperator s2r;
        switch (r2S) {
            case DSTREAM:
                s2r = new Dstream(1);
                break;
            case ISTREAM:
                s2r = new Istream(1);
                break;
            case RSTREAM:
                s2r = new Rstream();
                break;
            default:
                s2r = new Rstream();
                break;
        }

        if (query.getQ().isSelectType()) {
            cqe = new ContinuousSelectSubscriber(query, sds, r, s2r);
        } else if (query.getQ().isConstructType()) {
            cqe = new ContinuouConstructSubscriber(query, sds, r, s2r);
        } else {
            throw new RuntimeException("Unsupported ContinuousQuery Type [" + query.getQ().getQueryType() + "]");
        }

        return cqe;
    }


    public static JenaTVGReasoner getGenericRuleReasoner(Entailment ent, Model tbox) {
        JenaTVGReasoner reasoner = null;
        switch (ent) {
            case OWL2DL:
                break;
            case OWL2EL:
                break;
            case OWL2QL:
                break;
            case OWL2RL:
                break;
            case PELLET:
                reasoner = new TVGReasonerPellet();
                reasoner.bindSchema(tbox);
                break;
            case RDFS:
                ReasonerRegistry.getRDFSSimpleReasoner();
                reasoner = getTvgReasoner(tbox, Rule.rulesFromURL(RDFSRuleReasoner.DEFAULT_RULES));
                break;
            case RHODF:
                reasoner = getTvgReasoner(tbox, Rule.rulesFromURL(BaselinesUtils.RHODF_RULE_SET_RUNTIME));
                break;
            case NONE:
            default:
                reasoner = null;
        }
        return reasoner;

    }

    private static JenaTVGReasoner getTvgReasoner(Model tbox, List<Rule> rules) {
        GenericRuleJenaTVGReasoner reasoner = new GenericRuleJenaTVGReasoner(rules);
        reasoner.setMode(GenericRuleReasoner.HYBRID);
        return (GenericRuleJenaTVGReasoner) reasoner.bindSchema(tbox);
    }
}
