package org.streamreasoning.rsp4j.io.utils.serialization;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.streamreasoning.rsp4j.io.utils.RDFBase;

import java.io.StringWriter;

public class JenaRDFSerializationStrategy implements StringSerializationStrategy<Graph>{

    private final RDFBase base;

    public JenaRDFSerializationStrategy(RDFBase base){
        this.base = base;
    }
    @Override
    public String serialize(Graph object) {
        //convert Graph to Jena Model
        JenaRDF jena = new JenaRDF();
        org.apache.jena.graph.Graph jenaGraph = jena.asJenaGraph(object);
        Model dataModel = ModelFactory.createModelForGraph(jenaGraph);
        //convert Jena Model to String
        return modelToString(dataModel);
    }
    private String modelToString(Model m) {
        String syntax = base.name();
        StringWriter out = new StringWriter();
        m.write(out, syntax);
        return out.toString();

    }
}
