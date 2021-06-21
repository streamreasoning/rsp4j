package org.streamreasoning.rsp4j.api.stream.web;

import org.apache.commons.rdf.api.Graph;

/**
 * Created by riccardo on 10/07/2017.
 */

public interface WebStream {

    String uri();

    Graph describe();

}
