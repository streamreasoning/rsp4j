package it.polimi.jasper.engine.query.execution.observer;

import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.jasper.engine.query.execution.subscribers.ContinuouConstructSubscriber;
import it.polimi.jasper.engine.query.execution.subscribers.ContinuousSelectSubscriber;
import it.polimi.jasper.engine.reasoning.GenericRuleJenaTVGReasoner;
import it.polimi.jasper.engine.reasoning.JenaTVGReasoner;
import it.polimi.jasper.engine.reasoning.pellet.TVGReasonerPellet;
import it.polimi.yasper.core.quering.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.quering.SDS;
import it.polimi.yasper.core.reasoning.Entailment;
import it.polimi.yasper.core.enums.StreamOperator;
import it.polimi.yasper.core.quering.execution.ContinuousQueryExecutionObserver;
import it.polimi.yasper.core.quering.execution.ContinuousQueryExecutionSubscriber;
import it.polimi.yasper.core.quering.operators.r2s.Dstream;
import it.polimi.yasper.core.quering.operators.r2s.Istream;
import it.polimi.yasper.core.quering.operators.r2s.Rstream;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by riccardo on 04/07/2017.
 */
public final class ContinuousQueryExecutionFactory extends QueryExecutionFactory {


    static public ContinuousQueryExecutionObserver createObserver(RSPQuery query, SDS sds, TVGReasoner r) {
        ContinuousQueryExecutionObserver cqe;
        StreamOperator r2S = query.getR2S() != null ? query.getR2S() : StreamOperator.RSTREAM;
        RelationToStreamOperator s2r = getToStreamOperator(r2S);

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
        StreamOperator r2S = query.getR2S() != null ? query.getR2S() : StreamOperator.RSTREAM;
        RelationToStreamOperator s2r = getToStreamOperator(r2S);

        if (query.getQ().isSelectType()) {
            cqe = new ContinuousSelectSubscriber(query, sds, r, s2r);
        } else if (query.getQ().isConstructType()) {
            cqe = new ContinuouConstructSubscriber(query, sds, r, s2r);
        } else {
            throw new RuntimeException("Unsupported ContinuousQuery Type [" + query.getQ().getQueryType() + "]");
        }

        return cqe;
    }

    private static RelationToStreamOperator getToStreamOperator(StreamOperator r2S) {
        switch (r2S) {
            case DSTREAM:
                return new Dstream(1);
            case ISTREAM:
                return new Istream(1);
            case RSTREAM:
                return new Rstream();
            default:
                return new Rstream();
        }
    }


    public static JenaTVGReasoner getGenericRuleReasoner(Entailment ent, Model tbox) {
        JenaTVGReasoner reasoner = null;
        switch (ent.getType()) {
            case OWL2DL:
            case OWL2EL:
            case OWL2QL:
            case OWL2RL:
            case CUSTOM:
            case PELLET:
                reasoner = new TVGReasonerPellet();
                reasoner.bindSchema(tbox);
                break;
            default:
                reasoner = getTvgReasoner(tbox, (List<Rule>) ent.getRules());
        }

        return reasoner;
    }

    private static JenaTVGReasoner getTvgReasoner(Model tbox, List<Rule> rules) {
        GenericRuleJenaTVGReasoner reasoner = new GenericRuleJenaTVGReasoner(rules);
        reasoner.setMode(GenericRuleReasoner.HYBRID);
        return (GenericRuleJenaTVGReasoner) reasoner.bindSchema(tbox);
    }

    public static JenaTVGReasoner emptyReasoner() {
        return getTvgReasoner(ModelFactory.createDefaultModel(), new ArrayList<>());
    }
}
