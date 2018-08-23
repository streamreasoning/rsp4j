package it.polimi.yasper.core.rspql.syntax;

import it.polimi.yasper.core.rspql.querying.ContinuousQuery;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
* Depending on the implementation this class may be sed later. As of now, please see how
* this is implemented in yasper-jena.
*/

public class ContinuousQueryFactory {
    public static ContinuousQuery parse(String queryString) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(queryString.getBytes());
        return parse(inputStream);
    }

    public static ContinuousQuery parse(InputStream inputStream) throws IOException {
        // Ignore case for keywords
        CaseChangingCharStream charStream = new CaseChangingCharStream(CharStreams.fromStream(inputStream), true);
        RSPQLLexer lexer = new RSPQLLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RSPQLParser parser = new RSPQLParser(tokens);
        parser.setErrorHandler(new BailErrorStrategy());
        ParseTree tree = parser.queryUnit();
        ContinuousQuery query = null; //new RSPQLQueryImpl();
        //RSPQLSimpleVisitor visitor = new RSPQLSimpleVisitor(query);
        //visitor.visit(tree);
        return query;
    }
}
