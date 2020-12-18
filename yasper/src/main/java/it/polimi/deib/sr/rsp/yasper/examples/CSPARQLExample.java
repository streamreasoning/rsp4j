package it.polimi.deib.sr.rsp.yasper.examples;

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

import java.net.URL;
import java.time.Duration;

/**
 * Created by Riccardo on 03/08/16.
 */
public class CSPARQLExample {

    static CSPARQLImpl sr;

    public static void main(String[] args) throws ConfigurationException {

        URL resource = CSPARQLExample.class.getResource("/default.properties");
        SDSConfiguration config = new SDSConfiguration(resource.getPath());
        EngineConfiguration ec = EngineConfiguration.loadConfig("/default.properties");

        sr = new CSPARQLImpl(0, ec);

        //STREAM DECLARATION
        WebStreamDecl s = new WebStreamDecl("stream1");

        RDFStream stream = sr.register(s);

        //_____

        ContinuousQuery q = new ContinuousQueryImpl("q1");

        WindowNode wn = new WindowNodeImpl(RDFUtils.createIRI("w1"), Duration.ofSeconds(2), Duration.ofSeconds(2), 0);

        q.addNamedWindow("stream1", wn);

        ContinuousQueryExecution cqe = sr.register(q, config);

        cqe.add(new InstResponseSysOutFormatter("TTL", true));

        //RUNTIME DATA

        Graph graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S1"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O1")));
        stream.put(graph, 1000);

        graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S2"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O2")));
        stream.put(graph, 1999);

        graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S3"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O3")));
        stream.put(graph, 2001);

        graph = RDFUtils.getInstance().createGraph();

        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S4"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O4")));
        stream.put(graph, 3000);


        graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S5"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O5")));
        stream.put(graph, 5000);


        graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S6"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O6")));
        stream.put(graph, 5000);
        stream.put(graph, 6000);


        graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S7"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O7")));
        stream.put(graph, 7000);

        //stream.put(new it.polimi.deib.rsp.test.examples.windowing.RDFStreamDecl.Elem(3000, graph));


    }


}
