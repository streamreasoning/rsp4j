package org.streamreasoning.rsp.distribution;

import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.jena.JenaGraph;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.rdf.model.ModelFactory;
import org.streamreasoning.rsp.SLD;
import org.streamreasoning.rsp.enums.Format;
import org.streamreasoning.rsp.enums.License;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.io.sources.WebsocketClientSource;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;

import java.io.StringWriter;

import static spark.Spark.*;

@Log4j
public class WebSocketDistribution<E> implements SLD.Distribution<E> {

    private final IRI uri;
    private final String access;
    private final License license;
    private final Format format;
    private final SLD.Publisher p;
    private final boolean source;
    private SLD.WebDataStream<E> ds;
    private WebSocketHandler<E> wsh;
    RDF rdf = RDFUtils.getInstance();
    private Graph graph;
    private String tostring;

    public WebSocketDistribution(IRI uri, String access, License license, Format format, SLD.Publisher p, Graph graph) {
        this.uri = uri;
        this.access = access;
        this.license = license;
        this.format = format;
        this.p = p;
        this.source = false;
        this.ds = new WebDataStreamSource<>(access, describe(), this, p);
        this.graph = graph;
    }

    public WebSocketDistribution(IRI uri, String access, License license, Format format, SLD.Publisher p, Graph graph, boolean b) {
        this.uri = uri;
        this.access = access;
        this.license = license;
        this.format = format;
        this.p = p;
        this.source = b;
        this.graph = graph;
        this.ds = new WebDataStreamSink<E>(access, this.graph, this, p);
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
            ds.addConsumer(wsh);
            return ds;
        }
        throw new UnsupportedOperationException("Read-Only Distribution");
    }

    @Override
    public SLD.WebDataStream<E> getWebStream() {
        if (ds == null) {
            serve();
        }
        return ds;
    }


    @Override
    public Graph describe() {
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

