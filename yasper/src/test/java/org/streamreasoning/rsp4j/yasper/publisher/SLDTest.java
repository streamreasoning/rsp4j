package org.streamreasoning.rsp4j.yasper.publisher;

import junit.framework.TestCase;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

public class SLDTest extends TestCase {


    public static void main(String[] args) {
        JenaRDF rdf = new JenaRDF();

        String uri = "https://raw.githubusercontent.com/riccardotommasini/webstreams/site/src/main/resources/streams/sgraphs/gdelt.ttl";

        Model read = rdf.createGraph().asJenaModel().read(uri, "TTL");
        read.write(System.out, "TTL");


        Query query = QueryFactory.create("PREFIX vocals: <http://w3id.org/rsp/vocals#> SELECT ?s  WHERE {?s a vocals:Stream } ");

        ResultSet resultSet = QueryExecutionFactory.create(query, read).execSelect();

        while (resultSet.hasNext())
            System.err.println(resultSet.next());
    }

}