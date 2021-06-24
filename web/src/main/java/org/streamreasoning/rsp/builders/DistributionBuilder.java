package org.streamreasoning.rsp.builders;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp.SLD;
import org.streamreasoning.rsp.distribution.WebSocketDistribution;
import org.streamreasoning.rsp.enums.Format;
import org.streamreasoning.rsp.enums.License;
import org.streamreasoning.rsp.enums.Protocol;
import org.streamreasoning.rsp.enums.Security;
import org.streamreasoning.rsp.vocabulary.DCAT;
import org.streamreasoning.rsp.vocabulary.VOCALS;
import org.streamreasoning.rsp.vocabulary.XSD;
import org.streamreasoning.rsp4j.api.RDFUtils;

import static org.streamreasoning.rsp.vocabulary.RDF.pTYPE;

public class DistributionBuilder {

    org.apache.commons.rdf.api.RDF is = RDFUtils.getInstance();

    Graph graph = is.createGraph();

    private License license;
    private Security security;
    private Protocol protocol;
    private Format format;
    final String base = "http://example.org";
    String uri;
    private SLD.Publisher p;

    public Graph describe() {
        return graph;
    }

    public DistributionBuilder publisher(SLD.Publisher p) {
        this.p = p;
        return this;
    }

    public DistributionBuilder access(String id, boolean fragment) {
        this.uri = (fragment) ? this.base + "/" + id : id;
        this.graph.add(is.createIRI(uri), pTYPE, VOCALS.STREAM_);
        return this;
    }

    public DistributionBuilder protocol(Protocol protocol) {
        this.protocol = protocol;
        graph.add(is.createTriple(is.createIRI(base), DCAT.pDESCRIPTION, is.createLiteral(protocol.name(), XSD.tString)));

        return this;
    }

    public DistributionBuilder security(Security security) {
        this.security = security;
        graph.add(is.createTriple(is.createIRI(base), DCAT.pDESCRIPTION, is.createLiteral(security.name(), XSD.tString)));

        return this;
    }

    public DistributionBuilder license(License license) {
        this.license = license;
        graph.add(is.createTriple(is.createIRI(base), DCAT.pDESCRIPTION, is.createLiteral(license.name(), XSD.tString)));

        return this;
    }

    public DistributionBuilder format(Format format) {
        this.format = format;
        graph.add(is.createTriple(is.createIRI(base), DCAT.pDESCRIPTION, is.createLiteral(format.name(), XSD.tString)));

        return this;
    }

    public <T> SLD.Distribution<T> build(String path, boolean sink) {
        return sink ? new WebSocketDistribution<T>(uri, path, license, format, p) : new WebSocketDistribution<T>(uri, path, license, format, p, !sink);
    }


}
