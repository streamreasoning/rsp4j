package org.streamreasoning.rsp4j.yasper.querying.syntax;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.tree.ParseTree;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.syntax.CaseChangingCharStream;
import org.streamreasoning.rsp4j.api.querying.syntax.RSPQLLexer;
import org.streamreasoning.rsp4j.api.querying.syntax.RSPQLParser;
import org.streamreasoning.rsp4j.yasper.querying.formatter.ContinuousQueryImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TPQueryFactory {

    /**
     * Demonstrate the use of the query factory.
     * @param args
     */
    public static void main(String[] args){
        ContinuousQuery c = TPQueryFactory.parse("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT * " +
                "FROM NAMED WINDOW <window1> ON <stream1> [RANGE PT10S STEP PT5S] " +
                "FROM NAMED WINDOW <window2> ON <stream2> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                "   ?s <http://testprop> ?o ." +
                "}");
        System.out.println("ISTREAM? " + c.isIstream());
        System.out.println("SELECT? " + c.isSelectType());
        c.getWindowMap().keySet().forEach(x -> {
            System.out.println(x.iri() + " " + c.getWindowMap().get(x).uri());
        });
    }

    public static ContinuousQuery parse(String queryString){
        InputStream inputStream = new ByteArrayInputStream(queryString.getBytes());
        return parse(inputStream);
    }

    public static ContinuousQuery parse(InputStream inputStream) {
        try {
            // Ignore case for keywords
            CaseChangingCharStream charStream = new CaseChangingCharStream(CharStreams.fromStream(inputStream), true);
            RSPQLLexer lexer = new RSPQLLexer(charStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            RSPQLParser parser = new RSPQLParser(tokens);
            parser.setErrorHandler(new DefaultErrorStrategy());
            ParseTree tree = parser.queryUnit();
            ContinuousQuery query = new ContinuousQueryImpl("w1");
            TPVisitorImpl visitor = new TPVisitorImpl();
            visitor.visit(tree);
      System.out.println(query.getSPARQL());
            return query;
        } catch(IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
