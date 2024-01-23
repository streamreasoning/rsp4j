package org.streamreasoning.rsp.vocabulary;

import org.apache.commons.rdf.api.IRI;

public class VPROV extends Vocabulary {

    private static final String uri = "http://w3id.org/rsp/vocals-prov#";

    public static final IRI FILTER_OPERATOR = resource("FilterOperator");
    public static final IRI OPERATOR = resource("Operator");
    public static final IRI PHYSICAL_WINDOW = resource("PhysicalWindow");
    public static final IRI R2R_OPERATOR = resource("R2ROperator");
    public static final IRI R2S_OPERATOR = resource("R2SOperator");
    public static final IRI RSPQL_OPERATOR = resource("RSPQLOperator");
    public static final IRI REPLAY = resource("Replay");
    public static final IRI S2R_OPERATOR = resource("S2ROperator");
    public static final IRI S2S_OPERATOR = resource("S2SOperator");
    public static final IRI SET_OPERATOR = resource("SetOperator");
    public static final IRI STREAMING_AGENT = resource("StreamingAgent");
    public static final IRI TASK = resource("Task");
    public static final IRI TIME_BASED_WINDOW = resource("TimeBasedWindow");
    public static final IRI TRANSFORM_OPERATOR = resource("TransformOperator");
    public static final IRI WINDOW = resource("Window");
    public static final IRI WINDOW_OPERATOR = resource("WindowOperator");

    public static final IRI CONTAINED_IN = resource("containedIn");
    public static final IRI CONTAINS = resource("contains");
    public static final IRI ENDED_AT = resource("endedAt");
    public static final IRI FOLLOWED_BY = resource("followedBy");
    public static final IRI HAS_OUTPUT = resource("hasOutput");
    public static final IRI PERFORMS = resource("performs");
    public static final IRI PRECEDED_BY = resource("precededBy");
    public static final IRI STARTED_AT = resource("startedAt");
    public static final IRI STARTING_TIME = resource("startingTime");

    public static final IRI QUERY = resource("query");
    public static final IRI RANGE = resource("range");
    public static final IRI STEP = resource("step");

    private static IRI resource(String rdfStream) {
        return Vocabulary.resource(uri, rdfStream);
    }

}
