package it.polimi.jasper.parser;

import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

/**
 * SPARQL Parser
 *
 * @author Ken Wenzel, adapted by Mathias Doenitz
 */
@BuildParseTree
public class Lexer extends ParserUtils {

    public Rule HAVING() {
        return StringIgnoreCaseWS("HAVING");
    }

    public Rule SEPARATOR() {
        return StringIgnoreCaseWS("SEPARATOR");
    }

    public Rule COUNT() {
        return StringIgnoreCaseWS("COUNT");
    }

    public Rule SUM() {
        return StringIgnoreCaseWS("SUM");
    }

    public Rule MIN() {
        return StringIgnoreCaseWS("MIN");
    }

    public Rule MAX() {
        return StringIgnoreCaseWS("MAX");
    }

    public Rule SAMPLE() {
        return StringIgnoreCaseWS("SAMPLE");
    }

    public Rule GROUP_CONCAT() {
        return StringIgnoreCaseWS("GROUP_CONCAT");
    }

    public Rule IF() {
        return StringIgnoreCaseWS("IF");
    }

    public Rule STRLANG() {
        return IgnoreCase("STRLANG");
    }

    public Rule STRDT() {
        return IgnoreCase("STRDT");
    }

    public Rule SAME_TERM() {
        return IgnoreCase("SAME_TERM");
    }

    public Rule IS_NUMERIC() {
        return IgnoreCase("IS_NUMERIC");
    }

    public Rule ENCODE_FOR_URI() {
        return IgnoreCase("ENCODE_FOR_URI");
    }

    public Rule CONTAINS() {
        return IgnoreCase("CONTAINS");
    }

    public Rule SUBSTR() {
        return IgnoreCase("SUBSTR");
    }

    public Rule REPLACE() {
        return IgnoreCase("REPLACE");
    }

    public Rule STRLEN() {
        return IgnoreCase("STRLEN");
    }

    public Rule UCASE() {
        return IgnoreCase("UCASE");
    }

    public Rule LCASE() {
        return IgnoreCase("LCASE");
    }

    public Rule AVG() {
        return IgnoreCase("AVG");
    }

    public Rule ROUND() {
        return IgnoreCase("ROUND");
    }

    public Rule FLOOR() {
        return IgnoreCase("FLOOR");
    }

    public Rule CONCAT() {
        return IgnoreCase("CONCAT");
    }

    public Rule CEIL() {
        return IgnoreCase("CEIL");
    }

    public Rule RAND() {
        return IgnoreCase("RAND");
    }

    public Rule NIL() {
        return Sequence(LESS(), IgnoreCase("NIL"), GREATER());
    }

    public Rule NIL2() {
        return Sequence(LPAR(), WS(), RPAR());
    }

    public Rule WS() {
        return ZeroOrMore(FirstOf(COMMENT(), WS_NO_COMMENT()));
    }

    public Rule WS_NO_COMMENT() {
        return FirstOf(Ch(' '), Ch('\t'), Ch('\f'), EOL());
    }

    public Rule PNAME_NS() {
        return Sequence(Optional(PN_PREFIX()), ChWS(':'));
    }

    public Rule PNAME_LN() {
        return Sequence(PNAME_NS(), PN_LOCAL());
    }

    public Rule BASE() {
        return StringIgnoreCaseWS("BASE");
    }

    public Rule PREFIX() {
        return StringIgnoreCaseWS("PREFIX");
    }

    public Rule SELECT() {
        return StringIgnoreCaseWS("SELECT");
    }

    public Rule DISTINCT() {
        return StringIgnoreCaseWS("DISTINCT");
    }

    public Rule REDUCED() {
        return StringIgnoreCaseWS("REDUCED");
    }

    public Rule CONSTRUCT() {
        return StringIgnoreCaseWS("CONSTRUCT");
    }

    public Rule DESCRIBE() {
        return StringIgnoreCaseWS("DESCRIBE");
    }

