package it.polimi.jasper.parser.run;

import it.polimi.jasper.parser.SPARQLQuery;
import it.polimi.jasper.parser.SPARQLParser;
import org.apache.commons.io.FileUtils;
import org.apache.jena.graph.Node;
import org.apache.jena.query.SortCondition;
import org.apache.jena.riot.system.IRIResolver;
import org.apache.jena.sparql.core.BasicPattern;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.core.VarExprList;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.lang.SyntaxVarScope;
import org.apache.jena.sparql.syntax.Element;
import org.parboiled.Parboiled;
import org.parboiled.errors.ParseError;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class SPARQLMain {

    public static void main(String[] args) throws IOException {

        String input = getInput();

        SPARQLParser parser = Parboiled.createParser(SPARQLParser.class);

        parser.setResolver(IRIResolver.create());

        ParsingResult<SPARQLQuery> result = new ReportingParseRunner(parser.Query()).run(input);

        if (result.hasErrors()) {
            for (ParseError arg : result.parseErrors) {
                System.out.println(input.substring(0, arg.getStartIndex()) + "|->" + input.substring(arg.getStartIndex(), arg.getEndIndex()) + "<-|" + input.substring(arg.getEndIndex() + 1, input.length() - 1));
            }
        }
        SPARQLQuery q = result.resultValue;

        print(q);
        System.out.println("Check valid");
        SyntaxVarScope.check(q);
    }

    private static void print(SPARQLQuery q) throws UnsupportedEncodingException {
        System.out.println("--MQL--");
        System.out.println(q.getGraphURIs());
        System.out.println(q.getQueryType());
        System.out.println(q.getNamedGraphURIs());

        VarExprList project = q.getProject();

        if (q.isSelectType()) {

            for (Var v : project.getVars()) {
                System.out.println("Project Var " + v.toString() + " Expr " + project.getExpr(v));
            }
            for (String v : q.getResultVars()) {
                System.out.println("Result Var " + v);
            }
        } else if (q.isConstructType()) {
            Map<Node, BasicPattern> graphPattern = q.getConstructTemplate().getGraphPattern();
            for (Node b : graphPattern.keySet()) {
                System.out.println("Node " + b + " Pattern " + graphPattern.get(b));
            }
        }

        Element queryPattern = q.getQueryPattern();
        System.out.println("queryPattern " + queryPattern);

        System.out.println("PREFIXES");

        Map<String, String> nsPrefixMap = q.getPrologue().getPrefixMapping().getNsPrefixMap();
        for (String prefix : nsPrefixMap.keySet()) {
            String uri = nsPrefixMap.get(prefix);
            System.out.println(prefix + ":" + uri);
        }

        List<SortCondition> orderBy = q.getOrderBy();

        if (orderBy != null && !orderBy.isEmpty())
            for (SortCondition sc : orderBy) {
                System.out.println(sc.getExpression().toString() + "  "
                        + ((org.apache.jena.query.Query.ORDER_DESCENDING == sc.direction) ? "DESC" : "ASC"));
            }

        System.out.println("LIMIT " + q.getLimit());
        System.out.println("OFFSET " + q.getOffset());

        VarExprList groupBy = q.getGroupBy();

        System.out.println("GROUP BY");
        List<Var> vars = groupBy.getVars();
        for (Var v : vars) {
            System.out.println("VAR " + v + " EXPR " + groupBy.getExpr(v));
        }

        System.out.println("HAVING");
        List<Expr> havingExprs = q.getHavingExprs();
        for (Expr e : havingExprs) {
            System.out.println("EXPR " + e.toString());
        }
        System.out.println("---");

        System.out.println("--SPARQL--");

        System.out.println(q.toString());

    }

    public static String getInput() throws IOException {
        File file = new File("/Users/Riccardo/_Projects/Streamreasoning/MQL-Parser/src/main/resources/query.q");
        return FileUtils.readFileToString(file);
    }


}