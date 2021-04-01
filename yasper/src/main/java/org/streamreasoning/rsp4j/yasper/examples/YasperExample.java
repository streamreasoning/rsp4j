package org.streamreasoning.rsp4j.yasper.examples;

import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.engine.config.EngineConfiguration;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.streamreasoning.rsp4j.yasper.engines.Yasper;
import org.streamreasoning.rsp4j.yasper.querying.formatter.ContinuousQueryImpl;
import org.streamreasoning.rsp4j.yasper.querying.formatter.InstResponseSysOutFormatter;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.WindowNodeImpl;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;

import java.time.Duration;

/**
 * Created by Riccardo on 03/08/16.
 */
public class YasperExample {


    public static void main(String[] args) throws ConfigurationException {

        EngineConfiguration ec = EngineConfiguration.loadConfig("/csparql.properties");

        Yasper sr = new Yasper(ec);

        //STREAM DECLARATION
        RDFStream stream = new RDFStream("stream1");

        sr.register(stream);

        //_____

        ContinuousQuery q = new ContinuousQueryImpl("q1");

        WindowNode wn = new WindowNodeImpl(RDFUtils.createIRI("w1"), Duration.ofSeconds(2), Duration.ofSeconds(2), 0);

        q.addNamedWindow("stream1", wn);

        ContinuousQueryExecution<Graph, Graph, Triple> cqe = sr.register(q);

        WebDataStream<Triple> outstream = cqe.outstream();
        outstream.addConsumer(new InstResponseSysOutFormatter("TTL", true));

        //RUNTIME DATA

        RDF instance = RDFUtils.getInstance();
        Graph graph = instance.createGraph();
        IRI p = instance.createIRI("p");
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
