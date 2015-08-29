package it.polimi.heaven.run;

import it.polimi.heaven.baselines.JenaRSPEngineFactory;
import it.polimi.heaven.core.enums.ExperimentType;
import it.polimi.heaven.core.enums.FlowRateProfile;
import it.polimi.heaven.core.enums.Reasoning;
import it.polimi.heaven.core.ts.TestStand;
import it.polimi.heaven.core.ts.events.Experiment;
import it.polimi.heaven.core.ts.events.Stimulus;
import it.polimi.heaven.core.ts.rspengine.RSPEngine;
import it.polimi.heaven.core.ts.rspengine.RSPListener;
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
import it.polimi.heaven.services.system.ExecutionEnvirorment;
import it.polimi.services.FileService;
import it.polimi.utils.FileUtils;
import it.polimi.utils.GetPropertyValues;

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

	private static TestStand testStand;
	private static TSResultCollector streamingEventResultCollector;
	private static RSPListener listener;

	private static final DateFormat DT = new SimpleDateFormat("yyyy_MM_dd");

	private static ExperimentType EXPERIMENT_TYPE;

	private static int EXECUTION_NUMBER;

	private static String whereOutput, whereWindow, outputFileName,
			windowFileName, experimentDescription;
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
	private static String wINDOWSIZE;
	private static boolean INCREMENTAL;
	private static String DEFAULT_INPUT_FILE;
	private static String DEFAULT_OUTPUT_DIR;

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException, ParseException {

		if (args.length >= 1) {
			INPUT_PROPERTIES = args[0];
			DEFAULT_INPUT_FILE = args[1];
			DEFAULT_OUTPUT_DIR = args[2];

			try {
				InputStream inputStream = GetPropertyValues.class
						.getClassLoader().getResourceAsStream(INPUT_PROPERTIES);

				if (inputStream != null) {
					GetPropertyValues.prop.load(inputStream);
				} else {
					log.error("property file '" + INPUT_PROPERTIES
							+ "' not found in the classpath");
				}

			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}


		EXPERIMENT_NUMBER = GetPropertyValues
				.getIntegerProperty("experiment_number");
		EXPERIMENT_DATE = GetPropertyValues.getDateProperty("experiment_date");

		EXECUTION_NUMBER = GetPropertyValues
				.getIntegerProperty("execution_number");

		CURRENT_RSPENGINE = GetPropertyValues.getProperty("current_engine");

		if (CURRENT_RSPENGINE.equals("JENA")) {
			ONTO_LANGUAGE = GetPropertyValues.getEnumProperty(
					OntoLanguage.class, "onto_lang");
			CEP_EVENT_TYPE = GetPropertyValues.getEnumProperty(
					JenaEventType.class, "cep_event_type");
			REASONING_MODE = GetPropertyValues.getEnumProperty(Reasoning.class,
					"reasoning_mode");
			INCREMENTAL = Reasoning.INCREMENTAL.equals(REASONING_MODE);
		}

		MAX_EVENT_STREAM = GetPropertyValues
				.getIntegerProperty("max_event_stream");
		FLOW_RATE_PROFILE = GetPropertyValues.getEnumProperty(
				FlowRateProfile.class, "flow_rate_profile");
		INIT_SIZE = GetPropertyValues.getIntegerProperty("init_size");
		X = GetPropertyValues.getIntegerProperty("x_size");
		Y = GetPropertyValues.getIntegerProperty("y_size");

		log.info("Experiment [" + EXPERIMENT_NUMBER + "] on ["
				+ DEFAULT_INPUT_FILE + "] of [" + EXPERIMENT_DATE
				+ "] Number of Events [" + MAX_EVENT_STREAM + "]");

		testStand = new TestStandImpl();

		eventBuilderCodeName = flowRateProfileSelection();

		FileService.createOutputFolder(FileUtils.daypath + "/exp"
				+ EXPERIMENT_NUMBER + "/" + ONTO_LANGUAGE.name());

		wINDOWSIZE = GetPropertyValues.getProperty("rsp_events_in_window");

		String generalName = "EN" + EXPERIMENT_NUMBER + "_" + "EXE"
				+ EXECUTION_NUMBER + "_" + DT.format(EXPERIMENT_DATE) + "_"
				+ DEFAULT_INPUT_FILE.split("\\.")[0] + "_" + ONTO_LANGUAGE
				+ "_" + REASONING_MODE + "_" + CEP_EVENT_TYPE
				+ eventBuilderCodeName + "_EW_" + wINDOWSIZE;

		EXPERIMENT_TYPE = GetPropertyValues.getEnumProperty(
				ExperimentType.class, "experiment_type");
		outputFileName = EXPERIMENT_TYPE.ordinal() + "Result_" + generalName;
		windowFileName = EXPERIMENT_TYPE.ordinal() + "Window_" + generalName;

		whereOutput = "exp" + EXPERIMENT_NUMBER + "/" + ONTO_LANGUAGE.name()
				+ "/" + outputFileName;

		if (GetPropertyValues.getBooleanProperty("save_abox_log")) {
			whereWindow = "exp" + EXPERIMENT_NUMBER + "/"
					+ ONTO_LANGUAGE.name() + "/" + windowFileName;
			log.info("Window file name will be: ["
					+ whereWindow.replace("0Result", "RESLOG")
							.replace("0Window", "WINLOG")
							.replace("1Result", "LATLOG")
							.replace("1Window", "WINLATLOG")
							.replace("2Result", "MEMLOG")
							.replace("2Window", "WINMEMLOG") + "]");

		}

		log.info("Output file name will be: ["
				+ whereOutput.replace("0Result", "RESLOG")
						.replace("0Window", "WINLOG")
						.replace("1Result", "LATLOG")
						.replace("1Window", "WINLATLOG")
						.replace("2Result", "MEMLOG")
						.replace("2Window", "WINMEMLOG") + "]");

		reasonerSelection();
		collectorSelection();
		jenaEngineSelection();

		run(DEFAULT_INPUT_FILE, "", EXPERIMENT_NUMBER, EXPERIMENT_DATE,
				experimentDescription);

	}

	protected static String flowRateProfileSelection() {
		FlowRateProfiler<Stimulus> eb = null;

		String code = "_FRP_";
		String message = "Flow Rate Profile [" + FLOW_RATE_PROFILE + "] ["
				+ INIT_SIZE + "] ";

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
			eb = new StepFactorFlowRateProfiler(X, Y, INIT_SIZE,
					EXPERIMENT_NUMBER);
			code += "S" + INIT_SIZE + "W" + X + "H" + Y;
			break;
		case CUSTOM_STEP:
			message += " Custom Step Init [" + INIT_SIZE + "] FINAL [" + Y
					+ "] WIDTH [" + X + "] ";
			eb = new CustomStepFlowRateProfiler(X, Y, INIT_SIZE,
					EXPERIMENT_NUMBER);
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
		throw new IllegalArgumentException("Not valid case ["
				+ FLOW_RATE_PROFILE + "]");
	}

	protected static void jenaEngineSelection() {
		String message = "Engine Selection: [" + CEP_EVENT_TYPE + "] ["
				+ ONTO_LANGUAGE.name().toUpperCase() + "] ["
				+ (INCREMENTAL ? "INCREMENTAL" : "NAIVE") + "]";
		log.info(message);
		switch (CEP_EVENT_TYPE) {
		case TEVENT:
			engine = INCREMENTAL ? JenaRSPEngineFactory
					.getIncrementalSerializedEngine(testStand, listener)
					: JenaRSPEngineFactory.getSerializedEngine(testStand,
							listener);
			return;
		case STMT:
			engine = INCREMENTAL ? JenaRSPEngineFactory
					.getIncrementalStmtEngine(testStand, listener)
					: JenaRSPEngineFactory.getStmtEngine(testStand, listener);
			return;
		case GRAPH:
			engine = INCREMENTAL ? JenaRSPEngineFactory
					.getIncrementalJenaEngineGraph(testStand, listener)
					: JenaRSPEngineFactory.getJenaEngineGraph(testStand,
							listener);
			return;
		default:
			message = "Not valid case [" + CEP_EVENT_TYPE + "]";
		}
		log.info(message);
		throw new IllegalArgumentException("Not valid case [" + CEP_EVENT_TYPE
				+ "]");
	}

	protected static void reasonerSelection() {
		log.info("Reasoner Selection: [" + ONTO_LANGUAGE + "]");
		switch (ONTO_LANGUAGE) {
		case SMPL:
			listener = INCREMENTAL ? JenaReasoningListenerFactory
					.getIncrementalSMPLListener(testStand)
					: JenaReasoningListenerFactory.getSMPLListener(testStand);
			break;
		case RHODF:
			listener = INCREMENTAL ? JenaReasoningListenerFactory
					.getIncrementalRhoDfListener(testStand)
					: JenaReasoningListenerFactory.getRhoDfListener(testStand);
			break;
		case FULL:
			listener = INCREMENTAL ? JenaReasoningListenerFactory
					.getIncrementalFULLListener(testStand)
					: JenaReasoningListenerFactory.getFULLListener(testStand);
			break;
		default:
			log.error("Not valid case [" + ONTO_LANGUAGE + "]");
			throw new IllegalArgumentException("Not valid case ["
					+ ONTO_LANGUAGE + "]");
		}
	}

	protected static void collectorSelection() {

		streamingEventResultCollector = new TSResultCollector(
				ONTO_LANGUAGE.name() + "/");
		String exp = "";
		if (ExecutionEnvirorment.finalresultTrigLogEnabled)
			exp += "Result C&S ";
		if (ExecutionEnvirorment.memoryLogEnabled)
			exp += "Memory ";
		if (ExecutionEnvirorment.latencyLogEnabled)
			exp += "Latency ";

		log.info("Execution of " + exp + "Experiment");
	}

	private static void run(String f, String comment, int experimentNumber,
			Date d, String experimentDescription) {

		testStand.build(streamer, engine, streamingEventResultCollector);
		testStand.init();

		Experiment experiment = new Experiment(experimentNumber,
				FLOW_RATE_PROFILE.name() + eventBuilderCodeName,
				CURRENT_RSPENGINE + "_" + REASONING_MODE.name() + "_"
						+ ONTO_LANGUAGE.name(), FileUtils.INPUT_FILE_PATH + f,
				outputFileName, windowFileName, d.toString(),
				EXPERIMENT_TYPE.name(), "EXTERNAL", "");

		experimentNumber += testStand.run(experiment, comment);

		testStand.close();
	}

}
