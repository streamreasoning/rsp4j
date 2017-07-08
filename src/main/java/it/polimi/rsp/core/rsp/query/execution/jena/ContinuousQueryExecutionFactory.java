package it.polimi.rsp.core.rsp.query.execution.jena;

import it.polimi.rsp.core.enums.Entailment;
import it.polimi.rsp.core.rsp.query.execution.ContinuousQueryExecution;
import it.polimi.rsp.core.rsp.query.r2s.RelationToStreamOperator;
import it.polimi.rsp.core.rsp.query.reasoning.IdentityTVGReasoner;
import it.polimi.rsp.core.rsp.query.reasoning.TVGReasoner;
import it.polimi.rsp.core.rsp.query.reasoning.jena.TVGReasonerJena;
import it.polimi.rsp.core.rsp.query.reasoning.pellet.TVGReasonerPellet;
import it.polimi.rsp.core.rsp.sds.SDS;
import it.polimi.rsp.core.utils.BaselinesUtils;
import it.polimi.sr.rsp.RSPQuery;
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


    static public ContinuousQueryExecution create(RSPQuery query, SDS sds, TVGReasoner r) {
        ContinuousQueryExecution cqe;
        if (query.isSelectType()) {
            cqe = new ContinuousSelect(query, query.getQ(), sds, r, RelationToStreamOperator.RSTREAM.get());
        } else if (query.isConstructType()) {
            cqe = new ContinuouConstruct(query, query.getQ(), sds, r, RelationToStreamOperator.RSTREAM.get());
        } else {
            throw new RuntimeException("Unsupported Query Type [" + query.getQueryType() + "]");
        }
        return cqe;
    }

    public static TVGReasoner getGenericRuleReasoner(Entailment ent, Model tbox) {
        TVGReasoner reasoner = null;
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
                reasoner = new IdentityTVGReasoner();
        }
        return reasoner;

    }

    private static TVGReasonerJena getTvgReasoner(Model tbox, List<Rule> rules) {
        TVGReasonerJena reasoner = new TVGReasonerJena(rules);
        reasoner.setMode(GenericRuleReasoner.HYBRID);
        return (TVGReasonerJena) reasoner.bindSchema(tbox);
    }
}
