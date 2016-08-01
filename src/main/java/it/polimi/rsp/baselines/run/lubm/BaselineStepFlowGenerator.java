package it.polimi.rsp.baselines.run.lubm;

import it.polimi.heaven.lubm.FlowRateProfile;
import it.polimi.rsp.baselines.enums.JenaEventType;
import it.polimi.rsp.baselines.enums.OntoLanguage;
import it.polimi.heaven.core.enums.ExperimentType;
import it.polimi.heaven.core.enums.Reasoning;
import it.polimi.services.FileService;

import java.io.IOException;

public class BaselineStepFlowGenerator {

	private static int experimentNumber = 39;
	private static final String eol = System.getProperty("line.separator");
	private static final String date = "2015-01-19";

	private static boolean latency = true, abox = false, result = false;
	private static int maxEventStream = 6000, x_size = 100, y_size = 0;
	private static final String inputFile = "BIG_SHUFFLED.nt";

	private static final OntoLanguage[] langs = new OntoLanguage[] { OntoLanguage.SMPL, OntoLanguage.RHODF };
	private static final ExperimentType[] experimentTypes = new ExperimentType[] { ExperimentType.LATENCY, ExperimentType.MEMORY };
	private static final FlowRateProfile profile = FlowRateProfile.STEP;
	private static final Reasoning[] reasoning = new Reasoning[] { Reasoning.NAIVE, Reasoning.INCREMENTAL };
	private static final JenaEventType[] jenaEventTypes = new JenaEventType[] { JenaEventType.STATEMENT, JenaEventType.GRAPH };

	public static void main(String[] args) throws IOException {

		String outputFolder = "./properties/ESWC15/" + profile + "FR" + "/SHORT100/";

		String content = "";
		String name = "";

		for (int rsp_events_in_window = 1; rsp_events_in_window <= 100; rsp_events_in_window *= 10) {
			int init_size = 100 / rsp_events_in_window;
			for (Reasoning reasoning_mode : reasoning) {
				for (OntoLanguage lang : langs) {
					for (JenaEventType eventType : jenaEventTypes) {
						for (ExperimentType type : experimentTypes) {
							y_size = init_size;

							name += profile + "_" + reasoning_mode + "_" + type + "_" + lang.name() + "_" + eventType + "INIT" + init_size + "W"
									+ rsp_events_in_window;

							for (int executionNumber = 0; executionNumber < 5; executionNumber++) {
								content = experimentProperties(content, executionNumber, date, type.name());
								content = engineProperties(content, "JENA", eventType, reasoning_mode, lang);
								content = eventsProperties(content, init_size, profile, rsp_events_in_window, maxEventStream);
								content = timeProperties(content, true);

								writeOnFile(outputFolder + reasoning_mode + "/", content, name, lang, eventType, rsp_events_in_window, init_size,
										type, executionNumber);
							}
							System.out.println("Generate experiment [" + experimentNumber + "] name [" + name + "]");

							name = "";
							content = "";

						}
					}

				}
				experimentNumber++;
			}
		}

		System.out.println("Generated [" + (experimentNumber - 1) + "] Experiments");
	}

	private static void writeOnFile(String outputFolder, String content, String name, OntoLanguage lang, JenaEventType eventType,
			int rsp_events_in_window, int init_size, ExperimentType type, int executionNumber) {
		String currentOutputFolder = outputFolder + "/" + type + "/" + lang + "/" + eventType + "/" + "SLOT" + rsp_events_in_window + "STEPS"
				+ maxEventStream / x_size + "INIT" + init_size + "END" + rsp_events_in_window * init_size * (maxEventStream / x_size) + "/";
		FileService.createFolders(currentOutputFolder);
		FileService.createFolders(outputFolder + "/Experiments/");
		FileService.write(currentOutputFolder + "" + name + "EN" + executionNumber + ".properties", content);
		FileService.write(outputFolder + "/Experiments/" + name + "EN" + executionNumber + ".properties", content);
	}

	private static String timeProperties(String content, boolean externalTiming) {
		content += "#Timing";
		content += eol;
		content += "external_time_control_on = " + maxEventStream;
		content += eol;
		content += "#Timing End";
		content += eol;
		return content;
	}

	private static String eventsProperties(String content, int init_size, FlowRateProfile profile, int rsp_events_in_window, int maxEventStream) {
		content += "#Events";
		content += eol;
		content += "max_event_stream = " + maxEventStream;
		content += eol;
		content += "rsp_events_in_window = " + rsp_events_in_window;
		content += eol;
		content += "flow_rate_profile = " + profile;
		content += eol;
		content += "init_size = " + init_size;
		content += eol;
		content += "x_size = " + x_size;
		content += eol;
		content += "y_size = " + y_size;
		content += eol;
		content += "#Events End";
		content += eol;
		return content;
	}

	private static String engineProperties(String content, String engine, JenaEventType eventType, Reasoning reasoning, OntoLanguage lang) {
		content += "#Engine";
		content += eol;
		content += "current_engine = " + engine;
		content += eol;
		content += "cep_event_type = " + eventType;
		content += eol;
		content += "onto_lang = " + lang;
		content += eol;
		content += "reasoning_mode = " + reasoning;
		content += eol;
		content += "#Engine End";
		content += eol;
		return content;
	}

	private static String experimentProperties(String content, int executionNumber, String date, String type) {
		content += "#ESWC15 Baselines Constant Flow Rate Experiments";
		content += eol;
		content += "#Experiment";
		content += eol;
		content += "experiment_name = ESWC15 BASELINES CONSTANT FLOW RATE";
		content += eol;
		content += "experiment_number = " + experimentNumber;
		content += eol;
		content += "execution_number = " + executionNumber;
		content += eol;
		content += "experiment_date = " + date;
		content += eol;
		content += "experiment_type = " + type;
		content += eol;
		content += "result_log_enabled = " + result;
		content += eol;
		content += "latency_log_enabled = " + latency;
		content += eol;
		content += "MEMORY".equals(type) ? "memory_log_enabled = true " : "memory_log_enabled = false";
		content += eol;
		content += "save_abox_log = " + abox;
		content += eol;
		content += "input_file = " + inputFile;
		content += eol;
		content += "#Experiment End";
		content += eol;
		return content;
	}
}
