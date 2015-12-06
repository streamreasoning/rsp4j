package it.polimi.heaven.baselines.utils;


public final class FileUtils {

	public static String daypath;

	public static final String MODEL_FILE_PATH = "";
	public static final String INPUT_FILE_PATH = "./data/input/";
	public static final String OUTPUT_FILE_PATH = daypath;
	public static final String PREPROCESSING_FILE_PATH = "./data/preprocessing/";
	public static final String PREPROCESSING_EXCLUDED_FILE_PATH = PREPROCESSING_FILE_PATH
			+ "excluded/";
	public static final String PREPROCESSING_DATATYPE_FILE_PATH = PREPROCESSING_FILE_PATH
			+ "datatype/";
	public static final String CSV_OUTPUT_FILE_PATH = OUTPUT_FILE_PATH + "/";

	public static final String CSV = ".csv";
	public static final String SQLLITE_OUTPUT_FILE_PATH = OUTPUT_FILE_PATH
			+ "database/";
	public static final String SQLLITE_FILE_EXTENSION = ".db";
	public static final String TRIG_OUTPUT_FILE_PATH = OUTPUT_FILE_PATH + "/";
	public static final String TRIG_FILE_EXTENSION = ".trig";

	public static final String RHODF_RULE_SET = "./data/inference/rules/rdfs-rules-rhodf.rules";
	public static final String RHODF_RULE_SET_RUNTIME = "./data/inference/rules/rdfs-rules-rhodf-runtime.rules";

	public static final String UNIV_BENCH = "./data/inference/univ-bench-rdfs.rdf";

	public static final String UNIV_BENCH_RDFS = "./data/inference/univ-bench-rdfs-without-datatype-materialized.rdfs";
	public static final String UNIV_BENCH_RDFS_MODIFIED = "./data/inference/univ-bench-rdfs-without-datatype-materialized.rdfs";

	public static final String UNIV_BENCH_RHODF = "./data/inference/univ-bench-rdfs-materialized-rhodf.rdf";
	public static final String UNIV_BENCH_RHODF_MODIFIED = "./data/inference/univ-bench-rdfs-materialized-rhodf-modified.rdf";
	public static final String DATABASEPATH = daypath + "database/";

	public static String getTrigPath(String w) {
		return FileUtils.TRIG_OUTPUT_FILE_PATH + w
				+ FileUtils.TRIG_FILE_EXTENSION;
	}

	public static String getCSVpath(String w) {
		String csvLog = w.replace("0Result", "RESLOG")
				.replace("0Window", "WINLOG").replace("1Result", "LATLOG")
				.replace("1Window", "WINLATLOG").replace("2Result", "MEMLOG")
				.replace("2Window", "WINMEMLOG");
		return FileUtils.CSV_OUTPUT_FILE_PATH + csvLog + FileUtils.CSV;
	}

}
