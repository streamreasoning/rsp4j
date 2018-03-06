import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.*;
import org.junit.Test;
import it.polimi.jasper.engine.querying.syntax.QueryFactory;
import it.polimi.jasper.engine.querying.syntax.RSPQLJenaQuery;

import java.io.*;

/**
 * This validates the Yasper Jena parser for SPARQL 1.1.
 *
 * TODO: How to handle backslash in URIs? Should they be allowed at all?
 */

public class SPARQL11ParserTest {
    private static final String root = "./sparql11/";

    @Test
    public void positiveTests() {
        String mf = "http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#";
        String path = getClass().getClassLoader().getResource(root).getPath();
        Model model = ModelFactory.createDefaultModel();
        model.read(path + "manifest-sparql11-query.ttl", path);
        RDFList dirs = model
                .listObjectsOfProperty(ResourceFactory.createProperty(mf + "include"))
                .next().as(RDFList.class);

        int parsed = 0;
        int failed = 0;
        int correct = 0;
        for(RDFNode n : dirs.asJavaList()){
            Model m = ModelFactory.createDefaultModel();
            m.read(n.asResource().toString());
            String root = n.toString().replaceAll("^file://(.+/).*?$","$1");

            // Positive
            String queryString = "" +
                    "PREFIX mf: <http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#> " +
                    "PREFIX qt: <http://www.w3.org/2001/sw/DataAccess/tests/test-query#> " +
                    "SELECT ?file " +
                    "WHERE {" +
                    "   { ?entry a mf:PositiveSyntaxTest11 ;" +
                    "            mf:action ?action . " +
                    "     BIND( replace(str(?action), '^.*?/([^/]*)$', '$1') AS ?file) " +
                    "   }" +
                    "   UNION" +
                    "   { ?entry a mf:QueryEvaluationTest ; " +
                    "            mf:action/qt:query ?query . " +
                    "     BIND( replace(str(?query), '^.*?/([^/]*)$', '$1') AS ?file) " +
                    "   }" +
                    "}";
            ResultSet rs = QueryExecutionFactory.create(queryString, m).execSelect();

            while(rs.hasNext()) {
                String f = root + rs.next().get("file").toString();
                String q = readFile(f);
                try {
                    RSPQLJenaQuery q1 = QueryFactory.parse(q);
                    Query q2 = org.apache.jena.query.QueryFactory.create(q);
                    parsed++;
                    if(q1.toString().equals(q2.toString()))
                        correct++;
                    else {
                        System.out.println("Parsed but found mismatch: " + f);
                    }
                } catch (Exception e){
                    System.out.println("Error: Failed to parse " + f);
                    failed++;
                }
            }
        }
        System.out.println("Parsed: " + parsed);
        System.out.println("Correct: " + correct);
        System.out.println("Failed: " + failed);
    }

    /**
     * Correctly detecting all negative queries requires an extra validation step in SPARQL, which
     * is currently not supported.
     */
    @Test
    public void negativeTests() {
        String mf = "http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#";
        String rootPath = getClass().getClassLoader().getResource(root).getPath();
        Model model = ModelFactory.createDefaultModel();
        model.read(rootPath + "manifest-sparql11-query.ttl", rootPath);
        RDFList dirs = model
                .listObjectsOfProperty(ResourceFactory.createProperty(mf + "include"))
                .next().as(RDFList.class);

        int failed = 0;
        int correct = 0;
        for(RDFNode n : dirs.asJavaList()){
            Model m = ModelFactory.createDefaultModel();
            m.read(n.asResource().toString());
            String root = n.toString().replaceAll("^file://(.+/).*?$","$1");

            // Positive
            String queryString = "" +
                    "PREFIX mf: <http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#> " +
                    "PREFIX qt: <http://www.w3.org/2001/sw/DataAccess/tests/test-query#> " +
                    "SELECT ?file " +
                    "WHERE {" +
                    "  ?entry a mf:NegativeSyntaxTest11 ;" +
                    "         mf:action ?action . " +
                    "  BIND( replace(str(?action), '^.*?/([^/]*)$', '$1') AS ?file) " +
                    "}";
            ResultSet rs = QueryExecutionFactory.create(queryString, m).execSelect();
            while(rs.hasNext()) {
                String f = root + rs.next().get("file").toString();
                String q = readFile(f);
                try {
                    Query query = (Query) QueryFactory.parse(q);
                    failed++;
                } catch (Exception e){
                    correct++;
                }
            }
        }
        System.out.println("Correct: " + correct);
        System.out.println("Failed: " + failed);
    }

    private String readFile(String path) {
        try {
            InputStream is = new FileInputStream(path);
            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            }
            return sb.toString();
        } catch (IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}

