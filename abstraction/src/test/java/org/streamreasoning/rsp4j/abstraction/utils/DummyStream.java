package org.streamreasoning.rsp4j.abstraction.utils;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;

public class DummyStream {

        public static void populateStream(DataStream<Graph> stream, long startTime) {
            populateStream(stream, startTime,"");
        }

        public static void populateStream(DataStream<Graph> stream, long startTime, String prefix) {

        //RUNTIME DATA

        RDF instance = RDFUtils.getInstance();
        Graph graph = instance.createGraph();
        IRI p = instance.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        graph.add(instance.createTriple(instance.createIRI(prefix +"S1"), p, instance.createIRI("http://color#Green")));
        stream.put(graph, 1000 + startTime);

        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI(prefix +"S2"), p, instance.createIRI("http://color#Red")));
        stream.put(graph, 1999 + startTime);


        //cp.eval(1999l);
        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI(prefix +"S3"), p, instance.createIRI("http://color#Blue")));
        stream.put(graph, 2001 + startTime);


        graph = instance.createGraph();

        graph.add(instance.createTriple(instance.createIRI(prefix +"S4"), p, instance.createIRI("http://color#Green")));
        stream.put(graph, 3000 + startTime);


        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI(prefix +"S5"), p, instance.createIRI("http://color#Blue")));
        stream.put(graph, 5000 + startTime);


        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI(prefix +"S6"), p, instance.createIRI("http://color#Red")));
        stream.put(graph, 5000 + startTime);
        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI(prefix +"S7"), p, instance.createIRI("http://color#Red")));
        stream.put(graph, 6001 + startTime);
    }
    public static void populateValueStream(DataStream<Graph> stream, long startTime){
            populateValueStream(stream,startTime,"");
    }

    public static void populateValueStream(DataStream<Graph> stream, long startTime, String prefix) {

        //RUNTIME DATA

        RDF instance = RDFUtils.getInstance();
        Graph graph = instance.createGraph();
        IRI p = instance.createIRI("http://test/hasValue");
        graph.add(instance.createTriple(instance.createIRI(prefix +"S1"), p, instance.createLiteral("20",instance.createIRI("http://www.w3.org/2001/XMLSchema#integer"))));
        stream.put(graph, 1000 + startTime);

        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI(prefix +"S2"), p, instance.createLiteral("20",instance.createIRI("http://www.w3.org/2001/XMLSchema#integer"))));
        stream.put(graph, 1999 + startTime);


        //cp.eval(1999l);
        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI(prefix +"S3"), p, instance.createLiteral("40",instance.createIRI("http://www.w3.org/2001/XMLSchema#integer"))));
        stream.put(graph, 2001 + startTime);


        graph = instance.createGraph();

        graph.add(instance.createTriple(instance.createIRI(prefix +"S4"), p, instance.createLiteral("50",instance.createIRI("http://www.w3.org/2001/XMLSchema#integer"))));
        stream.put(graph, 3000 + startTime);


        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI(prefix +"S5"), p, instance.createLiteral("40",instance.createIRI("http://www.w3.org/2001/XMLSchema#integer"))));
        stream.put(graph, 5000 + startTime);


        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI(prefix +"S6"), p, instance.createLiteral("90",instance.createIRI("http://www.w3.org/2001/XMLSchema#integer"))));
        stream.put(graph, 5000 + startTime);
        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI(prefix +"S7"), p, instance.createLiteral("20",instance.createIRI("http://www.w3.org/2001/XMLSchema#integer"))));
        stream.put(graph, 6001 + startTime);
    }

    public static Graph createSingleColorGraph(String prefix, String color){
        RDF instance = RDFUtils.getInstance();
        Graph graph = instance.createGraph();
        IRI p = instance.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        graph.add(instance.createTriple(instance.createIRI(prefix +"S1"), p, instance.createIRI(prefix+color)));
        return graph;
    }
}