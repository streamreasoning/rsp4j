package org.streamreasoning.rsp.distribution;

import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.jena.JenaGraph;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.rdf.model.ModelFactory;
import org.streamreasoning.rsp.SLD;
import org.streamreasoning.rsp.enums.Format;
import org.streamreasoning.rsp.enums.License;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingResult;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.stream.Collectors;

import static spark.Spark.get;
import static spark.Spark.init;

@Log4j
public class HTTPDistribution<E> extends AbstractDistribution<E> {


    private Duration poll;
    private Integer retention;
    private LimitedList<E> state;


    public HTTPDistribution(BlankNodeOrIRI uri, String access, License license, Format format, SLD.Publisher p, Graph graph, Integer retention) {
        super(uri, access, license, format, p, graph);
        this.retention = retention;
        this.state = new LimitedList(retention);

    }

    public HTTPDistribution(BlankNodeOrIRI uri, String access, License license, Format format, SLD.Publisher p, Graph graph, boolean source, Duration poll) {
        super(uri, access, license, format, p, graph, source);
        this.poll = poll;
    }

    @Override
    public SLD.WebDataStream<E> serve() {
        if (!source) {
            //if the uri is a fragment, we can spawn two different services
            //use abstract class to distinguish
            get(access, (request, response) -> getString());

            get("/access/" + access, (request, response) -> state);

            init();
            //TODO actually, we need to pass the subset that interests the stream
            dataStream.addConsumer(new Consumer<E>() {
                @Override
                public void notify(E arg, long ts) {
                    state.add(new Pair<>(arg, ts));
                }
            });
            return dataStream;
        }
        throw new UnsupportedOperationException("Read-Only Distribution");
    }

    private String getString() {
        StringWriter writer = new StringWriter();
        JenaGraph graph = new JenaRDF().createGraph();
        this.graph.stream().forEach(graph::add);
        ModelFactory.createModelForGraph(graph.asJenaGraph()).write(writer, "TTL");
        String s = writer.toString();
        return s;
    }

    @Override
    public SLD.WebDataStream<E> start(ParsingStrategy<E> parsingStrategy) {
        if (source) {
            Runnable task = () -> {
                while (true) {
                    URL urlCon;
                    try {
                        urlCon = new URL(access);
                        URLConnection conn = urlCon.openConnection();
                        InputStream is = conn.getInputStream();
                        String result = "";
                        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.defaultCharset()))) {
                            result = br.lines().collect(Collectors.joining(System.lineSeparator()));
                            ParsingResult<E> parsingResult = parsingStrategy.parseAndAddTime(result);
                            dataStream.put(parsingResult.getResult(), parsingResult.getTimeStamp());
                        }
                        Thread.sleep(this.poll.toMillis());
                    } catch (MalformedURLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            };
            Thread thread = new Thread(task);
            thread.start();
        }
        return dataStream;
    }

}

