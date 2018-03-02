package it.polimi.jasper.parser.run;

import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.jasper.parser.RSPQLParser;
import it.polimi.jasper.parser.streams.WindowedStreamNode;
import it.polimi.jasper.esper.EPLFactory;
import org.apache.commons.io.FileUtils;
import org.apache.jena.graph.Node;
import org.apache.jena.query.SortCondition;
import org.apache.jena.sparql.core.BasicPattern;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.core.VarExprList;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.lang.SyntaxVarScope;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementNamedGraph;
import org.parboiled.Parboiled;
import org.parboiled.errors.ParseError;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RSPMain {

    public static void main(String[] args) throws IOException {

        String input = getInput();

        RSPQLParser parser = Parboiled.createParser(RSPQLParser.class);

        ParsingResult<RSPQuery> result = new ReportingParseRunner(parser.Query()).run(input);

        if (result.hasErrors()) {
            for (ParseError arg : result.parseErrors) {
                System.out.println(input.substring(0, arg.getStartIndex()) + "|->" + input.substring(arg.getStartIndex(), arg.getEndIndex()) + "<-|" + input.substring(arg.getEndIndex() + 1, input.length() - 1));
            }
        }
        RSPQuery q = result.resultValue;

        print(q);
        System.out.println("Check valid");
        SyntaxVarScope.check(q.getQ());
    }

    private static void print(RSPQuery q) throws UnsupportedEncodingException {
        System.out.println("--MQL--");
        System.out.println(q.getGraphURIs());
        System.out.println(q.getQ().getQueryType());
        System.out.println(q.getNamedGraphURIs());

        VarExprList project = q.getQ().getProject();

        if (q.getQ().isSelectType()) {

            for (Var v : project.getVars()) {
                System.out.println("Project Var " + v.toString() + " Expr " + project.getExpr(v));
            }
            for (String v : q.getQ().getResultVars()) {
                System.out.println("Result Var " + v);
            }
        } else if (q.getQ().isConstructType()) {
            Map<Node, BasicPattern> graphPattern = q.getQ().getConstructTemplate().getGraphPattern();
            for (Node b : graphPattern.keySet()) {
                System.out.println("Node " + b + " Pattern " + graphPattern.get(b));
            }
        }

        Element queryPattern = q.getQ().getQueryPattern();
        System.out.println("queryPattern " + queryPattern);

        System.out.println("PREFIXES");

        Map<String, String> nsPrefixMap = q.getQ().getPrologue().getPrefixMapping().getNsPrefixMap();
        for (String prefix : nsPrefixMap.keySet()) {
            String uri = nsPrefixMap.get(prefix);
            System.out.println(prefix + ":" + uri);
        }

        List<SortCondition> orderBy = q.getQ().getOrderBy();

        if (orderBy != null && !orderBy.isEmpty())
            for (SortCondition sc : orderBy) {
                System.out.println(sc.getExpression().toString() + "  "
                        + ((org.apache.jena.query.Query.ORDER_DESCENDING == sc.direction) ? "DESC" : "ASC"));
            }

        System.out.println("LIMIT " + q.getQ().getLimit());
        System.out.println("OFFSET " + q.getQ().getOffset());

        VarExprList groupBy = q.getQ().getGroupBy();

        System.out.println("GROUP BY");
        List<Var> vars = groupBy.getVars();
        for (Var v : vars) {
            System.out.println("VAR " + v + " EXPR " + groupBy.getExpr(v));
        }

        System.out.println("HAVING");
        List<Expr> havingExprs = q.getQ().getHavingExprs();
        for (Expr e : havingExprs) {
            System.out.println("EXPR " + e.toString());
        }
        System.out.println("---");

        if (q.getNamedwindows() != null) {

            for (Map.Entry<Node, WindowedStreamNode> e : q.getNamedwindows().entrySet()) {
                String stream = e.getKey().getURI();
                System.out.println(stream);
                WindowedStreamNode w = e.getValue();
                System.out.println(EPLFactory.toEPL(w, w.getStream()));
                System.out.println(w.getStream().toEPLSchema());
            }
        }

        if (q.getWindows() != null) {
            for (WindowedStreamNode w : q.getWindows()) {
                System.out.println(w.toString());
            }
        }

        if (q.getWindowGraphElements() != null) {
            for (ElementNamedGraph windowGraphElement : q.getWindowGraphElements()) {
                System.out.println(windowGraphElement.toString());
            }
        }


        System.out.println(q.getHeader());

        System.out.println("--SPARQL--");

        System.out.println(q.toString());

    }

    public static String getInput() throws IOException {
        File file = new File("/Users/Riccardo/_Projects/Streamreasoning/rsp-baseline-parser/src/main/resources/rspquery.q");
        return FileUtils.readFileToString(file);
    }

    private static String converVarsToEPLProps(Set<Var> vars) {
        String eplProp = "";
        for (Var var : vars) {
            eplProp += var.getVarName() + " String ,";
        }
        eplProp = eplProp == "" ? "" : eplProp.substring(0, eplProp.length() - 1);
        return eplProp;
    }


}