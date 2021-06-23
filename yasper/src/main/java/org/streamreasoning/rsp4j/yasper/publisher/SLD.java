package org.streamreasoning.rsp4j.yasper.publisher;

import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.rdf.model.Model;
import org.streamreasoning.rsp.WebDataStream;

public class SLD {

    static JenaRDF rdf = new JenaRDF();


    public static <T> WebDataStream<T> fetch(String s) {

        Model read = rdf.createGraph().asJenaModel().read(s);

        return null;
    }
}
