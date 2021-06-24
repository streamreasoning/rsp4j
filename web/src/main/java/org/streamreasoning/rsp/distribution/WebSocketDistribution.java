package org.streamreasoning.rsp.distribution;

import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.*;
import org.streamreasoning.rsp.SLD;
import org.streamreasoning.rsp.enums.Format;
import org.streamreasoning.rsp.enums.License;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.io.sources.WebsocketClientSource;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;

import static spark.Spark.*;

@Log4j
public class WebSocketDistribution<E> implements SLD.Distribution<E> {

    private final String access, path;
    private final License license;
    private final Format format;
    private final SLD.Publisher p;
    private final boolean source;
    private SLD.WebDataStream<E> ds;
    private WebSocketHandler wsh;
    RDF rdf = RDFUtils.getInstance();
    Graph graph = rdf.createGraph();

    public WebSocketDistribution(String access, String path, License license, Format format, SLD.Publisher p, boolean source) {
        this.access = access;
        this.path = path;
        this.license = license;
        this.format = format;
        this.p = p;
        this.source = source;
        this.ds = new WebDataStreamSource<>(path, describe(), this, p);


    }

    public WebSocketDistribution(String access, String path, License license, Format format, SLD.Publisher p) {
        this.access = access;
        this.path = path;
        this.license = license;
        this.format = format;
        this.p = p;
        this.source = false;
        this.ds = new WebDataStreamSink<E>(path, graph, this, p);
    }

    @Override
    public SLD.WebDataStream<E> serve() {
        if (!source) {
            //if the uri is a fragment, we can spawn two different services
            //use abstract class to distinguish
            webSocket("/access/" + access, this.wsh = new WebSocketHandler<>());
            get(path, (request, response) -> graph.toString());
            init();
            //TODO actually, we need to pass the subset that interests the stream
            ds.addConsumer(wsh);
            return ds;
        }
        throw new UnsupportedOperationException("Read-Only Distribution");
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

    @Override
    public IRI uri() {
        return null;
    }

    @Override
    public void start(ParsingStrategy<E> parsingStrategy) {
        if (source) {
            WebsocketClientSource<E> websocketSource = new WebsocketClientSource<E>("ws://localhost:4567/access/colours", parsingStrategy);
            websocketSource.startSocket();
            websocketSource.addConsumer(new Consumer<E>() {
                @Override
                public void notify(E arg, long ts) {
                    ds.put(arg, ts);
                }
            });

        }
    }
}

