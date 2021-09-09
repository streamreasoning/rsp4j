package org.streamreasoning.rsp.distribution;

import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.jena.JenaGraph;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.log4j.Logger;
import org.streamreasoning.rsp.SLD;
import org.streamreasoning.rsp.enums.Format;
import org.streamreasoning.rsp.enums.License;
import org.streamreasoning.rsp4j.io.sources.WebsocketClientSource;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;

import java.io.StringWriter;

import static spark.Spark.*;

public class WebSocketDistribution<E> extends AbstractDistribution<E> {
    private static final Logger log = Logger.getLogger(WebSocketDistribution.class);
    protected WebSocketHandler<E> wsh;


    public WebSocketDistribution(BlankNodeOrIRI uri, String access, License license, Format format, SLD.Publisher p, Graph graph) {
        super(uri, access, license, format, p, graph);
    }

    public WebSocketDistribution(BlankNodeOrIRI uri, String access, License license, Format format, SLD.Publisher p, Graph graph, boolean source) {
        super(uri, access, license, format, p, graph, source);
    }

    @Override
    public SLD.WebDataStream<E> serve() {
        if (!source) {
            //if the uri is a fragment, we can spawn two different services
            //use abstract class to distinguish
            this.wsh = new WebSocketHandler<>();
            webSocket("/access/" + access, this.wsh);
            StringWriter writer = new StringWriter();
            JenaGraph graph = new JenaRDF().createGraph();
            this.graph.stream().forEach(graph::add);
            ModelFactory.createModelForGraph(graph.asJenaGraph()).write(writer, "TTL");
            String s = writer.toString();
            System.out.println(s);
            get(access, (request, response) -> s);
            init();
            //TODO actually, we need to pass the subset that interests the stream
            dataStream.addConsumer(wsh);
            return dataStream;
        }
        throw new UnsupportedOperationException("Read-Only Distribution");
    }

    @Override
    public SLD.WebDataStream<E> start(ParsingStrategy<E> parsingStrategy) {
        if (source) {
            WebsocketClientSource<E> websocketSource = new WebsocketClientSource<E>(access, parsingStrategy);
            websocketSource.startSocket();
            websocketSource.addConsumer((arg, ts) -> dataStream.put(arg, ts));
        }
        return dataStream;
    }

}

