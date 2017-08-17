package it.polimi.jasper.engine.query.execution;

import it.polimi.jasper.engine.BaselinesUtils;
import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.jasper.engine.reasoning.GenericRuleJenaTVGReasoner;
import it.polimi.jasper.engine.reasoning.pellet.TVGReasonerPellet;
import it.polimi.jasper.engine.reasoning.JenaTVGReasoner;
import it.polimi.jasper.engine.sds.JenaSDS;
import it.polimi.yasper.core.enums.Entailment;
import it.polimi.yasper.core.enums.StreamOperator;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.query.operators.r2s.RelationToStreamOperator;
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


    static public ContinuousQueryExecution create(RSPQuery query, JenaSDS sds, TVGReasoner r) {
        ContinuousQueryExecution cqe;
        StreamOperator r2S = query.getR2S();
        RelationToStreamOperator s2r;
        switch (r2S) {
            case DSTREAM:
                s2r = RelationToStreamOperator.DSTREAM.get();
                break;
            case ISTREAM:
                s2r = RelationToStreamOperator.ISTREAM.get();
                break;
            case RSTREAM:
                s2r = RelationToStreamOperator.RSTREAM.get();
                break;
            default:
                s2r = RelationToStreamOperator.RSTREAM.get();
                break;
        }

        if (query.isSelectType()) {
            cqe = new ContinuousSelect(query, query.getQ(), sds, r, s2r);
        } else if (query.isConstructType()) {
            cqe = new ContinuouConstruct(query, query.getQ(), sds, r, s2r);
        } else {
            throw new RuntimeException("Unsupported ContinuousQuery Type [" + query.getQueryType() + "]");
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
