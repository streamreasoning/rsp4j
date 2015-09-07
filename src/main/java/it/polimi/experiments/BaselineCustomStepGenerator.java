package it.polimi.experiments;

import it.polimi.heaven.core.enums.ExperimentType;
import it.polimi.heaven.core.enums.FlowRateProfile;
import it.polimi.heaven.core.enums.Reasoning;
import it.polimi.heaven.enums.JenaEventType;
import it.polimi.heaven.enums.OntoLanguage;
import it.polimi.services.FileService;

import java.io.IOException;

import lombok.extern.java.Log;

@Log
public class BaselineCustomStepGenerator {

	private static int experimentNumber = 20;
	private static final String eol = System.getProperty("line.separator");
	private static final String date = "2015-04-30";

	private static boolean latency = true, abox = false, result = false;
	private static int maxEventStream = 60000, x_size, y_size = 10000, init_size = 1000, rsp_events_in_window = 10;
	private static final String inputFile = "BIG_SHUFFLED.nt";

	private static final OntoLanguage[] langs = new OntoLanguage[] { OntoLanguage.RHODF };
	private static final ExperimentType[] experimentTypes = new ExperimentType[] { ExperimentType.LATENCY, ExperimentType.MEMORY };
	private static final FlowRateProfile profile = FlowRateProfile.CUSTOM_STEP;
	private static final Reasoning[] reasoning = new Reasoning[] { Reasoning.NAIVE, Reasoning.INCREMENTAL };
	private static final JenaEventType[] jenaEventTypes = new JenaEventType[] { JenaEventType.STMT, JenaEventType.GRAPH };
	private static final int repsNumber = 10;

	public static void main(String[] args) throws IOException {

		String outputFolder = "./properties/THESIS/" + profile + "FR" + "/GROWSTEP/";
		String content = "";
		String name = "";
		x_size = maxEventStream / 2;

		if (args != null && args.length != 0) {
			if ("help".equals(args[0])) {
				log.info("Argument List, all mandatory: \n MaxEvent \n Event in Window \n Initial Size \n Final Size \n Width");
				return;
			} else {
				maxEventStream = Integer.parseInt(args[0]);
				rsp_events_in_window = Integer.parseInt(args[1]);
				init_size = Integer.parseInt(args[2]);
				y_size = Integer.parseInt(args[3]);
				x_size = Integer.parseInt(args[4]);
			}
		}

		for (OntoLanguage lang : langs) {
			for (Reasoning reasoning_mode : reasoning) {
				for (JenaEventType eventType : jenaEventTypes) {
					for (ExperimentType type : experimentTypes) {

						name += profile + "_" + reasoning_mode + "_" + type + "_" + lang.name() + "_" + eventType + "_INIT" + init_size + "FINAL"
								+ y_size + "WIDTH" + x_size + "W" + rsp_events_in_window;

						for (int executionNumber = 0; executionNumber < repsNumber; executionNumber++) {
							content = experimentProperties(content, executionNumber, date, type.name());
							content = engineProperties(content, "JENA", eventType, reasoning_mode, lang);
							content = eventsProperties(content, init_size, profile, rsp_events_in_window, maxEventStream);
							content = timeProperties(content, true);

							writeOnFile(outputFolder + reasoning_mode + "/", content, "EN" + (experimentNumber) + "_" + name, lang, eventType,
									rsp_events_in_window, init_size, type, executionNumber);

							experimentNumber += type.equals(ExperimentType.LATENCY) && eventType.equals(JenaEventType.STMT)
									&& reasoning_mode.equals(Reasoning.NAIVE) ? executionNumber / 9 : 0;

							content = "";
						}

						System.out.println("Generate experiment [" + experimentNumber + "] name [" + name + "]");

						name = "";

					}
				}

			}
		}

		System.out.println("Generated [" + (experimentNumber - 1) + "] Experiments");
	}

	private static void writeOnFile(String outputFolder, String content, String name, OntoLanguage lang, JenaEventType eventType,
			int rsp_events_in_window, int init_size, ExperimentType type, int executionNumber) {
		String currentOutputFolder = outputFolder + "/" + type + "/" + lang + "/" + eventType + "/" + "SLOT" + rsp_events_in_window + "STEPS" + 1
				+ "INIT" + init_size + "FINAL" + y_size + "WIDTH" + x_size + "/";
		FileService.createFolders(currentOutputFolder);
		FileService.createFolders(outputFolder + "/Experiments/");
		FileService.write(currentOutputFolder + "" + name + "EN" + executionNumber + ".properties", content);
		FileService.write(outputFolder + "/Experiments/" + name + "EN" + executionNumber + ".properties", content);
	}

	private static String timeProperties(String content, boolean externalTiming) {
		content += "#Timing";
		content += eol;
		content += "external_time_control_on = " + externalTiming;
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
		content += "experiment_name = THESI BASELINES CUSTOM STEP FLOW RATE";
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
