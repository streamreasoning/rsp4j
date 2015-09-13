package it.polimi.heaven.run;

import it.polimi.heaven.GetPropertyValues;
import it.polimi.heaven.baselines.JenaRSPEngineFactory;
import it.polimi.heaven.baselines.RSPListener;
import it.polimi.heaven.core.enums.FlowRateProfile;
import it.polimi.heaven.core.enums.Reasoning;
import it.polimi.heaven.core.ts.TestStand;
import it.polimi.heaven.core.ts.events.Experiment;
import it.polimi.heaven.core.ts.events.Stimulus;
import it.polimi.heaven.core.ts.rspengine.RSPEngine;
import it.polimi.heaven.core.ts.streamer.flowrateprofiler.FlowRateProfiler;
import it.polimi.heaven.core.tsimpl.TestStandImpl;
import it.polimi.heaven.core.tsimpl.collector.TSResultCollector;
import it.polimi.heaven.core.tsimpl.streamer.RDF2RDFStream;
import it.polimi.heaven.core.tsimpl.streamer.TSStreamer;
import it.polimi.heaven.core.tsimpl.streamer.flowrateprofiler.ConstantFlowRateProfiler;
import it.polimi.heaven.core.tsimpl.streamer.flowrateprofiler.CustomStepFlowRateProfiler;
import it.polimi.heaven.core.tsimpl.streamer.flowrateprofiler.RandomFlowRateProfiler;
import it.polimi.heaven.core.tsimpl.streamer.flowrateprofiler.StepFactorFlowRateProfiler;
import it.polimi.heaven.core.tsimpl.streamer.flowrateprofiler.StepFlowRateProfiler;
import it.polimi.heaven.enums.JenaEventType;
import it.polimi.heaven.enums.OntoLanguage;
import it.polimi.services.FileService;
import it.polimi.services.SQLListeService;

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

	private static Date EXPERIMENT_DATE;
	private static final DateFormat DT = new SimpleDateFormat("yyyy_MM_dd");

	private static TestStand testStand;
	private static TSResultCollector streamingEventResultCollector;
	private static RSPListener listener;

	private static int EXECUTION_NUMBER;

	private static TSStreamer streamer;
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

		testStand = new TestStandImpl();

		eventBuilderCodeName = flowRateProfileSelection();

		log.info("Results and Performance Logs will be located in [" + outputPath + "]");
		log.info("Database will be located in [" + dbPath + "]");

		FileService.createOutputFolder(dbPath);
		FileService.createOutputFolder(outputPath);

		SQLListeService.openDatabase(dbPath, "experiments" + DT.format(EXPERIMENT_DATE).toString() + ".db");

		reasonerSelection();
		collectorSelection();
		jenaEngineSelection();
		run();

	}

	protected static String flowRateProfileSelection() {

		FlowRateProfiler<Stimulus> eb = null;

		String code = "_FRP_";
		String message = "Flow Rate Profile [" + FLOW_RATE_PROFILE + "] [" + INIT_SIZE + "] ";

		switch (FLOW_RATE_PROFILE) {
		case CONSTANT:
			code += "K" + INIT_SIZE;
			eb = new ConstantFlowRateProfiler(INIT_SIZE, EXPERIMENT_NUMBER);
			break;
		case STEP:
			message += " Heigh [" + Y + "] Width [" + X + "] ";
			eb = new StepFlowRateProfiler(X, Y, INIT_SIZE, EXPERIMENT_NUMBER);
			code += "S" + INIT_SIZE + "W" + X + "H" + Y;
			break;
		case STEP_FACTOR:
			message += " Factor [" + Y + "] Width [" + X + "] ";
			eb = new StepFactorFlowRateProfiler(X, Y, INIT_SIZE, EXPERIMENT_NUMBER);
			code += "S" + INIT_SIZE + "W" + X + "H" + Y;
			break;
		case CUSTOM_STEP:
			message += " Custom Step Init [" + INIT_SIZE + "] FINAL [" + Y + "] WIDTH [" + X + "] ";
			eb = new CustomStepFlowRateProfiler(X, Y, INIT_SIZE, EXPERIMENT_NUMBER);
			code += "S" + INIT_SIZE + "F" + Y + "W" + X;
			break;
		case RANDOM:
			message += " RND";
			eb = new RandomFlowRateProfiler(Y, INIT_SIZE, EXPERIMENT_NUMBER);
			code += "S" + INIT_SIZE + "H" + X + "W" + Y;
			break;
		default:
			message = "Not valid case [" + FLOW_RATE_PROFILE + "]";
		}

		log.info(message);
		if (eb != null) {
			streamer = new RDF2RDFStream(testStand, eb, MAX_EVENT_STREAM);
			return code;
		}
		throw new IllegalArgumentException("Not valid case [" + FLOW_RATE_PROFILE + "]");
	}

	protected static void jenaEngineSelection() {
		String message = "Engine Selection: [" + CEP_EVENT_TYPE + "] [" + ONTO_LANGUAGE.name().toUpperCase() + "] ["
				+ (INCREMENTAL ? "INCREMENTAL" : "NAIVE") + "]";
		log.info(message);
		switch (CEP_EVENT_TYPE) {
		case TEVENT:
			engine = INCREMENTAL ? JenaRSPEngineFactory.getIncrementalSerializedEngine(testStand, listener) : JenaRSPEngineFactory
					.getSerializedEngine(testStand, listener);
			return;
		case STMT:
			engine = INCREMENTAL ? JenaRSPEngineFactory.getIncrementalStmtEngine(testStand, listener) : JenaRSPEngineFactory.getStmtEngine(testStand,
					listener);
			return;
		case GRAPH:
			engine = INCREMENTAL ? JenaRSPEngineFactory.getIncrementalJenaEngineGraph(testStand, listener) : JenaRSPEngineFactory.getJenaEngineGraph(
					testStand, listener);
			return;
		default:
			message = "Not valid case [" + CEP_EVENT_TYPE + "]";
		}
		log.info(message);
		throw new IllegalArgumentException("Not valid case [" + CEP_EVENT_TYPE + "]");
	}

	protected static void reasonerSelection() {
		log.info("Reasoner Selection: [" + ONTO_LANGUAGE + "]");
		switch (ONTO_LANGUAGE) {
		case SMPL:
			listener = INCREMENTAL ? JenaReasoningListenerFactory.getIncrementalSMPLListener(testStand) : JenaReasoningListenerFactory
					.getSMPLListener(testStand);
			break;
		case RHODF:
			listener = INCREMENTAL ? JenaReasoningListenerFactory.getIncrementalRhoDfListener(testStand) : JenaReasoningListenerFactory
					.getRhoDfListener(testStand);
			break;
		case FULL:
			listener = INCREMENTAL ? JenaReasoningListenerFactory.getIncrementalFULLListener(testStand) : JenaReasoningListenerFactory
					.getFULLListener(testStand);
			break;
		default:
			log.error("Not valid case [" + ONTO_LANGUAGE + "]");
			throw new IllegalArgumentException("Not valid case [" + ONTO_LANGUAGE + "]");
		}
	}

	protected static void collectorSelection() {

		streamingEventResultCollector = new TSResultCollector(outputPath);
		String exp = "";
		if (GetPropertyValues.getBooleanProperty("result_log_enabled"))
			exp += "Result C&S ";
		if (GetPropertyValues.getBooleanProperty("memory_log_enabled"))
			exp += "Memory ";
		if (GetPropertyValues.getBooleanProperty("latency_log_enabled"))
			exp += "Latency ";

		log.info("Execution of " + exp + "Experiment");
	}

	private static void run() {

		testStand.build(streamer, engine, streamingEventResultCollector);
		testStand.init();

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

		testStand.run(experiment);

		testStand.close();
	}
}
