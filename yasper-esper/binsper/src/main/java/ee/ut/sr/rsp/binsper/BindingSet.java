package ee.ut.sr.rsp.binsper;

import org.apache.jena.graph.Node;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.QuerySolutionMap;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.engine.binding.Binding;

import java.util.HashSet;
import java.util.Iterator;

public class BindingSet extends HashSet<Binding> {

    public QuerySolution asQuerySolutionSet() {
        QuerySolutionMap outer = new QuerySolutionMap();

        forEach(binding -> {
            Iterator<Var> vars = binding.vars();

            QuerySolutionMap inner = new QuerySolutionMap();
            while (vars.hasNext()) {
                Var next = vars.next();
                Node node = binding.get(next);
                inner.add(next.getVarName(), (RDFNode) node);
            }
            outer.addAll(inner);

        });

        return outer;
    }
}
