// Generated from /Users/riccardo/_Projects/RSP/yasper/yasper-core/src/main/java/it/polimi/yasper/core/quering/syntax/RSPQL.g4 by ANTLR 4.7
package org.streamreasoning.rsp4j.api.querying.syntax;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RSPQLParser}.
 */
public interface RSPQLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#queryUnit}.
	 * @param ctx the parse tree
	 */
	void enterQueryUnit(RSPQLParser.QueryUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#queryUnit}.
	 * @param ctx the parse tree
	 */
	void exitQueryUnit(RSPQLParser.QueryUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(RSPQLParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(RSPQLParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#prologue}.
	 * @param ctx the parse tree
	 */
	void enterPrologue(RSPQLParser.PrologueContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#prologue}.
	 * @param ctx the parse tree
	 */
	void exitPrologue(RSPQLParser.PrologueContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#baseDecl}.
	 * @param ctx the parse tree
	 */
	void enterBaseDecl(RSPQLParser.BaseDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#baseDecl}.
	 * @param ctx the parse tree
	 */
	void exitBaseDecl(RSPQLParser.BaseDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#prefixDecl}.
	 * @param ctx the parse tree
	 */
	void enterPrefixDecl(RSPQLParser.PrefixDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#prefixDecl}.
	 * @param ctx the parse tree
	 */
	void exitPrefixDecl(RSPQLParser.PrefixDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#registerClause}.
	 * @param ctx the parse tree
	 */
	void enterRegisterClause(RSPQLParser.RegisterClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#registerClause}.
	 * @param ctx the parse tree
	 */
	void exitRegisterClause(RSPQLParser.RegisterClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#outputStreamType}.
	 * @param ctx the parse tree
	 */
	void enterOutputStreamType(RSPQLParser.OutputStreamTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#outputStreamType}.
	 * @param ctx the parse tree
	 */
	void exitOutputStreamType(RSPQLParser.OutputStreamTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#outputStream}.
	 * @param ctx the parse tree
	 */
	void enterOutputStream(RSPQLParser.OutputStreamContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#outputStream}.
	 * @param ctx the parse tree
	 */
	void exitOutputStream(RSPQLParser.OutputStreamContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#selectQuery}.
	 * @param ctx the parse tree
	 */
	void enterSelectQuery(RSPQLParser.SelectQueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#selectQuery}.
	 * @param ctx the parse tree
	 */
	void exitSelectQuery(RSPQLParser.SelectQueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#subSelect}.
	 * @param ctx the parse tree
	 */
	void enterSubSelect(RSPQLParser.SubSelectContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#subSelect}.
	 * @param ctx the parse tree
	 */
	void exitSubSelect(RSPQLParser.SubSelectContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#selectClause}.
	 * @param ctx the parse tree
	 */
	void enterSelectClause(RSPQLParser.SelectClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#selectClause}.
	 * @param ctx the parse tree
	 */
	void exitSelectClause(RSPQLParser.SelectClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#resultStar}.
	 * @param ctx the parse tree
	 */
	void enterResultStar(RSPQLParser.ResultStarContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#resultStar}.
	 * @param ctx the parse tree
	 */
	void exitResultStar(RSPQLParser.ResultStarContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#resultVar}.
	 * @param ctx the parse tree
	 */
	void enterResultVar(RSPQLParser.ResultVarContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#resultVar}.
	 * @param ctx the parse tree
	 */
	void exitResultVar(RSPQLParser.ResultVarContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#distinct}.
	 * @param ctx the parse tree
	 */
	void enterDistinct(RSPQLParser.DistinctContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#distinct}.
	 * @param ctx the parse tree
	 */
	void exitDistinct(RSPQLParser.DistinctContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#reduced}.
	 * @param ctx the parse tree
	 */
	void enterReduced(RSPQLParser.ReducedContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#reduced}.
	 * @param ctx the parse tree
	 */
	void exitReduced(RSPQLParser.ReducedContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#constructQuery}.
	 * @param ctx the parse tree
	 */
	void enterConstructQuery(RSPQLParser.ConstructQueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#constructQuery}.
	 * @param ctx the parse tree
	 */
	void exitConstructQuery(RSPQLParser.ConstructQueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#describeQuery}.
	 * @param ctx the parse tree
	 */
	void enterDescribeQuery(RSPQLParser.DescribeQueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#describeQuery}.
	 * @param ctx the parse tree
	 */
	void exitDescribeQuery(RSPQLParser.DescribeQueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#askQuery}.
	 * @param ctx the parse tree
	 */
	void enterAskQuery(RSPQLParser.AskQueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#askQuery}.
	 * @param ctx the parse tree
	 */
	void exitAskQuery(RSPQLParser.AskQueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#datasetClause}.
	 * @param ctx the parse tree
	 */
	void enterDatasetClause(RSPQLParser.DatasetClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#datasetClause}.
	 * @param ctx the parse tree
	 */
	void exitDatasetClause(RSPQLParser.DatasetClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#defaultGraphClause}.
	 * @param ctx the parse tree
	 */
	void enterDefaultGraphClause(RSPQLParser.DefaultGraphClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#defaultGraphClause}.
	 * @param ctx the parse tree
	 */
	void exitDefaultGraphClause(RSPQLParser.DefaultGraphClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#namedGraphClause}.
	 * @param ctx the parse tree
	 */
	void enterNamedGraphClause(RSPQLParser.NamedGraphClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#namedGraphClause}.
	 * @param ctx the parse tree
	 */
	void exitNamedGraphClause(RSPQLParser.NamedGraphClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#namedWindowClause}.
	 * @param ctx the parse tree
	 */
	void enterNamedWindowClause(RSPQLParser.NamedWindowClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#namedWindowClause}.
	 * @param ctx the parse tree
	 */
	void exitNamedWindowClause(RSPQLParser.NamedWindowClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#windowUri}.
	 * @param ctx the parse tree
	 */
	void enterWindowUri(RSPQLParser.WindowUriContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#windowUri}.
	 * @param ctx the parse tree
	 */
	void exitWindowUri(RSPQLParser.WindowUriContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#streamUri}.
	 * @param ctx the parse tree
	 */
	void enterStreamUri(RSPQLParser.StreamUriContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#streamUri}.
	 * @param ctx the parse tree
	 */
	void exitStreamUri(RSPQLParser.StreamUriContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#window}.
	 * @param ctx the parse tree
	 */
	void enterWindow(RSPQLParser.WindowContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#window}.
	 * @param ctx the parse tree
	 */
	void exitWindow(RSPQLParser.WindowContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#physicalWindow}.
	 * @param ctx the parse tree
	 */
	void enterPhysicalWindow(RSPQLParser.PhysicalWindowContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#physicalWindow}.
	 * @param ctx the parse tree
	 */
	void exitPhysicalWindow(RSPQLParser.PhysicalWindowContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#physicalRange}.
	 * @param ctx the parse tree
	 */
	void enterPhysicalRange(RSPQLParser.PhysicalRangeContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#physicalRange}.
	 * @param ctx the parse tree
	 */
	void exitPhysicalRange(RSPQLParser.PhysicalRangeContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#physicalStep}.
	 * @param ctx the parse tree
	 */
	void enterPhysicalStep(RSPQLParser.PhysicalStepContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#physicalStep}.
	 * @param ctx the parse tree
	 */
	void exitPhysicalStep(RSPQLParser.PhysicalStepContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#logicalWindow}.
	 * @param ctx the parse tree
	 */
	void enterLogicalWindow(RSPQLParser.LogicalWindowContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#logicalWindow}.
	 * @param ctx the parse tree
	 */
	void exitLogicalWindow(RSPQLParser.LogicalWindowContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#logicalRange}.
	 * @param ctx the parse tree
	 */
	void enterLogicalRange(RSPQLParser.LogicalRangeContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#logicalRange}.
	 * @param ctx the parse tree
	 */
	void exitLogicalRange(RSPQLParser.LogicalRangeContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#logicalStep}.
	 * @param ctx the parse tree
	 */
	void enterLogicalStep(RSPQLParser.LogicalStepContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#logicalStep}.
	 * @param ctx the parse tree
	 */
	void exitLogicalStep(RSPQLParser.LogicalStepContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#duration}.
	 * @param ctx the parse tree
	 */
	void enterDuration(RSPQLParser.DurationContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#duration}.
	 * @param ctx the parse tree
	 */
	void exitDuration(RSPQLParser.DurationContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#sourceSelector}.
	 * @param ctx the parse tree
	 */
	void enterSourceSelector(RSPQLParser.SourceSelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#sourceSelector}.
	 * @param ctx the parse tree
	 */
	void exitSourceSelector(RSPQLParser.SourceSelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void enterWhereClause(RSPQLParser.WhereClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void exitWhereClause(RSPQLParser.WhereClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#solutionModifier}.
	 * @param ctx the parse tree
	 */
	void enterSolutionModifier(RSPQLParser.SolutionModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#solutionModifier}.
	 * @param ctx the parse tree
	 */
	void exitSolutionModifier(RSPQLParser.SolutionModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#groupClause}.
	 * @param ctx the parse tree
	 */
	void enterGroupClause(RSPQLParser.GroupClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#groupClause}.
	 * @param ctx the parse tree
	 */
	void exitGroupClause(RSPQLParser.GroupClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#groupCondition}.
	 * @param ctx the parse tree
	 */
	void enterGroupCondition(RSPQLParser.GroupConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#groupCondition}.
	 * @param ctx the parse tree
	 */
	void exitGroupCondition(RSPQLParser.GroupConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#havingClause}.
	 * @param ctx the parse tree
	 */
	void enterHavingClause(RSPQLParser.HavingClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#havingClause}.
	 * @param ctx the parse tree
	 */
	void exitHavingClause(RSPQLParser.HavingClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#havingCondition}.
	 * @param ctx the parse tree
	 */
	void enterHavingCondition(RSPQLParser.HavingConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#havingCondition}.
	 * @param ctx the parse tree
	 */
	void exitHavingCondition(RSPQLParser.HavingConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#orderClause}.
	 * @param ctx the parse tree
	 */
	void enterOrderClause(RSPQLParser.OrderClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#orderClause}.
	 * @param ctx the parse tree
	 */
	void exitOrderClause(RSPQLParser.OrderClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#orderCondition}.
	 * @param ctx the parse tree
	 */
	void enterOrderCondition(RSPQLParser.OrderConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#orderCondition}.
	 * @param ctx the parse tree
	 */
	void exitOrderCondition(RSPQLParser.OrderConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#limitOffsetClauses}.
	 * @param ctx the parse tree
	 */
	void enterLimitOffsetClauses(RSPQLParser.LimitOffsetClausesContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#limitOffsetClauses}.
	 * @param ctx the parse tree
	 */
	void exitLimitOffsetClauses(RSPQLParser.LimitOffsetClausesContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#limitClause}.
	 * @param ctx the parse tree
	 */
	void enterLimitClause(RSPQLParser.LimitClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#limitClause}.
	 * @param ctx the parse tree
	 */
	void exitLimitClause(RSPQLParser.LimitClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#offsetClause}.
	 * @param ctx the parse tree
	 */
	void enterOffsetClause(RSPQLParser.OffsetClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#offsetClause}.
	 * @param ctx the parse tree
	 */
	void exitOffsetClause(RSPQLParser.OffsetClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#valuesClause}.
	 * @param ctx the parse tree
	 */
	void enterValuesClause(RSPQLParser.ValuesClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#valuesClause}.
	 * @param ctx the parse tree
	 */
	void exitValuesClause(RSPQLParser.ValuesClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#graphOrDefault}.
	 * @param ctx the parse tree
	 */
	void enterGraphOrDefault(RSPQLParser.GraphOrDefaultContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#graphOrDefault}.
	 * @param ctx the parse tree
	 */
	void exitGraphOrDefault(RSPQLParser.GraphOrDefaultContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#graphRef}.
	 * @param ctx the parse tree
	 */
	void enterGraphRef(RSPQLParser.GraphRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#graphRef}.
	 * @param ctx the parse tree
	 */
	void exitGraphRef(RSPQLParser.GraphRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#quadPattern}.
	 * @param ctx the parse tree
	 */
	void enterQuadPattern(RSPQLParser.QuadPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#quadPattern}.
	 * @param ctx the parse tree
	 */
	void exitQuadPattern(RSPQLParser.QuadPatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#quads}.
	 * @param ctx the parse tree
	 */
	void enterQuads(RSPQLParser.QuadsContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#quads}.
	 * @param ctx the parse tree
	 */
	void exitQuads(RSPQLParser.QuadsContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#quadsNotTriples}.
	 * @param ctx the parse tree
	 */
	void enterQuadsNotTriples(RSPQLParser.QuadsNotTriplesContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#quadsNotTriples}.
	 * @param ctx the parse tree
	 */
	void exitQuadsNotTriples(RSPQLParser.QuadsNotTriplesContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#triplesTemplate}.
	 * @param ctx the parse tree
	 */
	void enterTriplesTemplate(RSPQLParser.TriplesTemplateContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#triplesTemplate}.
	 * @param ctx the parse tree
	 */
	void exitTriplesTemplate(RSPQLParser.TriplesTemplateContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#groupGraphPattern}.
	 * @param ctx the parse tree
	 */
	void enterGroupGraphPattern(RSPQLParser.GroupGraphPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#groupGraphPattern}.
	 * @param ctx the parse tree
	 */
	void exitGroupGraphPattern(RSPQLParser.GroupGraphPatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#groupGraphPatternSub}.
	 * @param ctx the parse tree
	 */
	void enterGroupGraphPatternSub(RSPQLParser.GroupGraphPatternSubContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#groupGraphPatternSub}.
	 * @param ctx the parse tree
	 */
	void exitGroupGraphPatternSub(RSPQLParser.GroupGraphPatternSubContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#triplesBlock}.
	 * @param ctx the parse tree
	 */
	void enterTriplesBlock(RSPQLParser.TriplesBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#triplesBlock}.
	 * @param ctx the parse tree
	 */
	void exitTriplesBlock(RSPQLParser.TriplesBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#graphPatternNotTriples}.
	 * @param ctx the parse tree
	 */
	void enterGraphPatternNotTriples(RSPQLParser.GraphPatternNotTriplesContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#graphPatternNotTriples}.
	 * @param ctx the parse tree
	 */
	void exitGraphPatternNotTriples(RSPQLParser.GraphPatternNotTriplesContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#optionalGraphPattern}.
	 * @param ctx the parse tree
	 */
	void enterOptionalGraphPattern(RSPQLParser.OptionalGraphPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#optionalGraphPattern}.
	 * @param ctx the parse tree
	 */
	void exitOptionalGraphPattern(RSPQLParser.OptionalGraphPatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#graphGraphPattern}.
	 * @param ctx the parse tree
	 */
	void enterGraphGraphPattern(RSPQLParser.GraphGraphPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#graphGraphPattern}.
	 * @param ctx the parse tree
	 */
	void exitGraphGraphPattern(RSPQLParser.GraphGraphPatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#windowGraphPattern}.
	 * @param ctx the parse tree
	 */
	void enterWindowGraphPattern(RSPQLParser.WindowGraphPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#windowGraphPattern}.
	 * @param ctx the parse tree
	 */
	void exitWindowGraphPattern(RSPQLParser.WindowGraphPatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#serviceGraphPattern}.
	 * @param ctx the parse tree
	 */
	void enterServiceGraphPattern(RSPQLParser.ServiceGraphPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#serviceGraphPattern}.
	 * @param ctx the parse tree
	 */
	void exitServiceGraphPattern(RSPQLParser.ServiceGraphPatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#bind}.
	 * @param ctx the parse tree
	 */
	void enterBind(RSPQLParser.BindContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#bind}.
	 * @param ctx the parse tree
	 */
	void exitBind(RSPQLParser.BindContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#inlineData}.
	 * @param ctx the parse tree
	 */
	void enterInlineData(RSPQLParser.InlineDataContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#inlineData}.
	 * @param ctx the parse tree
	 */
	void exitInlineData(RSPQLParser.InlineDataContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#dataBlock}.
	 * @param ctx the parse tree
	 */
	void enterDataBlock(RSPQLParser.DataBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#dataBlock}.
	 * @param ctx the parse tree
	 */
	void exitDataBlock(RSPQLParser.DataBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#inlineDataOneVar}.
	 * @param ctx the parse tree
	 */
	void enterInlineDataOneVar(RSPQLParser.InlineDataOneVarContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#inlineDataOneVar}.
	 * @param ctx the parse tree
	 */
	void exitInlineDataOneVar(RSPQLParser.InlineDataOneVarContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#inlineDataFull}.
	 * @param ctx the parse tree
	 */
	void enterInlineDataFull(RSPQLParser.InlineDataFullContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#inlineDataFull}.
	 * @param ctx the parse tree
	 */
	void exitInlineDataFull(RSPQLParser.InlineDataFullContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#dataBlockValues}.
	 * @param ctx the parse tree
	 */
	void enterDataBlockValues(RSPQLParser.DataBlockValuesContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#dataBlockValues}.
	 * @param ctx the parse tree
	 */
	void exitDataBlockValues(RSPQLParser.DataBlockValuesContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#dataBlockValue}.
	 * @param ctx the parse tree
	 */
	void enterDataBlockValue(RSPQLParser.DataBlockValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#dataBlockValue}.
	 * @param ctx the parse tree
	 */
	void exitDataBlockValue(RSPQLParser.DataBlockValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#undef}.
	 * @param ctx the parse tree
	 */
	void enterUndef(RSPQLParser.UndefContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#undef}.
	 * @param ctx the parse tree
	 */
	void exitUndef(RSPQLParser.UndefContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#minusGraphPattern}.
	 * @param ctx the parse tree
	 */
	void enterMinusGraphPattern(RSPQLParser.MinusGraphPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#minusGraphPattern}.
	 * @param ctx the parse tree
	 */
	void exitMinusGraphPattern(RSPQLParser.MinusGraphPatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#groupOrUnionGraphPattern}.
	 * @param ctx the parse tree
	 */
	void enterGroupOrUnionGraphPattern(RSPQLParser.GroupOrUnionGraphPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#groupOrUnionGraphPattern}.
	 * @param ctx the parse tree
	 */
	void exitGroupOrUnionGraphPattern(RSPQLParser.GroupOrUnionGraphPatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#filter}.
	 * @param ctx the parse tree
	 */
	void enterFilter(RSPQLParser.FilterContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#filter}.
	 * @param ctx the parse tree
	 */
	void exitFilter(RSPQLParser.FilterContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#constraint}.
	 * @param ctx the parse tree
	 */
	void enterConstraint(RSPQLParser.ConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#constraint}.
	 * @param ctx the parse tree
	 */
	void exitConstraint(RSPQLParser.ConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(RSPQLParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(RSPQLParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#argList}.
	 * @param ctx the parse tree
	 */
	void enterArgList(RSPQLParser.ArgListContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#argList}.
	 * @param ctx the parse tree
	 */
	void exitArgList(RSPQLParser.ArgListContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(RSPQLParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(RSPQLParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#constructTemplate}.
	 * @param ctx the parse tree
	 */
	void enterConstructTemplate(RSPQLParser.ConstructTemplateContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#constructTemplate}.
	 * @param ctx the parse tree
	 */
	void exitConstructTemplate(RSPQLParser.ConstructTemplateContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#constructTriples}.
	 * @param ctx the parse tree
	 */
	void enterConstructTriples(RSPQLParser.ConstructTriplesContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#constructTriples}.
	 * @param ctx the parse tree
	 */
	void exitConstructTriples(RSPQLParser.ConstructTriplesContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#triplesSameSubject}.
	 * @param ctx the parse tree
	 */
	void enterTriplesSameSubject(RSPQLParser.TriplesSameSubjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#triplesSameSubject}.
	 * @param ctx the parse tree
	 */
	void exitTriplesSameSubject(RSPQLParser.TriplesSameSubjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#propertyListNotEmpty}.
	 * @param ctx the parse tree
	 */
	void enterPropertyListNotEmpty(RSPQLParser.PropertyListNotEmptyContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#propertyListNotEmpty}.
	 * @param ctx the parse tree
	 */
	void exitPropertyListNotEmpty(RSPQLParser.PropertyListNotEmptyContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#propertyList}.
	 * @param ctx the parse tree
	 */
	void enterPropertyList(RSPQLParser.PropertyListContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#propertyList}.
	 * @param ctx the parse tree
	 */
	void exitPropertyList(RSPQLParser.PropertyListContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#verb}.
	 * @param ctx the parse tree
	 */
	void enterVerb(RSPQLParser.VerbContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#verb}.
	 * @param ctx the parse tree
	 */
	void exitVerb(RSPQLParser.VerbContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(RSPQLParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(RSPQLParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#objectList}.
	 * @param ctx the parse tree
	 */
	void enterObjectList(RSPQLParser.ObjectListContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#objectList}.
	 * @param ctx the parse tree
	 */
	void exitObjectList(RSPQLParser.ObjectListContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#object}.
	 * @param ctx the parse tree
	 */
	void enterObject(RSPQLParser.ObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#object}.
	 * @param ctx the parse tree
	 */
	void exitObject(RSPQLParser.ObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#triplesSameSubjectPath}.
	 * @param ctx the parse tree
	 */
	void enterTriplesSameSubjectPath(RSPQLParser.TriplesSameSubjectPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#triplesSameSubjectPath}.
	 * @param ctx the parse tree
	 */
	void exitTriplesSameSubjectPath(RSPQLParser.TriplesSameSubjectPathContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#propertyListPathNotEmpty}.
	 * @param ctx the parse tree
	 */
	void enterPropertyListPathNotEmpty(RSPQLParser.PropertyListPathNotEmptyContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#propertyListPathNotEmpty}.
	 * @param ctx the parse tree
	 */
	void exitPropertyListPathNotEmpty(RSPQLParser.PropertyListPathNotEmptyContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#propertyListPath}.
	 * @param ctx the parse tree
	 */
	void enterPropertyListPath(RSPQLParser.PropertyListPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#propertyListPath}.
	 * @param ctx the parse tree
	 */
	void exitPropertyListPath(RSPQLParser.PropertyListPathContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#verbPath}.
	 * @param ctx the parse tree
	 */
	void enterVerbPath(RSPQLParser.VerbPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#verbPath}.
	 * @param ctx the parse tree
	 */
	void exitVerbPath(RSPQLParser.VerbPathContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#verbSimple}.
	 * @param ctx the parse tree
	 */
	void enterVerbSimple(RSPQLParser.VerbSimpleContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#verbSimple}.
	 * @param ctx the parse tree
	 */
	void exitVerbSimple(RSPQLParser.VerbSimpleContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#objectListPath}.
	 * @param ctx the parse tree
	 */
	void enterObjectListPath(RSPQLParser.ObjectListPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#objectListPath}.
	 * @param ctx the parse tree
	 */
	void exitObjectListPath(RSPQLParser.ObjectListPathContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#objectPath}.
	 * @param ctx the parse tree
	 */
	void enterObjectPath(RSPQLParser.ObjectPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#objectPath}.
	 * @param ctx the parse tree
	 */
	void exitObjectPath(RSPQLParser.ObjectPathContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#path}.
	 * @param ctx the parse tree
	 */
	void enterPath(RSPQLParser.PathContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#path}.
	 * @param ctx the parse tree
	 */
	void exitPath(RSPQLParser.PathContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#pathAlternative}.
	 * @param ctx the parse tree
	 */
	void enterPathAlternative(RSPQLParser.PathAlternativeContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#pathAlternative}.
	 * @param ctx the parse tree
	 */
	void exitPathAlternative(RSPQLParser.PathAlternativeContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#pathSequence}.
	 * @param ctx the parse tree
	 */
	void enterPathSequence(RSPQLParser.PathSequenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#pathSequence}.
	 * @param ctx the parse tree
	 */
	void exitPathSequence(RSPQLParser.PathSequenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#pathElt}.
	 * @param ctx the parse tree
	 */
	void enterPathElt(RSPQLParser.PathEltContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#pathElt}.
	 * @param ctx the parse tree
	 */
	void exitPathElt(RSPQLParser.PathEltContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#pathEltOrInverse}.
	 * @param ctx the parse tree
	 */
	void enterPathEltOrInverse(RSPQLParser.PathEltOrInverseContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#pathEltOrInverse}.
	 * @param ctx the parse tree
	 */
	void exitPathEltOrInverse(RSPQLParser.PathEltOrInverseContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#pathMod}.
	 * @param ctx the parse tree
	 */
	void enterPathMod(RSPQLParser.PathModContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#pathMod}.
	 * @param ctx the parse tree
	 */
	void exitPathMod(RSPQLParser.PathModContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#pathPrimary}.
	 * @param ctx the parse tree
	 */
	void enterPathPrimary(RSPQLParser.PathPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#pathPrimary}.
	 * @param ctx the parse tree
	 */
	void exitPathPrimary(RSPQLParser.PathPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#pathNegatedPropertySet}.
	 * @param ctx the parse tree
	 */
	void enterPathNegatedPropertySet(RSPQLParser.PathNegatedPropertySetContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#pathNegatedPropertySet}.
	 * @param ctx the parse tree
	 */
	void exitPathNegatedPropertySet(RSPQLParser.PathNegatedPropertySetContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#pathOneInPropertySet}.
	 * @param ctx the parse tree
	 */
	void enterPathOneInPropertySet(RSPQLParser.PathOneInPropertySetContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#pathOneInPropertySet}.
	 * @param ctx the parse tree
	 */
	void exitPathOneInPropertySet(RSPQLParser.PathOneInPropertySetContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#integer}.
	 * @param ctx the parse tree
	 */
	void enterInteger(RSPQLParser.IntegerContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#integer}.
	 * @param ctx the parse tree
	 */
	void exitInteger(RSPQLParser.IntegerContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#triplesNode}.
	 * @param ctx the parse tree
	 */
	void enterTriplesNode(RSPQLParser.TriplesNodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#triplesNode}.
	 * @param ctx the parse tree
	 */
	void exitTriplesNode(RSPQLParser.TriplesNodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#blankNodePropertyList}.
	 * @param ctx the parse tree
	 */
	void enterBlankNodePropertyList(RSPQLParser.BlankNodePropertyListContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#blankNodePropertyList}.
	 * @param ctx the parse tree
	 */
	void exitBlankNodePropertyList(RSPQLParser.BlankNodePropertyListContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#triplesNodePath}.
	 * @param ctx the parse tree
	 */
	void enterTriplesNodePath(RSPQLParser.TriplesNodePathContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#triplesNodePath}.
	 * @param ctx the parse tree
	 */
	void exitTriplesNodePath(RSPQLParser.TriplesNodePathContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#blankNodePropertyListPath}.
	 * @param ctx the parse tree
	 */
	void enterBlankNodePropertyListPath(RSPQLParser.BlankNodePropertyListPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#blankNodePropertyListPath}.
	 * @param ctx the parse tree
	 */
	void exitBlankNodePropertyListPath(RSPQLParser.BlankNodePropertyListPathContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#collection}.
	 * @param ctx the parse tree
	 */
	void enterCollection(RSPQLParser.CollectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#collection}.
	 * @param ctx the parse tree
	 */
	void exitCollection(RSPQLParser.CollectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#collectionPath}.
	 * @param ctx the parse tree
	 */
	void enterCollectionPath(RSPQLParser.CollectionPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#collectionPath}.
	 * @param ctx the parse tree
	 */
	void exitCollectionPath(RSPQLParser.CollectionPathContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#graphNode}.
	 * @param ctx the parse tree
	 */
	void enterGraphNode(RSPQLParser.GraphNodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#graphNode}.
	 * @param ctx the parse tree
	 */
	void exitGraphNode(RSPQLParser.GraphNodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#graphNodePath}.
	 * @param ctx the parse tree
	 */
	void enterGraphNodePath(RSPQLParser.GraphNodePathContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#graphNodePath}.
	 * @param ctx the parse tree
	 */
	void exitGraphNodePath(RSPQLParser.GraphNodePathContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#varOrTerm}.
	 * @param ctx the parse tree
	 */
	void enterVarOrTerm(RSPQLParser.VarOrTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#varOrTerm}.
	 * @param ctx the parse tree
	 */
	void exitVarOrTerm(RSPQLParser.VarOrTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#varOrIri}.
	 * @param ctx the parse tree
	 */
	void enterVarOrIri(RSPQLParser.VarOrIriContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#varOrIri}.
	 * @param ctx the parse tree
	 */
	void exitVarOrIri(RSPQLParser.VarOrIriContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(RSPQLParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(RSPQLParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#graphTerm}.
	 * @param ctx the parse tree
	 */
	void enterGraphTerm(RSPQLParser.GraphTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#graphTerm}.
	 * @param ctx the parse tree
	 */
	void exitGraphTerm(RSPQLParser.GraphTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(RSPQLParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(RSPQLParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#conditionalOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalOrExpression(RSPQLParser.ConditionalOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#conditionalOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalOrExpression(RSPQLParser.ConditionalOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#conditionalAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalAndExpression(RSPQLParser.ConditionalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#conditionalAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalAndExpression(RSPQLParser.ConditionalAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#valueLogical}.
	 * @param ctx the parse tree
	 */
	void enterValueLogical(RSPQLParser.ValueLogicalContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#valueLogical}.
	 * @param ctx the parse tree
	 */
	void exitValueLogical(RSPQLParser.ValueLogicalContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpression(RSPQLParser.RelationalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpression(RSPQLParser.RelationalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#numericExpression}.
	 * @param ctx the parse tree
	 */
	void enterNumericExpression(RSPQLParser.NumericExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#numericExpression}.
	 * @param ctx the parse tree
	 */
	void exitNumericExpression(RSPQLParser.NumericExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(RSPQLParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(RSPQLParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#multiExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultiExpr(RSPQLParser.MultiExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#multiExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultiExpr(RSPQLParser.MultiExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(RSPQLParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(RSPQLParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(RSPQLParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(RSPQLParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpression(RSPQLParser.PrimaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpression(RSPQLParser.PrimaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#brackettedExpression}.
	 * @param ctx the parse tree
	 */
	void enterBrackettedExpression(RSPQLParser.BrackettedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#brackettedExpression}.
	 * @param ctx the parse tree
	 */
	void exitBrackettedExpression(RSPQLParser.BrackettedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#builtInCall}.
	 * @param ctx the parse tree
	 */
	void enterBuiltInCall(RSPQLParser.BuiltInCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#builtInCall}.
	 * @param ctx the parse tree
	 */
	void exitBuiltInCall(RSPQLParser.BuiltInCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#regexExpression}.
	 * @param ctx the parse tree
	 */
	void enterRegexExpression(RSPQLParser.RegexExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#regexExpression}.
	 * @param ctx the parse tree
	 */
	void exitRegexExpression(RSPQLParser.RegexExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#substringExpression}.
	 * @param ctx the parse tree
	 */
	void enterSubstringExpression(RSPQLParser.SubstringExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#substringExpression}.
	 * @param ctx the parse tree
	 */
	void exitSubstringExpression(RSPQLParser.SubstringExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#strReplaceExpression}.
	 * @param ctx the parse tree
	 */
	void enterStrReplaceExpression(RSPQLParser.StrReplaceExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#strReplaceExpression}.
	 * @param ctx the parse tree
	 */
	void exitStrReplaceExpression(RSPQLParser.StrReplaceExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#existsFunc}.
	 * @param ctx the parse tree
	 */
	void enterExistsFunc(RSPQLParser.ExistsFuncContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#existsFunc}.
	 * @param ctx the parse tree
	 */
	void exitExistsFunc(RSPQLParser.ExistsFuncContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#notExistsFunc}.
	 * @param ctx the parse tree
	 */
	void enterNotExistsFunc(RSPQLParser.NotExistsFuncContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#notExistsFunc}.
	 * @param ctx the parse tree
	 */
	void exitNotExistsFunc(RSPQLParser.NotExistsFuncContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#aggregate}.
	 * @param ctx the parse tree
	 */
	void enterAggregate(RSPQLParser.AggregateContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#aggregate}.
	 * @param ctx the parse tree
	 */
	void exitAggregate(RSPQLParser.AggregateContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#iriOrFunction}.
	 * @param ctx the parse tree
	 */
	void enterIriOrFunction(RSPQLParser.IriOrFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#iriOrFunction}.
	 * @param ctx the parse tree
	 */
	void exitIriOrFunction(RSPQLParser.IriOrFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#rdfliteral}.
	 * @param ctx the parse tree
	 */
	void enterRdfliteral(RSPQLParser.RdfliteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#rdfliteral}.
	 * @param ctx the parse tree
	 */
	void exitRdfliteral(RSPQLParser.RdfliteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#numericLiteral}.
	 * @param ctx the parse tree
	 */
	void enterNumericLiteral(RSPQLParser.NumericLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#numericLiteral}.
	 * @param ctx the parse tree
	 */
	void exitNumericLiteral(RSPQLParser.NumericLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#numericLiteralUnsigned}.
	 * @param ctx the parse tree
	 */
	void enterNumericLiteralUnsigned(RSPQLParser.NumericLiteralUnsignedContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#numericLiteralUnsigned}.
	 * @param ctx the parse tree
	 */
	void exitNumericLiteralUnsigned(RSPQLParser.NumericLiteralUnsignedContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#numericLiteralPositive}.
	 * @param ctx the parse tree
	 */
	void enterNumericLiteralPositive(RSPQLParser.NumericLiteralPositiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#numericLiteralPositive}.
	 * @param ctx the parse tree
	 */
	void exitNumericLiteralPositive(RSPQLParser.NumericLiteralPositiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#numericLiteralNegative}.
	 * @param ctx the parse tree
	 */
	void enterNumericLiteralNegative(RSPQLParser.NumericLiteralNegativeContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#numericLiteralNegative}.
	 * @param ctx the parse tree
	 */
	void exitNumericLiteralNegative(RSPQLParser.NumericLiteralNegativeContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(RSPQLParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(RSPQLParser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(RSPQLParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(RSPQLParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#iri}.
	 * @param ctx the parse tree
	 */
	void enterIri(RSPQLParser.IriContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#iri}.
	 * @param ctx the parse tree
	 */
	void exitIri(RSPQLParser.IriContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#prefixedName}.
	 * @param ctx the parse tree
	 */
	void enterPrefixedName(RSPQLParser.PrefixedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#prefixedName}.
	 * @param ctx the parse tree
	 */
	void exitPrefixedName(RSPQLParser.PrefixedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link RSPQLParser#blankNode}.
	 * @param ctx the parse tree
	 */
	void enterBlankNode(RSPQLParser.BlankNodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link RSPQLParser#blankNode}.
	 * @param ctx the parse tree
	 */
	void exitBlankNode(RSPQLParser.BlankNodeContext ctx);
}