package it.polimi.rsp.baselines.rsp.query.execution;

import it.polimi.rsp.baselines.enums.Entailment;
import it.polimi.rsp.baselines.rsp.sds.SDS;
import it.polimi.rsp.baselines.utils.BaselinesUtils;
import it.polimi.sr.rsp.RSPQuery;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerFactory;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.vocabulary.ReasonerVocabulary;

/**
 * Created by riccardo on 04/07/2017.
 */
public class ContinuousQueryExecutionFactory extends QueryExecutionFactory {


    static public ContinuousQueryExecution ccreate(RSPQuery query, SDS sds, Entailment ent) {
        Reasoner reasoner = null;
        ContinuousQueryExecution cqe = null;
        switch (ent) {
            case OWL2DL:
                reasoner = ReasonerRegistry.getOWLReasoner();
                break;
            case OWL2EL:
                break;
            case OWL2QL:
                break;
            case OWL2RL:
                break;
            case RDFS:
                reasoner = ReasonerRegistry.getRDFSReasoner();
                break;
            case RHODF:
                reasoner = new GenericRuleReasoner(Rule.rulesFromURL(BaselinesUtils.RHODF_RULE_SET_RUNTIME));
                break;
            case NONE:
            default:
                reasoner = null;
        }


        if (query.isSelectType()) {
            cqe = new ContinuousSelect(query, sds, reasoner);
        } else if (query.isConstructType()) {
            cqe = new ContinuouConstruct(query, sds, reasoner);
        } else {
            throw new RuntimeException("Unsupported Query Type [" + query.getQueryType() + "]");
        }

        return cqe;
    }
}
