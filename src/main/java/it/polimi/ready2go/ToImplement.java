package it.polimi.ready2go;

import it.polimi.baselines.enums.OntoLanguage;
import it.polimi.heaven.core.enums.ExperimentType;
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
import it.polimi.heaven.services.system.ExecutionEnvirorment;
import it.polimi.services.FileService;
import it.polimi.utils.FileUtils;
import it.polimi.utils.GetPropertyValues;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ToImplement {

	// EVENT TYPES

	private static int EXPERIMENT_NUMBER = 1;

	private static RSPEngine engine;

	private static Date EXPERIMENT_DATE = new Date();;

	private static String file;
	private static String COMMENT = "insert comment";
	private static TestStand testStand;
	private static TSResultCollector streamingEventResultCollector;

	private static final DateFormat DT = new SimpleDateFormat("yyyy_MM_dd");

	private static ExperimentType EXPERIMENT_TYPE;

	private static int EXECUTION_NUMBER = 1;

	private static String whereOutput, whereWindow, outputFileName,
			windowFileName, experimentDescription;
	private static TSStreamer streamer;
	private static OntoLanguage ONTO_LANGUAGE;
	private static Reasoning REASONING_MODE;
	private static FlowRateProfile FLOW_RATE_PROFILE;

	private static String eventBuilderCodeName;
	private static int INIT_SIZE = 10;
	private static int MAX_EVENT_STREAM;
	private static String CURRENT_RSPENGINE;
	public static String INPUT_PROPERTIES;
	private static String wINDOWSIZE;
	public static final String READY2GO = "./data/properties/ready2go.properties";

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException, ParseException {

		if (args.length >= 1) {
			INPUT_PROPERTIES = args[0];
			log.info(Arrays.deepToString(args));
		}
		
		file = GetPropertyValues
				.getProperty(GetPropertyValues.DEFAULT_INPUT_FILE);

		MAX_EVENT_STREAM = GetPropertyValues
				.getIntegerProperty("max_event_stream");
		EXPERIMENT_NUMBER = GetPropertyValues
				.getIntegerProperty("experiment_number");

		log.info("Experiment [" + EXPERIMENT_NUMBER + "] on [" + file
				+ "] of [" + EXPERIMENT_DATE + "] Number of Events ["
				+ MAX_EVENT_STREAM + "]");

		testStand = new TestStandImpl();

		eventBuilderCodeName = flowRateProfileSelection();

		FileService.createOutputFolder(FileUtils.daypath + "/exp"
				+ EXPERIMENT_NUMBER + "/C-SPARQL Engine");

		wINDOWSIZE = GetPropertyValues.getProperty("rsp_events_in_window");

		String generalName = "EN" + EXPERIMENT_NUMBER + "_" + "EXE"
				+ EXECUTION_NUMBER + "_"  + "_"
				+ DT.format(EXPERIMENT_DATE) + "_" + file.split("\\.")[0] + "_"
				+ "_EW_"
				+ wINDOWSIZE;

		EXPERIMENT_TYPE = GetPropertyValues.getEnumProperty(
				ExperimentType.class, "experiment_type");

		outputFileName = EXPERIMENT_TYPE.ordinal() + "Result_" + generalName;
		windowFileName = EXPERIMENT_TYPE.ordinal() + "Window_" + generalName;

		whereOutput = "exp" + EXPERIMENT_NUMBER + "/" + outputFileName;

		if (GetPropertyValues.getBooleanProperty("save_abox_log")) {
			whereWindow = "exp" + EXPERIMENT_NUMBER + "/prove/"
					+ windowFileName;
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

		collectorSelection();
		engineSelection();
	
		run(file, COMMENT, EXPERIMENT_NUMBER, EXPERIMENT_DATE,
				experimentDescription);

	}

	protected static String flowRateProfileSelection() {
		FlowRateProfiler<Stimulus> eb = new ConstantFlowRateProfiler(INIT_SIZE,
				EXPERIMENT_NUMBER);
		streamer = new RDF2RDFStream(testStand, eb, MAX_EVENT_STREAM);
		String code = "_FRP_";
		code += "K" + INIT_SIZE;
		return code;
	}

	protected static void engineSelection() {
		log.info("Reasoner Selection: [C-SPARQL Engine]");
		CURRENT_RSPENGINE = "C-SPARQL-Engine";
		engine = new CSPARQLEngineFacade("C-SPARQL-Engine", testStand);
	}

	protected static void collectorSelection() {

		streamingEventResultCollector = new TSResultCollector("");
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
				FLOW_RATE_PROFILE + eventBuilderCodeName, CURRENT_RSPENGINE,
				FileUtils.INPUT_FILE_PATH + f, outputFileName, windowFileName,
				d.toString(), EXPERIMENT_TYPE.name(), "EXTERNAL", "");

		experimentNumber += testStand.run(experiment, comment);

		testStand.close();
	}

}
