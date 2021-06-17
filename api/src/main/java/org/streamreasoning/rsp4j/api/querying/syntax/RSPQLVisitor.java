// Generated from /Users/psbonte/Documents/Github/rsp4j/api/src/main/java/org/streamreasoning/rsp4j/api/querying/syntax/RSPQL.g4 by ANTLR 4.9.1
package org.streamreasoning.rsp4j.api.querying.syntax;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link RSPQLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface RSPQLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#queryUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryUnit(RSPQLParser.QueryUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuery(RSPQLParser.QueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#prologue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrologue(RSPQLParser.PrologueContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#baseDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseDecl(RSPQLParser.BaseDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#prefixDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixDecl(RSPQLParser.PrefixDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#registerClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegisterClause(RSPQLParser.RegisterClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#outputStreamType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutputStreamType(RSPQLParser.OutputStreamTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#outputStream}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutputStream(RSPQLParser.OutputStreamContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#selectQuery}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectQuery(RSPQLParser.SelectQueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#subSelect}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubSelect(RSPQLParser.SubSelectContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#selectClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectClause(RSPQLParser.SelectClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#resultStar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResultStar(RSPQLParser.ResultStarContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#resultVar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResultVar(RSPQLParser.ResultVarContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#distinct}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDistinct(RSPQLParser.DistinctContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#reduced}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReduced(RSPQLParser.ReducedContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#constructQuery}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructQuery(RSPQLParser.ConstructQueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#describeQuery}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDescribeQuery(RSPQLParser.DescribeQueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#askQuery}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAskQuery(RSPQLParser.AskQueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#datasetClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatasetClause(RSPQLParser.DatasetClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#defaultGraphClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultGraphClause(RSPQLParser.DefaultGraphClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#namedGraphClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedGraphClause(RSPQLParser.NamedGraphClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#namedWindowClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedWindowClause(RSPQLParser.NamedWindowClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#windowUri}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowUri(RSPQLParser.WindowUriContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#streamUri}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStreamUri(RSPQLParser.StreamUriContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#window}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindow(RSPQLParser.WindowContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#physicalWindow}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPhysicalWindow(RSPQLParser.PhysicalWindowContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#physicalRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPhysicalRange(RSPQLParser.PhysicalRangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#physicalStep}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPhysicalStep(RSPQLParser.PhysicalStepContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#logicalWindow}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalWindow(RSPQLParser.LogicalWindowContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#logicalRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalRange(RSPQLParser.LogicalRangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#logicalStep}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalStep(RSPQLParser.LogicalStepContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#duration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDuration(RSPQLParser.DurationContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#sourceSelector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSourceSelector(RSPQLParser.SourceSelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#whereClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhereClause(RSPQLParser.WhereClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#solutionModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSolutionModifier(RSPQLParser.SolutionModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#groupClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupClause(RSPQLParser.GroupClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#groupCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupCondition(RSPQLParser.GroupConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#havingClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHavingClause(RSPQLParser.HavingClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#havingCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHavingCondition(RSPQLParser.HavingConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#orderClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrderClause(RSPQLParser.OrderClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#orderCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrderCondition(RSPQLParser.OrderConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#limitOffsetClauses}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLimitOffsetClauses(RSPQLParser.LimitOffsetClausesContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#limitClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLimitClause(RSPQLParser.LimitClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#offsetClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOffsetClause(RSPQLParser.OffsetClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#valuesClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValuesClause(RSPQLParser.ValuesClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#graphOrDefault}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphOrDefault(RSPQLParser.GraphOrDefaultContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#graphRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphRef(RSPQLParser.GraphRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#quadPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuadPattern(RSPQLParser.QuadPatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#quads}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuads(RSPQLParser.QuadsContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#quadsNotTriples}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuadsNotTriples(RSPQLParser.QuadsNotTriplesContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#triplesTemplate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTriplesTemplate(RSPQLParser.TriplesTemplateContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#groupGraphPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupGraphPattern(RSPQLParser.GroupGraphPatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#groupGraphPatternSub}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupGraphPatternSub(RSPQLParser.GroupGraphPatternSubContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#triplesBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTriplesBlock(RSPQLParser.TriplesBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#graphPatternNotTriples}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphPatternNotTriples(RSPQLParser.GraphPatternNotTriplesContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#optionalGraphPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOptionalGraphPattern(RSPQLParser.OptionalGraphPatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#graphGraphPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphGraphPattern(RSPQLParser.GraphGraphPatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#windowGraphPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowGraphPattern(RSPQLParser.WindowGraphPatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#serviceGraphPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitServiceGraphPattern(RSPQLParser.ServiceGraphPatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#bind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBind(RSPQLParser.BindContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#inlineData}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInlineData(RSPQLParser.InlineDataContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#dataBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataBlock(RSPQLParser.DataBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#inlineDataOneVar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInlineDataOneVar(RSPQLParser.InlineDataOneVarContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#inlineDataFull}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInlineDataFull(RSPQLParser.InlineDataFullContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#dataBlockValues}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataBlockValues(RSPQLParser.DataBlockValuesContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#dataBlockValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataBlockValue(RSPQLParser.DataBlockValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#undef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUndef(RSPQLParser.UndefContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#minusGraphPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinusGraphPattern(RSPQLParser.MinusGraphPatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#groupOrUnionGraphPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupOrUnionGraphPattern(RSPQLParser.GroupOrUnionGraphPatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#filter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilter(RSPQLParser.FilterContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#constraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraint(RSPQLParser.ConstraintContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(RSPQLParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#argList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgList(RSPQLParser.ArgListContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(RSPQLParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#constructTemplate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructTemplate(RSPQLParser.ConstructTemplateContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#constructTriples}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructTriples(RSPQLParser.ConstructTriplesContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#triplesSameSubject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTriplesSameSubject(RSPQLParser.TriplesSameSubjectContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#propertyListNotEmpty}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPropertyListNotEmpty(RSPQLParser.PropertyListNotEmptyContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#propertyList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPropertyList(RSPQLParser.PropertyListContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#verb}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVerb(RSPQLParser.VerbContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(RSPQLParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#objectList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectList(RSPQLParser.ObjectListContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#object}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObject(RSPQLParser.ObjectContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#triplesSameSubjectPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTriplesSameSubjectPath(RSPQLParser.TriplesSameSubjectPathContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#propertyListPathNotEmpty}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPropertyListPathNotEmpty(RSPQLParser.PropertyListPathNotEmptyContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#propertyListPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPropertyListPath(RSPQLParser.PropertyListPathContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#verbPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVerbPath(RSPQLParser.VerbPathContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#verbSimple}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVerbSimple(RSPQLParser.VerbSimpleContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#objectListPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectListPath(RSPQLParser.ObjectListPathContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#objectPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectPath(RSPQLParser.ObjectPathContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#path}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPath(RSPQLParser.PathContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#pathAlternative}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathAlternative(RSPQLParser.PathAlternativeContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#pathSequence}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathSequence(RSPQLParser.PathSequenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#pathElt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathElt(RSPQLParser.PathEltContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#pathEltOrInverse}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathEltOrInverse(RSPQLParser.PathEltOrInverseContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#pathMod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathMod(RSPQLParser.PathModContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#pathPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathPrimary(RSPQLParser.PathPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#pathNegatedPropertySet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathNegatedPropertySet(RSPQLParser.PathNegatedPropertySetContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#pathOneInPropertySet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathOneInPropertySet(RSPQLParser.PathOneInPropertySetContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#integer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger(RSPQLParser.IntegerContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#triplesNode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTriplesNode(RSPQLParser.TriplesNodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#blankNodePropertyList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlankNodePropertyList(RSPQLParser.BlankNodePropertyListContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#triplesNodePath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTriplesNodePath(RSPQLParser.TriplesNodePathContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#blankNodePropertyListPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlankNodePropertyListPath(RSPQLParser.BlankNodePropertyListPathContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#collection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCollection(RSPQLParser.CollectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#collectionPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCollectionPath(RSPQLParser.CollectionPathContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#graphNode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphNode(RSPQLParser.GraphNodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#graphNodePath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphNodePath(RSPQLParser.GraphNodePathContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#varOrTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarOrTerm(RSPQLParser.VarOrTermContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#varOrIri}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarOrIri(RSPQLParser.VarOrIriContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(RSPQLParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#graphTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphTerm(RSPQLParser.GraphTermContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(RSPQLParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#conditionalOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalOrExpression(RSPQLParser.ConditionalOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#conditionalAndExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalAndExpression(RSPQLParser.ConditionalAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#valueLogical}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueLogical(RSPQLParser.ValueLogicalContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#relationalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpression(RSPQLParser.RelationalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#numericExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericExpression(RSPQLParser.NumericExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(RSPQLParser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#multiExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiExpr(RSPQLParser.MultiExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(RSPQLParser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(RSPQLParser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExpression(RSPQLParser.PrimaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#brackettedExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBrackettedExpression(RSPQLParser.BrackettedExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#builtInCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBuiltInCall(RSPQLParser.BuiltInCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#regexExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegexExpression(RSPQLParser.RegexExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#substringExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubstringExpression(RSPQLParser.SubstringExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#strReplaceExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStrReplaceExpression(RSPQLParser.StrReplaceExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#existsFunc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExistsFunc(RSPQLParser.ExistsFuncContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#notExistsFunc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExistsFunc(RSPQLParser.NotExistsFuncContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#aggregate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAggregate(RSPQLParser.AggregateContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#iriOrFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIriOrFunction(RSPQLParser.IriOrFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#rdfliteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRdfliteral(RSPQLParser.RdfliteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#numericLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericLiteral(RSPQLParser.NumericLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#numericLiteralUnsigned}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericLiteralUnsigned(RSPQLParser.NumericLiteralUnsignedContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#numericLiteralPositive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericLiteralPositive(RSPQLParser.NumericLiteralPositiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#numericLiteralNegative}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericLiteralNegative(RSPQLParser.NumericLiteralNegativeContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#booleanLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteral(RSPQLParser.BooleanLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(RSPQLParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#iri}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIri(RSPQLParser.IriContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#prefixedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixedName(RSPQLParser.PrefixedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link RSPQLParser#blankNode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlankNode(RSPQLParser.BlankNodeContext ctx);
}