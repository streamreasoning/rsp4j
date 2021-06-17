import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Test;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;
import org.streamreasoning.rsp4j.yasper.sds.SDSImpl;

import java.io.InputStream;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

public class CompositionalTest {

    public void pipe() throws InterruptedException {

        RDF instance = RDFUtils.getInstance();

        VarOrTerm s = new VarImpl("s");
        VarOrTerm p = new TermImpl(instance.createIRI("p"));
        VarOrTerm o = new VarImpl("o");

        SDSImpl sds = new SDSImpl();

        sds.add(instance.createQuad(null, instance.createIRI("S1"), instance.createIRI("p"), instance.createIRI("O1")));
        sds.add(instance.createQuad(null, instance.createIRI("S2"), instance.createIRI("q"), instance.createIRI("O2")));
        sds.add(instance.createQuad(null, instance.createIRI("S3"), instance.createIRI("p"), instance.createIRI("O3")));

        TP TP = new TP(s, p, o);

        sds.materialize(0);

        Stream<Binding> eval = TP.eval(sds.toStream());
//        eval.forEach(System.out::println);
//        bgp.eval(0).forEach(System.err::println);

        Filter<Binding> filter = new Filter<>(TP.eval(sds.toStream()), binding -> binding.value(o).equals(instance.createIRI("O3")));
        Stream<Binding> eval1 = filter.eval(null);
//        eval1.forEach(System.out::println);

//        bgp.eval(0).filter(Objects::nonNull).map(filter).filter(Objects::nonNull).forEach(System.err::println);

        filter = new Filter<>(TP.eval(sds.toStream()), binding -> binding.value(o).equals(instance.createIRI("O3")));

        Projection projection = new Projection(filter.eval(null), s);

        Stream<Binding> eval2 = projection.eval(null);
        eval2.forEach(System.out::println);

        TP.eval(sds.toStream()).map(filter).filter(Objects::nonNull).map(projection).forEach(System.err::println);
        TP.eval(sds.toStream()).map(filter).filter(Objects::nonNull).map(projection).forEach(System.err::println);

    }

    @Test
    public void lazy() {

        RDF instance = RDFUtils.getInstance();

        VarOrTerm s = new VarImpl("s");
        VarOrTerm p = new TermImpl(instance.createIRI("p"));
        VarOrTerm o = new VarImpl("o");

        SDSImpl sds = new SDSImpl();

        sds.add(instance.createQuad(null, instance.createIRI("S1"), instance.createIRI("p"), instance.createIRI("O1")));
        sds.add(instance.createQuad(null, instance.createIRI("S2"), instance.createIRI("q"), instance.createIRI("O2")));
        sds.add(instance.createQuad(null, instance.createIRI("S3"), instance.createIRI("p"), instance.createIRI("O3")));

        TP TP = new TP(s, p, o);

        sds.materialize(0);

        TimeVarying<Collection<Binding>> bgpres = TP.apply(sds);

        bgpres.materialize(0);

        Filter<Binding> filter = new Filter<>(bgpres, binding -> binding.value(o).equals(instance.createIRI("O3")));

        TimeVarying<Collection<Binding>> fres = filter.apply((SDS<Binding>) null);


        fres.materialize(0);

        Projection projection = new Projection(fres, s);

        TimeVarying<Collection<Binding>> pres = projection.apply((SDS<Binding>) null);
        pres.materialize(0);
        Collection<Binding> bindings = pres.get();
        bindings.forEach(System.out::println);

        sds.materialize(0);
        TP.eval(sds.toStream()).map(filter).filter(Objects::nonNull).map(projection).forEach(System.err::println);
    }

    @Test
    public void testqueries() {

        InputStream resource = CompositionalTest.class.getClassLoader().getResourceAsStream("BasicPatterns/model0.nt");
        Model model = ModelFactory.createDefaultModel().read(resource, "", "TTL");
        JenaRDF rdf = new JenaRDF();

        Graph jenaGraph = rdf.asGraph(model);

        SDSImpl sds = new SDSImpl();

        jenaGraph.stream().forEach(q -> sds.add(rdf.createQuad(null, q.getSubject(), q.getPredicate(), q.getObject())));


    }
}
