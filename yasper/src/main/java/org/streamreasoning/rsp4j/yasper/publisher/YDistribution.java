package org.streamreasoning.rsp4j.yasper.publisher;

import org.apache.commons.rdf.api.*;
import org.streamreasoning.rsp.Distribution;
import org.streamreasoning.rsp.WebSocketEndpoint;
import org.streamreasoning.rsp.WebStreamEndpoint;
import org.streamreasoning.rsp.enums.Format;
import org.streamreasoning.rsp.enums.License;
import org.streamreasoning.rsp.enums.Protocol;
import org.streamreasoning.rsp.enums.Security;
import org.streamreasoning.rsp.vocabulary.DCAT;
import org.streamreasoning.rsp.vocabulary.VOCALS;
import org.streamreasoning.rsp.vocabulary.XSD;
import org.streamreasoning.rsp4j.api.RDFUtils;

import static org.streamreasoning.rsp.vocabulary.RDF.pTYPE;

public class YDistribution implements Distribution {

    org.apache.commons.rdf.api.RDF is = RDFUtils.getInstance();

    Graph graph = is.createGraph();

    private License license;
    private Security security;
    private Protocol protocol;
    private Format format;
    final String base = "http://example.org";
    String uri;

    @Override
    public Graph describe() {
        return graph;
    }

    @Override
    public Distribution access(String id, boolean fragment) {
        this.uri = (fragment) ? this.base + "/" + id : id;
        this.graph.add(is.createIRI(uri), pTYPE, VOCALS.STREAM_);
        return this;
    }

    @Override
    public Distribution protocol(Protocol protocol) {
        this.protocol = protocol;
        graph.add(is.createTriple(is.createIRI(base), DCAT.pDESCRIPTION, is.createLiteral(protocol.name(), XSD.tString)));

        return this;
    }

    @Override
    public Distribution security(Security security) {
        this.security = security;
        graph.add(is.createTriple(is.createIRI(base), DCAT.pDESCRIPTION, is.createLiteral(security.name(), XSD.tString)));

        return this;
    }

    @Override
    public Distribution license(License license) {
        this.license = license;
        graph.add(is.createTriple(is.createIRI(base), DCAT.pDESCRIPTION, is.createLiteral(license.name(), XSD.tString)));

        return this;
    }

    @Override
    public Distribution format(Format format) {
        this.format = format;
        graph.add(is.createTriple(is.createIRI(base), DCAT.pDESCRIPTION, is.createLiteral(format.name(), XSD.tString)));

        return this;
    }

    @Override
    public <E> WebStreamEndpoint<E> build(String path) {
        return new WebSocketEndpoint<>(uri, path, license, format);
    }
}
