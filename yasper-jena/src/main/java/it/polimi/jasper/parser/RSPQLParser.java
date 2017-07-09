package it.polimi.jasper.parser;

import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.jasper.parser.streams.Register;
import it.polimi.jasper.parser.streams.Window;
import it.polimi.yasper.core.query.ContinuousQuery;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.Node_URI;
import org.apache.jena.sparql.syntax.ElementNamedGraph;
import org.parboiled.Rule;

/**
 * Created by Riccardo on 09/08/16.
 */
public class RSPQLParser extends SPARQLParser implements ContinuousQuery{

    @Override
    public Rule Query() {
        return Sequence(push(new RSPQuery()), WS(), Optional(Registration()), Prologue(),
                FirstOf(SelectQuery(), ConstructQuery(), AskQuery(), DescribeQuery()), EOI);
    }

    public Rule Registration() {
        return Sequence(REGISTER(), push(new Register()), FirstOf(STREAM(), QUERY()),
                push(((Register) pop()).setType(trimMatch())),
                FirstOf(String(), VARNAME()),
                push(((Register) pop()).setId((match()))),
                WS(), COMPUTED(), EVERY(), TimeConstrain(),
                push(((Register) pop()).addCompute((match()))), AS(), WS(),
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

}