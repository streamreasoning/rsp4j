package org.streamreasoning.rsp4j.csparql2.engine;

import org.apache.jena.graph.Graph;
import org.apache.jena.irix.IRIs;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.sparql.engine.binding.Binding;
import org.streamreasoning.rsp4j.api.enums.StreamOperator;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.csparql2.operators.R2ROperatorSPARQL;
import org.streamreasoning.rsp4j.csparql2.operators.R2ROperatorSPARQLEnt;
import org.streamreasoning.rsp4j.csparql2.syntax.RSPQLJenaQuery;
import org.streamreasoning.rsp4j.esper.operators.r2s.JDStream;
import org.streamreasoning.rsp4j.esper.operators.r2s.JIStream;
import org.streamreasoning.rsp4j.esper.operators.r2s.JRStream;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by riccardo on 04/07/2017.
 */
public final class ContinuousQueryExecutionFactory extends QueryExecutionFactory {

    private static Reasoner reasoner;

    static public JenaContinuousQueryExecution create(RSPQLJenaQuery query, Reasoner reasoner, SDS<Graph> sds, DataStream<?> out) {
        StreamOperator r2S = query.getR2S() != null ? query.getR2S() : StreamOperator.RSTREAM;
        RelationToRelationOperator<SolutionMapping<Binding>, SolutionMapping<Binding>> r2r = reasoner != null ?
                new R2ROperatorSPARQLEnt(query, reasoner, sds, IRIs.getBaseStr()) :
                new R2ROperatorSPARQL(query, sds, IRIs.getBaseStr());
        RelationToStreamOperator<SolutionMapping<Binding>, SolutionMapping<Binding>> s2r = getToStreamOperator(r2S);
        return new JenaContinuousQueryExecution(out, query, sds, r2r, s2r, new ArrayList());
    }

    public static RelationToStreamOperator<SolutionMapping<Binding>, SolutionMapping<Binding>> getToStreamOperator(StreamOperator r2S) {
        switch (r2S) {
            case DSTREAM:
                return new JDStream(1);
            case ISTREAM:
                return new JIStream(1);
            case RSTREAM:
                return new JRStream();
            default:
                return new JRStream();
        }
    }


    public static Reasoner getReasoner() {
        return reasoner;
    }

    private static GenericRuleReasoner getTvgReasoner(Model tbox, List<Rule> rules) {
        GenericRuleReasoner reasoner = new GenericRuleReasoner(rules);
        reasoner.setMode(GenericRuleReasoner.HYBRID);
        return (GenericRuleReasoner) reasoner.bindSchema(tbox);
    }

    public static GenericRuleReasoner emptyReasoner() {
        return getTvgReasoner(ModelFactory.createDefaultModel(), new ArrayList<>());
    }
}
