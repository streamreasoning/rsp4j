package org.streamreasoning.rsp4j.yasper.querying;

import org.streamreasoning.rsp4j.yasper.querying.syntax.CQ;
import org.streamreasoning.rsp4j.yasper.querying.syntax.RSPQLVisitorImpl;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.syntax.CaseChangingCharStream;
import org.streamreasoning.rsp4j.api.querying.syntax.RSPQLLexer;
import org.streamreasoning.rsp4j.api.querying.syntax.RSPQLParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class QueryFactory {

    static ThrowingErrorListener listener = ThrowingErrorListener.INSTANCE;

    public static ContinuousQuery parse(String querystring) throws IOException {

        InputStream inputStream = new ByteArrayInputStream(querystring.getBytes());

        CaseChangingCharStream charStream = new CaseChangingCharStream(CharStreams.fromStream(inputStream), true);
        RSPQLLexer lexer = new RSPQLLexer(charStream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(listener);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RSPQLParser parser = new RSPQLParser(tokens);
        parser.setErrorHandler(new DefaultErrorStrategy());
        parser.removeErrorListeners();
        parser.addErrorListener(listener);

        RSPQLParser.QueryUnitContext tree = parser.queryUnit();

        ContinuousQuery query = new CQ();

        RSPQLVisitorImpl visitor = new RSPQLVisitorImpl(query);
        visitor.visit(tree);
        return query;

    }

    static class ThrowingErrorListener extends BaseErrorListener {

        public static final ThrowingErrorListener INSTANCE = new ThrowingErrorListener();

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
                throws ParseCancellationException {
            throw new ParseCancellationException("line " + line + ":" + charPositionInLine + " " + msg);
        }
    }
}

