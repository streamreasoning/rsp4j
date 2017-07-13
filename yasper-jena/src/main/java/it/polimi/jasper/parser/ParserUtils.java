package it.polimi.jasper.parser;

import it.polimi.jasper.parser.sparql.Function;
import it.polimi.jasper.parser.sparql.ValuesClauseBuilder;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.TypeMapper;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;
import org.apache.jena.riot.system.IRIResolver;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.ExprList;
import org.apache.jena.sparql.expr.aggregate.Args;
import org.apache.jena.sparql.graph.NodeConst;
import org.apache.jena.sparql.syntax.*;
import org.apache.jena.sparql.util.ExprUtils;
import org.apache.jena.sparql.util.LabelToNodeMap;
import org.apache.jena.vocabulary.RDF;
import org.parboiled.BaseParser;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Riccardo on 09/08/16.
 */
public class ParserUtils extends BaseParser<Object> {

    // NodeConst
    protected final Node XSD_TRUE = NodeConst.nodeTrue;
    protected final Node XSD_FALSE = NodeConst.nodeFalse;

    protected final Node nRDFtype = NodeConst.nodeRDFType;

    protected final Node nRDFnil = NodeConst.nodeNil;
    protected final Node nRDFfirst = NodeConst.nodeFirst;
    protected final Node nRDFrest = NodeConst.nodeRest;

    protected final Node nRDFsubject = RDF.Nodes.subject;
    protected final Node nRDFpredicate = RDF.Nodes.predicate;
    protected final Node nRDFobject = RDF.Nodes.object;
    // label => bNode for construct templates patterns
    final LabelToNodeMap bNodeLabels = LabelToNodeMap.createBNodeMap();
    // label => bNode (as variable) for graph patterns
    final LabelToNodeMap anonVarLabels = LabelToNodeMap.createVarMap();
    protected LabelToNodeMap activeLabelMap = anonVarLabels;
    protected Set<String> previousLabels = new HashSet<String>();

    // This is the map used allocate blank node labels during sparql11.
    // 1/ It is different between CONSTRUCT intersection the query pattern
    // 2/ Each BasicGraphPattern is a scope for blank node labels so each
    // BGP causes the map to be cleared at the start of the BGP
    // Graph patterns, true; in templates, false.
    private boolean bNodesAreVariables = true;
    // In DELETE, false.
    private boolean bNodesAreAllowed = true;
    private IRIResolver resolver;

    public boolean bNodeOff() {
        activeLabelMap = bNodeLabels;
        return activeLabelMap.equals(bNodeLabels);
    }

    public boolean bNodeOn() {
        activeLabelMap = anonVarLabels;
        return activeLabelMap.equals(anonVarLabels);
    }

    public SPARQLQuery getQuery(int i) {
        if (i == -1) {
            int size = getContext().getValueStack().size();
            i = size > 0 ? size - 1 : 0;
        }
        return (SPARQLQuery) peek(i);
    }

    public SPARQLQuery popQuery(int i) {
        if (i == -1) {
            int size = getContext().getValueStack().size();
            i = size > 0 ? size - 1 : 0;
        }
        return (SPARQLQuery) pop(i);
    }

    public boolean pushQuery(SPARQLQuery q) {
        return push(0, q);
    }

    public Element popElement() {
        return ((Element) pop());
    }

    public boolean addElementToQuery() {
        getQuery(1).addElement((ElementGroup) popElement());
        return true;
    }

    public boolean addTemplateToQuery() {
        getQuery(1).setQConstructTemplate(new Template((((TripleCollectorBGP) pop()).getBGP())));
        return true;

    }

    public boolean addTemplateAndPatternToQuery() {
        ((ElementGroup) peek(1)).addElement(new ElementPathBlock(((TripleCollectorBGP) peek()).getBGP()));
        getQuery(2).setQConstructTemplate(new Template((((TripleCollectorBGP) pop()).getBGP())));
        return true;

    }

    public boolean addSubElement() {
        return addSubElement(1);
    }

    public boolean addSubElement(int i) {
        ((ElementGroup) peek(i)).addElement(popElement());
        return true;
    }

    public boolean addFilterElement() {
        return push(new ElementFilter((Expr) pop()));
    }

    public boolean addOptionalElement() {
        return push(new ElementOptional(popElement()));
    }

    public boolean createUnionElement() {
        return push(new ElementUnion());
    }

    public boolean addUnionElement() {
        ((ElementUnion) peek(1)).addElement((ElementGroup) popElement());
        return true;
    }

    public boolean addTripleToBloc(TripleCollector peek) {
        peek.addTriple(new Triple((Node) peek(2), (Node) peek(1), (Node) pop()));
        return true;
    }

    public boolean addNamedGraphElement() {
        return push(new ElementNamedGraph((Node) pop(), popElement()));
    }

    public boolean addFunctionCall() {
        return push(((Function) pop()).build());
    }

    public boolean addArg() {
        ((Args) peek(1)).add((Expr) pop());
        return true;
    }

    public boolean allocVariable(String s) {
        return push(Var.alloc(s.substring(1)));
    }

    public boolean asExpr() {
        return push(ExprUtils.nodeToExpr((Node) pop()));
    }

    public boolean addExprToExprList() {
        ((ExprList) peek(1)).add((Expr) pop());
        return true;
    }

    public String trimMatch() {
        String trim = match().trim();
        return trim;
    }

    public String stringMatch() {
        String trim = match().trim();
        return trim.substring(1, trim.length() - 1);
    }

    public String URIMatch() {
        return getQuery(-1).resolveSilent(trimMatch().replace(">", "").replace("<", ""));
    }

    public String resolvePNAME(String match) {
        // TODO I think this is correct beacause subqueries refer to the same
        // prologue
        String s = getQuery(-1).getQ().getPrologue().expandPrefixedName(match);
        return s;
    }

    public RDFDatatype getSafeTypeByName(String uri) {
        RDFDatatype safeTypeByName = TypeMapper.getInstance().getSafeTypeByName(uri);
        return safeTypeByName;
    }

    public IRIResolver getResolver() {
        return resolver;
    }

    public void setResolver(IRIResolver resolver) {
        this.resolver = resolver;
    }

    public boolean addValuesToQuery() {
        ValuesClauseBuilder vcb = (ValuesClauseBuilder) pop();
        getQuery(0).getQ().setValuesDataBlock(vcb.getElm().getVars(), vcb.getElm().getRows());
        return true;
    }

    public boolean startDataBlockValueRow(int i) {
        ValuesClauseBuilder pop = (ValuesClauseBuilder) peek(i);
        pop.addBinding();
        pop.currentColumn = -1;
        return true;
    }

    public boolean emitDataBlockValue(Node n) {

        ValuesClauseBuilder pop = (ValuesClauseBuilder) peek();
        pop.currentColumn++;

        if (pop.isValid() && n != null) {
            Var v = pop.getElm().getVars().get(pop.currentColumn);
            pop.currentValueRow().add(v, n);
        }

        return true;
    }

    public boolean emitDataBlockVariable(Var v) {
        return push(((ValuesClauseBuilder) pop()).addVar(v));
    }

    public boolean startSubQuery(int i) {
        return push(new SPARQLQuery(getQuery(i).getQ().getPrologue()));
    }

    public boolean endSubQuery() {
        return push(new ElementSubQuery(popQuery(0).getQ()));
    }


}
