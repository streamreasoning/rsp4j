package rspql.syntax;

import it.polimi.yasper.core.rspql.syntax.CaseChangingCharStream;
import it.polimi.yasper.core.rspql.syntax.RSPQLLexer;
import it.polimi.yasper.core.rspql.syntax.RSPQLParser;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class QueryFactory {
    public static RSPQLJenaQuery parse(String queryString) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(queryString.getBytes());
        return parse(inputStream);
    }

    public static RSPQLJenaQuery parse(InputStream inputStream) throws IOException {
        // Ignore case for keywords
        CaseChangingCharStream charStream = new CaseChangingCharStream(CharStreams.fromStream(inputStream), true);
        RSPQLLexer lexer = new RSPQLLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RSPQLParser parser = new RSPQLParser(tokens);
        parser.setErrorHandler(new BailErrorStrategy());
        ParseTree tree = parser.queryUnit();
        RSPQLJenaQuery query = new RSPQLJenaQuery();
        RSPQLJenaVisitor visitor = new RSPQLJenaVisitor(query);
        visitor.visit(tree);
        return query;
    }
}
