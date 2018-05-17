package simple.querying;

import it.polimi.yasper.core.quering.ContinuousQuery;
import it.polimi.yasper.core.quering.syntax.CaseChangingCharStream;
import it.polimi.yasper.core.quering.syntax.RSPQLLexer;
import it.polimi.yasper.core.quering.syntax.RSPQLParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class QueryFactory {

    /**
     * Demonstrate the use of the query factory.
     * @param args
     */
    public static void main(String[] args){
        ContinuousQuery c = QueryFactory.parse("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT * " +
                "FROM NAMED WINDOW <window1> ON <stream1> [RANGE PT10S STEP PT5S] " +
                "FROM NAMED WINDOW <window2> ON <stream2> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                "   ?a ?b ?c ." +
                "}");
        System.out.println("ISTREAM? " + c.isIstream());
        System.out.println("SELECT? " + c.isSelectType());
        c.getWindowMap().keySet().forEach(x -> {
            System.out.println(x.getName() + " " + c.getWindowMap().get(x).getURI());
        });
        // Query pattern in WHERE clause not yet managed, depends what the internal BGP representation will be.
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
            ContinuousQuery query = new ContinuousQueryImpl();
            RSPQLVisitorImpl visitor = new RSPQLVisitorImpl(query);
            visitor.visit(tree);
            return query;
        } catch(IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
