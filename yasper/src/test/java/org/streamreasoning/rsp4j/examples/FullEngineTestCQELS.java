package org.streamreasoning.rsp4j.examples;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.junit.Before;
import org.junit.Test;
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
import java.util.*;

/**
 * Created by Riccardo on 03/08/16.
 */
public class FullEngineTestCQELS {

    static RDF instance;
    private long current_timestamp;

    @Before
    public void setUp() {
        instance = RDFUtils.getInstance();
    }

    @Test
    public void cqels() throws ConfigurationException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {


        EngineConfiguration ec = EngineConfiguration.loadConfig("/cqels.properties");

        Yasper sr = new Yasper(ec);

        Time time = sr.time();

        //STREAM DECLARATION
        RDFStream stream = new RDFStream("stream1");

        sr.register(stream);

        IRI p = instance.createIRI("p");

        VarOrTerm s = new VarImpl("s");
        VarOrTerm pp = new TermImpl(p);
        VarOrTerm o = new VarImpl("o");

        Rstream<Binding, Binding> r2s = new Rstream<Binding, Binding>();

        RSPQL<Binding> q = new SimpleRSPQLQuery<>("q1", stream, time, new WindowNodeImpl("w1", 2, 2, 0), s, pp, o, r2s);

        ContinuousQueryExecution<Graph, Graph, Binding, Binding> cqe = sr.register(q);

        Map<Long, Set<Binding>> results = new HashMap<>();


        cqe.outstream().addConsumer((arg, ts) -> System.out.println(arg + " " + ts));
        cqe.outstream().addConsumer(new TestConsumer(results));


//        cqe.outstream().addConsumer(new InstResponseSysOutFormatter("TTL", true));

        //RUNTIME DATA

        current_timestamp = 1000L;

        BindingImpl binding1000 = new BindingImpl();
        binding1000.add(s, instance.createIRI("S1"));
        binding1000.add(o, instance.createIRI("O1"));
        results.put(current_timestamp, Collections.singleton(binding1000));

        Graph graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S1"), instance.createIRI("p"), instance.createIRI("O1")));
        stream.put(graph, current_timestamp);


        current_timestamp = 1999;

        Set<Binding> emptySet = new HashSet();
        emptySet.add(binding1000);
        BindingImpl binding1999 = new BindingImpl();
        binding1999.add(s, instance.createIRI("S2"));
        binding1999.add(o, instance.createIRI("O2"));
        emptySet.add(binding1999);
        results.put(current_timestamp, emptySet);

        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S2"), instance.createIRI("p"), instance.createIRI("O2")));
        stream.put(graph, current_timestamp);

        current_timestamp = 2001L;


        emptySet = new HashSet();
        emptySet.add(binding1000);
        emptySet.add(binding1999);
        BindingImpl binding2001 = new BindingImpl();
        binding2001.add(s, instance.createIRI("S3"));
        binding2001.add(o, instance.createIRI("O3"));
        emptySet.add(binding2001);
        results.put(2001L, emptySet);

        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S3"), instance.createIRI("p"), instance.createIRI("O3")));
        stream.put(graph, current_timestamp);

        current_timestamp = 3000L;

        emptySet = new HashSet();
        emptySet.add(binding1000);
        emptySet.add(binding1999);
        emptySet.add(binding2001);
        BindingImpl binding3000 = new BindingImpl();
        binding3000.add(s, instance.createIRI("S4"));
        binding3000.add(o, instance.createIRI("O4"));
        emptySet.add(binding3000);
        results.put(current_timestamp, emptySet);

        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S4"), instance.createIRI("p"), instance.createIRI("O4")));
        stream.put(graph, current_timestamp);


        current_timestamp = 5000L;

        emptySet = new HashSet();
        emptySet.add(binding1000);
        emptySet.add(binding1999);
        emptySet.add(binding2001);
        emptySet.add(binding3000);
        BindingImpl binding5000 = new BindingImpl();
        binding5000.add(s, instance.createIRI("S5"));
        binding5000.add(o, instance.createIRI("O5"));
        emptySet.add(binding5000);
        results.put(5000L, emptySet);


        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S5"), instance.createIRI("p"), instance.createIRI("O5")));
        stream.put(graph, current_timestamp);

        current_timestamp = 6000L;


        emptySet = new HashSet();
        emptySet.add(binding1000);
        emptySet.add(binding1999);
        emptySet.add(binding2001);
        emptySet.add(binding3000);
        emptySet.add(binding5000);
        binding5000.add(s, instance.createIRI("S6"));
        binding5000.add(o, instance.createIRI("O6"));
        results.put(5000L, emptySet);
        BindingImpl binding6000 = new BindingImpl();
        binding6000.add(s, instance.createIRI("S6"));
        binding6000.add(o, instance.createIRI("O6"));
        emptySet.add(binding6000);
        results.put(6000L, emptySet);

        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S6"), instance.createIRI("p"), instance.createIRI("O6")));
        stream.put(graph, 5000);
        stream.put(graph, current_timestamp);


        current_timestamp = 7000L;

        emptySet = new HashSet();
        emptySet.add(binding1000);
        emptySet.add(binding1999);
        emptySet.add(binding2001);
        emptySet.add(binding3000);
        emptySet.add(binding5000);
        emptySet.add(binding6000);
        BindingImpl binding7000 = new BindingImpl();
        binding7000.add(s, instance.createIRI("S7"));
        binding7000.add(o, instance.createIRI("O7"));
        emptySet.add(binding7000);
        results.put(current_timestamp, emptySet);


        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S7"), instance.createIRI("p"), instance.createIRI("O7")));
        stream.put(graph, current_timestamp);

        //stream.put(new it.polimi.deib.rsp.test.examples.windowing.RDFStreamDecl.Elem(3000, graph));

    }


}
