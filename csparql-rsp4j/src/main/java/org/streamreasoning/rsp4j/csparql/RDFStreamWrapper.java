package org.streamreasoning.rsp4j.csparql;

import com.hp.hpl.jena.graph.Node;
import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;
import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;

public class RDFStreamWrapper extends RdfStream {
    private final DataStream<Graph> rsp4jStream;

    public RDFStreamWrapper(DataStream<Graph> s) {
        super(s.getName());
        this.rsp4jStream = s;
        this.registerStream();
    }
    private void registerStream(){
        rsp4jStream.addConsumer((el, ts) -> el.stream().forEach(triple->this.put(
                new RdfQuadruple(RDFUtils.trimTags(triple.getSubject().toString()),
                        RDFUtils.trimTags(triple.getPredicate().toString()),
                        RDFUtils.trimTags(triple.getObject().toString()),
                        ts))));
    }
}
