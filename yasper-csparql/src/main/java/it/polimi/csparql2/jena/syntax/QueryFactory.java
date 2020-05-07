package it.polimi.csparql2.jena.syntax;

import it.polimi.yasper.core.querying.syntax.CaseChangingCharStream;
import it.polimi.yasper.core.querying.syntax.RSPQLLexer;
import it.polimi.yasper.core.querying.syntax.RSPQLParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.jena.riot.system.IRIResolver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class QueryFactory {

    static ThrowingErrorListener listener = ThrowingErrorListener.INSTANCE;

    public static RSPQLJenaQuery parse(String baseUri, String queryString) throws IOException {

        InputStream inputStream = new ByteArrayInputStream(queryString.getBytes());
        return parse(baseUri, inputStream);
    }

    public static RSPQLJenaQuery parse(String baseUri, InputStream inputStream) throws IOException {
        // Ignore case for keywords
        CaseChangingCharStream charStream = new CaseChangingCharStream(CharStreams.fromStream(inputStream), true);
        RSPQLLexer lexer = new RSPQLLexer(charStream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(listener);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RSPQLParser parser = new RSPQLParser(tokens);
        parser.setErrorHandler(new DefaultErrorStrategy());
        parser.removeErrorListeners();
        parser.addErrorListener(listener);
        ParseTree tree = parser.queryUnit();

        RSPQLJenaQuery query = new RSPQLJenaQuery(IRIResolver.create(baseUri));
        RSPQLJenaVisitor visitor = new RSPQLJenaVisitor(query);
        visitor.visit(tree);

        return query;
    }
}
