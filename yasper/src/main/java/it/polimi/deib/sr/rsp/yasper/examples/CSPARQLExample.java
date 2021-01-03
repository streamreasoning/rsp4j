package it.polimi.deib.sr.rsp.yasper.examples;

import it.polimi.deib.sr.rsp.api.stream.data.WebDataStream;
import it.polimi.deib.sr.rsp.yasper.WebStreamDecl;
import it.polimi.deib.sr.rsp.yasper.engines.CSPARQLImpl;
import it.polimi.deib.sr.rsp.api.querying.ContinuousQueryExecution;
import it.polimi.deib.sr.rsp.api.querying.ContinuousQuery;
import it.polimi.deib.sr.rsp.api.operators.s2r.syntax.WindowNode;
import it.polimi.deib.sr.rsp.api.engine.config.EngineConfiguration;
import it.polimi.deib.sr.rsp.api.sds.SDSConfiguration;
import it.polimi.deib.sr.rsp.api.RDFUtils;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.rdf.api.Graph;
import it.polimi.deib.sr.rsp.yasper.querying.formatter.ContinuousQueryImpl;
import it.polimi.deib.sr.rsp.yasper.querying.formatter.InstResponseSysOutFormatter;
import it.polimi.deib.sr.rsp.yasper.querying.operators.windowing.WindowNodeImpl;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;

import java.net.URL;
import java.time.Duration;

/**
 * Created by Riccardo on 03/08/16.
 */
public class CSPARQLExample {


    public static void main(String[] args) throws ConfigurationException {

        URL resource = CSPARQLExample.class.getResource("/default.properties");
        EngineConfiguration ec = EngineConfiguration.loadConfig("/default.properties");

        CSPARQLImpl  sr = new CSPARQLImpl(0, ec);

        //STREAM DECLARATION
        RDFStream stream = new RDFStream("stream1");

        sr.register(stream);

        //_____

        ContinuousQuery q = new ContinuousQueryImpl("q1");

        WindowNode wn = new WindowNodeImpl(RDFUtils.createIRI("w1"), Duration.ofSeconds(2), Duration.ofSeconds(2), 0);

        q.addNamedWindow("stream1", wn);

        ContinuousQueryExecution<Graph,Graph, Triple> cqe = sr.register(q);

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
