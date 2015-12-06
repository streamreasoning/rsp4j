package it.polimi;

import it.polimi.heaven.baselines.enums.JenaEventType;
import it.polimi.heaven.baselines.enums.OntoLanguage;
import it.polimi.heaven.baselines.esper.RSPListener;
import it.polimi.heaven.baselines.jena.BaselineQuery;
import it.polimi.heaven.baselines.jena.GraphBaseline;
import it.polimi.heaven.baselines.jena.JenaEngine;
import it.polimi.heaven.baselines.jena.StatementBaseline;
import it.polimi.heaven.baselines.jena.encoders.GraphEncoder;
import it.polimi.heaven.baselines.jena.encoders.StatementEncoder;
import it.polimi.heaven.baselines.utils.BaselinesUtils;
import it.polimi.heaven.baselines.utils.FileUtils;
import it.polimi.heaven.baselines.utils.GetPropertyValues;
import it.polimi.heaven.core.enums.Reasoning;
import it.polimi.heaven.core.teststand.TestStand;
import it.polimi.heaven.core.teststand.collector.ResultCollector;
import it.polimi.heaven.core.teststand.data.Experiment;
import it.polimi.heaven.core.teststand.rspengine.RSPEngine;
import it.polimi.heaven.core.teststand.rspengine.Receiver;
import it.polimi.heaven.core.teststand.streamer.Encoder;
import it.polimi.heaven.core.teststand.streamer.ParsingTemplate;
import it.polimi.heaven.core.teststand.streamer.Streamer;
import it.polimi.heaven.core.teststand.streamer.flowrateprofiler.FlowRateProfiler;
import it.polimi.heaven.core.teststand.streamer.flowrateprofiler.RDF2RDFStream;
import it.polimi.heaven.core.teststand.streamer.flowrateprofiler.profiles.ConstantFlowRateProfiler;
import it.polimi.heaven.core.teststand.streamer.flowrateprofiler.profiles.CustomStepFlowRateProfiler;
import it.polimi.heaven.core.teststand.streamer.flowrateprofiler.profiles.FlowRateProfile;
import it.polimi.heaven.core.teststand.streamer.flowrateprofiler.profiles.RandomFlowRateProfiler;
import it.polimi.heaven.core.teststand.streamer.flowrateprofiler.profiles.StepFactorFlowRateProfiler;
import it.polimi.heaven.core.teststand.streamer.flowrateprofiler.profiles.StepFlowRateProfiler;
import it.polimi.heaven.core.teststand.streamer.lubm.LUBMParser;
import it.polimi.services.FileService;
import it.polimi.services.SQLListeService;
import it.polimi.utils.RDFSUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaselineMain {

	// EVENT TYPES

	private static JenaEventType CEP_EVENT_TYPE;
	private static int EXPERIMENT_NUMBER;

	private static RSPEngine engine;
	private static Receiver receiver;
	private static Date EXPERIMENT_DATE;
	private static final DateFormat DT = new SimpleDateFormat("yyyy_MM_dd");

	private static TestStand test_stand;
	private static ResultCollector result_collector;
	private static RSPListener listener;

	private static int EXECUTION_NUMBER;

	private static Streamer streamer;
	private static OntoLanguage ONTO_LANGUAGE;
	private static Reasoning REASONING_MODE;
	private static FlowRateProfile FLOW_RATE_PROFILE;

	private static String eventBuilderCodeName;
	private static int X;
	private static int Y;
	private static int INIT_SIZE;
	private static int MAX_EVENT_STREAM;
	private static String CURRENT_RSPENGINE;
	public static String INPUT_PROPERTIES;
	private static boolean INCREMENTAL;
	private static String INPUT_FILE;
	private static String OUTPUT_DIR;
	private static String TIME_CONTROL;
	private static String outputPath;
	private static String dbPath;
	private static FlowRateProfiler profiler;

	public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {

		if (args.length >= 1) {
			INPUT_PROPERTIES = args[0];
			INPUT_FILE = args[1];
			OUTPUT_DIR = args[2];

			try {
				InputStream inputStream = new FileInputStream(INPUT_PROPERTIES);

				GetPropertyValues.prop.load(inputStream);

			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}

		EXPERIMENT_NUMBER = GetPropertyValues.getIntegerProperty("experiment_number");
		EXECUTION_NUMBER = GetPropertyValues.getIntegerProperty("execution_number");
		EXPERIMENT_DATE = GetPropertyValues.getDateProperty("experiment_date");

		CURRENT_RSPENGINE = GetPropertyValues.getProperty("current_engine");
		TIME_CONTROL = GetPropertyValues.getBooleanProperty("external_time_control_on") ? "External" : "Internal";
		ONTO_LANGUAGE = GetPropertyValues.getEnumProperty(OntoLanguage.class, "onto_lang");
		CEP_EVENT_TYPE = GetPropertyValues.getEnumProperty(JenaEventType.class, "cep_event_type");
		REASONING_MODE = GetPropertyValues.getEnumProperty(Reasoning.class, "reasoning_mode");
		INCREMENTAL = Reasoning.INCREMENTAL.equals(REASONING_MODE);

		MAX_EVENT_STREAM = GetPropertyValues.getIntegerProperty("max_event_stream");
		FLOW_RATE_PROFILE = GetPropertyValues.getEnumProperty(FlowRateProfile.class, "flow_rate_profile");
		INIT_SIZE = GetPropertyValues.getIntegerProperty("init_size");
		X = GetPropertyValues.getIntegerProperty("x_size");
		Y = GetPropertyValues.getIntegerProperty("y_size");

		outputPath = OUTPUT_DIR + DT.format(GetPropertyValues.getDateProperty("experiment_date")).toString() + "/exp" + EXPERIMENT_NUMBER + "/"
				+ ONTO_LANGUAGE.name() + "/";

		dbPath = OUTPUT_DIR + DT.format(EXPERIMENT_DATE).toString() + "/database/";

		log.info("Experiment [" + EXPERIMENT_NUMBER + "] on [" + INPUT_FILE + "] of [" + EXPERIMENT_DATE + "] Number of Events [" + MAX_EVENT_STREAM
				+ "]");

		eventBuilderCodeName = flowRateProfileSelection();

		log.info("Results and Performance Logs will be located in [" + outputPath + "]");
		log.info("Database will be located in [" + dbPath + "]");

		FileService.createOutputFolder(dbPath);
		FileService.createOutputFolder(outputPath);

		SQLListeService.openDatabase(dbPath, "experiments" + DT.format(EXPERIMENT_DATE).toString() + ".db");

		collectorSelection();
		jenaEngineSelection();
		run();

	}

	protected static String flowRateProfileSelection() {

		RDF2RDFStream rdf2rdfstream = new RDF2RDFStream();
		ParsingTemplate parser = new LUBMParser();
		String code = "_FRP_";
		String message = "Flow Rate Profile [" + FLOW_RATE_PROFILE + "] [" + INIT_SIZE + "] ";

		switch (FLOW_RATE_PROFILE) {
		case CONSTANT:
			code += "K" + INIT_SIZE;
			profiler = new ConstantFlowRateProfiler(parser, INIT_SIZE, EXPERIMENT_NUMBER, BaselinesUtils.beta);
			break;
		case STEP:
			message += " Heigh [" + Y + "] Width [" + X + "] ";
			profiler = new StepFlowRateProfiler(parser, X, Y, INIT_SIZE, EXPERIMENT_NUMBER, BaselinesUtils.beta);
			code += "S" + INIT_SIZE + "W" + X + "H" + Y;
			break;
		case STEP_FACTOR:
			message += " Factor [" + Y + "] Width [" + X + "] ";
			profiler = new StepFactorFlowRateProfiler(parser, X, Y, INIT_SIZE, EXPERIMENT_NUMBER, BaselinesUtils.beta);
			code += "S" + INIT_SIZE + "W" + X + "H" + Y;
			break;
		case CUSTOM_STEP:
			message += " Custom Step Init [" + INIT_SIZE + "] FINAL [" + Y + "] WIDTH [" + X + "] ";
			profiler = new CustomStepFlowRateProfiler(parser, X, Y, INIT_SIZE, EXPERIMENT_NUMBER, BaselinesUtils.beta);
			code += "S" + INIT_SIZE + "F" + Y + "W" + X;
			break;
		case RANDOM:
			message += " RND";
			profiler = new RandomFlowRateProfiler(parser, Y, INIT_SIZE, EXPERIMENT_NUMBER, BaselinesUtils.beta);
			code += "S" + INIT_SIZE + "H" + X + "W" + Y;
			break;
		default:
			message = "Not valid case [" + FLOW_RATE_PROFILE + "]";
		}

		log.info(message);
		if (profiler != null) {
			rdf2rdfstream.setProfiler(profiler);
			rdf2rdfstream.setEventLimit(MAX_EVENT_STREAM);
			rdf2rdfstream.setCollector(test_stand);
			streamer = rdf2rdfstream;
			return code;
		}
		throw new IllegalArgumentException("Not valid case [" + FLOW_RATE_PROFILE + "]");
	}

	protected static void jenaEngineSelection() {
		JenaEngine baseline;
		Encoder encoder;
		String esperQuery = INCREMENTAL ? BaselinesUtils.JENA_INPUT_QUERY_INCREMENTAL : BaselinesUtils.JENA_INPUT_QUERY_NAIVE;
		Reasoning r = INCREMENTAL ? Reasoning.INCREMENTAL : Reasoning.NAIVE;
		receiver = new Receiver();
		BaselineQuery query = queryDefinition(esperQuery);

		String message = "Engine Selection: [" + CEP_EVENT_TYPE + "] [" + ONTO_LANGUAGE.name().toUpperCase() + "] ["
				+ (INCREMENTAL ? "INCREMENTAL" : "NAIVE") + "]";
		log.info(message);

		switch (CEP_EVENT_TYPE) {
		case STATEMENT:
			baseline = new StatementBaseline(listener, receiver);
			encoder = new StatementEncoder();
			engine = baseline;
			break;
		case GRAPH:
			encoder = new GraphEncoder();
			baseline = new GraphBaseline(listener, receiver);

			break;
		default:
			throw new IllegalArgumentException("Not valid case [" + CEP_EVENT_TYPE + "]");
		}
		streamer.setEngine(baseline);
		profiler.setEncoder(encoder);
		baseline.setReasoning(r);
		baseline.setOntology_language(ONTO_LANGUAGE);
		baseline.registerQuery(query);

		engine = baseline;
	}

	private static BaselineQuery queryDefinition(String esperQuery) {
		BaselineQuery query = new BaselineQuery();
		query.setEsperQuery(esperQuery);
		query.setSparqlQuery("SELECT ?s ?p ?o  WHERE {?s ?p ?o}");
		query.setEsperStreams(new String[] { "lubmEvent" });
		query.setTbox(RDFSUtils.loadModel(FileUtils.UNIV_BENCH_RHODF_MODIFIED));
		return query;
	}

	protected static void collectorSelection() {

		String exp = "";
		boolean result_log_enabled = GetPropertyValues.getBooleanProperty("result_log_enabled");
		boolean memory_log_enabled = GetPropertyValues.getBooleanProperty("memory_log_enabled");
		boolean latency_log_enabled = GetPropertyValues.getBooleanProperty("latency_log_enabled");
		if (result_log_enabled)
			exp += "Result C&S ";
		if (memory_log_enabled)
			exp += "Memory ";
		if (latency_log_enabled)
			exp += "Latency ";

		result_collector = new ResultCollector(result_log_enabled, memory_log_enabled, latency_log_enabled);
		log.info("Execution of " + exp + "Experiment");
	}

	private static void run() {

		test_stand = new TestStand(streamer, engine, result_collector, receiver);
		Experiment experiment = createExperiment();
		test_stand.init(experiment);
		test_stand.run();

	}

	private static Experiment createExperiment() {
		Experiment experiment = new Experiment();
		experiment.setExperimentNumber(EXPERIMENT_NUMBER);
		experiment.setExecutionNumber(EXECUTION_NUMBER);
		experiment.setDate(DT.format(GetPropertyValues.getDateProperty("experiment_date")));

		experiment.setEngine(CURRENT_RSPENGINE);
		experiment.setMetadata(ONTO_LANGUAGE + "_" + REASONING_MODE + "_" + CEP_EVENT_TYPE + eventBuilderCodeName + "_EW_"
				+ GetPropertyValues.getProperty("rsp_events_in_window"));

		experiment.setTimecontrol(TIME_CONTROL);
		experiment.setAboxLog(GetPropertyValues.getBooleanProperty("abox_log_enabled"));
		experiment.setMemoryLog(GetPropertyValues.getBooleanProperty("memory_log_enabled"));
		experiment.setLatencyLog(GetPropertyValues.getBooleanProperty("latency_log_enabled"));
		experiment.setResultLog(GetPropertyValues.getBooleanProperty("result_log_enabled"));

		experiment.setInputSource(INPUT_FILE);
		experiment.setOutputPath(OUTPUT_DIR);
		experiment.setResponsivity(100L);

		return experiment;
	}
}
