package it.polimi.csparql2.jena.engine;

import it.polimi.yasper.core.format.QueryResultFormatter;
import it.polimi.yasper.core.operators.r2r.RelationToRelationOperator;
import it.polimi.yasper.core.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.operators.s2r.StreamToRelationOperator;
import it.polimi.yasper.core.querying.ContinuousQuery;
import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.querying.result.SolutionMapping;
import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.stream.data.WebDataStream;
import lombok.extern.log4j.Log4j;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.graph.*;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.riot.system.IRIResolver;
import org.apache.jena.sparql.algebra.Algebra;
import org.apache.jena.sparql.algebra.Table;
import org.apache.jena.sparql.algebra.TableFactory;
import org.apache.jena.sparql.core.Quad;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.engine.binding.BindingFactory;
import org.apache.jena.sparql.modify.TemplateLib;
import org.apache.jena.sparql.syntax.Template;

import java.util.*;

/**
 * Created by riccardo on 03/07/2017.
 */
@Log4j
public class JenaContinuousQueryExecution extends Observable implements Observer, ContinuousQueryExecution<Graph, Graph, Binding> {

    private final RelationToStreamOperator<Binding> r2s;
    private List<StreamToRelationOperator<Graph, Graph>> s2rs;
    private final RelationToRelationOperator<Binding> r2r;
    private final SDS<Graph> sds;
    private final ContinuousQuery query;
    private final Query q;
    private final Template template;
    private final WebDataStream out;
    protected QueryExecution execution;
    protected IRIResolver resolver;

    public JenaContinuousQueryExecution(IRIResolver resolver, WebDataStream out, ContinuousQuery query, SDS<Graph> sds, RelationToRelationOperator<Binding> r2r, RelationToStreamOperator<Binding> r2s, List<StreamToRelationOperator<Graph, Graph>> s2rs) {
        this.resolver = resolver;
        this.query = query;
        this.q = (Query) query;
        this.template = q.getConstructTemplate();
        this.sds = sds;
        this.s2rs = s2rs;
        this.r2r = r2r;
        this.r2s = r2s;
        this.out = out;
    }

    @Override
    public void update(Observable o, Object arg) {
        Long now = (Long) arg;
//        sds.materialize(now);
        r2r.eval(now).forEach((SolutionMapping<Binding> ib) -> {
            Binding eval = r2s.eval(ib, now);
            setChanged();
            if (query.isConstructType()) {
                Graph apply = apply(eval, now);
                if (outstream() != null) {
                    outstream().put(apply, now);
                }
                notifyObservers(apply);
            } else {
                Table arg1 = apply2(eval, now);
                if (outstream() != null) {
                    outstream().put(arg1, now);
                }
                notifyObservers(arg1);
            }
        });
    }

    @Override
    public <T> WebDataStream<T> outstream() {
        return out;
    }

    private Table apply2(Binding eval, Long now) {
        Table table = TableFactory.create();

        Node etime = NodeFactory.createLiteral(now + "", XSDDatatype.XSDdateTimeStamp);
        Binding timestamp = BindingFactory.binding(Var.alloc("eventTime"), etime);
        Binding merge = Algebra.merge(eval, timestamp);
        Node ptime = NodeFactory.createLiteral(System.currentTimeMillis() + "", XSDDatatype.XSDdateTimeStamp);
        timestamp = BindingFactory.binding(Var.alloc("processingTime"), ptime);
        merge = Algebra.merge(merge, timestamp);
        table.addBinding(merge);
        return table;
    }

    public Graph apply(Binding b, long now) {
        // Iteration is a new mapping of bnodes.
        Graph g = Factory.createGraphMem();
        Map<Node, Node> bNodeMap = new HashMap<>();
        bNodeMap.clear();
        List<Quad> quads = template.getQuads();
        for (Quad quad : quads) {
            Quad q = TemplateLib.subst(quad, b, bNodeMap);
            if (!q.isConcrete()) {
                // Log.warn(TemplateLib.class, "Unbound quad:
                // "+FmtUtils.stringForQuad(quad)) ;
                continue;
            }
            g.add(q.asTriple());
        }
        Node s = NodeFactory.createURI(resolver.resolveToString("/result/" + now));
        Node p = NodeFactory.createURI(resolver.resolveToString("eventTime"));
        Node o = NodeFactory.createLiteral(now + "", XSDDatatype.XSDdateTimeStamp);
        g.add(Triple.create(s, p, o));
        p = NodeFactory.createURI(resolver.resolveToString("processingTime"));
        o = NodeFactory.createLiteral(System.currentTimeMillis() + "", XSDDatatype.XSDdateTimeStamp);
        g.add(Triple.create(s, p, o));
        return g;
    }

    @Override
    public ContinuousQuery getContinuousQuery() {
        return query;
    }

    @Override
    public SDS<Graph> getSDS() {
        return sds;
    }


    public void addS2R(StreamToRelationOperator<Graph, Graph> op) {
        s2rs.add(op);
    }

    @Override
    public StreamToRelationOperator<Graph, Graph>[] getS2R() {
        return s2rs.toArray(new StreamToRelationOperator[s2rs.size()]);
    }

    @Override
    public RelationToRelationOperator getR2R() {
        return r2r;
    }

    @Override
    public RelationToStreamOperator getR2S() {
        return r2s;
    }


    @Override
    public void add(QueryResultFormatter o) {
        addObserver(o);
    }

    @Override
    public void remove(QueryResultFormatter o) {
        deleteObserver(o);
    }

}
