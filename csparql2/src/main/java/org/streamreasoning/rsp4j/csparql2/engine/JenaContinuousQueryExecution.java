package org.streamreasoning.rsp4j.csparql2.engine;


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
import org.streamreasoning.rsp4j.api.format.QueryResultFormatter;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by riccardo on 03/07/2017.
 */
@Log4j
public class JenaContinuousQueryExecution extends Observable implements Observer, ContinuousQueryExecution<Graph, Graph, SolutionMapping<Binding>,SolutionMapping<Binding>> {

    private final RelationToStreamOperator<SolutionMapping<Binding>,SolutionMapping<Binding>> r2s;
    private List<StreamToRelationOp<Graph, Graph>> s2rs;
    private final RelationToRelationOperator<SolutionMapping<Binding>,SolutionMapping<Binding>> r2r;
    private final SDS<Graph> sds;
    private final ContinuousQuery query;
    private final Query q;
    private final Template template;
    private final DataStream out;
    protected QueryExecution execution;
    protected IRIResolver resolver;

    public JenaContinuousQueryExecution(IRIResolver resolver, DataStream out, ContinuousQuery query, SDS<Graph> sds, RelationToRelationOperator<SolutionMapping<Binding>,SolutionMapping<Binding>> r2r, RelationToStreamOperator<SolutionMapping<Binding>,SolutionMapping<Binding>> r2s, List<StreamToRelationOp<Graph, Graph>> s2rs) {
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
    r2r.eval(null)
        .forEach(
            (SolutionMapping<Binding> ib) -> {
              Stream<SolutionMapping<Binding>> evalStream = r2s.eval(Stream.of(ib), now);
              Optional<SolutionMapping<Binding>> eval = evalStream.findFirst();
              if (eval.isPresent()) {
                setChanged();
                if (query.isConstructType()) {
                  Graph apply = apply(eval.get().get(), now);
                  if (outstream() != null) {
                    outstream().put(apply, now);
                  }
                  notifyObservers(apply);
                } else {
                  Table arg1 = apply2(eval.get().get(), now);
                  if (outstream() != null) {
                    outstream().put(arg1, now);
                  }
                  notifyObservers(arg1);
                }
              }
            });
    }

    public DataStream outstream() {
        return out;
    }

    private Table apply2(Binding eval, Long now) {
        Table table = TableFactory.create();

        Node etime = NodeFactory.createLiteral(now + "", XSDDatatype.XSDdateTimeStamp);
        Binding timestamp = BindingFactory.binding(Var.alloc("eventTime"), etime);
        Binding merge = Algebra.merge(eval, timestamp);
        Node ptime = NodeFactory.createLiteral(System.currentTimeMillis() + "", XSDDatatype.XSDlong);
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



    //@Override
    public SDS<Graph> getSDS() {
        return sds;
    }


    public void addS2R(StreamToRelationOp<Graph, Graph> op) {
        s2rs.add(op);
    }

    //@Override
    public StreamToRelationOp<Graph, Graph>[] getS2R() {
        return s2rs.toArray(new StreamToRelationOp[s2rs.size()]);
    }

    //@Override
    public RelationToRelationOperator getR2R() {
        return r2r;
    }

    //@Override
    public RelationToStreamOperator getR2S() {
        return r2s;
    }



    public void addQueryFormatter(QueryResultFormatter o) {
        addObserver(o);
    }

   // @Override
    public void remove(QueryResultFormatter o) {
       // deleteObserver(o);
    }


    @Override
    public TimeVarying<Collection<SolutionMapping<Binding>>> output() {
        return null;
    }

    @Override
    public ContinuousQuery query() {
        return query;
    }

    @Override
    public SDS<Graph> sds() {
        return null;
    }

    @Override
    public StreamToRelationOp<Graph, Graph>[] s2rs() {
        return new StreamToRelationOp[0];
    }

    @Override
    public RelationToRelationOperator<Graph, SolutionMapping<Binding>> r2r() {
        return null;
    }

    @Override
    public RelationToStreamOperator<SolutionMapping<Binding>, SolutionMapping<Binding>> r2s() {
        return null;
    }

    @Override
    public void add(StreamToRelationOp<Graph, Graph> op) {

    }

    @Override
    public Stream<SolutionMapping<Binding>> eval(Long now) {
        return null;
    }
}