    public Rule ASK() {
        return StringIgnoreCaseWS("ASK");

    }

    public Rule IN() {
        return StringIgnoreCaseWS("IN");

    }

    public Rule FROM() {
        return StringIgnoreCaseWS("FROM");
    }

    public Rule NAMED() {
        return StringIgnoreCaseWS("NAMED");
    }

    public Rule WHERE() {
        return StringIgnoreCaseWS("WHERE");
    }

    public Rule ORDER() {
        return StringIgnoreCaseWS("ORDER");
    }

    public Rule BY() {
        return StringIgnoreCaseWS("BY");
    }

    public Rule ASC() {
        return Sequence(StringIgnoreCaseWS("ASC"), push("ASC"));
    }

    public Rule DESC() {
        return Sequence(StringIgnoreCaseWS("DESC"), push("DESC"));
    }

    public Rule LIMIT() {
        return StringIgnoreCaseWS("LIMIT");
    }

    public Rule OFFSET() {
        return StringIgnoreCaseWS("OFFSET");
    }

    public Rule OPTIONAL() {
        return StringIgnoreCaseWS("OPTIONAL");
    }

    public Rule GRAPH() {
        return StringIgnoreCaseWS("GRAPH");
    }

    public Rule UNION() {
        return StringIgnoreCaseWS("UNION");
    }

    public Rule FILTER() {
        return StringIgnoreCaseWS("FILTER");
    }

    public Rule A() {
        return ChWS('a');
    }

    public Rule GROUP() {
        return StringIgnoreCaseWS("GROUP");
    }

    public Rule AS() {
        return StringIgnoreCaseWS("AS");
    }

    public Rule STR() {
        return StringIgnoreCaseWS("STR");
    }

    public Rule LANG() {
        return StringIgnoreCaseWS("LANG");
    }

    public Rule LANGMATCHES() {
        return StringIgnoreCaseWS("LANGMATCHES");
    }

    public Rule DATATYPE() {
        return StringIgnoreCaseWS("DATATYPE");
    }

    public Rule BOUND() {
        return StringIgnoreCaseWS("BOUND");
    }

    public Rule BNODE() {
        return StringIgnoreCaseWS("BNODE");
    }

    public Rule SAMETERM() {
        return StringIgnoreCaseWS("SAMETERM");
    }

    public Rule ISIRI() {
        return StringIgnoreCaseWS("ISIRI");
    }

    public Rule ISURI() {
        return StringIgnoreCaseWS("ISURI");
    }

    public Rule ISBLANK() {
        return StringIgnoreCaseWS("ISBLANK");
    }

    public Rule ISLITERAL() {
        return StringIgnoreCaseWS("ISLITERAL");
    }

    public Rule REGEX() {
        return StringIgnoreCaseWS("REGEX");
    }

    public Rule TRUE() {
        return StringIgnoreCaseWS("TRUE");
    }

    public Rule FALSE() {
        return StringIgnoreCaseWS("FALSE");
    }

    public Rule IRI_REF() {
        return Sequence(LESS_NO_COMMENT(),
                ZeroOrMore(
                        Sequence(
                                TestNot(FirstOf(LESS_NO_COMMENT(), GREATER(), '"', OPEN_CURLY_BRACE(),
                                        CLOSE_CURLY_BRACE(), '|', '^', '\\', '`', CharRange('\u0000', '\u0020'))),
                                ANY)),
                GREATER(), WS());
    }

    public Rule BLANK_NODE_LABEL() {
        return Sequence("_:", PN_LOCAL(), WS());
    }

    public Rule VAR1() {
        return Sequence('?', VARNAME(), WS());
    }

    public Rule VAR2() {
        return Sequence('$', VARNAME(), WS());
    }

    public Rule LANGTAG() {
        return Sequence('@', OneOrMore(PN_CHARS_BASE()),
                ZeroOrMore(Sequence(MINUS(), OneOrMore(Sequence(PN_CHARS_BASE(), DIGIT())))), WS());
    }

