package org.streamreasoning.rsp.vocabulary;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Triple;
import org.streamreasoning.rsp.RDFManager;
import org.streamreasoning.rsp4j.api.RDFUtils;


public abstract class VOCALS {

    private static final String uri = "http://w3id.org/rsp/vocals#";

    public static final IRI RDF_SSTREAM = resource("RDFStream");

    private static IRI resource(String rdfStream) {
        return RDFManager.resource(uri, rdfStream);
    }

    public static final IRI STREAM_ = resource("Stream");
    public static final IRI STREAM_DESCRIPTOR = resource("StreamDescriptor");
    public static final IRI STREAM_DISTRIBUTION = resource("StreamDistribution");
    public static final IRI STREAM_ENDPOINT = resource("StreamEndpoint");
    public static final IRI STREAM_PARTITION = resource("StreamPartition");
    public static final IRI FEATURE = resource("feature");
    public static final IRI PREVIOUS = resource("previous");
    public static final IRI HAS_STREAM = resource("stream");
    public static final IRI HAS_ENDPOINT = resource("hasEndpoint");
    public static final IRI HAS_PARTITION = resource("hasPartition");


    public static Triple stream(IRI s) {
        return RDFUtils.getInstance().createTriple(s, RDF.pTYPE, STREAM_);
    }

    public static Triple endpoint(IRI s) {
        return RDFUtils.getInstance().createTriple(s, RDF.pTYPE, STREAM_ENDPOINT);
    }

    public static Triple endpoint(IRI s, IRI o) {
        return RDFUtils.getInstance().createTriple(s, VSD.pENDPOINT, o);
    }

    public static Triple descriptor() {
        return RDFUtils.getInstance().createTriple(RDFUtils.createIRI(""), RDF.pTYPE, STREAM_DESCRIPTOR);

    }
}
