package org.streamreasoning.rsp4j.yasper.examples;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.engine.config.EngineConfiguration;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.yasper.engines.Yasper;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.TermImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarOrTerm;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.WindowNodeImpl;
import org.streamreasoning.rsp4j.yasper.querying.syntax.RSPQL;
import org.streamreasoning.rsp4j.yasper.querying.syntax.SimpleRSPQLQuery;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Riccardo on 03/08/16.
 */
public class YasperExample {

    static RDF instance = RDFUtils.getInstance();

    public static void main(String[] args) throws ConfigurationException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        EngineConfiguration ec = EngineConfiguration.loadConfig("/csparql.properties");

        Yasper sr = new Yasper(ec);

        //STREAM DECLARATION
        RDFStream stream = new RDFStream("stream1");

        sr.register(stream);

        IRI p = instance.createIRI("p");

        VarOrTerm s = new VarImpl("s");
        VarOrTerm pp = new TermImpl(p);
        VarOrTerm o = new VarImpl("o");


        WindowNode wn = new WindowNodeImpl("w1", 2, 2, 0);

        RSPQL<Binding> q = new SimpleRSPQLQuery<Binding>("q1", stream, wn, s, pp, o);

        q.addNamedWindow("stream1", wn);

        ContinuousQueryExecution<Graph, Graph, Binding,Binding> cqe = sr.register(q);

        DataStream<Binding> outstream = cqe.outstream();
//        outstream.addConsumer(new InstResponseSysOutFormatter("TTL", true));
        cqe.outstream().addConsumer((arg, ts) -> System.out.println(arg));

        //RUNTIME DATA

        Graph graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S1"), p, instance.createIRI("O1")));
        stream.put(graph, 1000);

        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S2"), p, instance.createIRI("O2")));
        stream.put(graph, 1999);

        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S3"), p, instance.createIRI("O3")));
        stream.put(graph, 2001);

        graph = instance.createGraph();

        graph.add(instance.createTriple(instance.createIRI("S4"), p, instance.createIRI("O4")));
        stream.put(graph, 3000);


        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S5"), p, instance.createIRI("O5")));
        stream.put(graph, 5000);


        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S6"), p, instance.createIRI("O6")));
        stream.put(graph, 5000);
        stream.put(graph, 6000);


        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S7"), p, instance.createIRI("O7")));
        stream.put(graph, 7000);

        //stream.put(new it.polimi.deib.rsp.test.examples.windowing.RDFStreamDecl.Elem(3000, graph));


    }


}
