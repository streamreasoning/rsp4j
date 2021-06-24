package org.streamreasoning.rsp.distribution;

import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.jena.JenaGraph;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.rdf.model.ModelFactory;
import org.streamreasoning.rsp.SLD;
import org.streamreasoning.rsp.enums.Format;
import org.streamreasoning.rsp.enums.License;
import org.streamreasoning.rsp4j.io.sources.WebsocketClientSource;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;

import java.io.StringWriter;
import java.util.Objects;

import static spark.Spark.*;

@Log4j
public class WebSocketDistribution<E> implements SLD.Distribution<E> {

    private final BlankNodeOrIRI uri;
    private final String access;
    private final License license;
    private final Format format;
    private final SLD.Publisher p;
    private final boolean source;
    private SLD.WebDataStream<E> ds;
    private WebSocketHandler<E> wsh;
    private Graph graph;

    public WebSocketDistribution(BlankNodeOrIRI uri, String access, License license, Format format, SLD.Publisher p, Graph graph) {
        this.uri = uri;
        this.access = access;
        this.license = license;
        this.format = format;
        this.p = p;
        this.source = false;
        this.ds = new WebDataStreamSink<E>(access, this.graph, this, p);
        this.graph = graph;
    }

    public WebSocketDistribution(BlankNodeOrIRI uri, String access, License license, Format format, SLD.Publisher p, Graph graph, boolean source) {
        this.uri = uri;
        this.access = access;
        this.license = license;
        this.format = format;
        this.p = p;
        this.source = source;
        this.graph = graph;
        this.ds = new WebDataStreamSource<>(access, describe(), this, p);
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
            WebsocketClientSource<E> websocketSource = new WebsocketClientSource<E>(access, parsingStrategy);
            websocketSource.startSocket();
            websocketSource.addConsumer((arg, ts) -> ds.put(arg, ts));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebSocketDistribution<?> that = (WebSocketDistribution<?>) o;
        return source == that.source && Objects.equals(access, that.access) && license == that.license && format == that.format && Objects.equals(wsh, that.wsh);
    }

    @Override
    public int hashCode() {
        return Objects.hash(access, license, format, source, wsh);
    }
}

