package org.streamreasoning.rsp;

import org.apache.commons.rdf.api.*;
import org.streamreasoning.rsp.enums.Format;
import org.streamreasoning.rsp.enums.License;
import org.streamreasoning.rsp4j.api.RDFUtils;

import static spark.Spark.*;

public class WebSocketEndpoint<E> implements WebStreamEndpoint<E> {

    private final String access, path;
    private final License license;
    private final Format format;
    private final Distribution yDistribution;
    private final Publisher p;
    private WebSocketHandler wsh;
    RDF rdf = RDFUtils.getInstance();
    Graph graph = rdf.createGraph();

    public WebSocketEndpoint(String access, String path, License license, Format format, Distribution yDistribution, Publisher p) {
        this.access = access;
        this.path = path;
        this.license = license;
        this.format = format;
        this.yDistribution = yDistribution;
        this.p=p;
    }

    @Override
    public WebDataStream<E> serve() {
        ignite();
        //TODO actually, we need to pass the subset that interests the stream
        WebDataStream<E> eDataStream = new WebDataStreamSink<E>(path, graph, yDistribution, p);
        eDataStream.addConsumer(wsh);
        return eDataStream;
    }


    private void ignite() {
        //if the uri is a fragment, we can spawn two different services
        //use abstract class to distinguish
        webSocket("/access/" + access, this.wsh = new WebSocketHandler());
        get(path, (request, response) -> graph.toString());
        init();
    }


    @Override
    public Graph describe() {
        BlankNode subject = rdf.createBlankNode();
        IRI dcatname = rdf.createIRI("access");
        IRI uripath = rdf.createIRI(path);
        Triple triple = rdf.createTriple(subject, dcatname, uripath);
        graph.add(triple);

        return graph;
    }

}

