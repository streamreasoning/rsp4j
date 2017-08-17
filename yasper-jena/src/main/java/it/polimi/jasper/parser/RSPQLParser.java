package it.polimi.jasper.parser;

import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.jasper.parser.streams.Window;
import it.polimi.jasper.parser.streams.Register;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.Node_URI;
import org.apache.jena.sparql.syntax.ElementNamedGraph;
import org.parboiled.Rule;

import static it.polimi.yasper.core.enums.StreamOperator.*;

/**
 * Created by Riccardo on 09/08/16.
 */
public class RSPQLParser extends SPARQLParser {

    @Override
    public Rule Query() {
        return Sequence(push(new RSPQuery()), WS(), Optional(Prologue(), Registration()),
                FirstOf(SelectQuery(), ConstructQuery(), AskQuery(), DescribeQuery()), EOI);
    }

    public Rule Registration() {
        return Sequence(REGISTER(), push(new Register()), FirstOf(STREAM(), QUERY()),
                push(((Register) pop()).setType(trimMatch())),
                Sequence(SourceSelector(), swap(), push(((Register) pop()).setId((Node) pop()))), Optional(
                        WS(), COMPUTED(), EVERY(), TimeConstrain(),
                        push(((Register) pop()).addCompute((match())))), AS(), WS(),
                pushQuery(((RSPQuery) popQuery(1)).setRegister((Register) pop())));
    }


    @Override
    public Rule DataClause() {
        return ZeroOrMore(FirstOf(DatasetClause(), DatastreamClause()));
    }

    public Rule DatastreamClause() {
        return Sequence(FROM(), FirstOf(DefaultStreamClause(), NamedStreamClause()),
                pushQuery(((RSPQuery) popQuery(1)).addWindow((Window) pop())));
    }

    public Rule DefaultStreamClause() {
        return Sequence(WINDOW(), push(new Window()), WindowClause(), ON(), STREAM(), SourceSelector(),
                push(((Window) pop(1)).addStreamUri(((Node_URI) pop()))));
    }

    public Rule NamedStreamClause() {
        return Sequence(NAMED(), WINDOW(), SourceSelector(), push(new Window((Node_URI) pop())), WindowClause(), ON(),
                STREAM(), SourceSelector(), push(((Window) pop(1)).addStreamUri(((Node_URI) pop()))));
    }

    public Rule WindowClause() {
        return Sequence(OPEN_SQUARE_BRACE(), RANGE(), WindowDef(), CLOSE_SQUARE_BRACE());
    }

    public Rule WindowDef() {
        return FirstOf(LogicalWindow(), PhysicalWindow());
    }

    public Rule LogicalWindow() {
        return Sequence(TimeConstrain(), push((((Window) pop())).addConstrain(match())), COMMA(),
                FirstOf(Sequence(SLIDE(), TimeConstrain(), push((((Window) pop())).addSlide(trimMatch()))), TUMBLING()),
                WS());
    }

    public Rule PhysicalWindow() {
        return Sequence(PhysicalConstrain(), push((((Window) pop())).addConstrain(match())), COMMA(), FirstOf(
                Sequence(SLIDE(), PhysicalConstrain(), push((((Window) pop())).addSlide(trimMatch()))), TUMBLING()));
    }

    public Rule TimeConstrain() {
        return Sequence(INTEGER(), TIME_UNIT());
    }

    public Rule PhysicalConstrain() {
        return Sequence(INTEGER(), FirstOf(TRIPLES(), GRAPH()));
    }

    @Override
    public Rule GraphPatternNotTriples() {
        return FirstOf(GroupOrUnionGraphPattern(), OptionalGraphPattern(), MinusGraphPattern(), GraphGraphPattern(),
                WindowGraphPattern(), ServiceGraphPattern(), Filter(), Bind(), InlineData());
    }

    public Rule WindowGraphPattern() {
        return Sequence(WINDOW(), VarOrIRIref(), GroupGraphPattern(), swap(), addNamedWindowElement());
    }

    @Override
    public Rule ConstructClause() {
        return Sequence(FirstOf(ConstructIStream(), ConstructDStream(), ConstructRStream()), ConstructTemplate(),
                addTemplateToQuery());
    }

    public Rule ConstructRStream() {
        return Sequence(CONSTRUCT(), Optional(RSTREAM()), pushQuery(((RSPQuery) popQuery(0)).setConstructQuery(RSTREAM)));
    }


    public Rule ConstructIStream() {
        return Sequence(CONSTRUCT(), ISTREAM(), pushQuery(((RSPQuery) popQuery(0)).setConstructQuery(ISTREAM)));
    }

    public Rule ConstructDStream() {
        return Sequence(CONSTRUCT(), DSTREAM(), pushQuery(((RSPQuery) popQuery(0)).setConstructQuery(DSTREAM)));
    }

    //Utility Methods

    @Override
    public boolean startSubQuery(int i) {
        return push(new RSPQuery(getQuery(i).getQ().getPrologue()));
    }

    // CSPARQL
    public boolean addNamedWindowElement() {
        ElementNamedGraph value = new ElementNamedGraph((Node) pop(), popElement());
        ((RSPQuery) getQuery(-1)).addElement(value);
        return push(value);
    }

    // RSP Syntax Extensions

    public Rule REGISTER() {
        return StringIgnoreCaseWS("REGISTER");
    }

    public Rule QUERY() {
        return StringIgnoreCaseWS("QUERY");
    }

    public Rule EVERY() {
        return StringIgnoreCaseWS("EVERY");
    }

    public Rule COMPUTED() {
        return StringIgnoreCaseWS("COMPUTED");
    }

    public Rule TIME_UNIT() {
        return Sequence(FirstOf("ms", 's', 'm', 'h', 'd'), WS());
    }

    public Rule TRIPLES() {
        return StringIgnoreCaseWS("TRIPLES");
    }

    public Rule TUMBLING() {
        return StringIgnoreCaseWS("TUMBLING");
    }

    public Rule SLIDE() {
        return StringIgnoreCaseWS("SLIDE");
    }

    public Rule WINDOW() {
        return StringIgnoreCaseWS("WINDOW");
    }

    public Rule RANGE() {
        return StringIgnoreCaseWS("RANGE");
    }

    public Rule ON() {
        return StringIgnoreCaseWS("ON");
    }

    public Rule STREAM() {
        return StringIgnoreCaseWS("STREAM");
    }

    public Rule ISTREAM() {
        return StringIgnoreCaseWS("ISTREAM");
    }

    public Rule RSTREAM() {
        return StringIgnoreCaseWS("RSTREAM");
    }

    public Rule DSTREAM() {
        return StringIgnoreCaseWS("DSTREAM");
    }


}