    public Rule INTEGER() {
        return Sequence(OneOrMore(DIGIT()), WS());
    }

    public Rule DECIMAL() {
        return Sequence(
                FirstOf(Sequence(OneOrMore(DIGIT()), DOT(), ZeroOrMore(DIGIT())), Sequence(DOT(), OneOrMore(DIGIT()))),
                WS());

    }

    public Rule DOUBLE() {
        return Sequence(
                FirstOf(Sequence(OneOrMore(DIGIT()), DOT(), ZeroOrMore(DIGIT()), EXPONENT()),
                        Sequence(DOT(), OneOrMore(DIGIT()), EXPONENT()), Sequence(OneOrMore(DIGIT()), EXPONENT())),
                WS());
    }

    public Rule INTEGER_POSITIVE() {
        return Sequence(PLUS(), INTEGER());
    }

    public Rule DECIMAL_POSITIVE() {
        return Sequence(PLUS(), DECIMAL());
    }

    public Rule DOUBLE_POSITIVE() {
        return Sequence(PLUS(), DOUBLE());
    }

    public Rule INTEGER_NEGATIVE() {
        return Sequence(MINUS(), INTEGER());
    }

    public Rule DECIMAL_NEGATIVE() {
        return Sequence(MINUS(), DECIMAL());
    }

    public Rule DOUBLE_NEGATIVE() {
        return Sequence(MINUS(), DOUBLE());
    }

    public Rule EXPONENT() {
        return Sequence(IgnoreCase('e'), Optional(FirstOf(PLUS(), MINUS())), OneOrMore(DIGIT()));
    }

    public Rule STRING_LITERAL1() {
        return Sequence("'", ZeroOrMore(FirstOf(Sequence(TestNot(FirstOf("'", '\\', '\n', '\r')), ANY), ECHAR())), "'",
                WS());
    }

    public Rule STRING_LITERAL2() {
        return Sequence('"', ZeroOrMore(FirstOf(Sequence(TestNot(AnyOf("\"\\\n\r")), ANY), ECHAR())), '"', WS());
    }

    public Rule STRING_LITERAL_LONG1() {
        return Sequence("'''", ZeroOrMore(
                Sequence(Optional(FirstOf("''", "'")), FirstOf(Sequence(TestNot(FirstOf("'", "\\")), ANY), ECHAR()))),
                "'''", WS());
    }

    public Rule STRING_LITERAL_LONG2() {
        return Sequence("\"\"\"", ZeroOrMore(Sequence(Optional(FirstOf("\"\"", "\"")),
                FirstOf(Sequence(TestNot(FirstOf("\"", "\\")), ANY), ECHAR()))), "\"\"\"", WS());
    }

    public Rule ECHAR() {
        return Sequence('\\', AnyOf("tbnrf\\\"\'"));
    }

    public Rule PN_CHARS_U() {
        return FirstOf(PN_CHARS_BASE(), '_');
    }

    public Rule VARNAME() {
        return Sequence(FirstOf(PN_CHARS_U(), DIGIT()), ZeroOrMore(
                FirstOf(PN_CHARS_U(), DIGIT(), '\u00B7', CharRange('\u0300', '\u036F'), CharRange('\u203F', '\u2040'))),
                WS());
    }

    public Rule PN_CHARS() {
        return FirstOf(MINUS(), DIGIT(), PN_CHARS_U(), '\u00B7', CharRange('\u0300', '\u036F'),
                CharRange('\u203F', '\u2040'));
    }

    public Rule PN_PREFIX() {
        return Sequence(PN_CHARS_BASE(), Optional(ZeroOrMore(FirstOf(PN_CHARS(), Sequence(DOT(), PN_CHARS())))));
    }

    public Rule PN_LOCAL() {
        return Sequence(FirstOf(PN_CHARS_U(), DIGIT()),
                Optional(ZeroOrMore(FirstOf(PN_CHARS(), Sequence(DOT(), PN_CHARS())))), WS());
    }

