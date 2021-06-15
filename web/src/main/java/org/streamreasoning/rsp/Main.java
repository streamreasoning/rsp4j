package org.streamreasoning.rsp;

import lombok.SneakyThrows;
import org.apache.commons.rdf.api.*;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {


        Publisher publisher = new Publisher() {

            final String base = "http://example.org/";
            String uri, name, descriptiption;
            List<Distribution> distributions = new ArrayList<>();


            @Override
            public Graph describe() {
                RDF rdf = RDFUtils.getInstance();
                Graph graph = rdf.createGraph();

                IRI subject = rdf.createIRI(uri);
                IRI dcatname = rdf.createIRI("dcat:name");
                RDFTerm object = rdf.createLiteral(name);
                Triple triple = rdf.createTriple(subject, dcatname, object);
                graph.add(triple);

                distributions.stream().map(Describable::describe).flatMap(Graph::stream).forEach(graph::add);

                return graph;
            }


            @Override
            public Publisher stream(String id, boolean fragment) {
                this.uri = (fragment) ? this.base + id : id;
                return this;
            }

            @Override
            public Publisher name(String name) {
                this.name = name;
                return this;
            }

            @Override
            public Publisher description(String description) {
                this.descriptiption = description;
                return this;
            }

            @Override
            public Publisher distribution(Distribution distribution) {
                distributions.add(distribution);
                return this;
            }

            @Override
            public <E> WebStreamEndpoint<E> build() {
                return distributions.get(0).build(uri);
            }

        };

        Distribution d = new Distribution() {

            @Override
            public Graph describe() {
                RDF rdf = RDFUtils.getInstance();
                Graph graph = rdf.createGraph();

                IRI subject = rdf.createIRI(uri);
                IRI dcatname = rdf.createIRI("dcat:name");
                RDFTerm object = rdf.createLiteral(uri);
                Triple triple = rdf.createTriple(subject, dcatname, object);
                graph.add(triple);

                return graph;
            }

            private License license;
            private Security security;
            private Protocol protocol;
            private Format format;
            final String base = "http://example.org";
            String uri;

            @Override
            public Distribution access(String id, boolean fragment) {
                this.uri = (fragment) ? this.base + "/" + id : id;
                return this;
            }

            @Override
            public Distribution protocol(Protocol protocol) {
                this.protocol = protocol;
                return this;
            }

            @Override
            public Distribution security(Security security) {
                this.security = security;
                return this;
            }

            @Override
            public Distribution license(License license) {
                this.license = license;
                return this;
            }

            @Override
            public Distribution format(Format format) {
                this.format = format;
                return this;
            }

            @Override
            public <E> WebStreamEndpoint<E> build(String path) {
                return new WebSocketEndpoint<>(uri, path, license, format);
            }
        };

        WebStreamEndpoint<String> wse = publisher
                .stream("colours", false)
                .name("Colour Stream")
                .description("tream of primary colours")
                .distribution(d.
                        access("colours", false)
                        .protocol(Protocol.WebSocket)
                        .security(Security.SSL)
                        .license(License.CC)
                        .format(Format.JSONLD))
                .<String>build();

//        WebStream serve = wse.serve();

        wse.describe().stream().forEach(System.err::println);

        WebDataStream<String> serve = wse.<String>deploy();


        new Thread(() -> {
            try {
                Random r = new Random();
                String[] colors = new String[]{"Red", "Yellow", "Blue"};
                while (true) {
                    serve.put(colors[r.nextInt(colors.length - 1)], System.currentTimeMillis());
                    Thread.sleep(5 * 1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
