package org.streamreasoning.rsp.builders;

import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.streamreasoning.rsp.SLD;
import org.streamreasoning.rsp.distribution.HTTPDistribution;
import org.streamreasoning.rsp.distribution.WebSocketDistribution;
import org.streamreasoning.rsp.enums.Format;
import org.streamreasoning.rsp.enums.License;
import org.streamreasoning.rsp.enums.Protocol;
import org.streamreasoning.rsp.enums.Security;
import org.streamreasoning.rsp.vocabulary.DCAT;
import org.streamreasoning.rsp.vocabulary.VOCALS;
import org.streamreasoning.rsp.vocabulary.XSD;
import org.streamreasoning.rsp4j.api.RDFUtils;

import java.time.Duration;

import static org.streamreasoning.rsp4j.api.RDFUtils.createIRI;

public class DistributionBuilder {

    private org.apache.commons.rdf.api.RDF is = RDFUtils.getInstance();

    private Graph dgraph = is.createGraph();

    private License license;
    private Security security;
    private Protocol protocol;
    private Format format;
    private String base;
    private IRI uri;
    private SLD.Publisher p;
    private String urlBody;
    private String id;
    private String access;
    private BlankNodeOrIRI ep;

    public DistributionBuilder(String base) {
        this.base = base;
    }

    public Graph describe() {
        return dgraph;
    }

    public DistributionBuilder publisher(SLD.Publisher p) {
        this.p = p;
        this.base = p.uri().getIRIString();
        return this;
    }

    public DistributionBuilder access(String id) {
        return access(id, false);
    }

    public DistributionBuilder access(String id, boolean fragment) {
        this.id = id;
        this.uri = createIRI((fragment) ? this.base + "/" + id : id);

        if (!fragment) {
            access = id;
        } else this.access = this.base.replace("http://", "");

        this.dgraph.add(VOCALS.endpoint(uri, this.ep = is.createBlankNode()));
        this.dgraph.add(VOCALS.endpoint(this.ep));

        return this;
    }

    public DistributionBuilder protocol(Protocol protocol) {
        this.protocol = protocol;
        dgraph.add(ep, DCAT.pPROTOCOL, is.createLiteral(protocol.name(), XSD.tString));
        return this;
    }

    public DistributionBuilder security(Security security) {
        this.security = security;
        dgraph.add(ep, DCAT.pSECUTIRTY, is.createLiteral(security.name(), XSD.tString));
        return this;
    }

    public DistributionBuilder license(License license) {
        this.license = license;
        dgraph.add(DCAT.license(ep, license));
        return this;
    }

    public DistributionBuilder format(Format format) {
        this.format = format;
        dgraph.add(DCAT.format(ep, format));
        return this;
    }

    public <T> SLD.Distribution<T> buildSource(Graph sgraph) {
        switch (protocol) {
            case HTTP:
                return new HTTPDistribution<>(this.ep, access, license, format, p, sgraph, true, Duration.ofMillis(10000));
            case WebSocket:
            default:
                return new WebSocketDistribution<T>(this.ep, access, license, format, p, sgraph, true);


        }
    }

    public <T> SLD.Distribution<T> buildSink(Graph sgraph) {

        SLD.Distribution<T> d;
        switch (protocol) {
            case HTTP:
                d = new HTTPDistribution<>(this.ep, id, license, format, p, sgraph, 10);
                break;
            case WebSocket:
            default:
                d = new WebSocketDistribution<T>(this.ep, id, license, format, p, sgraph);
        }
        access = access.equals(id) ? access.replace(Protocol.HTTP.schema(), protocol.schema()) : protocol.schema() + access + "/access/" + id;
        this.dgraph.add(DCAT.access(this.ep, access));
        this.dgraph.stream().forEach(sgraph::add);

        return d;
    }


}