    public Rule PN_CHARS_BASE() {
        return FirstOf( //
                CharRange('A', 'Z'), //
                CharRange('a', 'z'), //
                CharRange('\u00C0', '\u00D6'), //
                CharRange('\u00D8', '\u00F6'), //
                CharRange('\u00F8', '\u02FF'), //
                CharRange('\u0370', '\u037D'), //
                CharRange('\u037F', '\u1FFF'), //
                CharRange('\u200C', '\u200D'), //
                CharRange('\u2070', '\u218F'), //
                CharRange('\u2C00', '\u2FEF'), //
                CharRange('\u3001', '\uD7FF'), //
                CharRange('\uF900', '\uFDCF'), //
                CharRange('\uFDF0', '\uFFFD') //
        );
    }

    public Rule DIGIT() {
        return CharRange('0', '9');
    }

    public Rule COMMENT() {
        return Sequence('#', ZeroOrMore(Sequence(TestNot(EOL()), ANY)), EOL());
    }

    public Rule EOL() {
        return AnyOf("\n\r");
    }

    public Rule REFERENCE() {
        return StringWS("^^");
    }

    public Rule LESS_EQUAL() {
        return StringWS("<=");
    }

    public Rule GREATER_EQUAL() {
        return StringWS(">=");
    }

    public Rule NOT_EQUAL() {
        return StringWS("!=");
    }

    public Rule AND() {
        return StringWS("&&");
    }

    public Rule OR() {
        return StringWS("||");
    }

    public Rule LPAR() {
        return ChWS('(');
    }

    public Rule RPAR() {
        return ChWS(')');
    }

    public Rule OPEN_CURLY_BRACE() {
        return ChWS('{');
    }

    public Rule CLOSE_CURLY_BRACE() {
        return ChWS('}');
    }

    public Rule OPEN_SQUARE_BRACE() {
        return ChWS('[');
    }

    public Rule CLOSE_SQUARE_BRACE() {
        return ChWS(']');
    }

    public Rule SEMICOLON() {
        return ChWS(';');
    }

    public Rule DOT() {
        return ChWS('.');
    }

    public Rule PLUS() {
        return ChWS('+');
    }

    public Rule MINUS() {
        return ChWS('-');
    }

    public Rule MINUSC() {
        return StringIgnoreCaseWS("MINUS");
    }

    public Rule BIND() {
        return StringIgnoreCaseWS("BIND");
    }

    public Rule SERVICE() {
        return StringIgnoreCaseWS("SERVICE");
    }

    public Rule SILENT() {
        return StringIgnoreCaseWS("SILENT");
    }

    public Rule EXISTS() {
        return StringIgnoreCaseWS("EXISTS");
    }

    public Rule UNDEF() {
        return StringIgnoreCaseWS("UNDEF");
    }

    public Rule VALUES() {
        return StringIgnoreCaseWS("VALUES");
    }

    public Rule ASTERISK() {
        return ChWS('*');
    }

    public Rule COMMA() {
        return ChWS(',');
    }

    public Rule BANG() {
        return ChWS('!');
    }

    public Rule NOT() {
        return StringIgnoreCaseWS("NOT");
    }

    public Rule DIVIDE() {
        return ChWS('/');
    }

    public Rule EQUAL() {
        return ChWS('=');
    }

    public Rule LESS_NO_COMMENT() {
        return Sequence(Ch('<'), ZeroOrMore(WS_NO_COMMENT()));
    }

    public Rule LESS() {
        return ChWS('<');
    }

    public Rule GREATER() {
        return ChWS('>');
    }

    // </Lexer>

    public Rule ChWS(char c) {
        return Sequence(Ch(c), WS());
    }

    public Rule StringWS(String s) {
        return Sequence(String(s), WS());
    }

    public Rule StringIgnoreCaseWS(String string) {
        return Sequence(IgnoreCase(string), WS());
    }


}