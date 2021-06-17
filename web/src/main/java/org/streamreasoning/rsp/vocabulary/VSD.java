package org.streamreasoning.rsp.vocabulary;

import org.apache.commons.rdf.api.IRI;
import org.streamreasoning.rsp.RDFManager;

public class VSD {

    private static String uri = "http://w3id.org/rsp/vocals-sd#";

    public final static IRI URI_PARAM = resource("uri_param");
    public final static IRI BODY_PARAM = resource("body_param");

    public final static IRI CATALOG_SERVICE = resource("CatalogService");
    public final static IRI OPERATION = resource("Operation");
    public final static IRI PHYSICAL_WINDOW = resource("PhysicalWindow");
    public final static IRI PROCESSING_SERVICE = resource("ProcessingService");
    public final static IRI PUBLISHING_SERVICE = resource("PublishingService");
    public final static IRI R2R_OPERATION = resource("R2ROperation");
    public final static IRI R2S_OPERATION = resource("R2SOperation");
    public final static IRI RDF_STREAM_SERVICE = resource("RDFStreamService");
    public final static IRI RDF_STREAMING_FEATURE = resource("RDFStreamingFeature");
    public final static IRI REPORTING_POLICY = resource("ReportingPolicy");
    public final static IRI S2R_OPERATION = resource("S2ROperation");
    public final static IRI S2S_OPERATION = resource("S2SOperation");
    public final static IRI SET_OPERATION = resource("SetOperation");
    public final static IRI STREAMING_DATASET = resource("StreamingDataset");
    public final static IRI STREAMING_SERVICE = resource("StreamingService");
    public final static IRI TIME_BASED_WINDOW = resource("TimeBasedWindow");
    public final static IRI TIME_CONTROL = resource("TimeControl");
    public final static IRI TIME_SEMANTICS = resource("TimeSemantics");
    public final static IRI TIME_VARYING_GRAPH = resource("TimeVaryingGraph");
    public final static IRI WINDOW = resource("Window");


    public final static IRI HAS_SERVICE = resource("hasService");
    public final static IRI ENDPOINT = resource("endpoint");
    public final static IRI METHOD = resource("method");
    public final static IRI BASE = resource("base");
    public final static IRI PARAMS = resource("params");
    public final static IRI INDEX = resource("index");


    public final static IRI NAME = resource("name");
    public final static IRI AVAILABLE_GRAPH = resource("availableGraph");
    public final static IRI AVAILABLE_STREAM = resource("availableStream");
    public final static IRI DEFAULT_GRAPH = resource("defaultGraph");
    public final static IRI DEFAULT_STREAMING_DATASET = resource("defaultStreamingDataset");
    public final static IRI DESCRIBED_BY = resource("describedBy");
    public final static IRI ENDED_AT = resource("endedAt");
    public final static IRI GRAPH = resource("graph");
    public final static IRI HAS_FEATURE = resource("hasFeature");
    public final static IRI NAMED_TIME_VARYING_GRAPH = resource("namedTimeVaryingGraph");
    public final static IRI PUBLISHED_BY = resource("publishedBy");
    public final static IRI REGISTERED_TASK = resource("registeredTask");
    public final static IRI REGISTERED_BY = resource("registeredBy");
    public final static IRI REGISTERED_STREAMS = resource("registeredStreams");
    public final static IRI RESULT_FORMAT = resource("resultFormat");
    public final static IRI RSP_ENDPOINT = resource("rspEndpoint");
    public final static IRI STARTED_AT = resource("startedAt");

    private static IRI resource(String rdfStream) {
        return RDFManager.resource(uri, rdfStream);
    }

}
