package it.polimi.sr.rsp.onsper.streams;

import org.apache.commons.rdf.api.Graph;

public interface OBDAStream {

    Graph mappings();

    void mappings(Graph mappings);
}
