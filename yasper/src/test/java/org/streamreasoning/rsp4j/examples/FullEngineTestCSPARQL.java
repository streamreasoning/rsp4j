package org.streamreasoning.rsp4j.examples;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.junit.Before;
import org.streamreasoning.rsp4j.TestConsumer;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.engine.config.EngineConfiguration;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.yasper.engines.Yasper;
import org.streamreasoning.rsp4j.yasper.examples.RDFStream;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.WindowNodeImpl;
import org.streamreasoning.rsp4j.yasper.querying.syntax.RSPQL;
import org.streamreasoning.rsp4j.yasper.querying.syntax.SimpleRSPQLQuery;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Riccardo on 03/08/16.
 */
public class FullEngineTestCSPARQL {

    static RDF instance;

    @Before
    public void setUp() {
        instance = RDFUtils.getInstance();
    }

    public void csparql() throws ConfigurationException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        EngineConfiguration ec = EngineConfiguration.loadConfig("/csparql.properties");

        Yasper csparql = new Yasper(ec);
        Time time = csparql.time();

        //STREAM DECLARATION
        RDFStream stream = new RDFStream("stream1");

        csparql.register(stream);

        //_____
        IRI p = instance.createIRI("p");

        VarOrTerm s = new VarImpl("s");
        VarOrTerm pp = new TermImpl(p);
        VarOrTerm o = new VarImpl("o");

        Rstream<Binding, Binding> r2s = new Rstream<Binding, Binding>();

        RSPQL<Binding> q = new SimpleRSPQLQuery<>("q1", stream, time, new WindowNodeImpl("w1", 2, 2, 0), s, pp, o, r2s);


        ContinuousQueryExecution<Graph, Graph, Binding, Binding> cqe = csparql.register(q);

        Map<Long, Set<Binding>> results = new HashMap<>();

        cqe.outstream().addConsumer((arg, ts) -> System.out.println(arg + " " + ts));
        cqe.outstream().addConsumer(new TestConsumer(results));

        //RUNTIME DATA


        Set<Binding> emptySet = new HashSet();
        BindingImpl binding1000 = new BindingImpl();
        binding1000.add(s, instance.createIRI("S1"));
        binding1000.add(o, instance.createIRI("O1"));
        emptySet.add(binding1000);

        Graph graph = instance.createGraph();

        graph.add(instance.createTriple(instance.createIRI("S1"), p, instance.createIRI("O1")));
        stream.put(graph, 1000);


        BindingImpl binding1999 = new BindingImpl();
        binding1999.add(s, instance.createIRI("S2"));
        binding1999.add(o, instance.createIRI("O2"));
        emptySet.add(binding1999);

        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S2"), p, instance.createIRI("O2")));
        stream.put(graph, 1999);

        //2001
        results.put(2001L, emptySet);
        emptySet = new HashSet();

        BindingImpl binding2001 = new BindingImpl();
        binding2001.add(s, instance.createIRI("S3"));
        binding2001.add(o, instance.createIRI("O3"));
        emptySet.add(binding2001);

        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S3"), p, instance.createIRI("O3")));
        stream.put(graph, 2001);

        BindingImpl binding3000 = new BindingImpl();
        binding3000.add(s, instance.createIRI("S4"));
        binding3000.add(o, instance.createIRI("O4"));
        emptySet.add(binding3000);

        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S4"), p, instance.createIRI("O4")));
        stream.put(graph, 3000);

        results.put(5000L, emptySet);
        emptySet = new HashSet();

        BindingImpl binding5000 = new BindingImpl();
        binding5000.add(s, instance.createIRI("S5"));
        binding5000.add(o, instance.createIRI("O5"));
        emptySet.add(binding5000);

        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S5"), p, instance.createIRI("O5")));
        stream.put(graph, 5000);

        BindingImpl binding5000b = new BindingImpl();
        binding5000b.add(s, instance.createIRI("S6"));
        binding5000b.add(o, instance.createIRI("O6"));
        emptySet.add(binding5000b);

        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S6"), p, instance.createIRI("O6")));
        stream.put(graph, 5000);
        stream.put(graph, 6000);

        BindingImpl binding6000 = new BindingImpl();
        binding6000.add(s, instance.createIRI("S6"));
        binding6000.add(o, instance.createIRI("O6"));
        emptySet.add(binding6000);

        results.put(7000L, emptySet);
        emptySet = new HashSet();

        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S7"), p, instance.createIRI("O7")));
        stream.put(graph, 7000);


        //stream.put(new it.polimi.deib.rsp.test.examples.windowing.RDFStreamDecl.Elem(3000, graph));
    }


}
