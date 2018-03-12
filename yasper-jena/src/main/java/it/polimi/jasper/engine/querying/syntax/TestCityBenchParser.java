package it.polimi.jasper.engine.querying.syntax;

import org.apache.commons.io.FileUtils;
import org.apache.jena.query.Syntax;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestCityBenchParser {
    private static final String root = "/Users/rictomm/_Projects/Semantic/StreamReasoning/RSP/yasper/src/test/resources/queries/citybench/rspql/";

    @Test
    public void query1() throws IOException {

        File file = new File(root + "Q1.rspql");

        String q = FileUtils.readFileToString(file);
        System.err.println(q);

        RSPQLJenaQuery query = QueryFactory.parse(null, q);
        query.setSyntax(Syntax.syntaxARQ);
        // Print the query (only the SPARQL 1.1 parts)
        System.out.println(query);

    }


    @Test
    public void query2() throws IOException {

        File file = new File(root + "Q2.txt");

        String q = FileUtils.readFileToString(file);
        System.err.println(q);

        RSPQLJenaQuery query = QueryFactory.parse(null, q);
        query.setSyntax(Syntax.syntaxARQ);
        // Print the query (only the SPARQL 1.1 parts)
        System.out.println(query);

    }

    @Test
    public void query10() throws IOException {

        File file = new File(root + "Q10.rspql");

        String q = FileUtils.readFileToString(file);
        System.err.println(q);

        RSPQLJenaQuery query = QueryFactory.parse(null, q);
        query.setSyntax(Syntax.syntaxARQ);
        // Print the query (only the SPARQL 1.1 parts)
        System.out.println(query);

    }
}
