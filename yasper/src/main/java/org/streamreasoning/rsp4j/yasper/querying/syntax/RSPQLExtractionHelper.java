package org.streamreasoning.rsp4j.yasper.querying.syntax;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.syntax.RSPQLParser;
import org.streamreasoning.rsp4j.yasper.querying.PrefixMap;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.WindowNodeImpl;

import java.time.Duration;
import java.util.AbstractMap;
import java.util.Map;

public class RSPQLExtractionHelper {

    public static Map.Entry<String, WindowNode> extractNamedWindowClause(RSPQLParser.NamedWindowClauseContext ctx) {

        String windowUri = RDFUtils.trimTags(ctx.windowUri().getText());
        String streamUri = RDFUtils.trimTags(ctx.streamUri().getText());
        return getStringWindowNodeEntry(ctx, windowUri, streamUri);
    }
    public static Map.Entry<String, WindowNode> extractNamedWindowClause(RSPQLParser.NamedWindowClauseContext ctx, PrefixMap prefixes) {

        String windowUri = RDFUtils.trimTags(ctx.windowUri().getText());
        String streamUri = RDFUtils.trimTags(ctx.streamUri().getText());
        windowUri = prefixes.expandIfPrefixed(windowUri);
        streamUri = prefixes.expandIfPrefixed(streamUri);
        return getStringWindowNodeEntry(ctx, windowUri, streamUri);
    }

    private static Map.Entry<String, WindowNode> getStringWindowNodeEntry(RSPQLParser.NamedWindowClauseContext ctx, String windowUri, String streamUri) {
        RSPQLParser.LogicalWindowContext c = ctx.window().logicalWindow();
        Duration range = Duration.parse(c.logicalRange().duration().getText());
        Duration step = null;

        if (c.logicalStep() != null) {
            step = Duration.parse(c.logicalStep().duration().getText());
        }

        WindowNode wn = new WindowNodeImpl(RDFUtils.createIRI(windowUri), range, step, 0);
        return new AbstractMap.SimpleEntry<>(streamUri, wn);
    }

    public static String extractOutputStream(RSPQLParser.OutputStreamContext ctx) {
        RSPQLParser.SourceSelectorContext sourceSelectorContext = ctx.sourceSelector();
        RSPQLParser.IriContext iri1 = sourceSelectorContext.iri();
        TerminalNode iriref = iri1.IRIREF();
        String text = "";
        if(iriref == null){
            text = iri1.prefixedName().getText();
        } else {
          text = iriref.getText();
          text = RDFUtils.trimTags(text);
        }
        return text;
    }


    public static void setOutputStreamType(ContinuousQuery query, String outputStreamType) {
        switch (outputStreamType) {
            case "ISTREAM":
                query.setIstream();
                break;
            case "RSTREAM":
                query.setRstream();
                break;
            case "DSTREAM":
                query.setDstream();
                break;
        }
    }
}
