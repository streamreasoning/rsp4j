// Generated from RSPQL.g4 by ANTLR 4.7
package it.polimi.yasper.core.quering.syntax;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class RSPQLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, T__53=54, T__54=55, T__55=56, T__56=57, T__57=58, T__58=59, 
		T__59=60, T__60=61, T__61=62, T__62=63, T__63=64, T__64=65, T__65=66, 
		T__66=67, T__67=68, T__68=69, T__69=70, T__70=71, T__71=72, T__72=73, 
		T__73=74, T__74=75, T__75=76, T__76=77, T__77=78, T__78=79, T__79=80, 
		T__80=81, T__81=82, T__82=83, T__83=84, T__84=85, T__85=86, T__86=87, 
		T__87=88, T__88=89, T__89=90, T__90=91, T__91=92, T__92=93, T__93=94, 
		T__94=95, T__95=96, T__96=97, T__97=98, T__98=99, T__99=100, T__100=101, 
		T__101=102, T__102=103, T__103=104, T__104=105, T__105=106, T__106=107, 
		T__107=108, T__108=109, T__109=110, T__110=111, T__111=112, T__112=113, 
		T__113=114, T__114=115, T__115=116, T__116=117, T__117=118, T__118=119, 
		T__119=120, T__120=121, T__121=122, T__122=123, T__123=124, T__124=125, 
		T__125=126, T__126=127, T__127=128, T__128=129, T__129=130, T__130=131, 
		TYPE=132, COMMENT=133, DURATION=134, IRIREF=135, PNAME_NS=136, PNAME_LN=137, 
		BLANK_NODE_LABEL=138, VAR1=139, VAR2=140, LANGTAG=141, INTEGER=142, DECIMAL=143, 
		DOUBLE=144, INTEGER_POSITIVE=145, DECIMAL_POSITIVE=146, DOUBLE_POSITIVE=147, 
		INTEGER_NEGATIVE=148, DECIMAL_NEGATIVE=149, DOUBLE_NEGATIVE=150, EXPONENT=151, 
		STRING_LITERAL1=152, STRING_LITERAL2=153, STRING_LITERAL_LONG1=154, STRING_LITERAL_LONG2=155, 
		ECHAR=156, NIL=157, WS=158, ANON=159, PN_CHARS_BASE=160, PN_CHARS_U=161, 
		VARNAME=162, PN_CHARS=163, PN_PREFIX=164, PN_LOCAL=165, PLX=166, PERCENT=167, 
		HEX=168, PN_LOCAL_ESC=169, ANYCHAR=170;
	public static final int
		RULE_queryUnit = 0, RULE_query = 1, RULE_prologue = 2, RULE_baseDecl = 3, 
		RULE_prefixDecl = 4, RULE_registerClause = 5, RULE_outputStreamType = 6, 
		RULE_outputStream = 7, RULE_selectQuery = 8, RULE_subSelect = 9, RULE_selectClause = 10, 
		RULE_resultStar = 11, RULE_resultVar = 12, RULE_distinct = 13, RULE_reduced = 14, 
		RULE_constructQuery = 15, RULE_describeQuery = 16, RULE_askQuery = 17, 
		RULE_datasetClause = 18, RULE_defaultGraphClause = 19, RULE_namedGraphClause = 20, 
		RULE_namedWindowClause = 21, RULE_windowUri = 22, RULE_streamUri = 23, 
		RULE_window = 24, RULE_physicalWindow = 25, RULE_physicalRange = 26, RULE_physicalStep = 27, 
		RULE_logicalWindow = 28, RULE_logicalRange = 29, RULE_logicalStep = 30, 
		RULE_duration = 31, RULE_sourceSelector = 32, RULE_whereClause = 33, RULE_solutionModifier = 34, 
		RULE_groupClause = 35, RULE_groupCondition = 36, RULE_havingClause = 37, 
		RULE_havingCondition = 38, RULE_orderClause = 39, RULE_orderCondition = 40, 
		RULE_limitOffsetClauses = 41, RULE_limitClause = 42, RULE_offsetClause = 43, 
		RULE_valuesClause = 44, RULE_graphOrDefault = 45, RULE_graphRef = 46, 
		RULE_quadPattern = 47, RULE_quads = 48, RULE_quadsNotTriples = 49, RULE_triplesTemplate = 50, 
		RULE_groupGraphPattern = 51, RULE_groupGraphPatternSub = 52, RULE_triplesBlock = 53, 
		RULE_graphPatternNotTriples = 54, RULE_optionalGraphPattern = 55, RULE_graphGraphPattern = 56, 
		RULE_windowGraphPattern = 57, RULE_serviceGraphPattern = 58, RULE_bind = 59, 
		RULE_inlineData = 60, RULE_dataBlock = 61, RULE_inlineDataOneVar = 62, 
		RULE_inlineDataFull = 63, RULE_dataBlockValues = 64, RULE_dataBlockValue = 65, 
		RULE_undef = 66, RULE_minusGraphPattern = 67, RULE_groupOrUnionGraphPattern = 68, 
		RULE_filter = 69, RULE_constraint = 70, RULE_functionCall = 71, RULE_argList = 72, 
		RULE_expressionList = 73, RULE_constructTemplate = 74, RULE_constructTriples = 75, 
		RULE_triplesSameSubject = 76, RULE_propertyListNotEmpty = 77, RULE_propertyList = 78, 
		RULE_verb = 79, RULE_type = 80, RULE_objectList = 81, RULE_object = 82, 
		RULE_triplesSameSubjectPath = 83, RULE_propertyListPathNotEmpty = 84, 
		RULE_propertyListPath = 85, RULE_verbPath = 86, RULE_verbSimple = 87, 
		RULE_objectListPath = 88, RULE_objectPath = 89, RULE_path = 90, RULE_pathAlternative = 91, 
		RULE_pathSequence = 92, RULE_pathElt = 93, RULE_pathEltOrInverse = 94, 
		RULE_pathMod = 95, RULE_pathPrimary = 96, RULE_pathNegatedPropertySet = 97, 
		RULE_pathOneInPropertySet = 98, RULE_integer = 99, RULE_triplesNode = 100, 
		RULE_blankNodePropertyList = 101, RULE_triplesNodePath = 102, RULE_blankNodePropertyListPath = 103, 
		RULE_collection = 104, RULE_collectionPath = 105, RULE_graphNode = 106, 
		RULE_graphNodePath = 107, RULE_varOrTerm = 108, RULE_varOrIri = 109, RULE_var = 110, 
		RULE_graphTerm = 111, RULE_expression = 112, RULE_conditionalOrExpression = 113, 
		RULE_conditionalAndExpression = 114, RULE_valueLogical = 115, RULE_relationalExpression = 116, 
		RULE_numericExpression = 117, RULE_additiveExpression = 118, RULE_multiExpr = 119, 
		RULE_multiplicativeExpression = 120, RULE_unaryExpression = 121, RULE_primaryExpression = 122, 
		RULE_brackettedExpression = 123, RULE_builtInCall = 124, RULE_regexExpression = 125, 
		RULE_substringExpression = 126, RULE_strReplaceExpression = 127, RULE_existsFunc = 128, 
		RULE_notExistsFunc = 129, RULE_aggregate = 130, RULE_iriOrFunction = 131, 
		RULE_rdfliteral = 132, RULE_numericLiteral = 133, RULE_numericLiteralUnsigned = 134, 
		RULE_numericLiteralPositive = 135, RULE_numericLiteralNegative = 136, 
		RULE_booleanLiteral = 137, RULE_string = 138, RULE_iri = 139, RULE_prefixedName = 140, 
		RULE_blankNode = 141;
	public static final String[] ruleNames = {
		"queryUnit", "query", "prologue", "baseDecl", "prefixDecl", "registerClause", 
		"outputStreamType", "outputStream", "selectQuery", "subSelect", "selectClause", 
		"resultStar", "resultVar", "distinct", "reduced", "constructQuery", "describeQuery", 
		"askQuery", "datasetClause", "defaultGraphClause", "namedGraphClause", 
		"namedWindowClause", "windowUri", "streamUri", "window", "physicalWindow", 
		"physicalRange", "physicalStep", "logicalWindow", "logicalRange", "logicalStep", 
		"duration", "sourceSelector", "whereClause", "solutionModifier", "groupClause", 
		"groupCondition", "havingClause", "havingCondition", "orderClause", "orderCondition", 
		"limitOffsetClauses", "limitClause", "offsetClause", "valuesClause", "graphOrDefault", 
		"graphRef", "quadPattern", "quads", "quadsNotTriples", "triplesTemplate", 
		"groupGraphPattern", "groupGraphPatternSub", "triplesBlock", "graphPatternNotTriples", 
		"optionalGraphPattern", "graphGraphPattern", "windowGraphPattern", "serviceGraphPattern", 
		"bindTVG", "inlineData", "dataBlock", "inlineDataOneVar", "inlineDataFull",
		"dataBlockValues", "dataBlockValue", "undef", "minusGraphPattern", "groupOrUnionGraphPattern", 
		"filter", "constraint", "functionCall", "argList", "expressionList", "constructTemplate", 
		"constructTriples", "triplesSameSubject", "propertyListNotEmpty", "propertyList", 
		"verb", "type", "objectList", "object", "triplesSameSubjectPath", "propertyListPathNotEmpty", 
		"propertyListPath", "verbPath", "verbSimple", "objectListPath", "objectPath", 
		"path", "pathAlternative", "pathSequence", "pathElt", "pathEltOrInverse", 
		"pathMod", "pathPrimary", "pathNegatedPropertySet", "pathOneInPropertySet", 
		"integer", "triplesNode", "blankNodePropertyList", "triplesNodePath", 
		"blankNodePropertyListPath", "collection", "collectionPath", "graphNode", 
		"graphNodePath", "varOrTerm", "varOrIri", "var", "graphTerm", "expression", 
		"conditionalOrExpression", "conditionalAndExpression", "valueLogical", 
		"relationalExpression", "numericExpression", "additiveExpression", "multiExpr", 
		"multiplicativeExpression", "unaryExpression", "primaryExpression", "brackettedExpression", 
		"builtInCall", "regexExpression", "substringExpression", "strReplaceExpression", 
		"existsFunc", "notExistsFunc", "aggregate", "iriOrFunction", "rdfliteral", 
		"numericLiteral", "numericLiteralUnsigned", "numericLiteralPositive", 
		"numericLiteralNegative", "booleanLiteral", "string", "iri", "prefixedName", 
		"blankNode"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'BASE'", "'PREFIX'", "'REGISTER'", "'AS'", "'ISTREAM'", "'RSTREAM'", 
		"'DSTREAM'", "'SELECT'", "'*'", "'('", "')'", "'DISTINCT'", "'REDUCED'", 
		"'CONSTRUCT'", "'WHERE'", "'{'", "'}'", "'DESCRIBE'", "'ASK'", "'FROM'", 
		"'NAMED'", "'WINDOW'", "'ON'", "'['", "']'", "'ELEMENTS'", "'STEP'", "'RANGE'", 
		"'GROUP'", "'BY'", "'HAVING'", "'ORDER'", "'ASC'", "'DESC'", "'LIMIT'", 
		"'OFFSET'", "'VALUES'", "'DEFAULT'", "'GRAPH'", "'.'", "'OPTIONAL'", "'SERVICE'", 
		"'SILENT'", "'BIND'", "'UNDEF'", "'MINUS'", "'UNION'", "'FILTER'", "','", 
		"';'", "'|'", "'/'", "'^'", "'?'", "'+'", "'!'", "'||'", "'&&'", "'='", 
		"'!='", "'<'", "'>'", "'<='", "'>='", "'IN'", "'NOT'", "'-'", "'STR'", 
		"'LANG'", "'LANGMATCHES'", "'DATATYPE'", "'BOUND'", "'IRI'", "'URI'", 
		"'BNODE'", "'RAND'", "'ABS'", "'CEIL'", "'FLOOR'", "'ROUND'", "'CONCAT'", 
		"'STRLEN'", "'UCASE'", "'LCASE'", "'ENCODE_FOR_URI'", "'CONTAINS'", "'STRSTARTS'", 
		"'STRENDS'", "'STRBEFORE'", "'STRAFTER'", "'YEAR'", "'MONTH'", "'DAY'", 
		"'HOURS'", "'MINUTES'", "'SECONDS'", "'TIMEZONE'", "'TZ'", "'NOW'", "'UUID'", 
		"'STRUUID'", "'MD5'", "'SHA1'", "'SHA256'", "'SHA384'", "'SHA512'", "'COALESCE'", 
		"'IF'", "'STRLANG'", "'STRDT'", "'SAMETERM'", "'ISIRI'", "'ISURI'", "'ISBLANK'", 
		"'ISLITERAL'", "'ISNUMERIC'", "'REGEX'", "'SUBSTR'", "'REPLACE'", "'EXISTS'", 
		"'COUNT'", "'SUM'", "'MIN'", "'MAX'", "'AVG'", "'SAMPLE'", "'GROUP_CONCAT'", 
		"'SEPARATOR'", "'^^'", "'TRUE'", "'FALSE'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		"TYPE", "COMMENT", "DURATION", "IRIREF", "PNAME_NS", "PNAME_LN", "BLANK_NODE_LABEL", 
		"VAR1", "VAR2", "LANGTAG", "INTEGER", "DECIMAL", "DOUBLE", "INTEGER_POSITIVE", 
		"DECIMAL_POSITIVE", "DOUBLE_POSITIVE", "INTEGER_NEGATIVE", "DECIMAL_NEGATIVE", 
		"DOUBLE_NEGATIVE", "EXPONENT", "STRING_LITERAL1", "STRING_LITERAL2", "STRING_LITERAL_LONG1", 
		"STRING_LITERAL_LONG2", "ECHAR", "NIL", "WS", "ANON", "PN_CHARS_BASE", 
		"PN_CHARS_U", "VARNAME", "PN_CHARS", "PN_PREFIX", "PN_LOCAL", "PLX", "PERCENT", 
		"HEX", "PN_LOCAL_ESC", "ANYCHAR"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "RSPQL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public RSPQLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class QueryUnitContext extends ParserRuleContext {
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public QueryUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queryUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterQueryUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitQueryUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitQueryUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryUnitContext queryUnit() throws RecognitionException {
		QueryUnitContext _localctx = new QueryUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_queryUnit);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(284);
			query();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QueryContext extends ParserRuleContext {
		public PrologueContext prologue() {
			return getRuleContext(PrologueContext.class,0);
		}
		public ValuesClauseContext valuesClause() {
			return getRuleContext(ValuesClauseContext.class,0);
		}
		public SelectQueryContext selectQuery() {
			return getRuleContext(SelectQueryContext.class,0);
		}
		public ConstructQueryContext constructQuery() {
			return getRuleContext(ConstructQueryContext.class,0);
		}
		public DescribeQueryContext describeQuery() {
			return getRuleContext(DescribeQueryContext.class,0);
		}
		public AskQueryContext askQuery() {
			return getRuleContext(AskQueryContext.class,0);
		}
		public RegisterClauseContext registerClause() {
			return getRuleContext(RegisterClauseContext.class,0);
		}
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_query);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(286);
			prologue();
			setState(288);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(287);
				registerClause();
				}
			}

			setState(294);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__7:
				{
				setState(290);
				selectQuery();
				}
				break;
			case T__13:
				{
				setState(291);
				constructQuery();
				}
				break;
			case T__17:
				{
				setState(292);
				describeQuery();
				}
				break;
			case T__18:
				{
				setState(293);
				askQuery();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(296);
			valuesClause();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrologueContext extends ParserRuleContext {
		public List<BaseDeclContext> baseDecl() {
			return getRuleContexts(BaseDeclContext.class);
		}
		public BaseDeclContext baseDecl(int i) {
			return getRuleContext(BaseDeclContext.class,i);
		}
		public List<PrefixDeclContext> prefixDecl() {
			return getRuleContexts(PrefixDeclContext.class);
		}
		public PrefixDeclContext prefixDecl(int i) {
			return getRuleContext(PrefixDeclContext.class,i);
		}
		public PrologueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prologue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterPrologue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitPrologue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitPrologue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrologueContext prologue() throws RecognitionException {
		PrologueContext _localctx = new PrologueContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_prologue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(302);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0 || _la==T__1) {
				{
				setState(300);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__0:
					{
					setState(298);
					baseDecl();
					}
					break;
				case T__1:
					{
					setState(299);
					prefixDecl();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(304);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BaseDeclContext extends ParserRuleContext {
		public TerminalNode IRIREF() { return getToken(RSPQLParser.IRIREF, 0); }
		public BaseDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_baseDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterBaseDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitBaseDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitBaseDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BaseDeclContext baseDecl() throws RecognitionException {
		BaseDeclContext _localctx = new BaseDeclContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_baseDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(305);
			match(T__0);
			setState(306);
			match(IRIREF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrefixDeclContext extends ParserRuleContext {
		public TerminalNode PNAME_NS() { return getToken(RSPQLParser.PNAME_NS, 0); }
		public TerminalNode IRIREF() { return getToken(RSPQLParser.IRIREF, 0); }
		public PrefixDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefixDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterPrefixDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitPrefixDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitPrefixDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrefixDeclContext prefixDecl() throws RecognitionException {
		PrefixDeclContext _localctx = new PrefixDeclContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_prefixDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(308);
			match(T__1);
			setState(309);
			match(PNAME_NS);
			setState(310);
			match(IRIREF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RegisterClauseContext extends ParserRuleContext {
		public OutputStreamTypeContext outputStreamType() {
			return getRuleContext(OutputStreamTypeContext.class,0);
		}
		public OutputStreamContext outputStream() {
			return getRuleContext(OutputStreamContext.class,0);
		}
		public RegisterClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_registerClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterRegisterClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitRegisterClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitRegisterClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RegisterClauseContext registerClause() throws RecognitionException {
		RegisterClauseContext _localctx = new RegisterClauseContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_registerClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(312);
			match(T__2);
			setState(313);
			outputStreamType();
			setState(314);
			outputStream();
			setState(315);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OutputStreamTypeContext extends ParserRuleContext {
		public OutputStreamTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_outputStreamType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterOutputStreamType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitOutputStreamType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitOutputStreamType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OutputStreamTypeContext outputStreamType() throws RecognitionException {
		OutputStreamTypeContext _localctx = new OutputStreamTypeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_outputStreamType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(317);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__5) | (1L << T__6))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OutputStreamContext extends ParserRuleContext {
		public SourceSelectorContext sourceSelector() {
			return getRuleContext(SourceSelectorContext.class,0);
		}
		public OutputStreamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_outputStream; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterOutputStream(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitOutputStream(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitOutputStream(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OutputStreamContext outputStream() throws RecognitionException {
		OutputStreamContext _localctx = new OutputStreamContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_outputStream);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(319);
			sourceSelector();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectQueryContext extends ParserRuleContext {
		public SelectClauseContext selectClause() {
			return getRuleContext(SelectClauseContext.class,0);
		}
		public WhereClauseContext whereClause() {
			return getRuleContext(WhereClauseContext.class,0);
		}
		public SolutionModifierContext solutionModifier() {
			return getRuleContext(SolutionModifierContext.class,0);
		}
		public List<DatasetClauseContext> datasetClause() {
			return getRuleContexts(DatasetClauseContext.class);
		}
		public DatasetClauseContext datasetClause(int i) {
			return getRuleContext(DatasetClauseContext.class,i);
		}
		public SelectQueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectQuery; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterSelectQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitSelectQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitSelectQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectQueryContext selectQuery() throws RecognitionException {
		SelectQueryContext _localctx = new SelectQueryContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_selectQuery);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(321);
			selectClause();
			setState(325);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__19) {
				{
				{
				setState(322);
				datasetClause();
				}
				}
				setState(327);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(328);
			whereClause();
			setState(329);
			solutionModifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SubSelectContext extends ParserRuleContext {
		public SelectClauseContext selectClause() {
			return getRuleContext(SelectClauseContext.class,0);
		}
		public WhereClauseContext whereClause() {
			return getRuleContext(WhereClauseContext.class,0);
		}
		public SolutionModifierContext solutionModifier() {
			return getRuleContext(SolutionModifierContext.class,0);
		}
		public ValuesClauseContext valuesClause() {
			return getRuleContext(ValuesClauseContext.class,0);
		}
		public SubSelectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subSelect; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterSubSelect(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitSubSelect(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitSubSelect(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubSelectContext subSelect() throws RecognitionException {
		SubSelectContext _localctx = new SubSelectContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_subSelect);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(331);
			selectClause();
			setState(332);
			whereClause();
			setState(333);
			solutionModifier();
			setState(334);
			valuesClause();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectClauseContext extends ParserRuleContext {
		public ResultStarContext resultStar() {
			return getRuleContext(ResultStarContext.class,0);
		}
		public DistinctContext distinct() {
			return getRuleContext(DistinctContext.class,0);
		}
		public ReducedContext reduced() {
			return getRuleContext(ReducedContext.class,0);
		}
		public List<ResultVarContext> resultVar() {
			return getRuleContexts(ResultVarContext.class);
		}
		public ResultVarContext resultVar(int i) {
			return getRuleContext(ResultVarContext.class,i);
		}
		public SelectClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterSelectClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitSelectClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitSelectClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectClauseContext selectClause() throws RecognitionException {
		SelectClauseContext _localctx = new SelectClauseContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_selectClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(336);
			match(T__7);
			setState(339);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__11:
				{
				setState(337);
				distinct();
				}
				break;
			case T__12:
				{
				setState(338);
				reduced();
				}
				break;
			case T__8:
			case T__9:
			case VAR1:
			case VAR2:
				break;
			default:
				break;
			}
			setState(347);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__9:
			case VAR1:
			case VAR2:
				{
				setState(342); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(341);
					resultVar();
					}
					}
					setState(344); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__9 || _la==VAR1 || _la==VAR2 );
				}
				break;
			case T__8:
				{
				setState(346);
				resultStar();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ResultStarContext extends ParserRuleContext {
		public ResultStarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_resultStar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterResultStar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitResultStar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitResultStar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ResultStarContext resultStar() throws RecognitionException {
		ResultStarContext _localctx = new ResultStarContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_resultStar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(349);
			match(T__8);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ResultVarContext extends ParserRuleContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ResultVarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_resultVar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterResultVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitResultVar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitResultVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ResultVarContext resultVar() throws RecognitionException {
		ResultVarContext _localctx = new ResultVarContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_resultVar);
		try {
			setState(358);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VAR1:
			case VAR2:
				enterOuterAlt(_localctx, 1);
				{
				setState(351);
				var();
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(352);
				match(T__9);
				setState(353);
				expression();
				setState(354);
				match(T__3);
				setState(355);
				var();
				setState(356);
				match(T__10);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DistinctContext extends ParserRuleContext {
		public DistinctContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_distinct; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterDistinct(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitDistinct(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitDistinct(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DistinctContext distinct() throws RecognitionException {
		DistinctContext _localctx = new DistinctContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_distinct);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(360);
			match(T__11);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReducedContext extends ParserRuleContext {
		public ReducedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_reduced; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterReduced(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitReduced(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitReduced(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReducedContext reduced() throws RecognitionException {
		ReducedContext _localctx = new ReducedContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_reduced);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(362);
			match(T__12);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstructQueryContext extends ParserRuleContext {
		public ConstructTemplateContext constructTemplate() {
			return getRuleContext(ConstructTemplateContext.class,0);
		}
		public WhereClauseContext whereClause() {
			return getRuleContext(WhereClauseContext.class,0);
		}
		public SolutionModifierContext solutionModifier() {
			return getRuleContext(SolutionModifierContext.class,0);
		}
		public List<DatasetClauseContext> datasetClause() {
			return getRuleContexts(DatasetClauseContext.class);
		}
		public DatasetClauseContext datasetClause(int i) {
			return getRuleContext(DatasetClauseContext.class,i);
		}
		public TriplesTemplateContext triplesTemplate() {
			return getRuleContext(TriplesTemplateContext.class,0);
		}
		public ConstructQueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructQuery; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterConstructQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitConstructQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitConstructQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructQueryContext constructQuery() throws RecognitionException {
		ConstructQueryContext _localctx = new ConstructQueryContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_constructQuery);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(364);
			match(T__13);
			setState(388);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__15:
				{
				setState(365);
				constructTemplate();
				setState(369);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__19) {
					{
					{
					setState(366);
					datasetClause();
					}
					}
					setState(371);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(372);
				whereClause();
				setState(373);
				solutionModifier();
				}
				break;
			case T__14:
			case T__19:
				{
				setState(378);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__19) {
					{
					{
					setState(375);
					datasetClause();
					}
					}
					setState(380);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(381);
				match(T__14);
				setState(382);
				match(T__15);
				setState(384);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__9 || _la==T__23 || ((((_la - 130)) & ~0x3f) == 0 && ((1L << (_la - 130)) & ((1L << (T__129 - 130)) | (1L << (T__130 - 130)) | (1L << (IRIREF - 130)) | (1L << (PNAME_NS - 130)) | (1L << (PNAME_LN - 130)) | (1L << (BLANK_NODE_LABEL - 130)) | (1L << (VAR1 - 130)) | (1L << (VAR2 - 130)) | (1L << (INTEGER - 130)) | (1L << (DECIMAL - 130)) | (1L << (DOUBLE - 130)) | (1L << (INTEGER_POSITIVE - 130)) | (1L << (DECIMAL_POSITIVE - 130)) | (1L << (DOUBLE_POSITIVE - 130)) | (1L << (INTEGER_NEGATIVE - 130)) | (1L << (DECIMAL_NEGATIVE - 130)) | (1L << (DOUBLE_NEGATIVE - 130)) | (1L << (STRING_LITERAL1 - 130)) | (1L << (STRING_LITERAL2 - 130)) | (1L << (STRING_LITERAL_LONG1 - 130)) | (1L << (STRING_LITERAL_LONG2 - 130)) | (1L << (NIL - 130)) | (1L << (ANON - 130)))) != 0)) {
					{
					setState(383);
					triplesTemplate();
					}
				}

				setState(386);
				match(T__16);
				setState(387);
				solutionModifier();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DescribeQueryContext extends ParserRuleContext {
		public SolutionModifierContext solutionModifier() {
			return getRuleContext(SolutionModifierContext.class,0);
		}
		public ResultStarContext resultStar() {
			return getRuleContext(ResultStarContext.class,0);
		}
		public List<DatasetClauseContext> datasetClause() {
			return getRuleContexts(DatasetClauseContext.class);
		}
		public DatasetClauseContext datasetClause(int i) {
			return getRuleContext(DatasetClauseContext.class,i);
		}
		public WhereClauseContext whereClause() {
			return getRuleContext(WhereClauseContext.class,0);
		}
		public List<VarOrIriContext> varOrIri() {
			return getRuleContexts(VarOrIriContext.class);
		}
		public VarOrIriContext varOrIri(int i) {
			return getRuleContext(VarOrIriContext.class,i);
		}
		public DescribeQueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_describeQuery; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterDescribeQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitDescribeQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitDescribeQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DescribeQueryContext describeQuery() throws RecognitionException {
		DescribeQueryContext _localctx = new DescribeQueryContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_describeQuery);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(390);
			match(T__17);
			setState(397);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
			case VAR1:
			case VAR2:
				{
				setState(392); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(391);
					varOrIri();
					}
					}
					setState(394); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( ((((_la - 135)) & ~0x3f) == 0 && ((1L << (_la - 135)) & ((1L << (IRIREF - 135)) | (1L << (PNAME_NS - 135)) | (1L << (PNAME_LN - 135)) | (1L << (VAR1 - 135)) | (1L << (VAR2 - 135)))) != 0) );
				}
				break;
			case T__8:
				{
				setState(396);
				resultStar();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(402);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__19) {
				{
				{
				setState(399);
				datasetClause();
				}
				}
				setState(404);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(406);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__14 || _la==T__15) {
				{
				setState(405);
				whereClause();
				}
			}

			setState(408);
			solutionModifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AskQueryContext extends ParserRuleContext {
		public WhereClauseContext whereClause() {
			return getRuleContext(WhereClauseContext.class,0);
		}
		public SolutionModifierContext solutionModifier() {
			return getRuleContext(SolutionModifierContext.class,0);
		}
		public List<DatasetClauseContext> datasetClause() {
			return getRuleContexts(DatasetClauseContext.class);
		}
		public DatasetClauseContext datasetClause(int i) {
			return getRuleContext(DatasetClauseContext.class,i);
		}
		public AskQueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_askQuery; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterAskQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitAskQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitAskQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AskQueryContext askQuery() throws RecognitionException {
		AskQueryContext _localctx = new AskQueryContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_askQuery);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(410);
			match(T__18);
			setState(414);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__19) {
				{
				{
				setState(411);
				datasetClause();
				}
				}
				setState(416);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(417);
			whereClause();
			setState(418);
			solutionModifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DatasetClauseContext extends ParserRuleContext {
		public DefaultGraphClauseContext defaultGraphClause() {
			return getRuleContext(DefaultGraphClauseContext.class,0);
		}
		public NamedGraphClauseContext namedGraphClause() {
			return getRuleContext(NamedGraphClauseContext.class,0);
		}
		public NamedWindowClauseContext namedWindowClause() {
			return getRuleContext(NamedWindowClauseContext.class,0);
		}
		public DatasetClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_datasetClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterDatasetClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitDatasetClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitDatasetClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DatasetClauseContext datasetClause() throws RecognitionException {
		DatasetClauseContext _localctx = new DatasetClauseContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_datasetClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(420);
			match(T__19);
			setState(424);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(421);
				defaultGraphClause();
				}
				break;
			case 2:
				{
				setState(422);
				namedGraphClause();
				}
				break;
			case 3:
				{
				setState(423);
				namedWindowClause();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DefaultGraphClauseContext extends ParserRuleContext {
		public SourceSelectorContext sourceSelector() {
			return getRuleContext(SourceSelectorContext.class,0);
		}
		public DefaultGraphClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defaultGraphClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterDefaultGraphClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitDefaultGraphClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitDefaultGraphClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefaultGraphClauseContext defaultGraphClause() throws RecognitionException {
		DefaultGraphClauseContext _localctx = new DefaultGraphClauseContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_defaultGraphClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(426);
			sourceSelector();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamedGraphClauseContext extends ParserRuleContext {
		public SourceSelectorContext sourceSelector() {
			return getRuleContext(SourceSelectorContext.class,0);
		}
		public NamedGraphClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namedGraphClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterNamedGraphClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitNamedGraphClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitNamedGraphClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamedGraphClauseContext namedGraphClause() throws RecognitionException {
		NamedGraphClauseContext _localctx = new NamedGraphClauseContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_namedGraphClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(428);
			match(T__20);
			setState(429);
			sourceSelector();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamedWindowClauseContext extends ParserRuleContext {
		public WindowUriContext windowUri() {
			return getRuleContext(WindowUriContext.class,0);
		}
		public StreamUriContext streamUri() {
			return getRuleContext(StreamUriContext.class,0);
		}
		public WindowContext window() {
			return getRuleContext(WindowContext.class,0);
		}
		public NamedWindowClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namedWindowClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterNamedWindowClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitNamedWindowClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitNamedWindowClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamedWindowClauseContext namedWindowClause() throws RecognitionException {
		NamedWindowClauseContext _localctx = new NamedWindowClauseContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_namedWindowClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(431);
			match(T__20);
			setState(432);
			match(T__21);
			setState(433);
			windowUri();
			setState(434);
			match(T__22);
			setState(435);
			streamUri();
			setState(436);
			match(T__23);
			setState(437);
			window();
			setState(438);
			match(T__24);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WindowUriContext extends ParserRuleContext {
		public SourceSelectorContext sourceSelector() {
			return getRuleContext(SourceSelectorContext.class,0);
		}
		public WindowUriContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_windowUri; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterWindowUri(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitWindowUri(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitWindowUri(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WindowUriContext windowUri() throws RecognitionException {
		WindowUriContext _localctx = new WindowUriContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_windowUri);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(440);
			sourceSelector();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StreamUriContext extends ParserRuleContext {
		public SourceSelectorContext sourceSelector() {
			return getRuleContext(SourceSelectorContext.class,0);
		}
		public StreamUriContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_streamUri; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterStreamUri(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitStreamUri(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitStreamUri(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StreamUriContext streamUri() throws RecognitionException {
		StreamUriContext _localctx = new StreamUriContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_streamUri);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(442);
			sourceSelector();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WindowContext extends ParserRuleContext {
		public PhysicalWindowContext physicalWindow() {
			return getRuleContext(PhysicalWindowContext.class,0);
		}
		public LogicalWindowContext logicalWindow() {
			return getRuleContext(LogicalWindowContext.class,0);
		}
		public WindowContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_window; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterWindow(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitWindow(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitWindow(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WindowContext window() throws RecognitionException {
		WindowContext _localctx = new WindowContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_window);
		try {
			setState(446);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__25:
				enterOuterAlt(_localctx, 1);
				{
				setState(444);
				physicalWindow();
				}
				break;
			case T__27:
				enterOuterAlt(_localctx, 2);
				{
				setState(445);
				logicalWindow();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PhysicalWindowContext extends ParserRuleContext {
		public PhysicalRangeContext physicalRange() {
			return getRuleContext(PhysicalRangeContext.class,0);
		}
		public PhysicalStepContext physicalStep() {
			return getRuleContext(PhysicalStepContext.class,0);
		}
		public PhysicalWindowContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_physicalWindow; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterPhysicalWindow(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitPhysicalWindow(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitPhysicalWindow(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PhysicalWindowContext physicalWindow() throws RecognitionException {
		PhysicalWindowContext _localctx = new PhysicalWindowContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_physicalWindow);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(448);
			physicalRange();
			setState(450);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__26) {
				{
				setState(449);
				physicalStep();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PhysicalRangeContext extends ParserRuleContext {
		public IntegerContext integer() {
			return getRuleContext(IntegerContext.class,0);
		}
		public PhysicalRangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_physicalRange; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterPhysicalRange(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitPhysicalRange(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitPhysicalRange(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PhysicalRangeContext physicalRange() throws RecognitionException {
		PhysicalRangeContext _localctx = new PhysicalRangeContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_physicalRange);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(452);
			match(T__25);
			setState(453);
			integer();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PhysicalStepContext extends ParserRuleContext {
		public IntegerContext integer() {
			return getRuleContext(IntegerContext.class,0);
		}
		public PhysicalStepContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_physicalStep; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterPhysicalStep(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitPhysicalStep(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitPhysicalStep(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PhysicalStepContext physicalStep() throws RecognitionException {
		PhysicalStepContext _localctx = new PhysicalStepContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_physicalStep);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(455);
			match(T__26);
			setState(456);
			integer();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicalWindowContext extends ParserRuleContext {
		public LogicalRangeContext logicalRange() {
			return getRuleContext(LogicalRangeContext.class,0);
		}
		public LogicalStepContext logicalStep() {
			return getRuleContext(LogicalStepContext.class,0);
		}
		public LogicalWindowContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalWindow; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterLogicalWindow(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitLogicalWindow(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitLogicalWindow(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalWindowContext logicalWindow() throws RecognitionException {
		LogicalWindowContext _localctx = new LogicalWindowContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_logicalWindow);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(458);
			logicalRange();
			setState(460);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__26) {
				{
				setState(459);
				logicalStep();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicalRangeContext extends ParserRuleContext {
		public DurationContext duration() {
			return getRuleContext(DurationContext.class,0);
		}
		public LogicalRangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalRange; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterLogicalRange(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitLogicalRange(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitLogicalRange(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalRangeContext logicalRange() throws RecognitionException {
		LogicalRangeContext _localctx = new LogicalRangeContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_logicalRange);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(462);
			match(T__27);
			setState(463);
			duration();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicalStepContext extends ParserRuleContext {
		public DurationContext duration() {
			return getRuleContext(DurationContext.class,0);
		}
		public LogicalStepContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalStep; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterLogicalStep(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitLogicalStep(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitLogicalStep(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalStepContext logicalStep() throws RecognitionException {
		LogicalStepContext _localctx = new LogicalStepContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_logicalStep);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(465);
			match(T__26);
			setState(466);
			duration();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DurationContext extends ParserRuleContext {
		public TerminalNode DURATION() { return getToken(RSPQLParser.DURATION, 0); }
		public DurationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_duration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterDuration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitDuration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitDuration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DurationContext duration() throws RecognitionException {
		DurationContext _localctx = new DurationContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_duration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(468);
			match(DURATION);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SourceSelectorContext extends ParserRuleContext {
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public SourceSelectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sourceSelector; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterSourceSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitSourceSelector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitSourceSelector(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SourceSelectorContext sourceSelector() throws RecognitionException {
		SourceSelectorContext _localctx = new SourceSelectorContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_sourceSelector);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(470);
			iri();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhereClauseContext extends ParserRuleContext {
		public GroupGraphPatternContext groupGraphPattern() {
			return getRuleContext(GroupGraphPatternContext.class,0);
		}
		public WhereClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whereClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterWhereClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitWhereClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitWhereClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhereClauseContext whereClause() throws RecognitionException {
		WhereClauseContext _localctx = new WhereClauseContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_whereClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(473);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__14) {
				{
				setState(472);
				match(T__14);
				}
			}

			setState(475);
			groupGraphPattern();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SolutionModifierContext extends ParserRuleContext {
		public GroupClauseContext groupClause() {
			return getRuleContext(GroupClauseContext.class,0);
		}
		public HavingClauseContext havingClause() {
			return getRuleContext(HavingClauseContext.class,0);
		}
		public OrderClauseContext orderClause() {
			return getRuleContext(OrderClauseContext.class,0);
		}
		public LimitOffsetClausesContext limitOffsetClauses() {
			return getRuleContext(LimitOffsetClausesContext.class,0);
		}
		public SolutionModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_solutionModifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterSolutionModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitSolutionModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitSolutionModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SolutionModifierContext solutionModifier() throws RecognitionException {
		SolutionModifierContext _localctx = new SolutionModifierContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_solutionModifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(478);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__28) {
				{
				setState(477);
				groupClause();
				}
			}

			setState(481);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__30) {
				{
				setState(480);
				havingClause();
				}
			}

			setState(484);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__31) {
				{
				setState(483);
				orderClause();
				}
			}

			setState(487);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__34 || _la==T__35) {
				{
				setState(486);
				limitOffsetClauses();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GroupClauseContext extends ParserRuleContext {
		public List<GroupConditionContext> groupCondition() {
			return getRuleContexts(GroupConditionContext.class);
		}
		public GroupConditionContext groupCondition(int i) {
			return getRuleContext(GroupConditionContext.class,i);
		}
		public GroupClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterGroupClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitGroupClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitGroupClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupClauseContext groupClause() throws RecognitionException {
		GroupClauseContext _localctx = new GroupClauseContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_groupClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(489);
			match(T__28);
			setState(490);
			match(T__29);
			setState(492); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(491);
				groupCondition();
				}
				}
				setState(494); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__9 || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (T__65 - 66)) | (1L << (T__67 - 66)) | (1L << (T__68 - 66)) | (1L << (T__69 - 66)) | (1L << (T__70 - 66)) | (1L << (T__71 - 66)) | (1L << (T__72 - 66)) | (1L << (T__73 - 66)) | (1L << (T__74 - 66)) | (1L << (T__75 - 66)) | (1L << (T__76 - 66)) | (1L << (T__77 - 66)) | (1L << (T__78 - 66)) | (1L << (T__79 - 66)) | (1L << (T__80 - 66)) | (1L << (T__81 - 66)) | (1L << (T__82 - 66)) | (1L << (T__83 - 66)) | (1L << (T__84 - 66)) | (1L << (T__85 - 66)) | (1L << (T__86 - 66)) | (1L << (T__87 - 66)) | (1L << (T__88 - 66)) | (1L << (T__89 - 66)) | (1L << (T__90 - 66)) | (1L << (T__91 - 66)) | (1L << (T__92 - 66)) | (1L << (T__93 - 66)) | (1L << (T__94 - 66)) | (1L << (T__95 - 66)) | (1L << (T__96 - 66)) | (1L << (T__97 - 66)) | (1L << (T__98 - 66)) | (1L << (T__99 - 66)) | (1L << (T__100 - 66)) | (1L << (T__101 - 66)) | (1L << (T__102 - 66)) | (1L << (T__103 - 66)) | (1L << (T__104 - 66)) | (1L << (T__105 - 66)) | (1L << (T__106 - 66)) | (1L << (T__107 - 66)) | (1L << (T__108 - 66)) | (1L << (T__109 - 66)) | (1L << (T__110 - 66)) | (1L << (T__111 - 66)) | (1L << (T__112 - 66)) | (1L << (T__113 - 66)) | (1L << (T__114 - 66)) | (1L << (T__115 - 66)) | (1L << (T__116 - 66)) | (1L << (T__117 - 66)) | (1L << (T__118 - 66)) | (1L << (T__119 - 66)) | (1L << (T__120 - 66)) | (1L << (T__121 - 66)) | (1L << (T__122 - 66)) | (1L << (T__123 - 66)) | (1L << (T__124 - 66)) | (1L << (T__125 - 66)) | (1L << (T__126 - 66)))) != 0) || ((((_la - 135)) & ~0x3f) == 0 && ((1L << (_la - 135)) & ((1L << (IRIREF - 135)) | (1L << (PNAME_NS - 135)) | (1L << (PNAME_LN - 135)) | (1L << (VAR1 - 135)) | (1L << (VAR2 - 135)))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GroupConditionContext extends ParserRuleContext {
		public BuiltInCallContext builtInCall() {
			return getRuleContext(BuiltInCallContext.class,0);
		}
		public FunctionCallContext functionCall() {
			return getRuleContext(FunctionCallContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public GroupConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupCondition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterGroupCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitGroupCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitGroupCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupConditionContext groupCondition() throws RecognitionException {
		GroupConditionContext _localctx = new GroupConditionContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_groupCondition);
		int _la;
		try {
			setState(507);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__65:
			case T__67:
			case T__68:
			case T__69:
			case T__70:
			case T__71:
			case T__72:
			case T__73:
			case T__74:
			case T__75:
			case T__76:
			case T__77:
			case T__78:
			case T__79:
			case T__80:
			case T__81:
			case T__82:
			case T__83:
			case T__84:
			case T__85:
			case T__86:
			case T__87:
			case T__88:
			case T__89:
			case T__90:
			case T__91:
			case T__92:
			case T__93:
			case T__94:
			case T__95:
			case T__96:
			case T__97:
			case T__98:
			case T__99:
			case T__100:
			case T__101:
			case T__102:
			case T__103:
			case T__104:
			case T__105:
			case T__106:
			case T__107:
			case T__108:
			case T__109:
			case T__110:
			case T__111:
			case T__112:
			case T__113:
			case T__114:
			case T__115:
			case T__116:
			case T__117:
			case T__118:
			case T__119:
			case T__120:
			case T__121:
			case T__122:
			case T__123:
			case T__124:
			case T__125:
			case T__126:
				enterOuterAlt(_localctx, 1);
				{
				setState(496);
				builtInCall();
				}
				break;
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 2);
				{
				setState(497);
				functionCall();
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 3);
				{
				setState(498);
				match(T__9);
				setState(499);
				expression();
				setState(502);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__3) {
					{
					setState(500);
					match(T__3);
					setState(501);
					var();
					}
				}

				setState(504);
				match(T__10);
				}
				break;
			case VAR1:
			case VAR2:
				enterOuterAlt(_localctx, 4);
				{
				setState(506);
				var();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HavingClauseContext extends ParserRuleContext {
		public List<HavingConditionContext> havingCondition() {
			return getRuleContexts(HavingConditionContext.class);
		}
		public HavingConditionContext havingCondition(int i) {
			return getRuleContext(HavingConditionContext.class,i);
		}
		public HavingClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_havingClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterHavingClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitHavingClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitHavingClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HavingClauseContext havingClause() throws RecognitionException {
		HavingClauseContext _localctx = new HavingClauseContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_havingClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(509);
			match(T__30);
			setState(511); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(510);
				havingCondition();
				}
				}
				setState(513); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( ((((_la - 10)) & ~0x3f) == 0 && ((1L << (_la - 10)) & ((1L << (T__9 - 10)) | (1L << (T__65 - 10)) | (1L << (T__67 - 10)) | (1L << (T__68 - 10)) | (1L << (T__69 - 10)) | (1L << (T__70 - 10)) | (1L << (T__71 - 10)) | (1L << (T__72 - 10)))) != 0) || ((((_la - 74)) & ~0x3f) == 0 && ((1L << (_la - 74)) & ((1L << (T__73 - 74)) | (1L << (T__74 - 74)) | (1L << (T__75 - 74)) | (1L << (T__76 - 74)) | (1L << (T__77 - 74)) | (1L << (T__78 - 74)) | (1L << (T__79 - 74)) | (1L << (T__80 - 74)) | (1L << (T__81 - 74)) | (1L << (T__82 - 74)) | (1L << (T__83 - 74)) | (1L << (T__84 - 74)) | (1L << (T__85 - 74)) | (1L << (T__86 - 74)) | (1L << (T__87 - 74)) | (1L << (T__88 - 74)) | (1L << (T__89 - 74)) | (1L << (T__90 - 74)) | (1L << (T__91 - 74)) | (1L << (T__92 - 74)) | (1L << (T__93 - 74)) | (1L << (T__94 - 74)) | (1L << (T__95 - 74)) | (1L << (T__96 - 74)) | (1L << (T__97 - 74)) | (1L << (T__98 - 74)) | (1L << (T__99 - 74)) | (1L << (T__100 - 74)) | (1L << (T__101 - 74)) | (1L << (T__102 - 74)) | (1L << (T__103 - 74)) | (1L << (T__104 - 74)) | (1L << (T__105 - 74)) | (1L << (T__106 - 74)) | (1L << (T__107 - 74)) | (1L << (T__108 - 74)) | (1L << (T__109 - 74)) | (1L << (T__110 - 74)) | (1L << (T__111 - 74)) | (1L << (T__112 - 74)) | (1L << (T__113 - 74)) | (1L << (T__114 - 74)) | (1L << (T__115 - 74)) | (1L << (T__116 - 74)) | (1L << (T__117 - 74)) | (1L << (T__118 - 74)) | (1L << (T__119 - 74)) | (1L << (T__120 - 74)) | (1L << (T__121 - 74)) | (1L << (T__122 - 74)) | (1L << (T__123 - 74)) | (1L << (T__124 - 74)) | (1L << (T__125 - 74)) | (1L << (T__126 - 74)) | (1L << (IRIREF - 74)) | (1L << (PNAME_NS - 74)) | (1L << (PNAME_LN - 74)))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HavingConditionContext extends ParserRuleContext {
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public HavingConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_havingCondition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterHavingCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitHavingCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitHavingCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HavingConditionContext havingCondition() throws RecognitionException {
		HavingConditionContext _localctx = new HavingConditionContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_havingCondition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(515);
			constraint();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrderClauseContext extends ParserRuleContext {
		public List<OrderConditionContext> orderCondition() {
			return getRuleContexts(OrderConditionContext.class);
		}
		public OrderConditionContext orderCondition(int i) {
			return getRuleContext(OrderConditionContext.class,i);
		}
		public OrderClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orderClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterOrderClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitOrderClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitOrderClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrderClauseContext orderClause() throws RecognitionException {
		OrderClauseContext _localctx = new OrderClauseContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_orderClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(517);
			match(T__31);
			setState(518);
			match(T__29);
			setState(520); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(519);
				orderCondition();
				}
				}
				setState(522); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__32) | (1L << T__33))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (T__65 - 66)) | (1L << (T__67 - 66)) | (1L << (T__68 - 66)) | (1L << (T__69 - 66)) | (1L << (T__70 - 66)) | (1L << (T__71 - 66)) | (1L << (T__72 - 66)) | (1L << (T__73 - 66)) | (1L << (T__74 - 66)) | (1L << (T__75 - 66)) | (1L << (T__76 - 66)) | (1L << (T__77 - 66)) | (1L << (T__78 - 66)) | (1L << (T__79 - 66)) | (1L << (T__80 - 66)) | (1L << (T__81 - 66)) | (1L << (T__82 - 66)) | (1L << (T__83 - 66)) | (1L << (T__84 - 66)) | (1L << (T__85 - 66)) | (1L << (T__86 - 66)) | (1L << (T__87 - 66)) | (1L << (T__88 - 66)) | (1L << (T__89 - 66)) | (1L << (T__90 - 66)) | (1L << (T__91 - 66)) | (1L << (T__92 - 66)) | (1L << (T__93 - 66)) | (1L << (T__94 - 66)) | (1L << (T__95 - 66)) | (1L << (T__96 - 66)) | (1L << (T__97 - 66)) | (1L << (T__98 - 66)) | (1L << (T__99 - 66)) | (1L << (T__100 - 66)) | (1L << (T__101 - 66)) | (1L << (T__102 - 66)) | (1L << (T__103 - 66)) | (1L << (T__104 - 66)) | (1L << (T__105 - 66)) | (1L << (T__106 - 66)) | (1L << (T__107 - 66)) | (1L << (T__108 - 66)) | (1L << (T__109 - 66)) | (1L << (T__110 - 66)) | (1L << (T__111 - 66)) | (1L << (T__112 - 66)) | (1L << (T__113 - 66)) | (1L << (T__114 - 66)) | (1L << (T__115 - 66)) | (1L << (T__116 - 66)) | (1L << (T__117 - 66)) | (1L << (T__118 - 66)) | (1L << (T__119 - 66)) | (1L << (T__120 - 66)) | (1L << (T__121 - 66)) | (1L << (T__122 - 66)) | (1L << (T__123 - 66)) | (1L << (T__124 - 66)) | (1L << (T__125 - 66)) | (1L << (T__126 - 66)))) != 0) || ((((_la - 135)) & ~0x3f) == 0 && ((1L << (_la - 135)) & ((1L << (IRIREF - 135)) | (1L << (PNAME_NS - 135)) | (1L << (PNAME_LN - 135)) | (1L << (VAR1 - 135)) | (1L << (VAR2 - 135)))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrderConditionContext extends ParserRuleContext {
		public BrackettedExpressionContext brackettedExpression() {
			return getRuleContext(BrackettedExpressionContext.class,0);
		}
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public OrderConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orderCondition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterOrderCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitOrderCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitOrderCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrderConditionContext orderCondition() throws RecognitionException {
		OrderConditionContext _localctx = new OrderConditionContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_orderCondition);
		int _la;
		try {
			setState(530);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__32:
			case T__33:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(524);
				_la = _input.LA(1);
				if ( !(_la==T__32 || _la==T__33) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(525);
				brackettedExpression();
				}
				}
				break;
			case T__9:
			case T__65:
			case T__67:
			case T__68:
			case T__69:
			case T__70:
			case T__71:
			case T__72:
			case T__73:
			case T__74:
			case T__75:
			case T__76:
			case T__77:
			case T__78:
			case T__79:
			case T__80:
			case T__81:
			case T__82:
			case T__83:
			case T__84:
			case T__85:
			case T__86:
			case T__87:
			case T__88:
			case T__89:
			case T__90:
			case T__91:
			case T__92:
			case T__93:
			case T__94:
			case T__95:
			case T__96:
			case T__97:
			case T__98:
			case T__99:
			case T__100:
			case T__101:
			case T__102:
			case T__103:
			case T__104:
			case T__105:
			case T__106:
			case T__107:
			case T__108:
			case T__109:
			case T__110:
			case T__111:
			case T__112:
			case T__113:
			case T__114:
			case T__115:
			case T__116:
			case T__117:
			case T__118:
			case T__119:
			case T__120:
			case T__121:
			case T__122:
			case T__123:
			case T__124:
			case T__125:
			case T__126:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
			case VAR1:
			case VAR2:
				enterOuterAlt(_localctx, 2);
				{
				setState(528);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__9:
				case T__65:
				case T__67:
				case T__68:
				case T__69:
				case T__70:
				case T__71:
				case T__72:
				case T__73:
				case T__74:
				case T__75:
				case T__76:
				case T__77:
				case T__78:
				case T__79:
				case T__80:
				case T__81:
				case T__82:
				case T__83:
				case T__84:
				case T__85:
				case T__86:
				case T__87:
				case T__88:
				case T__89:
				case T__90:
				case T__91:
				case T__92:
				case T__93:
				case T__94:
				case T__95:
				case T__96:
				case T__97:
				case T__98:
				case T__99:
				case T__100:
				case T__101:
				case T__102:
				case T__103:
				case T__104:
				case T__105:
				case T__106:
				case T__107:
				case T__108:
				case T__109:
				case T__110:
				case T__111:
				case T__112:
				case T__113:
				case T__114:
				case T__115:
				case T__116:
				case T__117:
				case T__118:
				case T__119:
				case T__120:
				case T__121:
				case T__122:
				case T__123:
				case T__124:
				case T__125:
				case T__126:
				case IRIREF:
				case PNAME_NS:
				case PNAME_LN:
					{
					setState(526);
					constraint();
					}
					break;
				case VAR1:
				case VAR2:
					{
					setState(527);
					var();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LimitOffsetClausesContext extends ParserRuleContext {
		public LimitClauseContext limitClause() {
			return getRuleContext(LimitClauseContext.class,0);
		}
		public OffsetClauseContext offsetClause() {
			return getRuleContext(OffsetClauseContext.class,0);
		}
		public LimitOffsetClausesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_limitOffsetClauses; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterLimitOffsetClauses(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitLimitOffsetClauses(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitLimitOffsetClauses(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LimitOffsetClausesContext limitOffsetClauses() throws RecognitionException {
		LimitOffsetClausesContext _localctx = new LimitOffsetClausesContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_limitOffsetClauses);
		int _la;
		try {
			setState(540);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__34:
				enterOuterAlt(_localctx, 1);
				{
				setState(532);
				limitClause();
				setState(534);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__35) {
					{
					setState(533);
					offsetClause();
					}
				}

				}
				break;
			case T__35:
				enterOuterAlt(_localctx, 2);
				{
				setState(536);
				offsetClause();
				setState(538);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__34) {
					{
					setState(537);
					limitClause();
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LimitClauseContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(RSPQLParser.INTEGER, 0); }
		public LimitClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_limitClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterLimitClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitLimitClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitLimitClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LimitClauseContext limitClause() throws RecognitionException {
		LimitClauseContext _localctx = new LimitClauseContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_limitClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(542);
			match(T__34);
			setState(543);
			match(INTEGER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OffsetClauseContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(RSPQLParser.INTEGER, 0); }
		public OffsetClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_offsetClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterOffsetClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitOffsetClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitOffsetClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OffsetClauseContext offsetClause() throws RecognitionException {
		OffsetClauseContext _localctx = new OffsetClauseContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_offsetClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(545);
			match(T__35);
			setState(546);
			match(INTEGER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValuesClauseContext extends ParserRuleContext {
		public DataBlockContext dataBlock() {
			return getRuleContext(DataBlockContext.class,0);
		}
		public ValuesClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valuesClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterValuesClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitValuesClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitValuesClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValuesClauseContext valuesClause() throws RecognitionException {
		ValuesClauseContext _localctx = new ValuesClauseContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_valuesClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(550);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__36) {
				{
				setState(548);
				match(T__36);
				setState(549);
				dataBlock();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GraphOrDefaultContext extends ParserRuleContext {
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public GraphOrDefaultContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_graphOrDefault; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterGraphOrDefault(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitGraphOrDefault(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitGraphOrDefault(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GraphOrDefaultContext graphOrDefault() throws RecognitionException {
		GraphOrDefaultContext _localctx = new GraphOrDefaultContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_graphOrDefault);
		int _la;
		try {
			setState(557);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__37:
				enterOuterAlt(_localctx, 1);
				{
				setState(552);
				match(T__37);
				}
				break;
			case T__38:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 2);
				{
				setState(554);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__38) {
					{
					setState(553);
					match(T__38);
					}
				}

				setState(556);
				iri();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GraphRefContext extends ParserRuleContext {
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public GraphRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_graphRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterGraphRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitGraphRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitGraphRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GraphRefContext graphRef() throws RecognitionException {
		GraphRefContext _localctx = new GraphRefContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_graphRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(559);
			match(T__38);
			setState(560);
			iri();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuadPatternContext extends ParserRuleContext {
		public QuadsContext quads() {
			return getRuleContext(QuadsContext.class,0);
		}
		public QuadPatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quadPattern; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterQuadPattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitQuadPattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitQuadPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuadPatternContext quadPattern() throws RecognitionException {
		QuadPatternContext _localctx = new QuadPatternContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_quadPattern);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(562);
			match(T__15);
			setState(563);
			quads();
			setState(564);
			match(T__16);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuadsContext extends ParserRuleContext {
		public List<TriplesTemplateContext> triplesTemplate() {
			return getRuleContexts(TriplesTemplateContext.class);
		}
		public TriplesTemplateContext triplesTemplate(int i) {
			return getRuleContext(TriplesTemplateContext.class,i);
		}
		public List<QuadsNotTriplesContext> quadsNotTriples() {
			return getRuleContexts(QuadsNotTriplesContext.class);
		}
		public QuadsNotTriplesContext quadsNotTriples(int i) {
			return getRuleContext(QuadsNotTriplesContext.class,i);
		}
		public QuadsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quads; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterQuads(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitQuads(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitQuads(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuadsContext quads() throws RecognitionException {
		QuadsContext _localctx = new QuadsContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_quads);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(567);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9 || _la==T__23 || ((((_la - 130)) & ~0x3f) == 0 && ((1L << (_la - 130)) & ((1L << (T__129 - 130)) | (1L << (T__130 - 130)) | (1L << (IRIREF - 130)) | (1L << (PNAME_NS - 130)) | (1L << (PNAME_LN - 130)) | (1L << (BLANK_NODE_LABEL - 130)) | (1L << (VAR1 - 130)) | (1L << (VAR2 - 130)) | (1L << (INTEGER - 130)) | (1L << (DECIMAL - 130)) | (1L << (DOUBLE - 130)) | (1L << (INTEGER_POSITIVE - 130)) | (1L << (DECIMAL_POSITIVE - 130)) | (1L << (DOUBLE_POSITIVE - 130)) | (1L << (INTEGER_NEGATIVE - 130)) | (1L << (DECIMAL_NEGATIVE - 130)) | (1L << (DOUBLE_NEGATIVE - 130)) | (1L << (STRING_LITERAL1 - 130)) | (1L << (STRING_LITERAL2 - 130)) | (1L << (STRING_LITERAL_LONG1 - 130)) | (1L << (STRING_LITERAL_LONG2 - 130)) | (1L << (NIL - 130)) | (1L << (ANON - 130)))) != 0)) {
				{
				setState(566);
				triplesTemplate();
				}
			}

			setState(578);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__38) {
				{
				{
				setState(569);
				quadsNotTriples();
				setState(571);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__39) {
					{
					setState(570);
					match(T__39);
					}
				}

				setState(574);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__9 || _la==T__23 || ((((_la - 130)) & ~0x3f) == 0 && ((1L << (_la - 130)) & ((1L << (T__129 - 130)) | (1L << (T__130 - 130)) | (1L << (IRIREF - 130)) | (1L << (PNAME_NS - 130)) | (1L << (PNAME_LN - 130)) | (1L << (BLANK_NODE_LABEL - 130)) | (1L << (VAR1 - 130)) | (1L << (VAR2 - 130)) | (1L << (INTEGER - 130)) | (1L << (DECIMAL - 130)) | (1L << (DOUBLE - 130)) | (1L << (INTEGER_POSITIVE - 130)) | (1L << (DECIMAL_POSITIVE - 130)) | (1L << (DOUBLE_POSITIVE - 130)) | (1L << (INTEGER_NEGATIVE - 130)) | (1L << (DECIMAL_NEGATIVE - 130)) | (1L << (DOUBLE_NEGATIVE - 130)) | (1L << (STRING_LITERAL1 - 130)) | (1L << (STRING_LITERAL2 - 130)) | (1L << (STRING_LITERAL_LONG1 - 130)) | (1L << (STRING_LITERAL_LONG2 - 130)) | (1L << (NIL - 130)) | (1L << (ANON - 130)))) != 0)) {
					{
					setState(573);
					triplesTemplate();
					}
				}

				}
				}
				setState(580);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuadsNotTriplesContext extends ParserRuleContext {
		public VarOrIriContext varOrIri() {
			return getRuleContext(VarOrIriContext.class,0);
		}
		public TriplesTemplateContext triplesTemplate() {
			return getRuleContext(TriplesTemplateContext.class,0);
		}
		public QuadsNotTriplesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quadsNotTriples; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterQuadsNotTriples(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitQuadsNotTriples(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitQuadsNotTriples(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuadsNotTriplesContext quadsNotTriples() throws RecognitionException {
		QuadsNotTriplesContext _localctx = new QuadsNotTriplesContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_quadsNotTriples);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(581);
			match(T__38);
			setState(582);
			varOrIri();
			setState(583);
			match(T__15);
			setState(585);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9 || _la==T__23 || ((((_la - 130)) & ~0x3f) == 0 && ((1L << (_la - 130)) & ((1L << (T__129 - 130)) | (1L << (T__130 - 130)) | (1L << (IRIREF - 130)) | (1L << (PNAME_NS - 130)) | (1L << (PNAME_LN - 130)) | (1L << (BLANK_NODE_LABEL - 130)) | (1L << (VAR1 - 130)) | (1L << (VAR2 - 130)) | (1L << (INTEGER - 130)) | (1L << (DECIMAL - 130)) | (1L << (DOUBLE - 130)) | (1L << (INTEGER_POSITIVE - 130)) | (1L << (DECIMAL_POSITIVE - 130)) | (1L << (DOUBLE_POSITIVE - 130)) | (1L << (INTEGER_NEGATIVE - 130)) | (1L << (DECIMAL_NEGATIVE - 130)) | (1L << (DOUBLE_NEGATIVE - 130)) | (1L << (STRING_LITERAL1 - 130)) | (1L << (STRING_LITERAL2 - 130)) | (1L << (STRING_LITERAL_LONG1 - 130)) | (1L << (STRING_LITERAL_LONG2 - 130)) | (1L << (NIL - 130)) | (1L << (ANON - 130)))) != 0)) {
				{
				setState(584);
				triplesTemplate();
				}
			}

			setState(587);
			match(T__16);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TriplesTemplateContext extends ParserRuleContext {
		public TriplesSameSubjectContext triplesSameSubject() {
			return getRuleContext(TriplesSameSubjectContext.class,0);
		}
		public TriplesTemplateContext triplesTemplate() {
			return getRuleContext(TriplesTemplateContext.class,0);
		}
		public TriplesTemplateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_triplesTemplate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterTriplesTemplate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitTriplesTemplate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitTriplesTemplate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TriplesTemplateContext triplesTemplate() throws RecognitionException {
		TriplesTemplateContext _localctx = new TriplesTemplateContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_triplesTemplate);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(589);
			triplesSameSubject();
			setState(594);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__39) {
				{
				setState(590);
				match(T__39);
				setState(592);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__9 || _la==T__23 || ((((_la - 130)) & ~0x3f) == 0 && ((1L << (_la - 130)) & ((1L << (T__129 - 130)) | (1L << (T__130 - 130)) | (1L << (IRIREF - 130)) | (1L << (PNAME_NS - 130)) | (1L << (PNAME_LN - 130)) | (1L << (BLANK_NODE_LABEL - 130)) | (1L << (VAR1 - 130)) | (1L << (VAR2 - 130)) | (1L << (INTEGER - 130)) | (1L << (DECIMAL - 130)) | (1L << (DOUBLE - 130)) | (1L << (INTEGER_POSITIVE - 130)) | (1L << (DECIMAL_POSITIVE - 130)) | (1L << (DOUBLE_POSITIVE - 130)) | (1L << (INTEGER_NEGATIVE - 130)) | (1L << (DECIMAL_NEGATIVE - 130)) | (1L << (DOUBLE_NEGATIVE - 130)) | (1L << (STRING_LITERAL1 - 130)) | (1L << (STRING_LITERAL2 - 130)) | (1L << (STRING_LITERAL_LONG1 - 130)) | (1L << (STRING_LITERAL_LONG2 - 130)) | (1L << (NIL - 130)) | (1L << (ANON - 130)))) != 0)) {
					{
					setState(591);
					triplesTemplate();
					}
				}

				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GroupGraphPatternContext extends ParserRuleContext {
		public SubSelectContext subSelect() {
			return getRuleContext(SubSelectContext.class,0);
		}
		public GroupGraphPatternSubContext groupGraphPatternSub() {
			return getRuleContext(GroupGraphPatternSubContext.class,0);
		}
		public GroupGraphPatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupGraphPattern; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterGroupGraphPattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitGroupGraphPattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitGroupGraphPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupGraphPatternContext groupGraphPattern() throws RecognitionException {
		GroupGraphPatternContext _localctx = new GroupGraphPatternContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_groupGraphPattern);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(596);
			match(T__15);
			setState(599);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__7:
				{
				setState(597);
				subSelect();
				}
				break;
			case T__9:
			case T__15:
			case T__16:
			case T__21:
			case T__23:
			case T__36:
			case T__38:
			case T__40:
			case T__41:
			case T__43:
			case T__45:
			case T__47:
			case T__129:
			case T__130:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
			case BLANK_NODE_LABEL:
			case VAR1:
			case VAR2:
			case INTEGER:
			case DECIMAL:
			case DOUBLE:
			case INTEGER_POSITIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE_POSITIVE:
			case INTEGER_NEGATIVE:
			case DECIMAL_NEGATIVE:
			case DOUBLE_NEGATIVE:
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
			case NIL:
			case ANON:
				{
				setState(598);
				groupGraphPatternSub();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(601);
			match(T__16);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GroupGraphPatternSubContext extends ParserRuleContext {
		public List<TriplesBlockContext> triplesBlock() {
			return getRuleContexts(TriplesBlockContext.class);
		}
		public TriplesBlockContext triplesBlock(int i) {
			return getRuleContext(TriplesBlockContext.class,i);
		}
		public List<GraphPatternNotTriplesContext> graphPatternNotTriples() {
			return getRuleContexts(GraphPatternNotTriplesContext.class);
		}
		public GraphPatternNotTriplesContext graphPatternNotTriples(int i) {
			return getRuleContext(GraphPatternNotTriplesContext.class,i);
		}
		public GroupGraphPatternSubContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupGraphPatternSub; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterGroupGraphPatternSub(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitGroupGraphPatternSub(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitGroupGraphPatternSub(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupGraphPatternSubContext groupGraphPatternSub() throws RecognitionException {
		GroupGraphPatternSubContext _localctx = new GroupGraphPatternSubContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_groupGraphPatternSub);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(604);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9 || _la==T__23 || ((((_la - 130)) & ~0x3f) == 0 && ((1L << (_la - 130)) & ((1L << (T__129 - 130)) | (1L << (T__130 - 130)) | (1L << (IRIREF - 130)) | (1L << (PNAME_NS - 130)) | (1L << (PNAME_LN - 130)) | (1L << (BLANK_NODE_LABEL - 130)) | (1L << (VAR1 - 130)) | (1L << (VAR2 - 130)) | (1L << (INTEGER - 130)) | (1L << (DECIMAL - 130)) | (1L << (DOUBLE - 130)) | (1L << (INTEGER_POSITIVE - 130)) | (1L << (DECIMAL_POSITIVE - 130)) | (1L << (DOUBLE_POSITIVE - 130)) | (1L << (INTEGER_NEGATIVE - 130)) | (1L << (DECIMAL_NEGATIVE - 130)) | (1L << (DOUBLE_NEGATIVE - 130)) | (1L << (STRING_LITERAL1 - 130)) | (1L << (STRING_LITERAL2 - 130)) | (1L << (STRING_LITERAL_LONG1 - 130)) | (1L << (STRING_LITERAL_LONG2 - 130)) | (1L << (NIL - 130)) | (1L << (ANON - 130)))) != 0)) {
				{
				setState(603);
				triplesBlock();
				}
			}

			setState(615);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << T__21) | (1L << T__36) | (1L << T__38) | (1L << T__40) | (1L << T__41) | (1L << T__43) | (1L << T__45) | (1L << T__47))) != 0)) {
				{
				{
				setState(606);
				graphPatternNotTriples();
				setState(608);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__39) {
					{
					setState(607);
					match(T__39);
					}
				}

				setState(611);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__9 || _la==T__23 || ((((_la - 130)) & ~0x3f) == 0 && ((1L << (_la - 130)) & ((1L << (T__129 - 130)) | (1L << (T__130 - 130)) | (1L << (IRIREF - 130)) | (1L << (PNAME_NS - 130)) | (1L << (PNAME_LN - 130)) | (1L << (BLANK_NODE_LABEL - 130)) | (1L << (VAR1 - 130)) | (1L << (VAR2 - 130)) | (1L << (INTEGER - 130)) | (1L << (DECIMAL - 130)) | (1L << (DOUBLE - 130)) | (1L << (INTEGER_POSITIVE - 130)) | (1L << (DECIMAL_POSITIVE - 130)) | (1L << (DOUBLE_POSITIVE - 130)) | (1L << (INTEGER_NEGATIVE - 130)) | (1L << (DECIMAL_NEGATIVE - 130)) | (1L << (DOUBLE_NEGATIVE - 130)) | (1L << (STRING_LITERAL1 - 130)) | (1L << (STRING_LITERAL2 - 130)) | (1L << (STRING_LITERAL_LONG1 - 130)) | (1L << (STRING_LITERAL_LONG2 - 130)) | (1L << (NIL - 130)) | (1L << (ANON - 130)))) != 0)) {
					{
					setState(610);
					triplesBlock();
					}
				}

				}
				}
				setState(617);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TriplesBlockContext extends ParserRuleContext {
		public TriplesSameSubjectPathContext triplesSameSubjectPath() {
			return getRuleContext(TriplesSameSubjectPathContext.class,0);
		}
		public TriplesBlockContext triplesBlock() {
			return getRuleContext(TriplesBlockContext.class,0);
		}
		public TriplesBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_triplesBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterTriplesBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitTriplesBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitTriplesBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TriplesBlockContext triplesBlock() throws RecognitionException {
		TriplesBlockContext _localctx = new TriplesBlockContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_triplesBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(618);
			triplesSameSubjectPath();
			setState(623);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__39) {
				{
				setState(619);
				match(T__39);
				setState(621);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__9 || _la==T__23 || ((((_la - 130)) & ~0x3f) == 0 && ((1L << (_la - 130)) & ((1L << (T__129 - 130)) | (1L << (T__130 - 130)) | (1L << (IRIREF - 130)) | (1L << (PNAME_NS - 130)) | (1L << (PNAME_LN - 130)) | (1L << (BLANK_NODE_LABEL - 130)) | (1L << (VAR1 - 130)) | (1L << (VAR2 - 130)) | (1L << (INTEGER - 130)) | (1L << (DECIMAL - 130)) | (1L << (DOUBLE - 130)) | (1L << (INTEGER_POSITIVE - 130)) | (1L << (DECIMAL_POSITIVE - 130)) | (1L << (DOUBLE_POSITIVE - 130)) | (1L << (INTEGER_NEGATIVE - 130)) | (1L << (DECIMAL_NEGATIVE - 130)) | (1L << (DOUBLE_NEGATIVE - 130)) | (1L << (STRING_LITERAL1 - 130)) | (1L << (STRING_LITERAL2 - 130)) | (1L << (STRING_LITERAL_LONG1 - 130)) | (1L << (STRING_LITERAL_LONG2 - 130)) | (1L << (NIL - 130)) | (1L << (ANON - 130)))) != 0)) {
					{
					setState(620);
					triplesBlock();
					}
				}

				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GraphPatternNotTriplesContext extends ParserRuleContext {
		public GroupOrUnionGraphPatternContext groupOrUnionGraphPattern() {
			return getRuleContext(GroupOrUnionGraphPatternContext.class,0);
		}
		public OptionalGraphPatternContext optionalGraphPattern() {
			return getRuleContext(OptionalGraphPatternContext.class,0);
		}
		public MinusGraphPatternContext minusGraphPattern() {
			return getRuleContext(MinusGraphPatternContext.class,0);
		}
		public GraphGraphPatternContext graphGraphPattern() {
			return getRuleContext(GraphGraphPatternContext.class,0);
		}
		public WindowGraphPatternContext windowGraphPattern() {
			return getRuleContext(WindowGraphPatternContext.class,0);
		}
		public ServiceGraphPatternContext serviceGraphPattern() {
			return getRuleContext(ServiceGraphPatternContext.class,0);
		}
		public FilterContext filter() {
			return getRuleContext(FilterContext.class,0);
		}
		public BindContext bind() {
			return getRuleContext(BindContext.class,0);
		}
		public InlineDataContext inlineData() {
			return getRuleContext(InlineDataContext.class,0);
		}
		public GraphPatternNotTriplesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_graphPatternNotTriples; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterGraphPatternNotTriples(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitGraphPatternNotTriples(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitGraphPatternNotTriples(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GraphPatternNotTriplesContext graphPatternNotTriples() throws RecognitionException {
		GraphPatternNotTriplesContext _localctx = new GraphPatternNotTriplesContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_graphPatternNotTriples);
		try {
			setState(634);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__15:
				enterOuterAlt(_localctx, 1);
				{
				setState(625);
				groupOrUnionGraphPattern();
				}
				break;
			case T__40:
				enterOuterAlt(_localctx, 2);
				{
				setState(626);
				optionalGraphPattern();
				}
				break;
			case T__45:
				enterOuterAlt(_localctx, 3);
				{
				setState(627);
				minusGraphPattern();
				}
				break;
			case T__38:
				enterOuterAlt(_localctx, 4);
				{
				setState(628);
				graphGraphPattern();
				}
				break;
			case T__21:
				enterOuterAlt(_localctx, 5);
				{
				setState(629);
				windowGraphPattern();
				}
				break;
			case T__41:
				enterOuterAlt(_localctx, 6);
				{
				setState(630);
				serviceGraphPattern();
				}
				break;
			case T__47:
				enterOuterAlt(_localctx, 7);
				{
				setState(631);
				filter();
				}
				break;
			case T__43:
				enterOuterAlt(_localctx, 8);
				{
				setState(632);
				bind();
				}
				break;
			case T__36:
				enterOuterAlt(_localctx, 9);
				{
				setState(633);
				inlineData();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OptionalGraphPatternContext extends ParserRuleContext {
		public GroupGraphPatternContext groupGraphPattern() {
			return getRuleContext(GroupGraphPatternContext.class,0);
		}
		public OptionalGraphPatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_optionalGraphPattern; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterOptionalGraphPattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitOptionalGraphPattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitOptionalGraphPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OptionalGraphPatternContext optionalGraphPattern() throws RecognitionException {
		OptionalGraphPatternContext _localctx = new OptionalGraphPatternContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_optionalGraphPattern);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(636);
			match(T__40);
			setState(637);
			groupGraphPattern();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GraphGraphPatternContext extends ParserRuleContext {
		public VarOrIriContext varOrIri() {
			return getRuleContext(VarOrIriContext.class,0);
		}
		public GroupGraphPatternContext groupGraphPattern() {
			return getRuleContext(GroupGraphPatternContext.class,0);
		}
		public GraphGraphPatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_graphGraphPattern; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterGraphGraphPattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitGraphGraphPattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitGraphGraphPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GraphGraphPatternContext graphGraphPattern() throws RecognitionException {
		GraphGraphPatternContext _localctx = new GraphGraphPatternContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_graphGraphPattern);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(639);
			match(T__38);
			setState(640);
			varOrIri();
			setState(641);
			groupGraphPattern();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WindowGraphPatternContext extends ParserRuleContext {
		public VarOrIriContext varOrIri() {
			return getRuleContext(VarOrIriContext.class,0);
		}
		public GroupGraphPatternContext groupGraphPattern() {
			return getRuleContext(GroupGraphPatternContext.class,0);
		}
		public WindowGraphPatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_windowGraphPattern; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterWindowGraphPattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitWindowGraphPattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitWindowGraphPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WindowGraphPatternContext windowGraphPattern() throws RecognitionException {
		WindowGraphPatternContext _localctx = new WindowGraphPatternContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_windowGraphPattern);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(643);
			match(T__21);
			setState(644);
			varOrIri();
			setState(645);
			groupGraphPattern();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ServiceGraphPatternContext extends ParserRuleContext {
		public VarOrIriContext varOrIri() {
			return getRuleContext(VarOrIriContext.class,0);
		}
		public GroupGraphPatternContext groupGraphPattern() {
			return getRuleContext(GroupGraphPatternContext.class,0);
		}
		public ServiceGraphPatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_serviceGraphPattern; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterServiceGraphPattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitServiceGraphPattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitServiceGraphPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ServiceGraphPatternContext serviceGraphPattern() throws RecognitionException {
		ServiceGraphPatternContext _localctx = new ServiceGraphPatternContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_serviceGraphPattern);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(647);
			match(T__41);
			setState(649);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__42) {
				{
				setState(648);
				match(T__42);
				}
			}

			setState(651);
			varOrIri();
			setState(652);
			groupGraphPattern();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BindContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public BindContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bind; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterBind(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitBind(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitBind(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BindContext bind() throws RecognitionException {
		BindContext _localctx = new BindContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_bind);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(654);
			match(T__43);
			setState(655);
			match(T__9);
			setState(656);
			expression();
			setState(657);
			match(T__3);
			setState(658);
			var();
			setState(659);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InlineDataContext extends ParserRuleContext {
		public DataBlockContext dataBlock() {
			return getRuleContext(DataBlockContext.class,0);
		}
		public InlineDataContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineData; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterInlineData(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitInlineData(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitInlineData(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InlineDataContext inlineData() throws RecognitionException {
		InlineDataContext _localctx = new InlineDataContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_inlineData);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(661);
			match(T__36);
			setState(662);
			dataBlock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DataBlockContext extends ParserRuleContext {
		public InlineDataOneVarContext inlineDataOneVar() {
			return getRuleContext(InlineDataOneVarContext.class,0);
		}
		public InlineDataFullContext inlineDataFull() {
			return getRuleContext(InlineDataFullContext.class,0);
		}
		public DataBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterDataBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitDataBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitDataBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DataBlockContext dataBlock() throws RecognitionException {
		DataBlockContext _localctx = new DataBlockContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_dataBlock);
		try {
			setState(666);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VAR1:
			case VAR2:
				enterOuterAlt(_localctx, 1);
				{
				setState(664);
				inlineDataOneVar();
				}
				break;
			case T__9:
			case NIL:
				enterOuterAlt(_localctx, 2);
				{
				setState(665);
				inlineDataFull();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InlineDataOneVarContext extends ParserRuleContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public List<DataBlockValueContext> dataBlockValue() {
			return getRuleContexts(DataBlockValueContext.class);
		}
		public DataBlockValueContext dataBlockValue(int i) {
			return getRuleContext(DataBlockValueContext.class,i);
		}
		public InlineDataOneVarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineDataOneVar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterInlineDataOneVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitInlineDataOneVar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitInlineDataOneVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InlineDataOneVarContext inlineDataOneVar() throws RecognitionException {
		InlineDataOneVarContext _localctx = new InlineDataOneVarContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_inlineDataOneVar);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(668);
			var();
			setState(669);
			match(T__15);
			setState(673);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__44 || ((((_la - 130)) & ~0x3f) == 0 && ((1L << (_la - 130)) & ((1L << (T__129 - 130)) | (1L << (T__130 - 130)) | (1L << (IRIREF - 130)) | (1L << (PNAME_NS - 130)) | (1L << (PNAME_LN - 130)) | (1L << (INTEGER - 130)) | (1L << (DECIMAL - 130)) | (1L << (DOUBLE - 130)) | (1L << (INTEGER_POSITIVE - 130)) | (1L << (DECIMAL_POSITIVE - 130)) | (1L << (DOUBLE_POSITIVE - 130)) | (1L << (INTEGER_NEGATIVE - 130)) | (1L << (DECIMAL_NEGATIVE - 130)) | (1L << (DOUBLE_NEGATIVE - 130)) | (1L << (STRING_LITERAL1 - 130)) | (1L << (STRING_LITERAL2 - 130)) | (1L << (STRING_LITERAL_LONG1 - 130)) | (1L << (STRING_LITERAL_LONG2 - 130)))) != 0)) {
				{
				{
				setState(670);
				dataBlockValue();
				}
				}
				setState(675);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(676);
			match(T__16);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InlineDataFullContext extends ParserRuleContext {
		public List<TerminalNode> NIL() { return getTokens(RSPQLParser.NIL); }
		public TerminalNode NIL(int i) {
			return getToken(RSPQLParser.NIL, i);
		}
		public List<DataBlockValuesContext> dataBlockValues() {
			return getRuleContexts(DataBlockValuesContext.class);
		}
		public DataBlockValuesContext dataBlockValues(int i) {
			return getRuleContext(DataBlockValuesContext.class,i);
		}
		public List<VarContext> var() {
			return getRuleContexts(VarContext.class);
		}
		public VarContext var(int i) {
			return getRuleContext(VarContext.class,i);
		}
		public InlineDataFullContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineDataFull; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterInlineDataFull(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitInlineDataFull(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitInlineDataFull(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InlineDataFullContext inlineDataFull() throws RecognitionException {
		InlineDataFullContext _localctx = new InlineDataFullContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_inlineDataFull);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(687);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NIL:
				{
				setState(678);
				match(NIL);
				}
				break;
			case T__9:
				{
				setState(679);
				match(T__9);
				setState(683);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==VAR1 || _la==VAR2) {
					{
					{
					setState(680);
					var();
					}
					}
					setState(685);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(686);
				match(T__10);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(689);
			match(T__15);
			setState(694);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9 || _la==NIL) {
				{
				setState(692);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__9:
					{
					setState(690);
					dataBlockValues();
					}
					break;
				case NIL:
					{
					setState(691);
					match(NIL);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(696);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(697);
			match(T__16);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DataBlockValuesContext extends ParserRuleContext {
		public List<DataBlockValueContext> dataBlockValue() {
			return getRuleContexts(DataBlockValueContext.class);
		}
		public DataBlockValueContext dataBlockValue(int i) {
			return getRuleContext(DataBlockValueContext.class,i);
		}
		public DataBlockValuesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataBlockValues; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterDataBlockValues(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitDataBlockValues(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitDataBlockValues(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DataBlockValuesContext dataBlockValues() throws RecognitionException {
		DataBlockValuesContext _localctx = new DataBlockValuesContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_dataBlockValues);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(699);
			match(T__9);
			setState(703);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__44 || ((((_la - 130)) & ~0x3f) == 0 && ((1L << (_la - 130)) & ((1L << (T__129 - 130)) | (1L << (T__130 - 130)) | (1L << (IRIREF - 130)) | (1L << (PNAME_NS - 130)) | (1L << (PNAME_LN - 130)) | (1L << (INTEGER - 130)) | (1L << (DECIMAL - 130)) | (1L << (DOUBLE - 130)) | (1L << (INTEGER_POSITIVE - 130)) | (1L << (DECIMAL_POSITIVE - 130)) | (1L << (DOUBLE_POSITIVE - 130)) | (1L << (INTEGER_NEGATIVE - 130)) | (1L << (DECIMAL_NEGATIVE - 130)) | (1L << (DOUBLE_NEGATIVE - 130)) | (1L << (STRING_LITERAL1 - 130)) | (1L << (STRING_LITERAL2 - 130)) | (1L << (STRING_LITERAL_LONG1 - 130)) | (1L << (STRING_LITERAL_LONG2 - 130)))) != 0)) {
				{
				{
				setState(700);
				dataBlockValue();
				}
				}
				setState(705);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(706);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DataBlockValueContext extends ParserRuleContext {
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public RdfliteralContext rdfliteral() {
			return getRuleContext(RdfliteralContext.class,0);
		}
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public BooleanLiteralContext booleanLiteral() {
			return getRuleContext(BooleanLiteralContext.class,0);
		}
		public UndefContext undef() {
			return getRuleContext(UndefContext.class,0);
		}
		public DataBlockValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataBlockValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterDataBlockValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitDataBlockValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitDataBlockValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DataBlockValueContext dataBlockValue() throws RecognitionException {
		DataBlockValueContext _localctx = new DataBlockValueContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_dataBlockValue);
		try {
			setState(713);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(708);
				iri();
				}
				break;
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
				enterOuterAlt(_localctx, 2);
				{
				setState(709);
				rdfliteral();
				}
				break;
			case INTEGER:
			case DECIMAL:
			case DOUBLE:
			case INTEGER_POSITIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE_POSITIVE:
			case INTEGER_NEGATIVE:
			case DECIMAL_NEGATIVE:
			case DOUBLE_NEGATIVE:
				enterOuterAlt(_localctx, 3);
				{
				setState(710);
				numericLiteral();
				}
				break;
			case T__129:
			case T__130:
				enterOuterAlt(_localctx, 4);
				{
				setState(711);
				booleanLiteral();
				}
				break;
			case T__44:
				enterOuterAlt(_localctx, 5);
				{
				setState(712);
				undef();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UndefContext extends ParserRuleContext {
		public UndefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_undef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterUndef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitUndef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitUndef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UndefContext undef() throws RecognitionException {
		UndefContext _localctx = new UndefContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_undef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(715);
			match(T__44);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MinusGraphPatternContext extends ParserRuleContext {
		public GroupGraphPatternContext groupGraphPattern() {
			return getRuleContext(GroupGraphPatternContext.class,0);
		}
		public MinusGraphPatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_minusGraphPattern; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterMinusGraphPattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitMinusGraphPattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitMinusGraphPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MinusGraphPatternContext minusGraphPattern() throws RecognitionException {
		MinusGraphPatternContext _localctx = new MinusGraphPatternContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_minusGraphPattern);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(717);
			match(T__45);
			setState(718);
			groupGraphPattern();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GroupOrUnionGraphPatternContext extends ParserRuleContext {
		public List<GroupGraphPatternContext> groupGraphPattern() {
			return getRuleContexts(GroupGraphPatternContext.class);
		}
		public GroupGraphPatternContext groupGraphPattern(int i) {
			return getRuleContext(GroupGraphPatternContext.class,i);
		}
		public GroupOrUnionGraphPatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupOrUnionGraphPattern; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterGroupOrUnionGraphPattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitGroupOrUnionGraphPattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitGroupOrUnionGraphPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupOrUnionGraphPatternContext groupOrUnionGraphPattern() throws RecognitionException {
		GroupOrUnionGraphPatternContext _localctx = new GroupOrUnionGraphPatternContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_groupOrUnionGraphPattern);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(720);
			groupGraphPattern();
			setState(725);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__46) {
				{
				{
				setState(721);
				match(T__46);
				setState(722);
				groupGraphPattern();
				}
				}
				setState(727);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FilterContext extends ParserRuleContext {
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public FilterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_filter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterFilter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitFilter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitFilter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FilterContext filter() throws RecognitionException {
		FilterContext _localctx = new FilterContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_filter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(728);
			match(T__47);
			setState(729);
			constraint();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstraintContext extends ParserRuleContext {
		public BrackettedExpressionContext brackettedExpression() {
			return getRuleContext(BrackettedExpressionContext.class,0);
		}
		public BuiltInCallContext builtInCall() {
			return getRuleContext(BuiltInCallContext.class,0);
		}
		public FunctionCallContext functionCall() {
			return getRuleContext(FunctionCallContext.class,0);
		}
		public ConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitConstraint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitConstraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstraintContext constraint() throws RecognitionException {
		ConstraintContext _localctx = new ConstraintContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_constraint);
		try {
			setState(734);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(731);
				brackettedExpression();
				}
				break;
			case T__65:
			case T__67:
			case T__68:
			case T__69:
			case T__70:
			case T__71:
			case T__72:
			case T__73:
			case T__74:
			case T__75:
			case T__76:
			case T__77:
			case T__78:
			case T__79:
			case T__80:
			case T__81:
			case T__82:
			case T__83:
			case T__84:
			case T__85:
			case T__86:
			case T__87:
			case T__88:
			case T__89:
			case T__90:
			case T__91:
			case T__92:
			case T__93:
			case T__94:
			case T__95:
			case T__96:
			case T__97:
			case T__98:
			case T__99:
			case T__100:
			case T__101:
			case T__102:
			case T__103:
			case T__104:
			case T__105:
			case T__106:
			case T__107:
			case T__108:
			case T__109:
			case T__110:
			case T__111:
			case T__112:
			case T__113:
			case T__114:
			case T__115:
			case T__116:
			case T__117:
			case T__118:
			case T__119:
			case T__120:
			case T__121:
			case T__122:
			case T__123:
			case T__124:
			case T__125:
			case T__126:
				enterOuterAlt(_localctx, 2);
				{
				setState(732);
				builtInCall();
				}
				break;
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 3);
				{
				setState(733);
				functionCall();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionCallContext extends ParserRuleContext {
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public ArgListContext argList() {
			return getRuleContext(ArgListContext.class,0);
		}
		public FunctionCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterFunctionCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitFunctionCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitFunctionCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionCallContext functionCall() throws RecognitionException {
		FunctionCallContext _localctx = new FunctionCallContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_functionCall);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(736);
			iri();
			setState(737);
			argList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgListContext extends ParserRuleContext {
		public TerminalNode NIL() { return getToken(RSPQLParser.NIL, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public DistinctContext distinct() {
			return getRuleContext(DistinctContext.class,0);
		}
		public ArgListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterArgList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitArgList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitArgList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgListContext argList() throws RecognitionException {
		ArgListContext _localctx = new ArgListContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_argList);
		int _la;
		try {
			setState(754);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NIL:
				enterOuterAlt(_localctx, 1);
				{
				setState(739);
				match(NIL);
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 2);
				{
				setState(740);
				match(T__9);
				setState(742);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__11) {
					{
					setState(741);
					distinct();
					}
				}

				setState(744);
				expression();
				setState(749);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__48) {
					{
					{
					setState(745);
					match(T__48);
					setState(746);
					expression();
					}
					}
					setState(751);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(752);
				match(T__10);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionListContext extends ParserRuleContext {
		public TerminalNode NIL() { return getToken(RSPQLParser.NIL, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterExpressionList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitExpressionList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitExpressionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_expressionList);
		int _la;
		try {
			setState(768);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NIL:
				enterOuterAlt(_localctx, 1);
				{
				setState(756);
				match(NIL);
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 2);
				{
				setState(757);
				match(T__9);
				setState(758);
				expression();
				setState(763);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__48) {
					{
					{
					setState(759);
					match(T__48);
					setState(760);
					expression();
					}
					}
					setState(765);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(766);
				match(T__10);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstructTemplateContext extends ParserRuleContext {
		public QuadsContext quads() {
			return getRuleContext(QuadsContext.class,0);
		}
		public ConstructTemplateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructTemplate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterConstructTemplate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitConstructTemplate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitConstructTemplate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructTemplateContext constructTemplate() throws RecognitionException {
		ConstructTemplateContext _localctx = new ConstructTemplateContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_constructTemplate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(770);
			match(T__15);
			setState(771);
			quads();
			setState(772);
			match(T__16);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstructTriplesContext extends ParserRuleContext {
		public TriplesSameSubjectContext triplesSameSubject() {
			return getRuleContext(TriplesSameSubjectContext.class,0);
		}
		public ConstructTriplesContext constructTriples() {
			return getRuleContext(ConstructTriplesContext.class,0);
		}
		public ConstructTriplesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructTriples; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterConstructTriples(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitConstructTriples(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitConstructTriples(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructTriplesContext constructTriples() throws RecognitionException {
		ConstructTriplesContext _localctx = new ConstructTriplesContext(_ctx, getState());
		enterRule(_localctx, 150, RULE_constructTriples);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(774);
			triplesSameSubject();
			setState(779);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,72,_ctx) ) {
			case 1:
				{
				setState(775);
				match(T__39);
				setState(777);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,71,_ctx) ) {
				case 1:
					{
					setState(776);
					constructTriples();
					}
					break;
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TriplesSameSubjectContext extends ParserRuleContext {
		public VarOrTermContext varOrTerm() {
			return getRuleContext(VarOrTermContext.class,0);
		}
		public PropertyListNotEmptyContext propertyListNotEmpty() {
			return getRuleContext(PropertyListNotEmptyContext.class,0);
		}
		public TriplesNodeContext triplesNode() {
			return getRuleContext(TriplesNodeContext.class,0);
		}
		public TriplesSameSubjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_triplesSameSubject; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterTriplesSameSubject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitTriplesSameSubject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitTriplesSameSubject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TriplesSameSubjectContext triplesSameSubject() throws RecognitionException {
		TriplesSameSubjectContext _localctx = new TriplesSameSubjectContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_triplesSameSubject);
		int _la;
		try {
			setState(788);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__129:
			case T__130:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
			case BLANK_NODE_LABEL:
			case VAR1:
			case VAR2:
			case INTEGER:
			case DECIMAL:
			case DOUBLE:
			case INTEGER_POSITIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE_POSITIVE:
			case INTEGER_NEGATIVE:
			case DECIMAL_NEGATIVE:
			case DOUBLE_NEGATIVE:
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
			case NIL:
			case ANON:
				enterOuterAlt(_localctx, 1);
				{
				setState(781);
				varOrTerm();
				setState(782);
				propertyListNotEmpty();
				}
				break;
			case T__9:
			case T__23:
				enterOuterAlt(_localctx, 2);
				{
				setState(784);
				triplesNode();
				setState(786);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 132)) & ~0x3f) == 0 && ((1L << (_la - 132)) & ((1L << (TYPE - 132)) | (1L << (IRIREF - 132)) | (1L << (PNAME_NS - 132)) | (1L << (PNAME_LN - 132)) | (1L << (VAR1 - 132)) | (1L << (VAR2 - 132)))) != 0)) {
					{
					setState(785);
					propertyListNotEmpty();
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyListNotEmptyContext extends ParserRuleContext {
		public List<PropertyListContext> propertyList() {
			return getRuleContexts(PropertyListContext.class);
		}
		public PropertyListContext propertyList(int i) {
			return getRuleContext(PropertyListContext.class,i);
		}
		public PropertyListNotEmptyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyListNotEmpty; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterPropertyListNotEmpty(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitPropertyListNotEmpty(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitPropertyListNotEmpty(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyListNotEmptyContext propertyListNotEmpty() throws RecognitionException {
		PropertyListNotEmptyContext _localctx = new PropertyListNotEmptyContext(_ctx, getState());
		enterRule(_localctx, 154, RULE_propertyListNotEmpty);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(790);
			propertyList();
			setState(797);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__49) {
				{
				{
				setState(791);
				match(T__49);
				setState(793);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 132)) & ~0x3f) == 0 && ((1L << (_la - 132)) & ((1L << (TYPE - 132)) | (1L << (IRIREF - 132)) | (1L << (PNAME_NS - 132)) | (1L << (PNAME_LN - 132)) | (1L << (VAR1 - 132)) | (1L << (VAR2 - 132)))) != 0)) {
					{
					setState(792);
					propertyList();
					}
				}

				}
				}
				setState(799);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyListContext extends ParserRuleContext {
		public VerbContext verb() {
			return getRuleContext(VerbContext.class,0);
		}
		public ObjectListContext objectList() {
			return getRuleContext(ObjectListContext.class,0);
		}
		public PropertyListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterPropertyList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitPropertyList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitPropertyList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyListContext propertyList() throws RecognitionException {
		PropertyListContext _localctx = new PropertyListContext(_ctx, getState());
		enterRule(_localctx, 156, RULE_propertyList);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(800);
			verb();
			setState(801);
			objectList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VerbContext extends ParserRuleContext {
		public VarOrIriContext varOrIri() {
			return getRuleContext(VarOrIriContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public VerbContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_verb; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterVerb(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitVerb(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitVerb(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VerbContext verb() throws RecognitionException {
		VerbContext _localctx = new VerbContext(_ctx, getState());
		enterRule(_localctx, 158, RULE_verb);
		try {
			setState(805);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
			case VAR1:
			case VAR2:
				enterOuterAlt(_localctx, 1);
				{
				setState(803);
				varOrIri();
				}
				break;
			case TYPE:
				enterOuterAlt(_localctx, 2);
				{
				setState(804);
				type();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode TYPE() { return getToken(RSPQLParser.TYPE, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 160, RULE_type);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(807);
			match(TYPE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ObjectListContext extends ParserRuleContext {
		public List<ObjectContext> object() {
			return getRuleContexts(ObjectContext.class);
		}
		public ObjectContext object(int i) {
			return getRuleContext(ObjectContext.class,i);
		}
		public ObjectListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_objectList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterObjectList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitObjectList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitObjectList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ObjectListContext objectList() throws RecognitionException {
		ObjectListContext _localctx = new ObjectListContext(_ctx, getState());
		enterRule(_localctx, 162, RULE_objectList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(809);
			object();
			setState(814);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__48) {
				{
				{
				setState(810);
				match(T__48);
				setState(811);
				object();
				}
				}
				setState(816);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ObjectContext extends ParserRuleContext {
		public GraphNodeContext graphNode() {
			return getRuleContext(GraphNodeContext.class,0);
		}
		public ObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_object; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitObject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitObject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ObjectContext object() throws RecognitionException {
		ObjectContext _localctx = new ObjectContext(_ctx, getState());
		enterRule(_localctx, 164, RULE_object);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(817);
			graphNode();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TriplesSameSubjectPathContext extends ParserRuleContext {
		public VarOrTermContext varOrTerm() {
			return getRuleContext(VarOrTermContext.class,0);
		}
		public PropertyListPathNotEmptyContext propertyListPathNotEmpty() {
			return getRuleContext(PropertyListPathNotEmptyContext.class,0);
		}
		public TriplesNodePathContext triplesNodePath() {
			return getRuleContext(TriplesNodePathContext.class,0);
		}
		public TriplesSameSubjectPathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_triplesSameSubjectPath; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterTriplesSameSubjectPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitTriplesSameSubjectPath(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitTriplesSameSubjectPath(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TriplesSameSubjectPathContext triplesSameSubjectPath() throws RecognitionException {
		TriplesSameSubjectPathContext _localctx = new TriplesSameSubjectPathContext(_ctx, getState());
		enterRule(_localctx, 166, RULE_triplesSameSubjectPath);
		int _la;
		try {
			setState(826);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__129:
			case T__130:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
			case BLANK_NODE_LABEL:
			case VAR1:
			case VAR2:
			case INTEGER:
			case DECIMAL:
			case DOUBLE:
			case INTEGER_POSITIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE_POSITIVE:
			case INTEGER_NEGATIVE:
			case DECIMAL_NEGATIVE:
			case DOUBLE_NEGATIVE:
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
			case NIL:
			case ANON:
				enterOuterAlt(_localctx, 1);
				{
				setState(819);
				varOrTerm();
				setState(820);
				propertyListPathNotEmpty();
				}
				break;
			case T__9:
			case T__23:
				enterOuterAlt(_localctx, 2);
				{
				setState(822);
				triplesNodePath();
				setState(824);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__52) | (1L << T__55))) != 0) || ((((_la - 132)) & ~0x3f) == 0 && ((1L << (_la - 132)) & ((1L << (TYPE - 132)) | (1L << (IRIREF - 132)) | (1L << (PNAME_NS - 132)) | (1L << (PNAME_LN - 132)) | (1L << (VAR1 - 132)) | (1L << (VAR2 - 132)))) != 0)) {
					{
					setState(823);
					propertyListPathNotEmpty();
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyListPathNotEmptyContext extends ParserRuleContext {
		public List<PropertyListPathContext> propertyListPath() {
			return getRuleContexts(PropertyListPathContext.class);
		}
		public PropertyListPathContext propertyListPath(int i) {
			return getRuleContext(PropertyListPathContext.class,i);
		}
		public PropertyListPathNotEmptyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyListPathNotEmpty; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterPropertyListPathNotEmpty(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitPropertyListPathNotEmpty(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitPropertyListPathNotEmpty(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyListPathNotEmptyContext propertyListPathNotEmpty() throws RecognitionException {
		PropertyListPathNotEmptyContext _localctx = new PropertyListPathNotEmptyContext(_ctx, getState());
		enterRule(_localctx, 168, RULE_propertyListPathNotEmpty);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(828);
			propertyListPath();
			setState(833);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__49) {
				{
				{
				setState(829);
				match(T__49);
				setState(830);
				propertyListPath();
				}
				}
				setState(835);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyListPathContext extends ParserRuleContext {
		public ObjectListPathContext objectListPath() {
			return getRuleContext(ObjectListPathContext.class,0);
		}
		public VerbPathContext verbPath() {
			return getRuleContext(VerbPathContext.class,0);
		}
		public VerbSimpleContext verbSimple() {
			return getRuleContext(VerbSimpleContext.class,0);
		}
		public PropertyListPathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyListPath; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterPropertyListPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitPropertyListPath(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitPropertyListPath(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyListPathContext propertyListPath() throws RecognitionException {
		PropertyListPathContext _localctx = new PropertyListPathContext(_ctx, getState());
		enterRule(_localctx, 170, RULE_propertyListPath);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(838);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,82,_ctx) ) {
			case 1:
				{
				setState(836);
				verbPath();
				}
				break;
			case 2:
				{
				setState(837);
				verbSimple();
				}
				break;
			}
			setState(840);
			objectListPath();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VerbPathContext extends ParserRuleContext {
		public PathContext path() {
			return getRuleContext(PathContext.class,0);
		}
		public VerbPathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_verbPath; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterVerbPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitVerbPath(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitVerbPath(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VerbPathContext verbPath() throws RecognitionException {
		VerbPathContext _localctx = new VerbPathContext(_ctx, getState());
		enterRule(_localctx, 172, RULE_verbPath);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(842);
			path();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VerbSimpleContext extends ParserRuleContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public VerbSimpleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_verbSimple; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterVerbSimple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitVerbSimple(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitVerbSimple(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VerbSimpleContext verbSimple() throws RecognitionException {
		VerbSimpleContext _localctx = new VerbSimpleContext(_ctx, getState());
		enterRule(_localctx, 174, RULE_verbSimple);
		try {
			setState(846);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VAR1:
			case VAR2:
				enterOuterAlt(_localctx, 1);
				{
				setState(844);
				var();
				}
				break;
			case TYPE:
				enterOuterAlt(_localctx, 2);
				{
				setState(845);
				type();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ObjectListPathContext extends ParserRuleContext {
		public List<ObjectPathContext> objectPath() {
			return getRuleContexts(ObjectPathContext.class);
		}
		public ObjectPathContext objectPath(int i) {
			return getRuleContext(ObjectPathContext.class,i);
		}
		public ObjectListPathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_objectListPath; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterObjectListPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitObjectListPath(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitObjectListPath(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ObjectListPathContext objectListPath() throws RecognitionException {
		ObjectListPathContext _localctx = new ObjectListPathContext(_ctx, getState());
		enterRule(_localctx, 176, RULE_objectListPath);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(848);
			objectPath();
			setState(853);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__48) {
				{
				{
				setState(849);
				match(T__48);
				setState(850);
				objectPath();
				}
				}
				setState(855);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ObjectPathContext extends ParserRuleContext {
		public GraphNodePathContext graphNodePath() {
			return getRuleContext(GraphNodePathContext.class,0);
		}
		public ObjectPathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_objectPath; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterObjectPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitObjectPath(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitObjectPath(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ObjectPathContext objectPath() throws RecognitionException {
		ObjectPathContext _localctx = new ObjectPathContext(_ctx, getState());
		enterRule(_localctx, 178, RULE_objectPath);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(856);
			graphNodePath();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PathContext extends ParserRuleContext {
		public PathAlternativeContext pathAlternative() {
			return getRuleContext(PathAlternativeContext.class,0);
		}
		public PathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_path; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitPath(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitPath(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathContext path() throws RecognitionException {
		PathContext _localctx = new PathContext(_ctx, getState());
		enterRule(_localctx, 180, RULE_path);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(858);
			pathAlternative();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PathAlternativeContext extends ParserRuleContext {
		public List<PathSequenceContext> pathSequence() {
			return getRuleContexts(PathSequenceContext.class);
		}
		public PathSequenceContext pathSequence(int i) {
			return getRuleContext(PathSequenceContext.class,i);
		}
		public PathAlternativeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathAlternative; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterPathAlternative(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitPathAlternative(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitPathAlternative(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathAlternativeContext pathAlternative() throws RecognitionException {
		PathAlternativeContext _localctx = new PathAlternativeContext(_ctx, getState());
		enterRule(_localctx, 182, RULE_pathAlternative);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(860);
			pathSequence();
			setState(865);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__50) {
				{
				{
				setState(861);
				match(T__50);
				setState(862);
				pathSequence();
				}
				}
				setState(867);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PathSequenceContext extends ParserRuleContext {
		public List<PathEltOrInverseContext> pathEltOrInverse() {
			return getRuleContexts(PathEltOrInverseContext.class);
		}
		public PathEltOrInverseContext pathEltOrInverse(int i) {
			return getRuleContext(PathEltOrInverseContext.class,i);
		}
		public PathSequenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathSequence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterPathSequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitPathSequence(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitPathSequence(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathSequenceContext pathSequence() throws RecognitionException {
		PathSequenceContext _localctx = new PathSequenceContext(_ctx, getState());
		enterRule(_localctx, 184, RULE_pathSequence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(868);
			pathEltOrInverse();
			setState(873);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__51) {
				{
				{
				setState(869);
				match(T__51);
				setState(870);
				pathEltOrInverse();
				}
				}
				setState(875);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PathEltContext extends ParserRuleContext {
		public PathPrimaryContext pathPrimary() {
			return getRuleContext(PathPrimaryContext.class,0);
		}
		public PathModContext pathMod() {
			return getRuleContext(PathModContext.class,0);
		}
		public PathEltContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathElt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterPathElt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitPathElt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitPathElt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathEltContext pathElt() throws RecognitionException {
		PathEltContext _localctx = new PathEltContext(_ctx, getState());
		enterRule(_localctx, 186, RULE_pathElt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(876);
			pathPrimary();
			setState(878);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__53) | (1L << T__54))) != 0)) {
				{
				setState(877);
				pathMod();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PathEltOrInverseContext extends ParserRuleContext {
		public PathEltContext pathElt() {
			return getRuleContext(PathEltContext.class,0);
		}
		public PathEltOrInverseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathEltOrInverse; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterPathEltOrInverse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitPathEltOrInverse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitPathEltOrInverse(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathEltOrInverseContext pathEltOrInverse() throws RecognitionException {
		PathEltOrInverseContext _localctx = new PathEltOrInverseContext(_ctx, getState());
		enterRule(_localctx, 188, RULE_pathEltOrInverse);
		try {
			setState(883);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__9:
			case T__55:
			case TYPE:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(880);
				pathElt();
				}
				break;
			case T__52:
				enterOuterAlt(_localctx, 2);
				{
				setState(881);
				match(T__52);
				setState(882);
				pathElt();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PathModContext extends ParserRuleContext {
		public PathModContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathMod; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterPathMod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitPathMod(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitPathMod(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathModContext pathMod() throws RecognitionException {
		PathModContext _localctx = new PathModContext(_ctx, getState());
		enterRule(_localctx, 190, RULE_pathMod);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(885);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__53) | (1L << T__54))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PathPrimaryContext extends ParserRuleContext {
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public PathNegatedPropertySetContext pathNegatedPropertySet() {
			return getRuleContext(PathNegatedPropertySetContext.class,0);
		}
		public PathContext path() {
			return getRuleContext(PathContext.class,0);
		}
		public PathPrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathPrimary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterPathPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitPathPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitPathPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathPrimaryContext pathPrimary() throws RecognitionException {
		PathPrimaryContext _localctx = new PathPrimaryContext(_ctx, getState());
		enterRule(_localctx, 192, RULE_pathPrimary);
		try {
			setState(895);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(887);
				iri();
				}
				break;
			case TYPE:
				enterOuterAlt(_localctx, 2);
				{
				setState(888);
				type();
				}
				break;
			case T__55:
				enterOuterAlt(_localctx, 3);
				{
				setState(889);
				match(T__55);
				setState(890);
				pathNegatedPropertySet();
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 4);
				{
				setState(891);
				match(T__9);
				setState(892);
				path();
				setState(893);
				match(T__10);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PathNegatedPropertySetContext extends ParserRuleContext {
		public List<PathOneInPropertySetContext> pathOneInPropertySet() {
			return getRuleContexts(PathOneInPropertySetContext.class);
		}
		public PathOneInPropertySetContext pathOneInPropertySet(int i) {
			return getRuleContext(PathOneInPropertySetContext.class,i);
		}
		public PathNegatedPropertySetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathNegatedPropertySet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterPathNegatedPropertySet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitPathNegatedPropertySet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitPathNegatedPropertySet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathNegatedPropertySetContext pathNegatedPropertySet() throws RecognitionException {
		PathNegatedPropertySetContext _localctx = new PathNegatedPropertySetContext(_ctx, getState());
		enterRule(_localctx, 194, RULE_pathNegatedPropertySet);
		int _la;
		try {
			setState(910);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__52:
			case TYPE:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(897);
				pathOneInPropertySet();
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 2);
				{
				setState(898);
				match(T__9);
				setState(907);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__52 || ((((_la - 132)) & ~0x3f) == 0 && ((1L << (_la - 132)) & ((1L << (TYPE - 132)) | (1L << (IRIREF - 132)) | (1L << (PNAME_NS - 132)) | (1L << (PNAME_LN - 132)))) != 0)) {
					{
					setState(899);
					pathOneInPropertySet();
					setState(904);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__50) {
						{
						{
						setState(900);
						match(T__50);
						setState(901);
						pathOneInPropertySet();
						}
						}
						setState(906);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(909);
				match(T__10);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PathOneInPropertySetContext extends ParserRuleContext {
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public PathOneInPropertySetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathOneInPropertySet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterPathOneInPropertySet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitPathOneInPropertySet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitPathOneInPropertySet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathOneInPropertySetContext pathOneInPropertySet() throws RecognitionException {
		PathOneInPropertySetContext _localctx = new PathOneInPropertySetContext(_ctx, getState());
		enterRule(_localctx, 196, RULE_pathOneInPropertySet);
		try {
			setState(919);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(912);
				iri();
				}
				break;
			case TYPE:
				enterOuterAlt(_localctx, 2);
				{
				setState(913);
				type();
				}
				break;
			case T__52:
				enterOuterAlt(_localctx, 3);
				{
				setState(914);
				match(T__52);
				setState(917);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case IRIREF:
				case PNAME_NS:
				case PNAME_LN:
					{
					setState(915);
					iri();
					}
					break;
				case TYPE:
					{
					setState(916);
					type();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntegerContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(RSPQLParser.INTEGER, 0); }
		public IntegerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterInteger(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitInteger(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitInteger(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntegerContext integer() throws RecognitionException {
		IntegerContext _localctx = new IntegerContext(_ctx, getState());
		enterRule(_localctx, 198, RULE_integer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(921);
			match(INTEGER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TriplesNodeContext extends ParserRuleContext {
		public CollectionContext collection() {
			return getRuleContext(CollectionContext.class,0);
		}
		public BlankNodePropertyListContext blankNodePropertyList() {
			return getRuleContext(BlankNodePropertyListContext.class,0);
		}
		public TriplesNodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_triplesNode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterTriplesNode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitTriplesNode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitTriplesNode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TriplesNodeContext triplesNode() throws RecognitionException {
		TriplesNodeContext _localctx = new TriplesNodeContext(_ctx, getState());
		enterRule(_localctx, 200, RULE_triplesNode);
		try {
			setState(925);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(923);
				collection();
				}
				break;
			case T__23:
				enterOuterAlt(_localctx, 2);
				{
				setState(924);
				blankNodePropertyList();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlankNodePropertyListContext extends ParserRuleContext {
		public PropertyListNotEmptyContext propertyListNotEmpty() {
			return getRuleContext(PropertyListNotEmptyContext.class,0);
		}
		public BlankNodePropertyListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blankNodePropertyList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterBlankNodePropertyList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitBlankNodePropertyList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitBlankNodePropertyList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlankNodePropertyListContext blankNodePropertyList() throws RecognitionException {
		BlankNodePropertyListContext _localctx = new BlankNodePropertyListContext(_ctx, getState());
		enterRule(_localctx, 202, RULE_blankNodePropertyList);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(927);
			match(T__23);
			setState(928);
			propertyListNotEmpty();
			setState(929);
			match(T__24);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TriplesNodePathContext extends ParserRuleContext {
		public CollectionPathContext collectionPath() {
			return getRuleContext(CollectionPathContext.class,0);
		}
		public BlankNodePropertyListPathContext blankNodePropertyListPath() {
			return getRuleContext(BlankNodePropertyListPathContext.class,0);
		}
		public TriplesNodePathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_triplesNodePath; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterTriplesNodePath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitTriplesNodePath(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitTriplesNodePath(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TriplesNodePathContext triplesNodePath() throws RecognitionException {
		TriplesNodePathContext _localctx = new TriplesNodePathContext(_ctx, getState());
		enterRule(_localctx, 204, RULE_triplesNodePath);
		try {
			setState(933);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(931);
				collectionPath();
				}
				break;
			case T__23:
				enterOuterAlt(_localctx, 2);
				{
				setState(932);
				blankNodePropertyListPath();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlankNodePropertyListPathContext extends ParserRuleContext {
		public PropertyListPathNotEmptyContext propertyListPathNotEmpty() {
			return getRuleContext(PropertyListPathNotEmptyContext.class,0);
		}
		public BlankNodePropertyListPathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blankNodePropertyListPath; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterBlankNodePropertyListPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitBlankNodePropertyListPath(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitBlankNodePropertyListPath(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlankNodePropertyListPathContext blankNodePropertyListPath() throws RecognitionException {
		BlankNodePropertyListPathContext _localctx = new BlankNodePropertyListPathContext(_ctx, getState());
		enterRule(_localctx, 206, RULE_blankNodePropertyListPath);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(935);
			match(T__23);
			setState(936);
			propertyListPathNotEmpty();
			setState(937);
			match(T__24);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CollectionContext extends ParserRuleContext {
		public List<GraphNodeContext> graphNode() {
			return getRuleContexts(GraphNodeContext.class);
		}
		public GraphNodeContext graphNode(int i) {
			return getRuleContext(GraphNodeContext.class,i);
		}
		public CollectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_collection; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterCollection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitCollection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitCollection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CollectionContext collection() throws RecognitionException {
		CollectionContext _localctx = new CollectionContext(_ctx, getState());
		enterRule(_localctx, 208, RULE_collection);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(939);
			match(T__9);
			setState(941); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(940);
				graphNode();
				}
				}
				setState(943); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__9 || _la==T__23 || ((((_la - 130)) & ~0x3f) == 0 && ((1L << (_la - 130)) & ((1L << (T__129 - 130)) | (1L << (T__130 - 130)) | (1L << (IRIREF - 130)) | (1L << (PNAME_NS - 130)) | (1L << (PNAME_LN - 130)) | (1L << (BLANK_NODE_LABEL - 130)) | (1L << (VAR1 - 130)) | (1L << (VAR2 - 130)) | (1L << (INTEGER - 130)) | (1L << (DECIMAL - 130)) | (1L << (DOUBLE - 130)) | (1L << (INTEGER_POSITIVE - 130)) | (1L << (DECIMAL_POSITIVE - 130)) | (1L << (DOUBLE_POSITIVE - 130)) | (1L << (INTEGER_NEGATIVE - 130)) | (1L << (DECIMAL_NEGATIVE - 130)) | (1L << (DOUBLE_NEGATIVE - 130)) | (1L << (STRING_LITERAL1 - 130)) | (1L << (STRING_LITERAL2 - 130)) | (1L << (STRING_LITERAL_LONG1 - 130)) | (1L << (STRING_LITERAL_LONG2 - 130)) | (1L << (NIL - 130)) | (1L << (ANON - 130)))) != 0) );
			setState(945);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CollectionPathContext extends ParserRuleContext {
		public List<GraphNodePathContext> graphNodePath() {
			return getRuleContexts(GraphNodePathContext.class);
		}
		public GraphNodePathContext graphNodePath(int i) {
			return getRuleContext(GraphNodePathContext.class,i);
		}
		public CollectionPathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_collectionPath; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterCollectionPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitCollectionPath(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitCollectionPath(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CollectionPathContext collectionPath() throws RecognitionException {
		CollectionPathContext _localctx = new CollectionPathContext(_ctx, getState());
		enterRule(_localctx, 210, RULE_collectionPath);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(947);
			match(T__9);
			setState(949); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(948);
				graphNodePath();
				}
				}
				setState(951); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__9 || _la==T__23 || ((((_la - 130)) & ~0x3f) == 0 && ((1L << (_la - 130)) & ((1L << (T__129 - 130)) | (1L << (T__130 - 130)) | (1L << (IRIREF - 130)) | (1L << (PNAME_NS - 130)) | (1L << (PNAME_LN - 130)) | (1L << (BLANK_NODE_LABEL - 130)) | (1L << (VAR1 - 130)) | (1L << (VAR2 - 130)) | (1L << (INTEGER - 130)) | (1L << (DECIMAL - 130)) | (1L << (DOUBLE - 130)) | (1L << (INTEGER_POSITIVE - 130)) | (1L << (DECIMAL_POSITIVE - 130)) | (1L << (DOUBLE_POSITIVE - 130)) | (1L << (INTEGER_NEGATIVE - 130)) | (1L << (DECIMAL_NEGATIVE - 130)) | (1L << (DOUBLE_NEGATIVE - 130)) | (1L << (STRING_LITERAL1 - 130)) | (1L << (STRING_LITERAL2 - 130)) | (1L << (STRING_LITERAL_LONG1 - 130)) | (1L << (STRING_LITERAL_LONG2 - 130)) | (1L << (NIL - 130)) | (1L << (ANON - 130)))) != 0) );
			setState(953);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GraphNodeContext extends ParserRuleContext {
		public VarOrTermContext varOrTerm() {
			return getRuleContext(VarOrTermContext.class,0);
		}
		public TriplesNodeContext triplesNode() {
			return getRuleContext(TriplesNodeContext.class,0);
		}
		public GraphNodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_graphNode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterGraphNode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitGraphNode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitGraphNode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GraphNodeContext graphNode() throws RecognitionException {
		GraphNodeContext _localctx = new GraphNodeContext(_ctx, getState());
		enterRule(_localctx, 212, RULE_graphNode);
		try {
			setState(957);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__129:
			case T__130:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
			case BLANK_NODE_LABEL:
			case VAR1:
			case VAR2:
			case INTEGER:
			case DECIMAL:
			case DOUBLE:
			case INTEGER_POSITIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE_POSITIVE:
			case INTEGER_NEGATIVE:
			case DECIMAL_NEGATIVE:
			case DOUBLE_NEGATIVE:
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
			case NIL:
			case ANON:
				enterOuterAlt(_localctx, 1);
				{
				setState(955);
				varOrTerm();
				}
				break;
			case T__9:
			case T__23:
				enterOuterAlt(_localctx, 2);
				{
				setState(956);
				triplesNode();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GraphNodePathContext extends ParserRuleContext {
		public VarOrTermContext varOrTerm() {
			return getRuleContext(VarOrTermContext.class,0);
		}
		public TriplesNodePathContext triplesNodePath() {
			return getRuleContext(TriplesNodePathContext.class,0);
		}
		public GraphNodePathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_graphNodePath; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterGraphNodePath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitGraphNodePath(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitGraphNodePath(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GraphNodePathContext graphNodePath() throws RecognitionException {
		GraphNodePathContext _localctx = new GraphNodePathContext(_ctx, getState());
		enterRule(_localctx, 214, RULE_graphNodePath);
		try {
			setState(961);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__129:
			case T__130:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
			case BLANK_NODE_LABEL:
			case VAR1:
			case VAR2:
			case INTEGER:
			case DECIMAL:
			case DOUBLE:
			case INTEGER_POSITIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE_POSITIVE:
			case INTEGER_NEGATIVE:
			case DECIMAL_NEGATIVE:
			case DOUBLE_NEGATIVE:
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
			case NIL:
			case ANON:
				enterOuterAlt(_localctx, 1);
				{
				setState(959);
				varOrTerm();
				}
				break;
			case T__9:
			case T__23:
				enterOuterAlt(_localctx, 2);
				{
				setState(960);
				triplesNodePath();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarOrTermContext extends ParserRuleContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public GraphTermContext graphTerm() {
			return getRuleContext(GraphTermContext.class,0);
		}
		public VarOrTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varOrTerm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterVarOrTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitVarOrTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitVarOrTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarOrTermContext varOrTerm() throws RecognitionException {
		VarOrTermContext _localctx = new VarOrTermContext(_ctx, getState());
		enterRule(_localctx, 216, RULE_varOrTerm);
		try {
			setState(965);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VAR1:
			case VAR2:
				enterOuterAlt(_localctx, 1);
				{
				setState(963);
				var();
				}
				break;
			case T__129:
			case T__130:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
			case BLANK_NODE_LABEL:
			case INTEGER:
			case DECIMAL:
			case DOUBLE:
			case INTEGER_POSITIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE_POSITIVE:
			case INTEGER_NEGATIVE:
			case DECIMAL_NEGATIVE:
			case DOUBLE_NEGATIVE:
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
			case NIL:
			case ANON:
				enterOuterAlt(_localctx, 2);
				{
				setState(964);
				graphTerm();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarOrIriContext extends ParserRuleContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public VarOrIriContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varOrIri; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterVarOrIri(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitVarOrIri(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitVarOrIri(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarOrIriContext varOrIri() throws RecognitionException {
		VarOrIriContext _localctx = new VarOrIriContext(_ctx, getState());
		enterRule(_localctx, 218, RULE_varOrIri);
		try {
			setState(969);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VAR1:
			case VAR2:
				enterOuterAlt(_localctx, 1);
				{
				setState(967);
				var();
				}
				break;
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 2);
				{
				setState(968);
				iri();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarContext extends ParserRuleContext {
		public TerminalNode VAR1() { return getToken(RSPQLParser.VAR1, 0); }
		public TerminalNode VAR2() { return getToken(RSPQLParser.VAR2, 0); }
		public VarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitVar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarContext var() throws RecognitionException {
		VarContext _localctx = new VarContext(_ctx, getState());
		enterRule(_localctx, 220, RULE_var);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(971);
			_la = _input.LA(1);
			if ( !(_la==VAR1 || _la==VAR2) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GraphTermContext extends ParserRuleContext {
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public RdfliteralContext rdfliteral() {
			return getRuleContext(RdfliteralContext.class,0);
		}
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public BooleanLiteralContext booleanLiteral() {
			return getRuleContext(BooleanLiteralContext.class,0);
		}
		public BlankNodeContext blankNode() {
			return getRuleContext(BlankNodeContext.class,0);
		}
		public TerminalNode NIL() { return getToken(RSPQLParser.NIL, 0); }
		public GraphTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_graphTerm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterGraphTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitGraphTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitGraphTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GraphTermContext graphTerm() throws RecognitionException {
		GraphTermContext _localctx = new GraphTermContext(_ctx, getState());
		enterRule(_localctx, 222, RULE_graphTerm);
		try {
			setState(979);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(973);
				iri();
				}
				break;
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
				enterOuterAlt(_localctx, 2);
				{
				setState(974);
				rdfliteral();
				}
				break;
			case INTEGER:
			case DECIMAL:
			case DOUBLE:
			case INTEGER_POSITIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE_POSITIVE:
			case INTEGER_NEGATIVE:
			case DECIMAL_NEGATIVE:
			case DOUBLE_NEGATIVE:
				enterOuterAlt(_localctx, 3);
				{
				setState(975);
				numericLiteral();
				}
				break;
			case T__129:
			case T__130:
				enterOuterAlt(_localctx, 4);
				{
				setState(976);
				booleanLiteral();
				}
				break;
			case BLANK_NODE_LABEL:
			case ANON:
				enterOuterAlt(_localctx, 5);
				{
				setState(977);
				blankNode();
				}
				break;
			case NIL:
				enterOuterAlt(_localctx, 6);
				{
				setState(978);
				match(NIL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ConditionalOrExpressionContext conditionalOrExpression() {
			return getRuleContext(ConditionalOrExpressionContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 224, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(981);
			conditionalOrExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionalOrExpressionContext extends ParserRuleContext {
		public List<ConditionalAndExpressionContext> conditionalAndExpression() {
			return getRuleContexts(ConditionalAndExpressionContext.class);
		}
		public ConditionalAndExpressionContext conditionalAndExpression(int i) {
			return getRuleContext(ConditionalAndExpressionContext.class,i);
		}
		public ConditionalOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionalOrExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterConditionalOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitConditionalOrExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitConditionalOrExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalOrExpressionContext conditionalOrExpression() throws RecognitionException {
		ConditionalOrExpressionContext _localctx = new ConditionalOrExpressionContext(_ctx, getState());
		enterRule(_localctx, 226, RULE_conditionalOrExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(983);
			conditionalAndExpression();
			setState(988);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__56) {
				{
				{
				setState(984);
				match(T__56);
				setState(985);
				conditionalAndExpression();
				}
				}
				setState(990);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionalAndExpressionContext extends ParserRuleContext {
		public List<ValueLogicalContext> valueLogical() {
			return getRuleContexts(ValueLogicalContext.class);
		}
		public ValueLogicalContext valueLogical(int i) {
			return getRuleContext(ValueLogicalContext.class,i);
		}
		public ConditionalAndExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionalAndExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterConditionalAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitConditionalAndExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitConditionalAndExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalAndExpressionContext conditionalAndExpression() throws RecognitionException {
		ConditionalAndExpressionContext _localctx = new ConditionalAndExpressionContext(_ctx, getState());
		enterRule(_localctx, 228, RULE_conditionalAndExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(991);
			valueLogical();
			setState(996);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__57) {
				{
				{
				setState(992);
				match(T__57);
				setState(993);
				valueLogical();
				}
				}
				setState(998);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueLogicalContext extends ParserRuleContext {
		public RelationalExpressionContext relationalExpression() {
			return getRuleContext(RelationalExpressionContext.class,0);
		}
		public ValueLogicalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueLogical; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterValueLogical(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitValueLogical(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitValueLogical(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueLogicalContext valueLogical() throws RecognitionException {
		ValueLogicalContext _localctx = new ValueLogicalContext(_ctx, getState());
		enterRule(_localctx, 230, RULE_valueLogical);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(999);
			relationalExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelationalExpressionContext extends ParserRuleContext {
		public List<NumericExpressionContext> numericExpression() {
			return getRuleContexts(NumericExpressionContext.class);
		}
		public NumericExpressionContext numericExpression(int i) {
			return getRuleContext(NumericExpressionContext.class,i);
		}
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public RelationalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationalExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterRelationalExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitRelationalExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitRelationalExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationalExpressionContext relationalExpression() throws RecognitionException {
		RelationalExpressionContext _localctx = new RelationalExpressionContext(_ctx, getState());
		enterRule(_localctx, 232, RULE_relationalExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1001);
			numericExpression();
			setState(1019);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__58:
				{
				setState(1002);
				match(T__58);
				setState(1003);
				numericExpression();
				}
				break;
			case T__59:
				{
				setState(1004);
				match(T__59);
				setState(1005);
				numericExpression();
				}
				break;
			case T__60:
				{
				setState(1006);
				match(T__60);
				setState(1007);
				numericExpression();
				}
				break;
			case T__61:
				{
				setState(1008);
				match(T__61);
				setState(1009);
				numericExpression();
				}
				break;
			case T__62:
				{
				setState(1010);
				match(T__62);
				setState(1011);
				numericExpression();
				}
				break;
			case T__63:
				{
				setState(1012);
				match(T__63);
				setState(1013);
				numericExpression();
				}
				break;
			case T__64:
				{
				setState(1014);
				match(T__64);
				setState(1015);
				expressionList();
				}
				break;
			case T__65:
				{
				setState(1016);
				match(T__65);
				setState(1017);
				match(T__64);
				setState(1018);
				expressionList();
				}
				break;
			case T__3:
			case T__10:
			case T__48:
			case T__49:
			case T__56:
			case T__57:
				break;
			default:
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumericExpressionContext extends ParserRuleContext {
		public AdditiveExpressionContext additiveExpression() {
			return getRuleContext(AdditiveExpressionContext.class,0);
		}
		public NumericExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterNumericExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitNumericExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitNumericExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericExpressionContext numericExpression() throws RecognitionException {
		NumericExpressionContext _localctx = new NumericExpressionContext(_ctx, getState());
		enterRule(_localctx, 234, RULE_numericExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1021);
			additiveExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AdditiveExpressionContext extends ParserRuleContext {
		public MultiplicativeExpressionContext multiplicativeExpression() {
			return getRuleContext(MultiplicativeExpressionContext.class,0);
		}
		public List<MultiExprContext> multiExpr() {
			return getRuleContexts(MultiExprContext.class);
		}
		public MultiExprContext multiExpr(int i) {
			return getRuleContext(MultiExprContext.class,i);
		}
		public AdditiveExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additiveExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterAdditiveExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitAdditiveExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitAdditiveExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdditiveExpressionContext additiveExpression() throws RecognitionException {
		AdditiveExpressionContext _localctx = new AdditiveExpressionContext(_ctx, getState());
		enterRule(_localctx, 236, RULE_additiveExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1023);
			multiplicativeExpression();
			setState(1027);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__54 || _la==T__66 || ((((_la - 145)) & ~0x3f) == 0 && ((1L << (_la - 145)) & ((1L << (INTEGER_POSITIVE - 145)) | (1L << (DECIMAL_POSITIVE - 145)) | (1L << (DOUBLE_POSITIVE - 145)) | (1L << (INTEGER_NEGATIVE - 145)) | (1L << (DECIMAL_NEGATIVE - 145)) | (1L << (DOUBLE_NEGATIVE - 145)))) != 0)) {
				{
				{
				setState(1024);
				multiExpr();
				}
				}
				setState(1029);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultiExprContext extends ParserRuleContext {
		public MultiplicativeExpressionContext multiplicativeExpression() {
			return getRuleContext(MultiplicativeExpressionContext.class,0);
		}
		public NumericLiteralPositiveContext numericLiteralPositive() {
			return getRuleContext(NumericLiteralPositiveContext.class,0);
		}
		public NumericLiteralNegativeContext numericLiteralNegative() {
			return getRuleContext(NumericLiteralNegativeContext.class,0);
		}
		public List<UnaryExpressionContext> unaryExpression() {
			return getRuleContexts(UnaryExpressionContext.class);
		}
		public UnaryExpressionContext unaryExpression(int i) {
			return getRuleContext(UnaryExpressionContext.class,i);
		}
		public MultiExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterMultiExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitMultiExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitMultiExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiExprContext multiExpr() throws RecognitionException {
		MultiExprContext _localctx = new MultiExprContext(_ctx, getState());
		enterRule(_localctx, 238, RULE_multiExpr);
		int _la;
		try {
			setState(1047);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__54:
				enterOuterAlt(_localctx, 1);
				{
				setState(1030);
				match(T__54);
				setState(1031);
				multiplicativeExpression();
				}
				break;
			case T__66:
				enterOuterAlt(_localctx, 2);
				{
				setState(1032);
				match(T__66);
				setState(1033);
				multiplicativeExpression();
				}
				break;
			case INTEGER_POSITIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE_POSITIVE:
			case INTEGER_NEGATIVE:
			case DECIMAL_NEGATIVE:
			case DOUBLE_NEGATIVE:
				enterOuterAlt(_localctx, 3);
				{
				setState(1036);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case INTEGER_POSITIVE:
				case DECIMAL_POSITIVE:
				case DOUBLE_POSITIVE:
					{
					setState(1034);
					numericLiteralPositive();
					}
					break;
				case INTEGER_NEGATIVE:
				case DECIMAL_NEGATIVE:
				case DOUBLE_NEGATIVE:
					{
					setState(1035);
					numericLiteralNegative();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1044);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__8 || _la==T__51) {
					{
					setState(1042);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case T__8:
						{
						setState(1038);
						match(T__8);
						setState(1039);
						unaryExpression();
						}
						break;
					case T__51:
						{
						setState(1040);
						match(T__51);
						setState(1041);
						unaryExpression();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(1046);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultiplicativeExpressionContext extends ParserRuleContext {
		public List<UnaryExpressionContext> unaryExpression() {
			return getRuleContexts(UnaryExpressionContext.class);
		}
		public UnaryExpressionContext unaryExpression(int i) {
			return getRuleContext(UnaryExpressionContext.class,i);
		}
		public MultiplicativeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicativeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterMultiplicativeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitMultiplicativeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitMultiplicativeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplicativeExpressionContext multiplicativeExpression() throws RecognitionException {
		MultiplicativeExpressionContext _localctx = new MultiplicativeExpressionContext(_ctx, getState());
		enterRule(_localctx, 240, RULE_multiplicativeExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1049);
			unaryExpression();
			setState(1056);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__8 || _la==T__51) {
				{
				setState(1054);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__8:
					{
					setState(1050);
					match(T__8);
					setState(1051);
					unaryExpression();
					}
					break;
				case T__51:
					{
					setState(1052);
					match(T__51);
					setState(1053);
					unaryExpression();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(1058);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnaryExpressionContext extends ParserRuleContext {
		public PrimaryExpressionContext primaryExpression() {
			return getRuleContext(PrimaryExpressionContext.class,0);
		}
		public UnaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterUnaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitUnaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryExpressionContext unaryExpression() throws RecognitionException {
		UnaryExpressionContext _localctx = new UnaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 242, RULE_unaryExpression);
		try {
			setState(1066);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__55:
				enterOuterAlt(_localctx, 1);
				{
				setState(1059);
				match(T__55);
				setState(1060);
				primaryExpression();
				}
				break;
			case T__54:
				enterOuterAlt(_localctx, 2);
				{
				setState(1061);
				match(T__54);
				setState(1062);
				primaryExpression();
				}
				break;
			case T__66:
				enterOuterAlt(_localctx, 3);
				{
				setState(1063);
				match(T__66);
				setState(1064);
				primaryExpression();
				}
				break;
			case T__9:
			case T__65:
			case T__67:
			case T__68:
			case T__69:
			case T__70:
			case T__71:
			case T__72:
			case T__73:
			case T__74:
			case T__75:
			case T__76:
			case T__77:
			case T__78:
			case T__79:
			case T__80:
			case T__81:
			case T__82:
			case T__83:
			case T__84:
			case T__85:
			case T__86:
			case T__87:
			case T__88:
			case T__89:
			case T__90:
			case T__91:
			case T__92:
			case T__93:
			case T__94:
			case T__95:
			case T__96:
			case T__97:
			case T__98:
			case T__99:
			case T__100:
			case T__101:
			case T__102:
			case T__103:
			case T__104:
			case T__105:
			case T__106:
			case T__107:
			case T__108:
			case T__109:
			case T__110:
			case T__111:
			case T__112:
			case T__113:
			case T__114:
			case T__115:
			case T__116:
			case T__117:
			case T__118:
			case T__119:
			case T__120:
			case T__121:
			case T__122:
			case T__123:
			case T__124:
			case T__125:
			case T__126:
			case T__129:
			case T__130:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
			case VAR1:
			case VAR2:
			case INTEGER:
			case DECIMAL:
			case DOUBLE:
			case INTEGER_POSITIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE_POSITIVE:
			case INTEGER_NEGATIVE:
			case DECIMAL_NEGATIVE:
			case DOUBLE_NEGATIVE:
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
				enterOuterAlt(_localctx, 4);
				{
				setState(1065);
				primaryExpression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryExpressionContext extends ParserRuleContext {
		public BrackettedExpressionContext brackettedExpression() {
			return getRuleContext(BrackettedExpressionContext.class,0);
		}
		public BuiltInCallContext builtInCall() {
			return getRuleContext(BuiltInCallContext.class,0);
		}
		public IriOrFunctionContext iriOrFunction() {
			return getRuleContext(IriOrFunctionContext.class,0);
		}
		public RdfliteralContext rdfliteral() {
			return getRuleContext(RdfliteralContext.class,0);
		}
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public BooleanLiteralContext booleanLiteral() {
			return getRuleContext(BooleanLiteralContext.class,0);
		}
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public PrimaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterPrimaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitPrimaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitPrimaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryExpressionContext primaryExpression() throws RecognitionException {
		PrimaryExpressionContext _localctx = new PrimaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 244, RULE_primaryExpression);
		try {
			setState(1075);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(1068);
				brackettedExpression();
				}
				break;
			case T__65:
			case T__67:
			case T__68:
			case T__69:
			case T__70:
			case T__71:
			case T__72:
			case T__73:
			case T__74:
			case T__75:
			case T__76:
			case T__77:
			case T__78:
			case T__79:
			case T__80:
			case T__81:
			case T__82:
			case T__83:
			case T__84:
			case T__85:
			case T__86:
			case T__87:
			case T__88:
			case T__89:
			case T__90:
			case T__91:
			case T__92:
			case T__93:
			case T__94:
			case T__95:
			case T__96:
			case T__97:
			case T__98:
			case T__99:
			case T__100:
			case T__101:
			case T__102:
			case T__103:
			case T__104:
			case T__105:
			case T__106:
			case T__107:
			case T__108:
			case T__109:
			case T__110:
			case T__111:
			case T__112:
			case T__113:
			case T__114:
			case T__115:
			case T__116:
			case T__117:
			case T__118:
			case T__119:
			case T__120:
			case T__121:
			case T__122:
			case T__123:
			case T__124:
			case T__125:
			case T__126:
				enterOuterAlt(_localctx, 2);
				{
				setState(1069);
				builtInCall();
				}
				break;
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 3);
				{
				setState(1070);
				iriOrFunction();
				}
				break;
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
				enterOuterAlt(_localctx, 4);
				{
				setState(1071);
				rdfliteral();
				}
				break;
			case INTEGER:
			case DECIMAL:
			case DOUBLE:
			case INTEGER_POSITIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE_POSITIVE:
			case INTEGER_NEGATIVE:
			case DECIMAL_NEGATIVE:
			case DOUBLE_NEGATIVE:
				enterOuterAlt(_localctx, 5);
				{
				setState(1072);
				numericLiteral();
				}
				break;
			case T__129:
			case T__130:
				enterOuterAlt(_localctx, 6);
				{
				setState(1073);
				booleanLiteral();
				}
				break;
			case VAR1:
			case VAR2:
				enterOuterAlt(_localctx, 7);
				{
				setState(1074);
				var();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BrackettedExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BrackettedExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_brackettedExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterBrackettedExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitBrackettedExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitBrackettedExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BrackettedExpressionContext brackettedExpression() throws RecognitionException {
		BrackettedExpressionContext _localctx = new BrackettedExpressionContext(_ctx, getState());
		enterRule(_localctx, 246, RULE_brackettedExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1077);
			match(T__9);
			setState(1078);
			expression();
			setState(1079);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BuiltInCallContext extends ParserRuleContext {
		public AggregateContext aggregate() {
			return getRuleContext(AggregateContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public TerminalNode NIL() { return getToken(RSPQLParser.NIL, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public SubstringExpressionContext substringExpression() {
			return getRuleContext(SubstringExpressionContext.class,0);
		}
		public StrReplaceExpressionContext strReplaceExpression() {
			return getRuleContext(StrReplaceExpressionContext.class,0);
		}
		public RegexExpressionContext regexExpression() {
			return getRuleContext(RegexExpressionContext.class,0);
		}
		public ExistsFuncContext existsFunc() {
			return getRuleContext(ExistsFuncContext.class,0);
		}
		public NotExistsFuncContext notExistsFunc() {
			return getRuleContext(NotExistsFuncContext.class,0);
		}
		public BuiltInCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_builtInCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterBuiltInCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitBuiltInCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitBuiltInCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BuiltInCallContext builtInCall() throws RecognitionException {
		BuiltInCallContext _localctx = new BuiltInCallContext(_ctx, getState());
		enterRule(_localctx, 248, RULE_builtInCall);
		try {
			setState(1339);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__120:
			case T__121:
			case T__122:
			case T__123:
			case T__124:
			case T__125:
			case T__126:
				enterOuterAlt(_localctx, 1);
				{
				setState(1081);
				aggregate();
				}
				break;
			case T__67:
				enterOuterAlt(_localctx, 2);
				{
				setState(1082);
				match(T__67);
				setState(1083);
				match(T__9);
				setState(1084);
				expression();
				setState(1085);
				match(T__10);
				}
				break;
			case T__68:
				enterOuterAlt(_localctx, 3);
				{
				setState(1087);
				match(T__68);
				setState(1088);
				match(T__9);
				setState(1089);
				expression();
				setState(1090);
				match(T__10);
				}
				break;
			case T__69:
				enterOuterAlt(_localctx, 4);
				{
				setState(1092);
				match(T__69);
				setState(1093);
				match(T__9);
				setState(1094);
				expression();
				setState(1095);
				match(T__48);
				setState(1096);
				expression();
				setState(1097);
				match(T__10);
				}
				break;
			case T__70:
				enterOuterAlt(_localctx, 5);
				{
				setState(1099);
				match(T__70);
				setState(1100);
				match(T__9);
				setState(1101);
				expression();
				setState(1102);
				match(T__10);
				}
				break;
			case T__71:
				enterOuterAlt(_localctx, 6);
				{
				setState(1104);
				match(T__71);
				setState(1105);
				match(T__9);
				setState(1106);
				var();
				setState(1107);
				match(T__10);
				}
				break;
			case T__72:
				enterOuterAlt(_localctx, 7);
				{
				setState(1109);
				match(T__72);
				setState(1110);
				match(T__9);
				setState(1111);
				expression();
				setState(1112);
				match(T__10);
				}
				break;
			case T__73:
				enterOuterAlt(_localctx, 8);
				{
				setState(1114);
				match(T__73);
				setState(1115);
				match(T__9);
				setState(1116);
				expression();
				setState(1117);
				match(T__10);
				}
				break;
			case T__74:
				enterOuterAlt(_localctx, 9);
				{
				setState(1119);
				match(T__74);
				setState(1125);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__9:
					{
					setState(1120);
					match(T__9);
					setState(1121);
					expression();
					setState(1122);
					match(T__10);
					}
					break;
				case NIL:
					{
					setState(1124);
					match(NIL);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case T__75:
				enterOuterAlt(_localctx, 10);
				{
				setState(1127);
				match(T__75);
				setState(1128);
				match(NIL);
				}
				break;
			case T__76:
				enterOuterAlt(_localctx, 11);
				{
				setState(1129);
				match(T__76);
				setState(1130);
				match(T__9);
				setState(1131);
				expression();
				setState(1132);
				match(T__10);
				}
				break;
			case T__77:
				enterOuterAlt(_localctx, 12);
				{
				setState(1134);
				match(T__77);
				setState(1135);
				match(T__9);
				setState(1136);
				expression();
				setState(1137);
				match(T__10);
				}
				break;
			case T__78:
				enterOuterAlt(_localctx, 13);
				{
				setState(1139);
				match(T__78);
				setState(1140);
				match(T__9);
				setState(1141);
				expression();
				setState(1142);
				match(T__10);
				}
				break;
			case T__79:
				enterOuterAlt(_localctx, 14);
				{
				setState(1144);
				match(T__79);
				setState(1145);
				match(T__9);
				setState(1146);
				expression();
				setState(1147);
				match(T__10);
				}
				break;
			case T__80:
				enterOuterAlt(_localctx, 15);
				{
				setState(1149);
				match(T__80);
				setState(1150);
				expressionList();
				}
				break;
			case T__117:
				enterOuterAlt(_localctx, 16);
				{
				setState(1151);
				substringExpression();
				}
				break;
			case T__81:
				enterOuterAlt(_localctx, 17);
				{
				setState(1152);
				match(T__81);
				setState(1153);
				match(T__9);
				setState(1154);
				expression();
				setState(1155);
				match(T__10);
				}
				break;
			case T__118:
				enterOuterAlt(_localctx, 18);
				{
				setState(1157);
				strReplaceExpression();
				}
				break;
			case T__82:
				enterOuterAlt(_localctx, 19);
				{
				setState(1158);
				match(T__82);
				setState(1159);
				match(T__9);
				setState(1160);
				expression();
				setState(1161);
				match(T__10);
				}
				break;
			case T__83:
				enterOuterAlt(_localctx, 20);
				{
				setState(1163);
				match(T__83);
				setState(1164);
				match(T__9);
				setState(1165);
				expression();
				setState(1166);
				match(T__10);
				}
				break;
			case T__84:
				enterOuterAlt(_localctx, 21);
				{
				setState(1168);
				match(T__84);
				setState(1169);
				match(T__9);
				setState(1170);
				expression();
				setState(1171);
				match(T__10);
				}
				break;
			case T__85:
				enterOuterAlt(_localctx, 22);
				{
				setState(1173);
				match(T__85);
				setState(1174);
				match(T__9);
				setState(1175);
				expression();
				setState(1176);
				match(T__48);
				setState(1177);
				expression();
				setState(1178);
				match(T__10);
				}
				break;
			case T__86:
				enterOuterAlt(_localctx, 23);
				{
				setState(1180);
				match(T__86);
				setState(1181);
				match(T__9);
				setState(1182);
				expression();
				setState(1183);
				match(T__48);
				setState(1184);
				expression();
				setState(1185);
				match(T__10);
				}
				break;
			case T__87:
				enterOuterAlt(_localctx, 24);
				{
				setState(1187);
				match(T__87);
				setState(1188);
				match(T__9);
				setState(1189);
				expression();
				setState(1190);
				match(T__48);
				setState(1191);
				expression();
				setState(1192);
				match(T__10);
				}
				break;
			case T__88:
				enterOuterAlt(_localctx, 25);
				{
				setState(1194);
				match(T__88);
				setState(1195);
				match(T__9);
				setState(1196);
				expression();
				setState(1197);
				match(T__48);
				setState(1198);
				expression();
				setState(1199);
				match(T__10);
				}
				break;
			case T__89:
				enterOuterAlt(_localctx, 26);
				{
				setState(1201);
				match(T__89);
				setState(1202);
				match(T__9);
				setState(1203);
				expression();
				setState(1204);
				match(T__48);
				setState(1205);
				expression();
				setState(1206);
				match(T__10);
				}
				break;
			case T__90:
				enterOuterAlt(_localctx, 27);
				{
				setState(1208);
				match(T__90);
				setState(1209);
				match(T__9);
				setState(1210);
				expression();
				setState(1211);
				match(T__10);
				}
				break;
			case T__91:
				enterOuterAlt(_localctx, 28);
				{
				setState(1213);
				match(T__91);
				setState(1214);
				match(T__9);
				setState(1215);
				expression();
				setState(1216);
				match(T__10);
				}
				break;
			case T__92:
				enterOuterAlt(_localctx, 29);
				{
				setState(1218);
				match(T__92);
				setState(1219);
				match(T__9);
				setState(1220);
				expression();
				setState(1221);
				match(T__10);
				}
				break;
			case T__93:
				enterOuterAlt(_localctx, 30);
				{
				setState(1223);
				match(T__93);
				setState(1224);
				match(T__9);
				setState(1225);
				expression();
				setState(1226);
				match(T__10);
				}
				break;
			case T__94:
				enterOuterAlt(_localctx, 31);
				{
				setState(1228);
				match(T__94);
				setState(1229);
				match(T__9);
				setState(1230);
				expression();
				setState(1231);
				match(T__10);
				}
				break;
			case T__95:
				enterOuterAlt(_localctx, 32);
				{
				setState(1233);
				match(T__95);
				setState(1234);
				match(T__9);
				setState(1235);
				expression();
				setState(1236);
				match(T__10);
				}
				break;
			case T__96:
				enterOuterAlt(_localctx, 33);
				{
				setState(1238);
				match(T__96);
				setState(1239);
				match(T__9);
				setState(1240);
				expression();
				setState(1241);
				match(T__10);
				}
				break;
			case T__97:
				enterOuterAlt(_localctx, 34);
				{
				setState(1243);
				match(T__97);
				setState(1244);
				match(T__9);
				setState(1245);
				expression();
				setState(1246);
				match(T__10);
				}
				break;
			case T__98:
				enterOuterAlt(_localctx, 35);
				{
				setState(1248);
				match(T__98);
				setState(1249);
				match(NIL);
				}
				break;
			case T__99:
				enterOuterAlt(_localctx, 36);
				{
				setState(1250);
				match(T__99);
				setState(1251);
				match(NIL);
				}
				break;
			case T__100:
				enterOuterAlt(_localctx, 37);
				{
				setState(1252);
				match(T__100);
				setState(1253);
				match(NIL);
				}
				break;
			case T__101:
				enterOuterAlt(_localctx, 38);
				{
				setState(1254);
				match(T__101);
				setState(1255);
				match(T__9);
				setState(1256);
				expression();
				setState(1257);
				match(T__10);
				}
				break;
			case T__102:
				enterOuterAlt(_localctx, 39);
				{
				setState(1259);
				match(T__102);
				setState(1260);
				match(T__9);
				setState(1261);
				expression();
				setState(1262);
				match(T__10);
				}
				break;
			case T__103:
				enterOuterAlt(_localctx, 40);
				{
				setState(1264);
				match(T__103);
				setState(1265);
				match(T__9);
				setState(1266);
				expression();
				setState(1267);
				match(T__10);
				}
				break;
			case T__104:
				enterOuterAlt(_localctx, 41);
				{
				setState(1269);
				match(T__104);
				setState(1270);
				match(T__9);
				setState(1271);
				expression();
				setState(1272);
				match(T__10);
				}
				break;
			case T__105:
				enterOuterAlt(_localctx, 42);
				{
				setState(1274);
				match(T__105);
				setState(1275);
				match(T__9);
				setState(1276);
				expression();
				setState(1277);
				match(T__10);
				}
				break;
			case T__106:
				enterOuterAlt(_localctx, 43);
				{
				setState(1279);
				match(T__106);
				setState(1280);
				expressionList();
				}
				break;
			case T__107:
				enterOuterAlt(_localctx, 44);
				{
				setState(1281);
				match(T__107);
				setState(1282);
				match(T__9);
				setState(1283);
				expression();
				setState(1284);
				match(T__48);
				setState(1285);
				expression();
				setState(1286);
				match(T__48);
				setState(1287);
				expression();
				setState(1288);
				match(T__10);
				}
				break;
			case T__108:
				enterOuterAlt(_localctx, 45);
				{
				setState(1290);
				match(T__108);
				setState(1291);
				match(T__9);
				setState(1292);
				expression();
				setState(1293);
				match(T__48);
				setState(1294);
				expression();
				setState(1295);
				match(T__10);
				}
				break;
			case T__109:
				enterOuterAlt(_localctx, 46);
				{
				setState(1297);
				match(T__109);
				setState(1298);
				match(T__9);
				setState(1299);
				expression();
				setState(1300);
				match(T__48);
				setState(1301);
				expression();
				setState(1302);
				match(T__10);
				}
				break;
			case T__110:
				enterOuterAlt(_localctx, 47);
				{
				setState(1304);
				match(T__110);
				setState(1305);
				match(T__9);
				setState(1306);
				expression();
				setState(1307);
				match(T__48);
				setState(1308);
				expression();
				setState(1309);
				match(T__10);
				}
				break;
			case T__111:
				enterOuterAlt(_localctx, 48);
				{
				setState(1311);
				match(T__111);
				setState(1312);
				match(T__9);
				setState(1313);
				expression();
				setState(1314);
				match(T__10);
				}
				break;
			case T__112:
				enterOuterAlt(_localctx, 49);
				{
				setState(1316);
				match(T__112);
				setState(1317);
				match(T__9);
				setState(1318);
				expression();
				setState(1319);
				match(T__10);
				}
				break;
			case T__113:
				enterOuterAlt(_localctx, 50);
				{
				setState(1321);
				match(T__113);
				setState(1322);
				match(T__9);
				setState(1323);
				expression();
				setState(1324);
				match(T__10);
				}
				break;
			case T__114:
				enterOuterAlt(_localctx, 51);
				{
				setState(1326);
				match(T__114);
				setState(1327);
				match(T__9);
				setState(1328);
				expression();
				setState(1329);
				match(T__10);
				}
				break;
			case T__115:
				enterOuterAlt(_localctx, 52);
				{
				setState(1331);
				match(T__115);
				setState(1332);
				match(T__9);
				setState(1333);
				expression();
				setState(1334);
				match(T__10);
				}
				break;
			case T__116:
				enterOuterAlt(_localctx, 53);
				{
				setState(1336);
				regexExpression();
				}
				break;
			case T__119:
				enterOuterAlt(_localctx, 54);
				{
				setState(1337);
				existsFunc();
				}
				break;
			case T__65:
				enterOuterAlt(_localctx, 55);
				{
				setState(1338);
				notExistsFunc();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RegexExpressionContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public RegexExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_regexExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterRegexExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitRegexExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitRegexExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RegexExpressionContext regexExpression() throws RecognitionException {
		RegexExpressionContext _localctx = new RegexExpressionContext(_ctx, getState());
		enterRule(_localctx, 250, RULE_regexExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1341);
			match(T__116);
			setState(1342);
			match(T__9);
			setState(1343);
			expression();
			setState(1344);
			match(T__48);
			setState(1345);
			expression();
			setState(1348);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__48) {
				{
				setState(1346);
				match(T__48);
				setState(1347);
				expression();
				}
			}

			setState(1350);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SubstringExpressionContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public SubstringExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_substringExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterSubstringExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitSubstringExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitSubstringExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubstringExpressionContext substringExpression() throws RecognitionException {
		SubstringExpressionContext _localctx = new SubstringExpressionContext(_ctx, getState());
		enterRule(_localctx, 252, RULE_substringExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1352);
			match(T__117);
			setState(1353);
			match(T__9);
			setState(1354);
			expression();
			setState(1355);
			match(T__48);
			setState(1356);
			expression();
			setState(1359);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__48) {
				{
				setState(1357);
				match(T__48);
				setState(1358);
				expression();
				}
			}

			setState(1361);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StrReplaceExpressionContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public StrReplaceExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_strReplaceExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterStrReplaceExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitStrReplaceExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitStrReplaceExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StrReplaceExpressionContext strReplaceExpression() throws RecognitionException {
		StrReplaceExpressionContext _localctx = new StrReplaceExpressionContext(_ctx, getState());
		enterRule(_localctx, 254, RULE_strReplaceExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1363);
			match(T__118);
			setState(1364);
			match(T__9);
			setState(1365);
			expression();
			setState(1366);
			match(T__48);
			setState(1367);
			expression();
			setState(1368);
			match(T__48);
			setState(1369);
			expression();
			setState(1372);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__48) {
				{
				setState(1370);
				match(T__48);
				setState(1371);
				expression();
				}
			}

			setState(1374);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExistsFuncContext extends ParserRuleContext {
		public GroupGraphPatternContext groupGraphPattern() {
			return getRuleContext(GroupGraphPatternContext.class,0);
		}
		public ExistsFuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_existsFunc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterExistsFunc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitExistsFunc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitExistsFunc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExistsFuncContext existsFunc() throws RecognitionException {
		ExistsFuncContext _localctx = new ExistsFuncContext(_ctx, getState());
		enterRule(_localctx, 256, RULE_existsFunc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1376);
			match(T__119);
			setState(1377);
			groupGraphPattern();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NotExistsFuncContext extends ParserRuleContext {
		public GroupGraphPatternContext groupGraphPattern() {
			return getRuleContext(GroupGraphPatternContext.class,0);
		}
		public NotExistsFuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_notExistsFunc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterNotExistsFunc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitNotExistsFunc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitNotExistsFunc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NotExistsFuncContext notExistsFunc() throws RecognitionException {
		NotExistsFuncContext _localctx = new NotExistsFuncContext(_ctx, getState());
		enterRule(_localctx, 258, RULE_notExistsFunc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1379);
			match(T__65);
			setState(1380);
			match(T__119);
			setState(1381);
			groupGraphPattern();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AggregateContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DistinctContext distinct() {
			return getRuleContext(DistinctContext.class,0);
		}
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public AggregateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aggregate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterAggregate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitAggregate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitAggregate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AggregateContext aggregate() throws RecognitionException {
		AggregateContext _localctx = new AggregateContext(_ctx, getState());
		enterRule(_localctx, 260, RULE_aggregate);
		int _la;
		try {
			setState(1447);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__120:
				enterOuterAlt(_localctx, 1);
				{
				setState(1383);
				match(T__120);
				setState(1384);
				match(T__9);
				setState(1386);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__11) {
					{
					setState(1385);
					distinct();
					}
				}

				setState(1390);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__8:
					{
					setState(1388);
					match(T__8);
					}
					break;
				case T__9:
				case T__54:
				case T__55:
				case T__65:
				case T__66:
				case T__67:
				case T__68:
				case T__69:
				case T__70:
				case T__71:
				case T__72:
				case T__73:
				case T__74:
				case T__75:
				case T__76:
				case T__77:
				case T__78:
				case T__79:
				case T__80:
				case T__81:
				case T__82:
				case T__83:
				case T__84:
				case T__85:
				case T__86:
				case T__87:
				case T__88:
				case T__89:
				case T__90:
				case T__91:
				case T__92:
				case T__93:
				case T__94:
				case T__95:
				case T__96:
				case T__97:
				case T__98:
				case T__99:
				case T__100:
				case T__101:
				case T__102:
				case T__103:
				case T__104:
				case T__105:
				case T__106:
				case T__107:
				case T__108:
				case T__109:
				case T__110:
				case T__111:
				case T__112:
				case T__113:
				case T__114:
				case T__115:
				case T__116:
				case T__117:
				case T__118:
				case T__119:
				case T__120:
				case T__121:
				case T__122:
				case T__123:
				case T__124:
				case T__125:
				case T__126:
				case T__129:
				case T__130:
				case IRIREF:
				case PNAME_NS:
				case PNAME_LN:
				case VAR1:
				case VAR2:
				case INTEGER:
				case DECIMAL:
				case DOUBLE:
				case INTEGER_POSITIVE:
				case DECIMAL_POSITIVE:
				case DOUBLE_POSITIVE:
				case INTEGER_NEGATIVE:
				case DECIMAL_NEGATIVE:
				case DOUBLE_NEGATIVE:
				case STRING_LITERAL1:
				case STRING_LITERAL2:
				case STRING_LITERAL_LONG1:
				case STRING_LITERAL_LONG2:
					{
					setState(1389);
					expression();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1392);
				match(T__10);
				}
				break;
			case T__121:
				enterOuterAlt(_localctx, 2);
				{
				setState(1393);
				match(T__121);
				setState(1394);
				match(T__9);
				setState(1396);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__11) {
					{
					setState(1395);
					distinct();
					}
				}

				setState(1398);
				expression();
				setState(1399);
				match(T__10);
				}
				break;
			case T__122:
				enterOuterAlt(_localctx, 3);
				{
				setState(1401);
				match(T__122);
				setState(1402);
				match(T__9);
				setState(1404);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__11) {
					{
					setState(1403);
					distinct();
					}
				}

				setState(1406);
				expression();
				setState(1407);
				match(T__10);
				}
				break;
			case T__123:
				enterOuterAlt(_localctx, 4);
				{
				setState(1409);
				match(T__123);
				setState(1410);
				match(T__9);
				setState(1412);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__11) {
					{
					setState(1411);
					distinct();
					}
				}

				setState(1414);
				expression();
				setState(1415);
				match(T__10);
				}
				break;
			case T__124:
				enterOuterAlt(_localctx, 5);
				{
				setState(1417);
				match(T__124);
				setState(1418);
				match(T__9);
				setState(1420);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__11) {
					{
					setState(1419);
					distinct();
					}
				}

				setState(1422);
				expression();
				setState(1423);
				match(T__10);
				}
				break;
			case T__125:
				enterOuterAlt(_localctx, 6);
				{
				setState(1425);
				match(T__125);
				setState(1426);
				match(T__9);
				setState(1428);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__11) {
					{
					setState(1427);
					distinct();
					}
				}

				setState(1430);
				expression();
				setState(1431);
				match(T__10);
				}
				break;
			case T__126:
				enterOuterAlt(_localctx, 7);
				{
				setState(1433);
				match(T__126);
				setState(1434);
				match(T__9);
				setState(1436);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__11) {
					{
					setState(1435);
					distinct();
					}
				}

				setState(1438);
				expression();
				setState(1443);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__49) {
					{
					setState(1439);
					match(T__49);
					setState(1440);
					match(T__127);
					setState(1441);
					match(T__58);
					setState(1442);
					string();
					}
				}

				setState(1445);
				match(T__10);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IriOrFunctionContext extends ParserRuleContext {
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public ArgListContext argList() {
			return getRuleContext(ArgListContext.class,0);
		}
		public IriOrFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iriOrFunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterIriOrFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitIriOrFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitIriOrFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IriOrFunctionContext iriOrFunction() throws RecognitionException {
		IriOrFunctionContext _localctx = new IriOrFunctionContext(_ctx, getState());
		enterRule(_localctx, 262, RULE_iriOrFunction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1449);
			iri();
			setState(1451);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9 || _la==NIL) {
				{
				setState(1450);
				argList();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RdfliteralContext extends ParserRuleContext {
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public TerminalNode LANGTAG() { return getToken(RSPQLParser.LANGTAG, 0); }
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public RdfliteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rdfliteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterRdfliteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitRdfliteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitRdfliteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RdfliteralContext rdfliteral() throws RecognitionException {
		RdfliteralContext _localctx = new RdfliteralContext(_ctx, getState());
		enterRule(_localctx, 264, RULE_rdfliteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1453);
			string();
			setState(1457);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LANGTAG:
				{
				setState(1454);
				match(LANGTAG);
				}
				break;
			case T__128:
				{
				{
				setState(1455);
				match(T__128);
				setState(1456);
				iri();
				}
				}
				break;
			case T__3:
			case T__8:
			case T__9:
			case T__10:
			case T__15:
			case T__16:
			case T__21:
			case T__23:
			case T__24:
			case T__36:
			case T__38:
			case T__39:
			case T__40:
			case T__41:
			case T__43:
			case T__44:
			case T__45:
			case T__47:
			case T__48:
			case T__49:
			case T__51:
			case T__52:
			case T__54:
			case T__55:
			case T__56:
			case T__57:
			case T__58:
			case T__59:
			case T__60:
			case T__61:
			case T__62:
			case T__63:
			case T__64:
			case T__65:
			case T__66:
			case T__129:
			case T__130:
			case TYPE:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
			case BLANK_NODE_LABEL:
			case VAR1:
			case VAR2:
			case INTEGER:
			case DECIMAL:
			case DOUBLE:
			case INTEGER_POSITIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE_POSITIVE:
			case INTEGER_NEGATIVE:
			case DECIMAL_NEGATIVE:
			case DOUBLE_NEGATIVE:
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
			case NIL:
			case ANON:
				break;
			default:
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumericLiteralContext extends ParserRuleContext {
		public NumericLiteralUnsignedContext numericLiteralUnsigned() {
			return getRuleContext(NumericLiteralUnsignedContext.class,0);
		}
		public NumericLiteralPositiveContext numericLiteralPositive() {
			return getRuleContext(NumericLiteralPositiveContext.class,0);
		}
		public NumericLiteralNegativeContext numericLiteralNegative() {
			return getRuleContext(NumericLiteralNegativeContext.class,0);
		}
		public NumericLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterNumericLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitNumericLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitNumericLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericLiteralContext numericLiteral() throws RecognitionException {
		NumericLiteralContext _localctx = new NumericLiteralContext(_ctx, getState());
		enterRule(_localctx, 266, RULE_numericLiteral);
		try {
			setState(1462);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INTEGER:
			case DECIMAL:
			case DOUBLE:
				enterOuterAlt(_localctx, 1);
				{
				setState(1459);
				numericLiteralUnsigned();
				}
				break;
			case INTEGER_POSITIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE_POSITIVE:
				enterOuterAlt(_localctx, 2);
				{
				setState(1460);
				numericLiteralPositive();
				}
				break;
			case INTEGER_NEGATIVE:
			case DECIMAL_NEGATIVE:
			case DOUBLE_NEGATIVE:
				enterOuterAlt(_localctx, 3);
				{
				setState(1461);
				numericLiteralNegative();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumericLiteralUnsignedContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(RSPQLParser.INTEGER, 0); }
		public TerminalNode DECIMAL() { return getToken(RSPQLParser.DECIMAL, 0); }
		public TerminalNode DOUBLE() { return getToken(RSPQLParser.DOUBLE, 0); }
		public NumericLiteralUnsignedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericLiteralUnsigned; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterNumericLiteralUnsigned(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitNumericLiteralUnsigned(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitNumericLiteralUnsigned(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericLiteralUnsignedContext numericLiteralUnsigned() throws RecognitionException {
		NumericLiteralUnsignedContext _localctx = new NumericLiteralUnsignedContext(_ctx, getState());
		enterRule(_localctx, 268, RULE_numericLiteralUnsigned);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1464);
			_la = _input.LA(1);
			if ( !(((((_la - 142)) & ~0x3f) == 0 && ((1L << (_la - 142)) & ((1L << (INTEGER - 142)) | (1L << (DECIMAL - 142)) | (1L << (DOUBLE - 142)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumericLiteralPositiveContext extends ParserRuleContext {
		public TerminalNode INTEGER_POSITIVE() { return getToken(RSPQLParser.INTEGER_POSITIVE, 0); }
		public TerminalNode DECIMAL_POSITIVE() { return getToken(RSPQLParser.DECIMAL_POSITIVE, 0); }
		public TerminalNode DOUBLE_POSITIVE() { return getToken(RSPQLParser.DOUBLE_POSITIVE, 0); }
		public NumericLiteralPositiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericLiteralPositive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterNumericLiteralPositive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitNumericLiteralPositive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitNumericLiteralPositive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericLiteralPositiveContext numericLiteralPositive() throws RecognitionException {
		NumericLiteralPositiveContext _localctx = new NumericLiteralPositiveContext(_ctx, getState());
		enterRule(_localctx, 270, RULE_numericLiteralPositive);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1466);
			_la = _input.LA(1);
			if ( !(((((_la - 145)) & ~0x3f) == 0 && ((1L << (_la - 145)) & ((1L << (INTEGER_POSITIVE - 145)) | (1L << (DECIMAL_POSITIVE - 145)) | (1L << (DOUBLE_POSITIVE - 145)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumericLiteralNegativeContext extends ParserRuleContext {
		public TerminalNode INTEGER_NEGATIVE() { return getToken(RSPQLParser.INTEGER_NEGATIVE, 0); }
		public TerminalNode DECIMAL_NEGATIVE() { return getToken(RSPQLParser.DECIMAL_NEGATIVE, 0); }
		public TerminalNode DOUBLE_NEGATIVE() { return getToken(RSPQLParser.DOUBLE_NEGATIVE, 0); }
		public NumericLiteralNegativeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericLiteralNegative; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterNumericLiteralNegative(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitNumericLiteralNegative(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitNumericLiteralNegative(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericLiteralNegativeContext numericLiteralNegative() throws RecognitionException {
		NumericLiteralNegativeContext _localctx = new NumericLiteralNegativeContext(_ctx, getState());
		enterRule(_localctx, 272, RULE_numericLiteralNegative);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1468);
			_la = _input.LA(1);
			if ( !(((((_la - 148)) & ~0x3f) == 0 && ((1L << (_la - 148)) & ((1L << (INTEGER_NEGATIVE - 148)) | (1L << (DECIMAL_NEGATIVE - 148)) | (1L << (DOUBLE_NEGATIVE - 148)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanLiteralContext extends ParserRuleContext {
		public BooleanLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterBooleanLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitBooleanLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitBooleanLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanLiteralContext booleanLiteral() throws RecognitionException {
		BooleanLiteralContext _localctx = new BooleanLiteralContext(_ctx, getState());
		enterRule(_localctx, 274, RULE_booleanLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1470);
			_la = _input.LA(1);
			if ( !(_la==T__129 || _la==T__130) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringContext extends ParserRuleContext {
		public TerminalNode STRING_LITERAL1() { return getToken(RSPQLParser.STRING_LITERAL1, 0); }
		public TerminalNode STRING_LITERAL2() { return getToken(RSPQLParser.STRING_LITERAL2, 0); }
		public TerminalNode STRING_LITERAL_LONG1() { return getToken(RSPQLParser.STRING_LITERAL_LONG1, 0); }
		public TerminalNode STRING_LITERAL_LONG2() { return getToken(RSPQLParser.STRING_LITERAL_LONG2, 0); }
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 276, RULE_string);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1472);
			_la = _input.LA(1);
			if ( !(((((_la - 152)) & ~0x3f) == 0 && ((1L << (_la - 152)) & ((1L << (STRING_LITERAL1 - 152)) | (1L << (STRING_LITERAL2 - 152)) | (1L << (STRING_LITERAL_LONG1 - 152)) | (1L << (STRING_LITERAL_LONG2 - 152)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IriContext extends ParserRuleContext {
		public TerminalNode IRIREF() { return getToken(RSPQLParser.IRIREF, 0); }
		public PrefixedNameContext prefixedName() {
			return getRuleContext(PrefixedNameContext.class,0);
		}
		public IriContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iri; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterIri(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitIri(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitIri(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IriContext iri() throws RecognitionException {
		IriContext _localctx = new IriContext(_ctx, getState());
		enterRule(_localctx, 278, RULE_iri);
		try {
			setState(1476);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IRIREF:
				enterOuterAlt(_localctx, 1);
				{
				setState(1474);
				match(IRIREF);
				}
				break;
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 2);
				{
				setState(1475);
				prefixedName();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrefixedNameContext extends ParserRuleContext {
		public TerminalNode PNAME_LN() { return getToken(RSPQLParser.PNAME_LN, 0); }
		public TerminalNode PNAME_NS() { return getToken(RSPQLParser.PNAME_NS, 0); }
		public PrefixedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefixedName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterPrefixedName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitPrefixedName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitPrefixedName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrefixedNameContext prefixedName() throws RecognitionException {
		PrefixedNameContext _localctx = new PrefixedNameContext(_ctx, getState());
		enterRule(_localctx, 280, RULE_prefixedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1478);
			_la = _input.LA(1);
			if ( !(_la==PNAME_NS || _la==PNAME_LN) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlankNodeContext extends ParserRuleContext {
		public TerminalNode BLANK_NODE_LABEL() { return getToken(RSPQLParser.BLANK_NODE_LABEL, 0); }
		public TerminalNode ANON() { return getToken(RSPQLParser.ANON, 0); }
		public BlankNodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blankNode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).enterBlankNode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RSPQLListener ) ((RSPQLListener)listener).exitBlankNode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RSPQLVisitor ) return ((RSPQLVisitor<? extends T>)visitor).visitBlankNode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlankNodeContext blankNode() throws RecognitionException {
		BlankNodeContext _localctx = new BlankNodeContext(_ctx, getState());
		enterRule(_localctx, 282, RULE_blankNode);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1480);
			_la = _input.LA(1);
			if ( !(_la==BLANK_NODE_LABEL || _la==ANON) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u00ac\u05cd\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\4Z\tZ\4[\t[\4\\\t\\\4]\t]\4^\t^\4_\t_\4"+
		"`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\4f\tf\4g\tg\4h\th\4i\ti\4j\tj\4k\t"+
		"k\4l\tl\4m\tm\4n\tn\4o\to\4p\tp\4q\tq\4r\tr\4s\ts\4t\tt\4u\tu\4v\tv\4"+
		"w\tw\4x\tx\4y\ty\4z\tz\4{\t{\4|\t|\4}\t}\4~\t~\4\177\t\177\4\u0080\t\u0080"+
		"\4\u0081\t\u0081\4\u0082\t\u0082\4\u0083\t\u0083\4\u0084\t\u0084\4\u0085"+
		"\t\u0085\4\u0086\t\u0086\4\u0087\t\u0087\4\u0088\t\u0088\4\u0089\t\u0089"+
		"\4\u008a\t\u008a\4\u008b\t\u008b\4\u008c\t\u008c\4\u008d\t\u008d\4\u008e"+
		"\t\u008e\4\u008f\t\u008f\3\2\3\2\3\3\3\3\5\3\u0123\n\3\3\3\3\3\3\3\3\3"+
		"\5\3\u0129\n\3\3\3\3\3\3\4\3\4\7\4\u012f\n\4\f\4\16\4\u0132\13\4\3\5\3"+
		"\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\7\n"+
		"\u0146\n\n\f\n\16\n\u0149\13\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3"+
		"\f\3\f\3\f\5\f\u0156\n\f\3\f\6\f\u0159\n\f\r\f\16\f\u015a\3\f\5\f\u015e"+
		"\n\f\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u0169\n\16\3\17\3"+
		"\17\3\20\3\20\3\21\3\21\3\21\7\21\u0172\n\21\f\21\16\21\u0175\13\21\3"+
		"\21\3\21\3\21\3\21\7\21\u017b\n\21\f\21\16\21\u017e\13\21\3\21\3\21\3"+
		"\21\5\21\u0183\n\21\3\21\3\21\5\21\u0187\n\21\3\22\3\22\6\22\u018b\n\22"+
		"\r\22\16\22\u018c\3\22\5\22\u0190\n\22\3\22\7\22\u0193\n\22\f\22\16\22"+
		"\u0196\13\22\3\22\5\22\u0199\n\22\3\22\3\22\3\23\3\23\7\23\u019f\n\23"+
		"\f\23\16\23\u01a2\13\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\5\24\u01ab"+
		"\n\24\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\30\3\30\3\31\3\31\3\32\3\32\5\32\u01c1\n\32\3\33\3\33\5\33\u01c5"+
		"\n\33\3\34\3\34\3\34\3\35\3\35\3\35\3\36\3\36\5\36\u01cf\n\36\3\37\3\37"+
		"\3\37\3 \3 \3 \3!\3!\3\"\3\"\3#\5#\u01dc\n#\3#\3#\3$\5$\u01e1\n$\3$\5"+
		"$\u01e4\n$\3$\5$\u01e7\n$\3$\5$\u01ea\n$\3%\3%\3%\6%\u01ef\n%\r%\16%\u01f0"+
		"\3&\3&\3&\3&\3&\3&\5&\u01f9\n&\3&\3&\3&\5&\u01fe\n&\3\'\3\'\6\'\u0202"+
		"\n\'\r\'\16\'\u0203\3(\3(\3)\3)\3)\6)\u020b\n)\r)\16)\u020c\3*\3*\3*\3"+
		"*\5*\u0213\n*\5*\u0215\n*\3+\3+\5+\u0219\n+\3+\3+\5+\u021d\n+\5+\u021f"+
		"\n+\3,\3,\3,\3-\3-\3-\3.\3.\5.\u0229\n.\3/\3/\5/\u022d\n/\3/\5/\u0230"+
		"\n/\3\60\3\60\3\60\3\61\3\61\3\61\3\61\3\62\5\62\u023a\n\62\3\62\3\62"+
		"\5\62\u023e\n\62\3\62\5\62\u0241\n\62\7\62\u0243\n\62\f\62\16\62\u0246"+
		"\13\62\3\63\3\63\3\63\3\63\5\63\u024c\n\63\3\63\3\63\3\64\3\64\3\64\5"+
		"\64\u0253\n\64\5\64\u0255\n\64\3\65\3\65\3\65\5\65\u025a\n\65\3\65\3\65"+
		"\3\66\5\66\u025f\n\66\3\66\3\66\5\66\u0263\n\66\3\66\5\66\u0266\n\66\7"+
		"\66\u0268\n\66\f\66\16\66\u026b\13\66\3\67\3\67\3\67\5\67\u0270\n\67\5"+
		"\67\u0272\n\67\38\38\38\38\38\38\38\38\38\58\u027d\n8\39\39\39\3:\3:\3"+
		":\3:\3;\3;\3;\3;\3<\3<\5<\u028c\n<\3<\3<\3<\3=\3=\3=\3=\3=\3=\3=\3>\3"+
		">\3>\3?\3?\5?\u029d\n?\3@\3@\3@\7@\u02a2\n@\f@\16@\u02a5\13@\3@\3@\3A"+
		"\3A\3A\7A\u02ac\nA\fA\16A\u02af\13A\3A\5A\u02b2\nA\3A\3A\3A\7A\u02b7\n"+
		"A\fA\16A\u02ba\13A\3A\3A\3B\3B\7B\u02c0\nB\fB\16B\u02c3\13B\3B\3B\3C\3"+
		"C\3C\3C\3C\5C\u02cc\nC\3D\3D\3E\3E\3E\3F\3F\3F\7F\u02d6\nF\fF\16F\u02d9"+
		"\13F\3G\3G\3G\3H\3H\3H\5H\u02e1\nH\3I\3I\3I\3J\3J\3J\5J\u02e9\nJ\3J\3"+
		"J\3J\7J\u02ee\nJ\fJ\16J\u02f1\13J\3J\3J\5J\u02f5\nJ\3K\3K\3K\3K\3K\7K"+
		"\u02fc\nK\fK\16K\u02ff\13K\3K\3K\5K\u0303\nK\3L\3L\3L\3L\3M\3M\3M\5M\u030c"+
		"\nM\5M\u030e\nM\3N\3N\3N\3N\3N\5N\u0315\nN\5N\u0317\nN\3O\3O\3O\5O\u031c"+
		"\nO\7O\u031e\nO\fO\16O\u0321\13O\3P\3P\3P\3Q\3Q\5Q\u0328\nQ\3R\3R\3S\3"+
		"S\3S\7S\u032f\nS\fS\16S\u0332\13S\3T\3T\3U\3U\3U\3U\3U\5U\u033b\nU\5U"+
		"\u033d\nU\3V\3V\3V\7V\u0342\nV\fV\16V\u0345\13V\3W\3W\5W\u0349\nW\3W\3"+
		"W\3X\3X\3Y\3Y\5Y\u0351\nY\3Z\3Z\3Z\7Z\u0356\nZ\fZ\16Z\u0359\13Z\3[\3["+
		"\3\\\3\\\3]\3]\3]\7]\u0362\n]\f]\16]\u0365\13]\3^\3^\3^\7^\u036a\n^\f"+
		"^\16^\u036d\13^\3_\3_\5_\u0371\n_\3`\3`\3`\5`\u0376\n`\3a\3a\3b\3b\3b"+
		"\3b\3b\3b\3b\3b\5b\u0382\nb\3c\3c\3c\3c\3c\7c\u0389\nc\fc\16c\u038c\13"+
		"c\5c\u038e\nc\3c\5c\u0391\nc\3d\3d\3d\3d\3d\5d\u0398\nd\5d\u039a\nd\3"+
		"e\3e\3f\3f\5f\u03a0\nf\3g\3g\3g\3g\3h\3h\5h\u03a8\nh\3i\3i\3i\3i\3j\3"+
		"j\6j\u03b0\nj\rj\16j\u03b1\3j\3j\3k\3k\6k\u03b8\nk\rk\16k\u03b9\3k\3k"+
		"\3l\3l\5l\u03c0\nl\3m\3m\5m\u03c4\nm\3n\3n\5n\u03c8\nn\3o\3o\5o\u03cc"+
		"\no\3p\3p\3q\3q\3q\3q\3q\3q\5q\u03d6\nq\3r\3r\3s\3s\3s\7s\u03dd\ns\fs"+
		"\16s\u03e0\13s\3t\3t\3t\7t\u03e5\nt\ft\16t\u03e8\13t\3u\3u\3v\3v\3v\3"+
		"v\3v\3v\3v\3v\3v\3v\3v\3v\3v\3v\3v\3v\3v\3v\5v\u03fe\nv\3w\3w\3x\3x\7"+
		"x\u0404\nx\fx\16x\u0407\13x\3y\3y\3y\3y\3y\3y\5y\u040f\ny\3y\3y\3y\3y"+
		"\7y\u0415\ny\fy\16y\u0418\13y\5y\u041a\ny\3z\3z\3z\3z\3z\7z\u0421\nz\f"+
		"z\16z\u0424\13z\3{\3{\3{\3{\3{\3{\3{\5{\u042d\n{\3|\3|\3|\3|\3|\3|\3|"+
		"\5|\u0436\n|\3}\3}\3}\3}\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~"+
		"\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~"+
		"\3~\3~\3~\3~\3~\3~\5~\u0468\n~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~"+
		"\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~"+
		"\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~"+
		"\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~"+
		"\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~"+
		"\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~"+
		"\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~"+
		"\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~"+
		"\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~"+
		"\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\3~\5~\u053e\n~\3\177\3\177"+
		"\3\177\3\177\3\177\3\177\3\177\5\177\u0547\n\177\3\177\3\177\3\u0080\3"+
		"\u0080\3\u0080\3\u0080\3\u0080\3\u0080\3\u0080\5\u0080\u0552\n\u0080\3"+
		"\u0080\3\u0080\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\5\u0081\u055f\n\u0081\3\u0081\3\u0081\3\u0082\3\u0082"+
		"\3\u0082\3\u0083\3\u0083\3\u0083\3\u0083\3\u0084\3\u0084\3\u0084\5\u0084"+
		"\u056d\n\u0084\3\u0084\3\u0084\5\u0084\u0571\n\u0084\3\u0084\3\u0084\3"+
		"\u0084\3\u0084\5\u0084\u0577\n\u0084\3\u0084\3\u0084\3\u0084\3\u0084\3"+
		"\u0084\3\u0084\5\u0084\u057f\n\u0084\3\u0084\3\u0084\3\u0084\3\u0084\3"+
		"\u0084\3\u0084\5\u0084\u0587\n\u0084\3\u0084\3\u0084\3\u0084\3\u0084\3"+
		"\u0084\3\u0084\5\u0084\u058f\n\u0084\3\u0084\3\u0084\3\u0084\3\u0084\3"+
		"\u0084\3\u0084\5\u0084\u0597\n\u0084\3\u0084\3\u0084\3\u0084\3\u0084\3"+
		"\u0084\3\u0084\5\u0084\u059f\n\u0084\3\u0084\3\u0084\3\u0084\3\u0084\3"+
		"\u0084\5\u0084\u05a6\n\u0084\3\u0084\3\u0084\5\u0084\u05aa\n\u0084\3\u0085"+
		"\3\u0085\5\u0085\u05ae\n\u0085\3\u0086\3\u0086\3\u0086\3\u0086\5\u0086"+
		"\u05b4\n\u0086\3\u0087\3\u0087\3\u0087\5\u0087\u05b9\n\u0087\3\u0088\3"+
		"\u0088\3\u0089\3\u0089\3\u008a\3\u008a\3\u008b\3\u008b\3\u008c\3\u008c"+
		"\3\u008d\3\u008d\5\u008d\u05c7\n\u008d\3\u008e\3\u008e\3\u008f\3\u008f"+
		"\3\u008f\2\2\u0090\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60"+
		"\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086"+
		"\u0088\u008a\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c\u009e"+
		"\u00a0\u00a2\u00a4\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0\u00b2\u00b4\u00b6"+
		"\u00b8\u00ba\u00bc\u00be\u00c0\u00c2\u00c4\u00c6\u00c8\u00ca\u00cc\u00ce"+
		"\u00d0\u00d2\u00d4\u00d6\u00d8\u00da\u00dc\u00de\u00e0\u00e2\u00e4\u00e6"+
		"\u00e8\u00ea\u00ec\u00ee\u00f0\u00f2\u00f4\u00f6\u00f8\u00fa\u00fc\u00fe"+
		"\u0100\u0102\u0104\u0106\u0108\u010a\u010c\u010e\u0110\u0112\u0114\u0116"+
		"\u0118\u011a\u011c\2\r\3\2\7\t\3\2#$\4\2\13\1389\3\2\u008d\u008e\3\2\u0090"+
		"\u0092\3\2\u0093\u0095\3\2\u0096\u0098\3\2\u0084\u0085\3\2\u009a\u009d"+
		"\3\2\u008a\u008b\4\2\u008c\u008c\u00a1\u00a1\2\u0628\2\u011e\3\2\2\2\4"+
		"\u0120\3\2\2\2\6\u0130\3\2\2\2\b\u0133\3\2\2\2\n\u0136\3\2\2\2\f\u013a"+
		"\3\2\2\2\16\u013f\3\2\2\2\20\u0141\3\2\2\2\22\u0143\3\2\2\2\24\u014d\3"+
		"\2\2\2\26\u0152\3\2\2\2\30\u015f\3\2\2\2\32\u0168\3\2\2\2\34\u016a\3\2"+
		"\2\2\36\u016c\3\2\2\2 \u016e\3\2\2\2\"\u0188\3\2\2\2$\u019c\3\2\2\2&\u01a6"+
		"\3\2\2\2(\u01ac\3\2\2\2*\u01ae\3\2\2\2,\u01b1\3\2\2\2.\u01ba\3\2\2\2\60"+
		"\u01bc\3\2\2\2\62\u01c0\3\2\2\2\64\u01c2\3\2\2\2\66\u01c6\3\2\2\28\u01c9"+
		"\3\2\2\2:\u01cc\3\2\2\2<\u01d0\3\2\2\2>\u01d3\3\2\2\2@\u01d6\3\2\2\2B"+
		"\u01d8\3\2\2\2D\u01db\3\2\2\2F\u01e0\3\2\2\2H\u01eb\3\2\2\2J\u01fd\3\2"+
		"\2\2L\u01ff\3\2\2\2N\u0205\3\2\2\2P\u0207\3\2\2\2R\u0214\3\2\2\2T\u021e"+
		"\3\2\2\2V\u0220\3\2\2\2X\u0223\3\2\2\2Z\u0228\3\2\2\2\\\u022f\3\2\2\2"+
		"^\u0231\3\2\2\2`\u0234\3\2\2\2b\u0239\3\2\2\2d\u0247\3\2\2\2f\u024f\3"+
		"\2\2\2h\u0256\3\2\2\2j\u025e\3\2\2\2l\u026c\3\2\2\2n\u027c\3\2\2\2p\u027e"+
		"\3\2\2\2r\u0281\3\2\2\2t\u0285\3\2\2\2v\u0289\3\2\2\2x\u0290\3\2\2\2z"+
		"\u0297\3\2\2\2|\u029c\3\2\2\2~\u029e\3\2\2\2\u0080\u02b1\3\2\2\2\u0082"+
		"\u02bd\3\2\2\2\u0084\u02cb\3\2\2\2\u0086\u02cd\3\2\2\2\u0088\u02cf\3\2"+
		"\2\2\u008a\u02d2\3\2\2\2\u008c\u02da\3\2\2\2\u008e\u02e0\3\2\2\2\u0090"+
		"\u02e2\3\2\2\2\u0092\u02f4\3\2\2\2\u0094\u0302\3\2\2\2\u0096\u0304\3\2"+
		"\2\2\u0098\u0308\3\2\2\2\u009a\u0316\3\2\2\2\u009c\u0318\3\2\2\2\u009e"+
		"\u0322\3\2\2\2\u00a0\u0327\3\2\2\2\u00a2\u0329\3\2\2\2\u00a4\u032b\3\2"+
		"\2\2\u00a6\u0333\3\2\2\2\u00a8\u033c\3\2\2\2\u00aa\u033e\3\2\2\2\u00ac"+
		"\u0348\3\2\2\2\u00ae\u034c\3\2\2\2\u00b0\u0350\3\2\2\2\u00b2\u0352\3\2"+
		"\2\2\u00b4\u035a\3\2\2\2\u00b6\u035c\3\2\2\2\u00b8\u035e\3\2\2\2\u00ba"+
		"\u0366\3\2\2\2\u00bc\u036e\3\2\2\2\u00be\u0375\3\2\2\2\u00c0\u0377\3\2"+
		"\2\2\u00c2\u0381\3\2\2\2\u00c4\u0390\3\2\2\2\u00c6\u0399\3\2\2\2\u00c8"+
		"\u039b\3\2\2\2\u00ca\u039f\3\2\2\2\u00cc\u03a1\3\2\2\2\u00ce\u03a7\3\2"+
		"\2\2\u00d0\u03a9\3\2\2\2\u00d2\u03ad\3\2\2\2\u00d4\u03b5\3\2\2\2\u00d6"+
		"\u03bf\3\2\2\2\u00d8\u03c3\3\2\2\2\u00da\u03c7\3\2\2\2\u00dc\u03cb\3\2"+
		"\2\2\u00de\u03cd\3\2\2\2\u00e0\u03d5\3\2\2\2\u00e2\u03d7\3\2\2\2\u00e4"+
		"\u03d9\3\2\2\2\u00e6\u03e1\3\2\2\2\u00e8\u03e9\3\2\2\2\u00ea\u03eb\3\2"+
		"\2\2\u00ec\u03ff\3\2\2\2\u00ee\u0401\3\2\2\2\u00f0\u0419\3\2\2\2\u00f2"+
		"\u041b\3\2\2\2\u00f4\u042c\3\2\2\2\u00f6\u0435\3\2\2\2\u00f8\u0437\3\2"+
		"\2\2\u00fa\u053d\3\2\2\2\u00fc\u053f\3\2\2\2\u00fe\u054a\3\2\2\2\u0100"+
		"\u0555\3\2\2\2\u0102\u0562\3\2\2\2\u0104\u0565\3\2\2\2\u0106\u05a9\3\2"+
		"\2\2\u0108\u05ab\3\2\2\2\u010a\u05af\3\2\2\2\u010c\u05b8\3\2\2\2\u010e"+
		"\u05ba\3\2\2\2\u0110\u05bc\3\2\2\2\u0112\u05be\3\2\2\2\u0114\u05c0\3\2"+
		"\2\2\u0116\u05c2\3\2\2\2\u0118\u05c6\3\2\2\2\u011a\u05c8\3\2\2\2\u011c"+
		"\u05ca\3\2\2\2\u011e\u011f\5\4\3\2\u011f\3\3\2\2\2\u0120\u0122\5\6\4\2"+
		"\u0121\u0123\5\f\7\2\u0122\u0121\3\2\2\2\u0122\u0123\3\2\2\2\u0123\u0128"+
		"\3\2\2\2\u0124\u0129\5\22\n\2\u0125\u0129\5 \21\2\u0126\u0129\5\"\22\2"+
		"\u0127\u0129\5$\23\2\u0128\u0124\3\2\2\2\u0128\u0125\3\2\2\2\u0128\u0126"+
		"\3\2\2\2\u0128\u0127\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u012b\5Z.\2\u012b"+
		"\5\3\2\2\2\u012c\u012f\5\b\5\2\u012d\u012f\5\n\6\2\u012e\u012c\3\2\2\2"+
		"\u012e\u012d\3\2\2\2\u012f\u0132\3\2\2\2\u0130\u012e\3\2\2\2\u0130\u0131"+
		"\3\2\2\2\u0131\7\3\2\2\2\u0132\u0130\3\2\2\2\u0133\u0134\7\3\2\2\u0134"+
		"\u0135\7\u0089\2\2\u0135\t\3\2\2\2\u0136\u0137\7\4\2\2\u0137\u0138\7\u008a"+
		"\2\2\u0138\u0139\7\u0089\2\2\u0139\13\3\2\2\2\u013a\u013b\7\5\2\2\u013b"+
		"\u013c\5\16\b\2\u013c\u013d\5\20\t\2\u013d\u013e\7\6\2\2\u013e\r\3\2\2"+
		"\2\u013f\u0140\t\2\2\2\u0140\17\3\2\2\2\u0141\u0142\5B\"\2\u0142\21\3"+
		"\2\2\2\u0143\u0147\5\26\f\2\u0144\u0146\5&\24\2\u0145\u0144\3\2\2\2\u0146"+
		"\u0149\3\2\2\2\u0147\u0145\3\2\2\2\u0147\u0148\3\2\2\2\u0148\u014a\3\2"+
		"\2\2\u0149\u0147\3\2\2\2\u014a\u014b\5D#\2\u014b\u014c\5F$\2\u014c\23"+
		"\3\2\2\2\u014d\u014e\5\26\f\2\u014e\u014f\5D#\2\u014f\u0150\5F$\2\u0150"+
		"\u0151\5Z.\2\u0151\25\3\2\2\2\u0152\u0155\7\n\2\2\u0153\u0156\5\34\17"+
		"\2\u0154\u0156\5\36\20\2\u0155\u0153\3\2\2\2\u0155\u0154\3\2\2\2\u0155"+
		"\u0156\3\2\2\2\u0156\u015d\3\2\2\2\u0157\u0159\5\32\16\2\u0158\u0157\3"+
		"\2\2\2\u0159\u015a\3\2\2\2\u015a\u0158\3\2\2\2\u015a\u015b\3\2\2\2\u015b"+
		"\u015e\3\2\2\2\u015c\u015e\5\30\r\2\u015d\u0158\3\2\2\2\u015d\u015c\3"+
		"\2\2\2\u015e\27\3\2\2\2\u015f\u0160\7\13\2\2\u0160\31\3\2\2\2\u0161\u0169"+
		"\5\u00dep\2\u0162\u0163\7\f\2\2\u0163\u0164\5\u00e2r\2\u0164\u0165\7\6"+
		"\2\2\u0165\u0166\5\u00dep\2\u0166\u0167\7\r\2\2\u0167\u0169\3\2\2\2\u0168"+
		"\u0161\3\2\2\2\u0168\u0162\3\2\2\2\u0169\33\3\2\2\2\u016a\u016b\7\16\2"+
		"\2\u016b\35\3\2\2\2\u016c\u016d\7\17\2\2\u016d\37\3\2\2\2\u016e\u0186"+
		"\7\20\2\2\u016f\u0173\5\u0096L\2\u0170\u0172\5&\24\2\u0171\u0170\3\2\2"+
		"\2\u0172\u0175\3\2\2\2\u0173\u0171\3\2\2\2\u0173\u0174\3\2\2\2\u0174\u0176"+
		"\3\2\2\2\u0175\u0173\3\2\2\2\u0176\u0177\5D#\2\u0177\u0178\5F$\2\u0178"+
		"\u0187\3\2\2\2\u0179\u017b\5&\24\2\u017a\u0179\3\2\2\2\u017b\u017e\3\2"+
		"\2\2\u017c\u017a\3\2\2\2\u017c\u017d\3\2\2\2\u017d\u017f\3\2\2\2\u017e"+
		"\u017c\3\2\2\2\u017f\u0180\7\21\2\2\u0180\u0182\7\22\2\2\u0181\u0183\5"+
		"f\64\2\u0182\u0181\3\2\2\2\u0182\u0183\3\2\2\2\u0183\u0184\3\2\2\2\u0184"+
		"\u0185\7\23\2\2\u0185\u0187\5F$\2\u0186\u016f\3\2\2\2\u0186\u017c\3\2"+
		"\2\2\u0187!\3\2\2\2\u0188\u018f\7\24\2\2\u0189\u018b\5\u00dco\2\u018a"+
		"\u0189\3\2\2\2\u018b\u018c\3\2\2\2\u018c\u018a\3\2\2\2\u018c\u018d\3\2"+
		"\2\2\u018d\u0190\3\2\2\2\u018e\u0190\5\30\r\2\u018f\u018a\3\2\2\2\u018f"+
		"\u018e\3\2\2\2\u0190\u0194\3\2\2\2\u0191\u0193\5&\24\2\u0192\u0191\3\2"+
		"\2\2\u0193\u0196\3\2\2\2\u0194\u0192\3\2\2\2\u0194\u0195\3\2\2\2\u0195"+
		"\u0198\3\2\2\2\u0196\u0194\3\2\2\2\u0197\u0199\5D#\2\u0198\u0197\3\2\2"+
		"\2\u0198\u0199\3\2\2\2\u0199\u019a\3\2\2\2\u019a\u019b\5F$\2\u019b#\3"+
		"\2\2\2\u019c\u01a0\7\25\2\2\u019d\u019f\5&\24\2\u019e\u019d\3\2\2\2\u019f"+
		"\u01a2\3\2\2\2\u01a0\u019e\3\2\2\2\u01a0\u01a1\3\2\2\2\u01a1\u01a3\3\2"+
		"\2\2\u01a2\u01a0\3\2\2\2\u01a3\u01a4\5D#\2\u01a4\u01a5\5F$\2\u01a5%\3"+
		"\2\2\2\u01a6\u01aa\7\26\2\2\u01a7\u01ab\5(\25\2\u01a8\u01ab\5*\26\2\u01a9"+
		"\u01ab\5,\27\2\u01aa\u01a7\3\2\2\2\u01aa\u01a8\3\2\2\2\u01aa\u01a9\3\2"+
		"\2\2\u01ab\'\3\2\2\2\u01ac\u01ad\5B\"\2\u01ad)\3\2\2\2\u01ae\u01af\7\27"+
		"\2\2\u01af\u01b0\5B\"\2\u01b0+\3\2\2\2\u01b1\u01b2\7\27\2\2\u01b2\u01b3"+
		"\7\30\2\2\u01b3\u01b4\5.\30\2\u01b4\u01b5\7\31\2\2\u01b5\u01b6\5\60\31"+
		"\2\u01b6\u01b7\7\32\2\2\u01b7\u01b8\5\62\32\2\u01b8\u01b9\7\33\2\2\u01b9"+
		"-\3\2\2\2\u01ba\u01bb\5B\"\2\u01bb/\3\2\2\2\u01bc\u01bd\5B\"\2\u01bd\61"+
		"\3\2\2\2\u01be\u01c1\5\64\33\2\u01bf\u01c1\5:\36\2\u01c0\u01be\3\2\2\2"+
		"\u01c0\u01bf\3\2\2\2\u01c1\63\3\2\2\2\u01c2\u01c4\5\66\34\2\u01c3\u01c5"+
		"\58\35\2\u01c4\u01c3\3\2\2\2\u01c4\u01c5\3\2\2\2\u01c5\65\3\2\2\2\u01c6"+
		"\u01c7\7\34\2\2\u01c7\u01c8\5\u00c8e\2\u01c8\67\3\2\2\2\u01c9\u01ca\7"+
		"\35\2\2\u01ca\u01cb\5\u00c8e\2\u01cb9\3\2\2\2\u01cc\u01ce\5<\37\2\u01cd"+
		"\u01cf\5> \2\u01ce\u01cd\3\2\2\2\u01ce\u01cf\3\2\2\2\u01cf;\3\2\2\2\u01d0"+
		"\u01d1\7\36\2\2\u01d1\u01d2\5@!\2\u01d2=\3\2\2\2\u01d3\u01d4\7\35\2\2"+
		"\u01d4\u01d5\5@!\2\u01d5?\3\2\2\2\u01d6\u01d7\7\u0088\2\2\u01d7A\3\2\2"+
		"\2\u01d8\u01d9\5\u0118\u008d\2\u01d9C\3\2\2\2\u01da\u01dc\7\21\2\2\u01db"+
		"\u01da\3\2\2\2\u01db\u01dc\3\2\2\2\u01dc\u01dd\3\2\2\2\u01dd\u01de\5h"+
		"\65\2\u01deE\3\2\2\2\u01df\u01e1\5H%\2\u01e0\u01df\3\2\2\2\u01e0\u01e1"+
		"\3\2\2\2\u01e1\u01e3\3\2\2\2\u01e2\u01e4\5L\'\2\u01e3\u01e2\3\2\2\2\u01e3"+
		"\u01e4\3\2\2\2\u01e4\u01e6\3\2\2\2\u01e5\u01e7\5P)\2\u01e6\u01e5\3\2\2"+
		"\2\u01e6\u01e7\3\2\2\2\u01e7\u01e9\3\2\2\2\u01e8\u01ea\5T+\2\u01e9\u01e8"+
		"\3\2\2\2\u01e9\u01ea\3\2\2\2\u01eaG\3\2\2\2\u01eb\u01ec\7\37\2\2\u01ec"+
		"\u01ee\7 \2\2\u01ed\u01ef\5J&\2\u01ee\u01ed\3\2\2\2\u01ef\u01f0\3\2\2"+
		"\2\u01f0\u01ee\3\2\2\2\u01f0\u01f1\3\2\2\2\u01f1I\3\2\2\2\u01f2\u01fe"+
		"\5\u00fa~\2\u01f3\u01fe\5\u0090I\2\u01f4\u01f5\7\f\2\2\u01f5\u01f8\5\u00e2"+
		"r\2\u01f6\u01f7\7\6\2\2\u01f7\u01f9\5\u00dep\2\u01f8\u01f6\3\2\2\2\u01f8"+
		"\u01f9\3\2\2\2\u01f9\u01fa\3\2\2\2\u01fa\u01fb\7\r\2\2\u01fb\u01fe\3\2"+
		"\2\2\u01fc\u01fe\5\u00dep\2\u01fd\u01f2\3\2\2\2\u01fd\u01f3\3\2\2\2\u01fd"+
		"\u01f4\3\2\2\2\u01fd\u01fc\3\2\2\2\u01feK\3\2\2\2\u01ff\u0201\7!\2\2\u0200"+
		"\u0202\5N(\2\u0201\u0200\3\2\2\2\u0202\u0203\3\2\2\2\u0203\u0201\3\2\2"+
		"\2\u0203\u0204\3\2\2\2\u0204M\3\2\2\2\u0205\u0206\5\u008eH\2\u0206O\3"+
		"\2\2\2\u0207\u0208\7\"\2\2\u0208\u020a\7 \2\2\u0209\u020b\5R*\2\u020a"+
		"\u0209\3\2\2\2\u020b\u020c\3\2\2\2\u020c\u020a\3\2\2\2\u020c\u020d\3\2"+
		"\2\2\u020dQ\3\2\2\2\u020e\u020f\t\3\2\2\u020f\u0215\5\u00f8}\2\u0210\u0213"+
		"\5\u008eH\2\u0211\u0213\5\u00dep\2\u0212\u0210\3\2\2\2\u0212\u0211\3\2"+
		"\2\2\u0213\u0215\3\2\2\2\u0214\u020e\3\2\2\2\u0214\u0212\3\2\2\2\u0215"+
		"S\3\2\2\2\u0216\u0218\5V,\2\u0217\u0219\5X-\2\u0218\u0217\3\2\2\2\u0218"+
		"\u0219\3\2\2\2\u0219\u021f\3\2\2\2\u021a\u021c\5X-\2\u021b\u021d\5V,\2"+
		"\u021c\u021b\3\2\2\2\u021c\u021d\3\2\2\2\u021d\u021f\3\2\2\2\u021e\u0216"+
		"\3\2\2\2\u021e\u021a\3\2\2\2\u021fU\3\2\2\2\u0220\u0221\7%\2\2\u0221\u0222"+
		"\7\u0090\2\2\u0222W\3\2\2\2\u0223\u0224\7&\2\2\u0224\u0225\7\u0090\2\2"+
		"\u0225Y\3\2\2\2\u0226\u0227\7\'\2\2\u0227\u0229\5|?\2\u0228\u0226\3\2"+
		"\2\2\u0228\u0229\3\2\2\2\u0229[\3\2\2\2\u022a\u0230\7(\2\2\u022b\u022d"+
		"\7)\2\2\u022c\u022b\3\2\2\2\u022c\u022d\3\2\2\2\u022d\u022e\3\2\2\2\u022e"+
		"\u0230\5\u0118\u008d\2\u022f\u022a\3\2\2\2\u022f\u022c\3\2\2\2\u0230]"+
		"\3\2\2\2\u0231\u0232\7)\2\2\u0232\u0233\5\u0118\u008d\2\u0233_\3\2\2\2"+
		"\u0234\u0235\7\22\2\2\u0235\u0236\5b\62\2\u0236\u0237\7\23\2\2\u0237a"+
		"\3\2\2\2\u0238\u023a\5f\64\2\u0239\u0238\3\2\2\2\u0239\u023a\3\2\2\2\u023a"+
		"\u0244\3\2\2\2\u023b\u023d\5d\63\2\u023c\u023e\7*\2\2\u023d\u023c\3\2"+
		"\2\2\u023d\u023e\3\2\2\2\u023e\u0240\3\2\2\2\u023f\u0241\5f\64\2\u0240"+
		"\u023f\3\2\2\2\u0240\u0241\3\2\2\2\u0241\u0243\3\2\2\2\u0242\u023b\3\2"+
		"\2\2\u0243\u0246\3\2\2\2\u0244\u0242\3\2\2\2\u0244\u0245\3\2\2\2\u0245"+
		"c\3\2\2\2\u0246\u0244\3\2\2\2\u0247\u0248\7)\2\2\u0248\u0249\5\u00dco"+
		"\2\u0249\u024b\7\22\2\2\u024a\u024c\5f\64\2\u024b\u024a\3\2\2\2\u024b"+
		"\u024c\3\2\2\2\u024c\u024d\3\2\2\2\u024d\u024e\7\23\2\2\u024ee\3\2\2\2"+
		"\u024f\u0254\5\u009aN\2\u0250\u0252\7*\2\2\u0251\u0253\5f\64\2\u0252\u0251"+
		"\3\2\2\2\u0252\u0253\3\2\2\2\u0253\u0255\3\2\2\2\u0254\u0250\3\2\2\2\u0254"+
		"\u0255\3\2\2\2\u0255g\3\2\2\2\u0256\u0259\7\22\2\2\u0257\u025a\5\24\13"+
		"\2\u0258\u025a\5j\66\2\u0259\u0257\3\2\2\2\u0259\u0258\3\2\2\2\u025a\u025b"+
		"\3\2\2\2\u025b\u025c\7\23\2\2\u025ci\3\2\2\2\u025d\u025f\5l\67\2\u025e"+
		"\u025d\3\2\2\2\u025e\u025f\3\2\2\2\u025f\u0269\3\2\2\2\u0260\u0262\5n"+
		"8\2\u0261\u0263\7*\2\2\u0262\u0261\3\2\2\2\u0262\u0263\3\2\2\2\u0263\u0265"+
		"\3\2\2\2\u0264\u0266\5l\67\2\u0265\u0264\3\2\2\2\u0265\u0266\3\2\2\2\u0266"+
		"\u0268\3\2\2\2\u0267\u0260\3\2\2\2\u0268\u026b\3\2\2\2\u0269\u0267\3\2"+
		"\2\2\u0269\u026a\3\2\2\2\u026ak\3\2\2\2\u026b\u0269\3\2\2\2\u026c\u0271"+
		"\5\u00a8U\2\u026d\u026f\7*\2\2\u026e\u0270\5l\67\2\u026f\u026e\3\2\2\2"+
		"\u026f\u0270\3\2\2\2\u0270\u0272\3\2\2\2\u0271\u026d\3\2\2\2\u0271\u0272"+
		"\3\2\2\2\u0272m\3\2\2\2\u0273\u027d\5\u008aF\2\u0274\u027d\5p9\2\u0275"+
		"\u027d\5\u0088E\2\u0276\u027d\5r:\2\u0277\u027d\5t;\2\u0278\u027d\5v<"+
		"\2\u0279\u027d\5\u008cG\2\u027a\u027d\5x=\2\u027b\u027d\5z>\2\u027c\u0273"+
		"\3\2\2\2\u027c\u0274\3\2\2\2\u027c\u0275\3\2\2\2\u027c\u0276\3\2\2\2\u027c"+
		"\u0277\3\2\2\2\u027c\u0278\3\2\2\2\u027c\u0279\3\2\2\2\u027c\u027a\3\2"+
		"\2\2\u027c\u027b\3\2\2\2\u027do\3\2\2\2\u027e\u027f\7+\2\2\u027f\u0280"+
		"\5h\65\2\u0280q\3\2\2\2\u0281\u0282\7)\2\2\u0282\u0283\5\u00dco\2\u0283"+
		"\u0284\5h\65\2\u0284s\3\2\2\2\u0285\u0286\7\30\2\2\u0286\u0287\5\u00dc"+
		"o\2\u0287\u0288\5h\65\2\u0288u\3\2\2\2\u0289\u028b\7,\2\2\u028a\u028c"+
		"\7-\2\2\u028b\u028a\3\2\2\2\u028b\u028c\3\2\2\2\u028c\u028d\3\2\2\2\u028d"+
		"\u028e\5\u00dco\2\u028e\u028f\5h\65\2\u028fw\3\2\2\2\u0290\u0291\7.\2"+
		"\2\u0291\u0292\7\f\2\2\u0292\u0293\5\u00e2r\2\u0293\u0294\7\6\2\2\u0294"+
		"\u0295\5\u00dep\2\u0295\u0296\7\r\2\2\u0296y\3\2\2\2\u0297\u0298\7\'\2"+
		"\2\u0298\u0299\5|?\2\u0299{\3\2\2\2\u029a\u029d\5~@\2\u029b\u029d\5\u0080"+
		"A\2\u029c\u029a\3\2\2\2\u029c\u029b\3\2\2\2\u029d}\3\2\2\2\u029e\u029f"+
		"\5\u00dep\2\u029f\u02a3\7\22\2\2\u02a0\u02a2\5\u0084C\2\u02a1\u02a0\3"+
		"\2\2\2\u02a2\u02a5\3\2\2\2\u02a3\u02a1\3\2\2\2\u02a3\u02a4\3\2\2\2\u02a4"+
		"\u02a6\3\2\2\2\u02a5\u02a3\3\2\2\2\u02a6\u02a7\7\23\2\2\u02a7\177\3\2"+
		"\2\2\u02a8\u02b2\7\u009f\2\2\u02a9\u02ad\7\f\2\2\u02aa\u02ac\5\u00dep"+
		"\2\u02ab\u02aa\3\2\2\2\u02ac\u02af\3\2\2\2\u02ad\u02ab\3\2\2\2\u02ad\u02ae"+
		"\3\2\2\2\u02ae\u02b0\3\2\2\2\u02af\u02ad\3\2\2\2\u02b0\u02b2\7\r\2\2\u02b1"+
		"\u02a8\3\2\2\2\u02b1\u02a9\3\2\2\2\u02b2\u02b3\3\2\2\2\u02b3\u02b8\7\22"+
		"\2\2\u02b4\u02b7\5\u0082B\2\u02b5\u02b7\7\u009f\2\2\u02b6\u02b4\3\2\2"+
		"\2\u02b6\u02b5\3\2\2\2\u02b7\u02ba\3\2\2\2\u02b8\u02b6\3\2\2\2\u02b8\u02b9"+
		"\3\2\2\2\u02b9\u02bb\3\2\2\2\u02ba\u02b8\3\2\2\2\u02bb\u02bc\7\23\2\2"+
		"\u02bc\u0081\3\2\2\2\u02bd\u02c1\7\f\2\2\u02be\u02c0\5\u0084C\2\u02bf"+
		"\u02be\3\2\2\2\u02c0\u02c3\3\2\2\2\u02c1\u02bf\3\2\2\2\u02c1\u02c2\3\2"+
		"\2\2\u02c2\u02c4\3\2\2\2\u02c3\u02c1\3\2\2\2\u02c4\u02c5\7\r\2\2\u02c5"+
		"\u0083\3\2\2\2\u02c6\u02cc\5\u0118\u008d\2\u02c7\u02cc\5\u010a\u0086\2"+
		"\u02c8\u02cc\5\u010c\u0087\2\u02c9\u02cc\5\u0114\u008b\2\u02ca\u02cc\5"+
		"\u0086D\2\u02cb\u02c6\3\2\2\2\u02cb\u02c7\3\2\2\2\u02cb\u02c8\3\2\2\2"+
		"\u02cb\u02c9\3\2\2\2\u02cb\u02ca\3\2\2\2\u02cc\u0085\3\2\2\2\u02cd\u02ce"+
		"\7/\2\2\u02ce\u0087\3\2\2\2\u02cf\u02d0\7\60\2\2\u02d0\u02d1\5h\65\2\u02d1"+
		"\u0089\3\2\2\2\u02d2\u02d7\5h\65\2\u02d3\u02d4\7\61\2\2\u02d4\u02d6\5"+
		"h\65\2\u02d5\u02d3\3\2\2\2\u02d6\u02d9\3\2\2\2\u02d7\u02d5\3\2\2\2\u02d7"+
		"\u02d8\3\2\2\2\u02d8\u008b\3\2\2\2\u02d9\u02d7\3\2\2\2\u02da\u02db\7\62"+
		"\2\2\u02db\u02dc\5\u008eH\2\u02dc\u008d\3\2\2\2\u02dd\u02e1\5\u00f8}\2"+
		"\u02de\u02e1\5\u00fa~\2\u02df\u02e1\5\u0090I\2\u02e0\u02dd\3\2\2\2\u02e0"+
		"\u02de\3\2\2\2\u02e0\u02df\3\2\2\2\u02e1\u008f\3\2\2\2\u02e2\u02e3\5\u0118"+
		"\u008d\2\u02e3\u02e4\5\u0092J\2\u02e4\u0091\3\2\2\2\u02e5\u02f5\7\u009f"+
		"\2\2\u02e6\u02e8\7\f\2\2\u02e7\u02e9\5\34\17\2\u02e8\u02e7\3\2\2\2\u02e8"+
		"\u02e9\3\2\2\2\u02e9\u02ea\3\2\2\2\u02ea\u02ef\5\u00e2r\2\u02eb\u02ec"+
		"\7\63\2\2\u02ec\u02ee\5\u00e2r\2\u02ed\u02eb\3\2\2\2\u02ee\u02f1\3\2\2"+
		"\2\u02ef\u02ed\3\2\2\2\u02ef\u02f0\3\2\2\2\u02f0\u02f2\3\2\2\2\u02f1\u02ef"+
		"\3\2\2\2\u02f2\u02f3\7\r\2\2\u02f3\u02f5\3\2\2\2\u02f4\u02e5\3\2\2\2\u02f4"+
		"\u02e6\3\2\2\2\u02f5\u0093\3\2\2\2\u02f6\u0303\7\u009f\2\2\u02f7\u02f8"+
		"\7\f\2\2\u02f8\u02fd\5\u00e2r\2\u02f9\u02fa\7\63\2\2\u02fa\u02fc\5\u00e2"+
		"r\2\u02fb\u02f9\3\2\2\2\u02fc\u02ff\3\2\2\2\u02fd\u02fb\3\2\2\2\u02fd"+
		"\u02fe\3\2\2\2\u02fe\u0300\3\2\2\2\u02ff\u02fd\3\2\2\2\u0300\u0301\7\r"+
		"\2\2\u0301\u0303\3\2\2\2\u0302\u02f6\3\2\2\2\u0302\u02f7\3\2\2\2\u0303"+
		"\u0095\3\2\2\2\u0304\u0305\7\22\2\2\u0305\u0306\5b\62\2\u0306\u0307\7"+
		"\23\2\2\u0307\u0097\3\2\2\2\u0308\u030d\5\u009aN\2\u0309\u030b\7*\2\2"+
		"\u030a\u030c\5\u0098M\2\u030b\u030a\3\2\2\2\u030b\u030c\3\2\2\2\u030c"+
		"\u030e\3\2\2\2\u030d\u0309\3\2\2\2\u030d\u030e\3\2\2\2\u030e\u0099\3\2"+
		"\2\2\u030f\u0310\5\u00dan\2\u0310\u0311\5\u009cO\2\u0311\u0317\3\2\2\2"+
		"\u0312\u0314\5\u00caf\2\u0313\u0315\5\u009cO\2\u0314\u0313\3\2\2\2\u0314"+
		"\u0315\3\2\2\2\u0315\u0317\3\2\2\2\u0316\u030f\3\2\2\2\u0316\u0312\3\2"+
		"\2\2\u0317\u009b\3\2\2\2\u0318\u031f\5\u009eP\2\u0319\u031b\7\64\2\2\u031a"+
		"\u031c\5\u009eP\2\u031b\u031a\3\2\2\2\u031b\u031c\3\2\2\2\u031c\u031e"+
		"\3\2\2\2\u031d\u0319\3\2\2\2\u031e\u0321\3\2\2\2\u031f\u031d\3\2\2\2\u031f"+
		"\u0320\3\2\2\2\u0320\u009d\3\2\2\2\u0321\u031f\3\2\2\2\u0322\u0323\5\u00a0"+
		"Q\2\u0323\u0324\5\u00a4S\2\u0324\u009f\3\2\2\2\u0325\u0328\5\u00dco\2"+
		"\u0326\u0328\5\u00a2R\2\u0327\u0325\3\2\2\2\u0327\u0326\3\2\2\2\u0328"+
		"\u00a1\3\2\2\2\u0329\u032a\7\u0086\2\2\u032a\u00a3\3\2\2\2\u032b\u0330"+
		"\5\u00a6T\2\u032c\u032d\7\63\2\2\u032d\u032f\5\u00a6T\2\u032e\u032c\3"+
		"\2\2\2\u032f\u0332\3\2\2\2\u0330\u032e\3\2\2\2\u0330\u0331\3\2\2\2\u0331"+
		"\u00a5\3\2\2\2\u0332\u0330\3\2\2\2\u0333\u0334\5\u00d6l\2\u0334\u00a7"+
		"\3\2\2\2\u0335\u0336\5\u00dan\2\u0336\u0337\5\u00aaV\2\u0337\u033d\3\2"+
		"\2\2\u0338\u033a\5\u00ceh\2\u0339\u033b\5\u00aaV\2\u033a\u0339\3\2\2\2"+
		"\u033a\u033b\3\2\2\2\u033b\u033d\3\2\2\2\u033c\u0335\3\2\2\2\u033c\u0338"+
		"\3\2\2\2\u033d\u00a9\3\2\2\2\u033e\u0343\5\u00acW\2\u033f\u0340\7\64\2"+
		"\2\u0340\u0342\5\u00acW\2\u0341\u033f\3\2\2\2\u0342\u0345\3\2\2\2\u0343"+
		"\u0341\3\2\2\2\u0343\u0344\3\2\2\2\u0344\u00ab\3\2\2\2\u0345\u0343\3\2"+
		"\2\2\u0346\u0349\5\u00aeX\2\u0347\u0349\5\u00b0Y\2\u0348\u0346\3\2\2\2"+
		"\u0348\u0347\3\2\2\2\u0349\u034a\3\2\2\2\u034a\u034b\5\u00b2Z\2\u034b"+
		"\u00ad\3\2\2\2\u034c\u034d\5\u00b6\\\2\u034d\u00af\3\2\2\2\u034e\u0351"+
		"\5\u00dep\2\u034f\u0351\5\u00a2R\2\u0350\u034e\3\2\2\2\u0350\u034f\3\2"+
		"\2\2\u0351\u00b1\3\2\2\2\u0352\u0357\5\u00b4[\2\u0353\u0354\7\63\2\2\u0354"+
		"\u0356\5\u00b4[\2\u0355\u0353\3\2\2\2\u0356\u0359\3\2\2\2\u0357\u0355"+
		"\3\2\2\2\u0357\u0358\3\2\2\2\u0358\u00b3\3\2\2\2\u0359\u0357\3\2\2\2\u035a"+
		"\u035b\5\u00d8m\2\u035b\u00b5\3\2\2\2\u035c\u035d\5\u00b8]\2\u035d\u00b7"+
		"\3\2\2\2\u035e\u0363\5\u00ba^\2\u035f\u0360\7\65\2\2\u0360\u0362\5\u00ba"+
		"^\2\u0361\u035f\3\2\2\2\u0362\u0365\3\2\2\2\u0363\u0361\3\2\2\2\u0363"+
		"\u0364\3\2\2\2\u0364\u00b9\3\2\2\2\u0365\u0363\3\2\2\2\u0366\u036b\5\u00be"+
		"`\2\u0367\u0368\7\66\2\2\u0368\u036a\5\u00be`\2\u0369\u0367\3\2\2\2\u036a"+
		"\u036d\3\2\2\2\u036b\u0369\3\2\2\2\u036b\u036c\3\2\2\2\u036c\u00bb\3\2"+
		"\2\2\u036d\u036b\3\2\2\2\u036e\u0370\5\u00c2b\2\u036f\u0371\5\u00c0a\2"+
		"\u0370\u036f\3\2\2\2\u0370\u0371\3\2\2\2\u0371\u00bd\3\2\2\2\u0372\u0376"+
		"\5\u00bc_\2\u0373\u0374\7\67\2\2\u0374\u0376\5\u00bc_\2\u0375\u0372\3"+
		"\2\2\2\u0375\u0373\3\2\2\2\u0376\u00bf\3\2\2\2\u0377\u0378\t\4\2\2\u0378"+
		"\u00c1\3\2\2\2\u0379\u0382\5\u0118\u008d\2\u037a\u0382\5\u00a2R\2\u037b"+
		"\u037c\7:\2\2\u037c\u0382\5\u00c4c\2\u037d\u037e\7\f\2\2\u037e\u037f\5"+
		"\u00b6\\\2\u037f\u0380\7\r\2\2\u0380\u0382\3\2\2\2\u0381\u0379\3\2\2\2"+
		"\u0381\u037a\3\2\2\2\u0381\u037b\3\2\2\2\u0381\u037d\3\2\2\2\u0382\u00c3"+
		"\3\2\2\2\u0383\u0391\5\u00c6d\2\u0384\u038d\7\f\2\2\u0385\u038a\5\u00c6"+
		"d\2\u0386\u0387\7\65\2\2\u0387\u0389\5\u00c6d\2\u0388\u0386\3\2\2\2\u0389"+
		"\u038c\3\2\2\2\u038a\u0388\3\2\2\2\u038a\u038b\3\2\2\2\u038b\u038e\3\2"+
		"\2\2\u038c\u038a\3\2\2\2\u038d\u0385\3\2\2\2\u038d\u038e\3\2\2\2\u038e"+
		"\u038f\3\2\2\2\u038f\u0391\7\r\2\2\u0390\u0383\3\2\2\2\u0390\u0384\3\2"+
		"\2\2\u0391\u00c5\3\2\2\2\u0392\u039a\5\u0118\u008d\2\u0393\u039a\5\u00a2"+
		"R\2\u0394\u0397\7\67\2\2\u0395\u0398\5\u0118\u008d\2\u0396\u0398\5\u00a2"+
		"R\2\u0397\u0395\3\2\2\2\u0397\u0396\3\2\2\2\u0398\u039a\3\2\2\2\u0399"+
		"\u0392\3\2\2\2\u0399\u0393\3\2\2\2\u0399\u0394\3\2\2\2\u039a\u00c7\3\2"+
		"\2\2\u039b\u039c\7\u0090\2\2\u039c\u00c9\3\2\2\2\u039d\u03a0\5\u00d2j"+
		"\2\u039e\u03a0\5\u00ccg\2\u039f\u039d\3\2\2\2\u039f\u039e\3\2\2\2\u03a0"+
		"\u00cb\3\2\2\2\u03a1\u03a2\7\32\2\2\u03a2\u03a3\5\u009cO\2\u03a3\u03a4"+
		"\7\33\2\2\u03a4\u00cd\3\2\2\2\u03a5\u03a8\5\u00d4k\2\u03a6\u03a8\5\u00d0"+
		"i\2\u03a7\u03a5\3\2\2\2\u03a7\u03a6\3\2\2\2\u03a8\u00cf\3\2\2\2\u03a9"+
		"\u03aa\7\32\2\2\u03aa\u03ab\5\u00aaV\2\u03ab\u03ac\7\33\2\2\u03ac\u00d1"+
		"\3\2\2\2\u03ad\u03af\7\f\2\2\u03ae\u03b0\5\u00d6l\2\u03af\u03ae\3\2\2"+
		"\2\u03b0\u03b1\3\2\2\2\u03b1\u03af\3\2\2\2\u03b1\u03b2\3\2\2\2\u03b2\u03b3"+
		"\3\2\2\2\u03b3\u03b4\7\r\2\2\u03b4\u00d3\3\2\2\2\u03b5\u03b7\7\f\2\2\u03b6"+
		"\u03b8\5\u00d8m\2\u03b7\u03b6\3\2\2\2\u03b8\u03b9\3\2\2\2\u03b9\u03b7"+
		"\3\2\2\2\u03b9\u03ba\3\2\2\2\u03ba\u03bb\3\2\2\2\u03bb\u03bc\7\r\2\2\u03bc"+
		"\u00d5\3\2\2\2\u03bd\u03c0\5\u00dan\2\u03be\u03c0\5\u00caf\2\u03bf\u03bd"+
		"\3\2\2\2\u03bf\u03be\3\2\2\2\u03c0\u00d7\3\2\2\2\u03c1\u03c4\5\u00dan"+
		"\2\u03c2\u03c4\5\u00ceh\2\u03c3\u03c1\3\2\2\2\u03c3\u03c2\3\2\2\2\u03c4"+
		"\u00d9\3\2\2\2\u03c5\u03c8\5\u00dep\2\u03c6\u03c8\5\u00e0q\2\u03c7\u03c5"+
		"\3\2\2\2\u03c7\u03c6\3\2\2\2\u03c8\u00db\3\2\2\2\u03c9\u03cc\5\u00dep"+
		"\2\u03ca\u03cc\5\u0118\u008d\2\u03cb\u03c9\3\2\2\2\u03cb\u03ca\3\2\2\2"+
		"\u03cc\u00dd\3\2\2\2\u03cd\u03ce\t\5\2\2\u03ce\u00df\3\2\2\2\u03cf\u03d6"+
		"\5\u0118\u008d\2\u03d0\u03d6\5\u010a\u0086\2\u03d1\u03d6\5\u010c\u0087"+
		"\2\u03d2\u03d6\5\u0114\u008b\2\u03d3\u03d6\5\u011c\u008f\2\u03d4\u03d6"+
		"\7\u009f\2\2\u03d5\u03cf\3\2\2\2\u03d5\u03d0\3\2\2\2\u03d5\u03d1\3\2\2"+
		"\2\u03d5\u03d2\3\2\2\2\u03d5\u03d3\3\2\2\2\u03d5\u03d4\3\2\2\2\u03d6\u00e1"+
		"\3\2\2\2\u03d7\u03d8\5\u00e4s\2\u03d8\u00e3\3\2\2\2\u03d9\u03de\5\u00e6"+
		"t\2\u03da\u03db\7;\2\2\u03db\u03dd\5\u00e6t\2\u03dc\u03da\3\2\2\2\u03dd"+
		"\u03e0\3\2\2\2\u03de\u03dc\3\2\2\2\u03de\u03df\3\2\2\2\u03df\u00e5\3\2"+
		"\2\2\u03e0\u03de\3\2\2\2\u03e1\u03e6\5\u00e8u\2\u03e2\u03e3\7<\2\2\u03e3"+
		"\u03e5\5\u00e8u\2\u03e4\u03e2\3\2\2\2\u03e5\u03e8\3\2\2\2\u03e6\u03e4"+
		"\3\2\2\2\u03e6\u03e7\3\2\2\2\u03e7\u00e7\3\2\2\2\u03e8\u03e6\3\2\2\2\u03e9"+
		"\u03ea\5\u00eav\2\u03ea\u00e9\3\2\2\2\u03eb\u03fd\5\u00ecw\2\u03ec\u03ed"+
		"\7=\2\2\u03ed\u03fe\5\u00ecw\2\u03ee\u03ef\7>\2\2\u03ef\u03fe\5\u00ec"+
		"w\2\u03f0\u03f1\7?\2\2\u03f1\u03fe\5\u00ecw\2\u03f2\u03f3\7@\2\2\u03f3"+
		"\u03fe\5\u00ecw\2\u03f4\u03f5\7A\2\2\u03f5\u03fe\5\u00ecw\2\u03f6\u03f7"+
		"\7B\2\2\u03f7\u03fe\5\u00ecw\2\u03f8\u03f9\7C\2\2\u03f9\u03fe\5\u0094"+
		"K\2\u03fa\u03fb\7D\2\2\u03fb\u03fc\7C\2\2\u03fc\u03fe\5\u0094K\2\u03fd"+
		"\u03ec\3\2\2\2\u03fd\u03ee\3\2\2\2\u03fd\u03f0\3\2\2\2\u03fd\u03f2\3\2"+
		"\2\2\u03fd\u03f4\3\2\2\2\u03fd\u03f6\3\2\2\2\u03fd\u03f8\3\2\2\2\u03fd"+
		"\u03fa\3\2\2\2\u03fd\u03fe\3\2\2\2\u03fe\u00eb\3\2\2\2\u03ff\u0400\5\u00ee"+
		"x\2\u0400\u00ed\3\2\2\2\u0401\u0405\5\u00f2z\2\u0402\u0404\5\u00f0y\2"+
		"\u0403\u0402\3\2\2\2\u0404\u0407\3\2\2\2\u0405\u0403\3\2\2\2\u0405\u0406"+
		"\3\2\2\2\u0406\u00ef\3\2\2\2\u0407\u0405\3\2\2\2\u0408\u0409\79\2\2\u0409"+
		"\u041a\5\u00f2z\2\u040a\u040b\7E\2\2\u040b\u041a\5\u00f2z\2\u040c\u040f"+
		"\5\u0110\u0089\2\u040d\u040f\5\u0112\u008a\2\u040e\u040c\3\2\2\2\u040e"+
		"\u040d\3\2\2\2\u040f\u0416\3\2\2\2\u0410\u0411\7\13\2\2\u0411\u0415\5"+
		"\u00f4{\2\u0412\u0413\7\66\2\2\u0413\u0415\5\u00f4{\2\u0414\u0410\3\2"+
		"\2\2\u0414\u0412\3\2\2\2\u0415\u0418\3\2\2\2\u0416\u0414\3\2\2\2\u0416"+
		"\u0417\3\2\2\2\u0417\u041a\3\2\2\2\u0418\u0416\3\2\2\2\u0419\u0408\3\2"+
		"\2\2\u0419\u040a\3\2\2\2\u0419\u040e\3\2\2\2\u041a\u00f1\3\2\2\2\u041b"+
		"\u0422\5\u00f4{\2\u041c\u041d\7\13\2\2\u041d\u0421\5\u00f4{\2\u041e\u041f"+
		"\7\66\2\2\u041f\u0421\5\u00f4{\2\u0420\u041c\3\2\2\2\u0420\u041e\3\2\2"+
		"\2\u0421\u0424\3\2\2\2\u0422\u0420\3\2\2\2\u0422\u0423\3\2\2\2\u0423\u00f3"+
		"\3\2\2\2\u0424\u0422\3\2\2\2\u0425\u0426\7:\2\2\u0426\u042d\5\u00f6|\2"+
		"\u0427\u0428\79\2\2\u0428\u042d\5\u00f6|\2\u0429\u042a\7E\2\2\u042a\u042d"+
		"\5\u00f6|\2\u042b\u042d\5\u00f6|\2\u042c\u0425\3\2\2\2\u042c\u0427\3\2"+
		"\2\2\u042c\u0429\3\2\2\2\u042c\u042b\3\2\2\2\u042d\u00f5\3\2\2\2\u042e"+
		"\u0436\5\u00f8}\2\u042f\u0436\5\u00fa~\2\u0430\u0436\5\u0108\u0085\2\u0431"+
		"\u0436\5\u010a\u0086\2\u0432\u0436\5\u010c\u0087\2\u0433\u0436\5\u0114"+
		"\u008b\2\u0434\u0436\5\u00dep\2\u0435\u042e\3\2\2\2\u0435\u042f\3\2\2"+
		"\2\u0435\u0430\3\2\2\2\u0435\u0431\3\2\2\2\u0435\u0432\3\2\2\2\u0435\u0433"+
		"\3\2\2\2\u0435\u0434\3\2\2\2\u0436\u00f7\3\2\2\2\u0437\u0438\7\f\2\2\u0438"+
		"\u0439\5\u00e2r\2\u0439\u043a\7\r\2\2\u043a\u00f9\3\2\2\2\u043b\u053e"+
		"\5\u0106\u0084\2\u043c\u043d\7F\2\2\u043d\u043e\7\f\2\2\u043e\u043f\5"+
		"\u00e2r\2\u043f\u0440\7\r\2\2\u0440\u053e\3\2\2\2\u0441\u0442\7G\2\2\u0442"+
		"\u0443\7\f\2\2\u0443\u0444\5\u00e2r\2\u0444\u0445\7\r\2\2\u0445\u053e"+
		"\3\2\2\2\u0446\u0447\7H\2\2\u0447\u0448\7\f\2\2\u0448\u0449\5\u00e2r\2"+
		"\u0449\u044a\7\63\2\2\u044a\u044b\5\u00e2r\2\u044b\u044c\7\r\2\2\u044c"+
		"\u053e\3\2\2\2\u044d\u044e\7I\2\2\u044e\u044f\7\f\2\2\u044f\u0450\5\u00e2"+
		"r\2\u0450\u0451\7\r\2\2\u0451\u053e\3\2\2\2\u0452\u0453\7J\2\2\u0453\u0454"+
		"\7\f\2\2\u0454\u0455\5\u00dep\2\u0455\u0456\7\r\2\2\u0456\u053e\3\2\2"+
		"\2\u0457\u0458\7K\2\2\u0458\u0459\7\f\2\2\u0459\u045a\5\u00e2r\2\u045a"+
		"\u045b\7\r\2\2\u045b\u053e\3\2\2\2\u045c\u045d\7L\2\2\u045d\u045e\7\f"+
		"\2\2\u045e\u045f\5\u00e2r\2\u045f\u0460\7\r\2\2\u0460\u053e\3\2\2\2\u0461"+
		"\u0467\7M\2\2\u0462\u0463\7\f\2\2\u0463\u0464\5\u00e2r\2\u0464\u0465\7"+
		"\r\2\2\u0465\u0468\3\2\2\2\u0466\u0468\7\u009f\2\2\u0467\u0462\3\2\2\2"+
		"\u0467\u0466\3\2\2\2\u0468\u053e\3\2\2\2\u0469\u046a\7N\2\2\u046a\u053e"+
		"\7\u009f\2\2\u046b\u046c\7O\2\2\u046c\u046d\7\f\2\2\u046d\u046e\5\u00e2"+
		"r\2\u046e\u046f\7\r\2\2\u046f\u053e\3\2\2\2\u0470\u0471\7P\2\2\u0471\u0472"+
		"\7\f\2\2\u0472\u0473\5\u00e2r\2\u0473\u0474\7\r\2\2\u0474\u053e\3\2\2"+
		"\2\u0475\u0476\7Q\2\2\u0476\u0477\7\f\2\2\u0477\u0478\5\u00e2r\2\u0478"+
		"\u0479\7\r\2\2\u0479\u053e\3\2\2\2\u047a\u047b\7R\2\2\u047b\u047c\7\f"+
		"\2\2\u047c\u047d\5\u00e2r\2\u047d\u047e\7\r\2\2\u047e\u053e\3\2\2\2\u047f"+
		"\u0480\7S\2\2\u0480\u053e\5\u0094K\2\u0481\u053e\5\u00fe\u0080\2\u0482"+
		"\u0483\7T\2\2\u0483\u0484\7\f\2\2\u0484\u0485\5\u00e2r\2\u0485\u0486\7"+
		"\r\2\2\u0486\u053e\3\2\2\2\u0487\u053e\5\u0100\u0081\2\u0488\u0489\7U"+
		"\2\2\u0489\u048a\7\f\2\2\u048a\u048b\5\u00e2r\2\u048b\u048c\7\r\2\2\u048c"+
		"\u053e\3\2\2\2\u048d\u048e\7V\2\2\u048e\u048f\7\f\2\2\u048f\u0490\5\u00e2"+
		"r\2\u0490\u0491\7\r\2\2\u0491\u053e\3\2\2\2\u0492\u0493\7W\2\2\u0493\u0494"+
		"\7\f\2\2\u0494\u0495\5\u00e2r\2\u0495\u0496\7\r\2\2\u0496\u053e\3\2\2"+
		"\2\u0497\u0498\7X\2\2\u0498\u0499\7\f\2\2\u0499\u049a\5\u00e2r\2\u049a"+
		"\u049b\7\63\2\2\u049b\u049c\5\u00e2r\2\u049c\u049d\7\r\2\2\u049d\u053e"+
		"\3\2\2\2\u049e\u049f\7Y\2\2\u049f\u04a0\7\f\2\2\u04a0\u04a1\5\u00e2r\2"+
		"\u04a1\u04a2\7\63\2\2\u04a2\u04a3\5\u00e2r\2\u04a3\u04a4\7\r\2\2\u04a4"+
		"\u053e\3\2\2\2\u04a5\u04a6\7Z\2\2\u04a6\u04a7\7\f\2\2\u04a7\u04a8\5\u00e2"+
		"r\2\u04a8\u04a9\7\63\2\2\u04a9\u04aa\5\u00e2r\2\u04aa\u04ab\7\r\2\2\u04ab"+
		"\u053e\3\2\2\2\u04ac\u04ad\7[\2\2\u04ad\u04ae\7\f\2\2\u04ae\u04af\5\u00e2"+
		"r\2\u04af\u04b0\7\63\2\2\u04b0\u04b1\5\u00e2r\2\u04b1\u04b2\7\r\2\2\u04b2"+
		"\u053e\3\2\2\2\u04b3\u04b4\7\\\2\2\u04b4\u04b5\7\f\2\2\u04b5\u04b6\5\u00e2"+
		"r\2\u04b6\u04b7\7\63\2\2\u04b7\u04b8\5\u00e2r\2\u04b8\u04b9\7\r\2\2\u04b9"+
		"\u053e\3\2\2\2\u04ba\u04bb\7]\2\2\u04bb\u04bc\7\f\2\2\u04bc\u04bd\5\u00e2"+
		"r\2\u04bd\u04be\7\r\2\2\u04be\u053e\3\2\2\2\u04bf\u04c0\7^\2\2\u04c0\u04c1"+
		"\7\f\2\2\u04c1\u04c2\5\u00e2r\2\u04c2\u04c3\7\r\2\2\u04c3\u053e\3\2\2"+
		"\2\u04c4\u04c5\7_\2\2\u04c5\u04c6\7\f\2\2\u04c6\u04c7\5\u00e2r\2\u04c7"+
		"\u04c8\7\r\2\2\u04c8\u053e\3\2\2\2\u04c9\u04ca\7`\2\2\u04ca\u04cb\7\f"+
		"\2\2\u04cb\u04cc\5\u00e2r\2\u04cc\u04cd\7\r\2\2\u04cd\u053e\3\2\2\2\u04ce"+
		"\u04cf\7a\2\2\u04cf\u04d0\7\f\2\2\u04d0\u04d1\5\u00e2r\2\u04d1\u04d2\7"+
		"\r\2\2\u04d2\u053e\3\2\2\2\u04d3\u04d4\7b\2\2\u04d4\u04d5\7\f\2\2\u04d5"+
		"\u04d6\5\u00e2r\2\u04d6\u04d7\7\r\2\2\u04d7\u053e\3\2\2\2\u04d8\u04d9"+
		"\7c\2\2\u04d9\u04da\7\f\2\2\u04da\u04db\5\u00e2r\2\u04db\u04dc\7\r\2\2"+
		"\u04dc\u053e\3\2\2\2\u04dd\u04de\7d\2\2\u04de\u04df\7\f\2\2\u04df\u04e0"+
		"\5\u00e2r\2\u04e0\u04e1\7\r\2\2\u04e1\u053e\3\2\2\2\u04e2\u04e3\7e\2\2"+
		"\u04e3\u053e\7\u009f\2\2\u04e4\u04e5\7f\2\2\u04e5\u053e\7\u009f\2\2\u04e6"+
		"\u04e7\7g\2\2\u04e7\u053e\7\u009f\2\2\u04e8\u04e9\7h\2\2\u04e9\u04ea\7"+
		"\f\2\2\u04ea\u04eb\5\u00e2r\2\u04eb\u04ec\7\r\2\2\u04ec\u053e\3\2\2\2"+
		"\u04ed\u04ee\7i\2\2\u04ee\u04ef\7\f\2\2\u04ef\u04f0\5\u00e2r\2\u04f0\u04f1"+
		"\7\r\2\2\u04f1\u053e\3\2\2\2\u04f2\u04f3\7j\2\2\u04f3\u04f4\7\f\2\2\u04f4"+
		"\u04f5\5\u00e2r\2\u04f5\u04f6\7\r\2\2\u04f6\u053e\3\2\2\2\u04f7\u04f8"+
		"\7k\2\2\u04f8\u04f9\7\f\2\2\u04f9\u04fa\5\u00e2r\2\u04fa\u04fb\7\r\2\2"+
		"\u04fb\u053e\3\2\2\2\u04fc\u04fd\7l\2\2\u04fd\u04fe\7\f\2\2\u04fe\u04ff"+
		"\5\u00e2r\2\u04ff\u0500\7\r\2\2\u0500\u053e\3\2\2\2\u0501\u0502\7m\2\2"+
		"\u0502\u053e\5\u0094K\2\u0503\u0504\7n\2\2\u0504\u0505\7\f\2\2\u0505\u0506"+
		"\5\u00e2r\2\u0506\u0507\7\63\2\2\u0507\u0508\5\u00e2r\2\u0508\u0509\7"+
		"\63\2\2\u0509\u050a\5\u00e2r\2\u050a\u050b\7\r\2\2\u050b\u053e\3\2\2\2"+
		"\u050c\u050d\7o\2\2\u050d\u050e\7\f\2\2\u050e\u050f\5\u00e2r\2\u050f\u0510"+
		"\7\63\2\2\u0510\u0511\5\u00e2r\2\u0511\u0512\7\r\2\2\u0512\u053e\3\2\2"+
		"\2\u0513\u0514\7p\2\2\u0514\u0515\7\f\2\2\u0515\u0516\5\u00e2r\2\u0516"+
		"\u0517\7\63\2\2\u0517\u0518\5\u00e2r\2\u0518\u0519\7\r\2\2\u0519\u053e"+
		"\3\2\2\2\u051a\u051b\7q\2\2\u051b\u051c\7\f\2\2\u051c\u051d\5\u00e2r\2"+
		"\u051d\u051e\7\63\2\2\u051e\u051f\5\u00e2r\2\u051f\u0520\7\r\2\2\u0520"+
		"\u053e\3\2\2\2\u0521\u0522\7r\2\2\u0522\u0523\7\f\2\2\u0523\u0524\5\u00e2"+
		"r\2\u0524\u0525\7\r\2\2\u0525\u053e\3\2\2\2\u0526\u0527\7s\2\2\u0527\u0528"+
		"\7\f\2\2\u0528\u0529\5\u00e2r\2\u0529\u052a\7\r\2\2\u052a\u053e\3\2\2"+
		"\2\u052b\u052c\7t\2\2\u052c\u052d\7\f\2\2\u052d\u052e\5\u00e2r\2\u052e"+
		"\u052f\7\r\2\2\u052f\u053e\3\2\2\2\u0530\u0531\7u\2\2\u0531\u0532\7\f"+
		"\2\2\u0532\u0533\5\u00e2r\2\u0533\u0534\7\r\2\2\u0534\u053e\3\2\2\2\u0535"+
		"\u0536\7v\2\2\u0536\u0537\7\f\2\2\u0537\u0538\5\u00e2r\2\u0538\u0539\7"+
		"\r\2\2\u0539\u053e\3\2\2\2\u053a\u053e\5\u00fc\177\2\u053b\u053e\5\u0102"+
		"\u0082\2\u053c\u053e\5\u0104\u0083\2\u053d\u043b\3\2\2\2\u053d\u043c\3"+
		"\2\2\2\u053d\u0441\3\2\2\2\u053d\u0446\3\2\2\2\u053d\u044d\3\2\2\2\u053d"+
		"\u0452\3\2\2\2\u053d\u0457\3\2\2\2\u053d\u045c\3\2\2\2\u053d\u0461\3\2"+
		"\2\2\u053d\u0469\3\2\2\2\u053d\u046b\3\2\2\2\u053d\u0470\3\2\2\2\u053d"+
		"\u0475\3\2\2\2\u053d\u047a\3\2\2\2\u053d\u047f\3\2\2\2\u053d\u0481\3\2"+
		"\2\2\u053d\u0482\3\2\2\2\u053d\u0487\3\2\2\2\u053d\u0488\3\2\2\2\u053d"+
		"\u048d\3\2\2\2\u053d\u0492\3\2\2\2\u053d\u0497\3\2\2\2\u053d\u049e\3\2"+
		"\2\2\u053d\u04a5\3\2\2\2\u053d\u04ac\3\2\2\2\u053d\u04b3\3\2\2\2\u053d"+
		"\u04ba\3\2\2\2\u053d\u04bf\3\2\2\2\u053d\u04c4\3\2\2\2\u053d\u04c9\3\2"+
		"\2\2\u053d\u04ce\3\2\2\2\u053d\u04d3\3\2\2\2\u053d\u04d8\3\2\2\2\u053d"+
		"\u04dd\3\2\2\2\u053d\u04e2\3\2\2\2\u053d\u04e4\3\2\2\2\u053d\u04e6\3\2"+
		"\2\2\u053d\u04e8\3\2\2\2\u053d\u04ed\3\2\2\2\u053d\u04f2\3\2\2\2\u053d"+
		"\u04f7\3\2\2\2\u053d\u04fc\3\2\2\2\u053d\u0501\3\2\2\2\u053d\u0503\3\2"+
		"\2\2\u053d\u050c\3\2\2\2\u053d\u0513\3\2\2\2\u053d\u051a\3\2\2\2\u053d"+
		"\u0521\3\2\2\2\u053d\u0526\3\2\2\2\u053d\u052b\3\2\2\2\u053d\u0530\3\2"+
		"\2\2\u053d\u0535\3\2\2\2\u053d\u053a\3\2\2\2\u053d\u053b\3\2\2\2\u053d"+
		"\u053c\3\2\2\2\u053e\u00fb\3\2\2\2\u053f\u0540\7w\2\2\u0540\u0541\7\f"+
		"\2\2\u0541\u0542\5\u00e2r\2\u0542\u0543\7\63\2\2\u0543\u0546\5\u00e2r"+
		"\2\u0544\u0545\7\63\2\2\u0545\u0547\5\u00e2r\2\u0546\u0544\3\2\2\2\u0546"+
		"\u0547\3\2\2\2\u0547\u0548\3\2\2\2\u0548\u0549\7\r\2\2\u0549\u00fd\3\2"+
		"\2\2\u054a\u054b\7x\2\2\u054b\u054c\7\f\2\2\u054c\u054d\5\u00e2r\2\u054d"+
		"\u054e\7\63\2\2\u054e\u0551\5\u00e2r\2\u054f\u0550\7\63\2\2\u0550\u0552"+
		"\5\u00e2r\2\u0551\u054f\3\2\2\2\u0551\u0552\3\2\2\2\u0552\u0553\3\2\2"+
		"\2\u0553\u0554\7\r\2\2\u0554\u00ff\3\2\2\2\u0555\u0556\7y\2\2\u0556\u0557"+
		"\7\f\2\2\u0557\u0558\5\u00e2r\2\u0558\u0559\7\63\2\2\u0559\u055a\5\u00e2"+
		"r\2\u055a\u055b\7\63\2\2\u055b\u055e\5\u00e2r\2\u055c\u055d\7\63\2\2\u055d"+
		"\u055f\5\u00e2r\2\u055e\u055c\3\2\2\2\u055e\u055f\3\2\2\2\u055f\u0560"+
		"\3\2\2\2\u0560\u0561\7\r\2\2\u0561\u0101\3\2\2\2\u0562\u0563\7z\2\2\u0563"+
		"\u0564\5h\65\2\u0564\u0103\3\2\2\2\u0565\u0566\7D\2\2\u0566\u0567\7z\2"+
		"\2\u0567\u0568\5h\65\2\u0568\u0105\3\2\2\2\u0569\u056a\7{\2\2\u056a\u056c"+
		"\7\f\2\2\u056b\u056d\5\34\17\2\u056c\u056b\3\2\2\2\u056c\u056d\3\2\2\2"+
		"\u056d\u0570\3\2\2\2\u056e\u0571\7\13\2\2\u056f\u0571\5\u00e2r\2\u0570"+
		"\u056e\3\2\2\2\u0570\u056f\3\2\2\2\u0571\u0572\3\2\2\2\u0572\u05aa\7\r"+
		"\2\2\u0573\u0574\7|\2\2\u0574\u0576\7\f\2\2\u0575\u0577\5\34\17\2\u0576"+
		"\u0575\3\2\2\2\u0576\u0577\3\2\2\2\u0577\u0578\3\2\2\2\u0578\u0579\5\u00e2"+
		"r\2\u0579\u057a\7\r\2\2\u057a\u05aa\3\2\2\2\u057b\u057c\7}\2\2\u057c\u057e"+
		"\7\f\2\2\u057d\u057f\5\34\17\2\u057e\u057d\3\2\2\2\u057e\u057f\3\2\2\2"+
		"\u057f\u0580\3\2\2\2\u0580\u0581\5\u00e2r\2\u0581\u0582\7\r\2\2\u0582"+
		"\u05aa\3\2\2\2\u0583\u0584\7~\2\2\u0584\u0586\7\f\2\2\u0585\u0587\5\34"+
		"\17\2\u0586\u0585\3\2\2\2\u0586\u0587\3\2\2\2\u0587\u0588\3\2\2\2\u0588"+
		"\u0589\5\u00e2r\2\u0589\u058a\7\r\2\2\u058a\u05aa\3\2\2\2\u058b\u058c"+
		"\7\177\2\2\u058c\u058e\7\f\2\2\u058d\u058f\5\34\17\2\u058e\u058d\3\2\2"+
		"\2\u058e\u058f\3\2\2\2\u058f\u0590\3\2\2\2\u0590\u0591\5\u00e2r\2\u0591"+
		"\u0592\7\r\2\2\u0592\u05aa\3\2\2\2\u0593\u0594\7\u0080\2\2\u0594\u0596"+
		"\7\f\2\2\u0595\u0597\5\34\17\2\u0596\u0595\3\2\2\2\u0596\u0597\3\2\2\2"+
		"\u0597\u0598\3\2\2\2\u0598\u0599\5\u00e2r\2\u0599\u059a\7\r\2\2\u059a"+
		"\u05aa\3\2\2\2\u059b\u059c\7\u0081\2\2\u059c\u059e\7\f\2\2\u059d\u059f"+
		"\5\34\17\2\u059e\u059d\3\2\2\2\u059e\u059f\3\2\2\2\u059f\u05a0\3\2\2\2"+
		"\u05a0\u05a5\5\u00e2r\2\u05a1\u05a2\7\64\2\2\u05a2\u05a3\7\u0082\2\2\u05a3"+
		"\u05a4\7=\2\2\u05a4\u05a6\5\u0116\u008c\2\u05a5\u05a1\3\2\2\2\u05a5\u05a6"+
		"\3\2\2\2\u05a6\u05a7\3\2\2\2\u05a7\u05a8\7\r\2\2\u05a8\u05aa\3\2\2\2\u05a9"+
		"\u0569\3\2\2\2\u05a9\u0573\3\2\2\2\u05a9\u057b\3\2\2\2\u05a9\u0583\3\2"+
		"\2\2\u05a9\u058b\3\2\2\2\u05a9\u0593\3\2\2\2\u05a9\u059b\3\2\2\2\u05aa"+
		"\u0107\3\2\2\2\u05ab\u05ad\5\u0118\u008d\2\u05ac\u05ae\5\u0092J\2\u05ad"+
		"\u05ac\3\2\2\2\u05ad\u05ae\3\2\2\2\u05ae\u0109\3\2\2\2\u05af\u05b3\5\u0116"+
		"\u008c\2\u05b0\u05b4\7\u008f\2\2\u05b1\u05b2\7\u0083\2\2\u05b2\u05b4\5"+
		"\u0118\u008d\2\u05b3\u05b0\3\2\2\2\u05b3\u05b1\3\2\2\2\u05b3\u05b4\3\2"+
		"\2\2\u05b4\u010b\3\2\2\2\u05b5\u05b9\5\u010e\u0088\2\u05b6\u05b9\5\u0110"+
		"\u0089\2\u05b7\u05b9\5\u0112\u008a\2\u05b8\u05b5\3\2\2\2\u05b8\u05b6\3"+
		"\2\2\2\u05b8\u05b7\3\2\2\2\u05b9\u010d\3\2\2\2\u05ba\u05bb\t\6\2\2\u05bb"+
		"\u010f\3\2\2\2\u05bc\u05bd\t\7\2\2\u05bd\u0111\3\2\2\2\u05be\u05bf\t\b"+
		"\2\2\u05bf\u0113\3\2\2\2\u05c0\u05c1\t\t\2\2\u05c1\u0115\3\2\2\2\u05c2"+
		"\u05c3\t\n\2\2\u05c3\u0117\3\2\2\2\u05c4\u05c7\7\u0089\2\2\u05c5\u05c7"+
		"\5\u011a\u008e\2\u05c6\u05c4\3\2\2\2\u05c6\u05c5\3\2\2\2\u05c7\u0119\3"+
		"\2\2\2\u05c8\u05c9\t\13\2\2\u05c9\u011b\3\2\2\2\u05ca\u05cb\t\f\2\2\u05cb"+
		"\u011d\3\2\2\2\u0089\u0122\u0128\u012e\u0130\u0147\u0155\u015a\u015d\u0168"+
		"\u0173\u017c\u0182\u0186\u018c\u018f\u0194\u0198\u01a0\u01aa\u01c0\u01c4"+
		"\u01ce\u01db\u01e0\u01e3\u01e6\u01e9\u01f0\u01f8\u01fd\u0203\u020c\u0212"+
		"\u0214\u0218\u021c\u021e\u0228\u022c\u022f\u0239\u023d\u0240\u0244\u024b"+
		"\u0252\u0254\u0259\u025e\u0262\u0265\u0269\u026f\u0271\u027c\u028b\u029c"+
		"\u02a3\u02ad\u02b1\u02b6\u02b8\u02c1\u02cb\u02d7\u02e0\u02e8\u02ef\u02f4"+
		"\u02fd\u0302\u030b\u030d\u0314\u0316\u031b\u031f\u0327\u0330\u033a\u033c"+
		"\u0343\u0348\u0350\u0357\u0363\u036b\u0370\u0375\u0381\u038a\u038d\u0390"+
		"\u0397\u0399\u039f\u03a7\u03b1\u03b9\u03bf\u03c3\u03c7\u03cb\u03d5\u03de"+
		"\u03e6\u03fd\u0405\u040e\u0414\u0416\u0419\u0420\u0422\u042c\u0435\u0467"+
		"\u053d\u0546\u0551\u055e\u056c\u0570\u0576\u057e\u0586\u058e\u0596\u059e"+
		"\u05a5\u05a9\u05ad\u05b3\u05b8\u05c6";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}