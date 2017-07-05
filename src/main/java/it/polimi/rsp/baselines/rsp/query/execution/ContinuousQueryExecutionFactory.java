package it.polimi.rsp.baselines.rsp.query.execution;

import it.polimi.rsp.baselines.enums.Entailment;
import it.polimi.rsp.baselines.rsp.query.reasoning.TVGReasoner;
import it.polimi.rsp.baselines.rsp.sds.SDS;
import it.polimi.rsp.baselines.utils.BaselinesUtils;
import it.polimi.sr.rsp.RSPQuery;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerFactory;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.RDFSRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.vocabulary.ReasonerVocabulary;

/**
 * Created by riccardo on 04/07/2017.
 */
public final class ContinuousQueryExecutionFactory extends QueryExecutionFactory {


    static public ContinuousQueryExecution create(RSPQuery query, SDS sds, TVGReasoner r) {
        ContinuousQueryExecution cqe;
        if (query.isSelectType()) {
            cqe = new ContinuousSelect(query, sds, r);
        } else if (query.isConstructType()) {
            cqe = new ContinuouConstruct(query, sds, r);
        } else {
            throw new RuntimeException("Unsupported Query Type [" + query.getQueryType() + "]");
        }
        return cqe;
    }

    public static TVGReasoner getGenericRuleReasoner(Entailment ent) {
        TVGReasoner reasoner;
        switch (ent) {
            case OWL2DL:
                reasoner = new TVGReasoner(Rule.rulesFromURL(BaselinesUtils.RHODF_RULE_SET_RUNTIME));
                reasoner.setMode(GenericRuleReasoner.FORWARD);
                break;
            case OWL2EL:
                reasoner = new TVGReasoner(Rule.rulesFromURL(BaselinesUtils.RHODF_RULE_SET_RUNTIME));
                reasoner.setMode(GenericRuleReasoner.FORWARD);
                break;
            case OWL2QL:
                reasoner = new TVGReasoner(Rule.rulesFromURL(BaselinesUtils.RHODF_RULE_SET_RUNTIME));
                reasoner.setMode(GenericRuleReasoner.FORWARD);
                break;
            case OWL2RL:
                reasoner = new TVGReasoner(Rule.rulesFromURL(BaselinesUtils.RHODF_RULE_SET_RUNTIME));
                reasoner.setMode(GenericRuleReasoner.FORWARD);
                break;
            case RDFS:
                reasoner = new TVGReasoner(Rule.rulesFromURL(RDFSRuleReasoner.DEFAULT_RULES));
                reasoner.setMode(GenericRuleReasoner.FORWARD);
                break;
            case RHODF:
                reasoner = new TVGReasoner(Rule.rulesFromURL(BaselinesUtils.RHODF_RULE_SET_RUNTIME));
                reasoner.setMode(GenericRuleReasoner.FORWARD);
                break;
            case NONE:
            default:
                reasoner = null;
        }
        return reasoner;
    }
}
