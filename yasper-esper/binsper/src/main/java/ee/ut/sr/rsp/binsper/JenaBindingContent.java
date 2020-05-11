package ee.ut.sr.rsp.binsper;

import it.polimi.jasper.secret.content.ContentEventBean;
import it.polimi.jasper.streams.items.StreamItem;
import lombok.extern.log4j.Log4j;
import org.apache.jena.ext.com.google.common.collect.Streams;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.Triple;
import org.apache.jena.mem.GraphMem;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.sparql.algebra.Algebra;
import org.apache.jena.sparql.algebra.op.OpGraph;
import org.apache.jena.sparql.algebra.op.OpTriple;
import org.apache.jena.sparql.engine.QueryIterator;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.engine.binding.BindingUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j
public class JenaBindingContent extends ContentEventBean<Triple, Set<Binding>> {

    private List<Binding> bindigs = new ArrayList<>();
    private Query query;
    private Binding set;
    private Graph content;
    private List<OpTriple> q;
    private OpGraph opgrah;

    public JenaBindingContent(List<OpTriple> q) {
        this.q = q;
    }

//    protected void handleSingleIStream(StreamItem<Triple> st) {
//        // log.debug("Handling single IStreamTest [" + st + "]");
//        Graph graphMem = GraphFactory.createGraphMem();
//        graphMem.add(st.getTypedContent());
//        QueryExecution cqe = QueryExecutionFactory.create(query, ModelFactory.createModelForGraph(graphMem));
//
//        elements.add(cqe.execSelect().nextBinding());
//    }

    @Override
    protected void handleSingleIStream(StreamItem<Triple> st) {
        Triple triple = st.getTypedContent();
        elements.add(triple);
        OpTriple op = new OpTriple(triple);
        GraphMem graphMem = new GraphMem();
        graphMem.add(triple);
        q.forEach(opt -> {
            QueryIterator exec = Algebra.exec(opt, graphMem);
            exec.forEachRemaining(binding -> bindigs.add(binding));
        });

    }

    @Override
    public void replace(Set<Binding> coalesce) {

    }

    public Set<Binding> coalesce() {
        return bindigs.stream().flatMap(binding -> {
            QueryExecution queryExecution = QueryExecutionFactory.create(query);
            queryExecution.setInitialBinding(binding);
            return Streams.stream(queryExecution.execSelect());
        }).map(BindingUtils::asBinding).collect(Collectors.toSet());
    }

}
