package it.polimi.heaven;

public class BaselinesUtils {

	public static final int beta = 100;

	private static int expectedRSPEvents = GetPropertyValues.getIntegerProperty("rsp_events_in_window");
	public static final int omega = ((beta * expectedRSPEvents) + 1); // TODO

	public static final String JENA_INPUT_QUERY_SNAPTSHOT = " select  * from TEvent.win:time(" + omega + " msec) output snapshot every " + beta
			+ " msec";

	public static final String JENA_INPUT_QUERY_INCREMENTAL = " select irstream * from TEvent.win:time(" + omega + "msec) output all every " + beta
			+ " msec";

	public static final String RHODF_RULE_SET = "./data/inference/rules/rdfs-rules-rhodf.rules";
	public static final String RHODF_RULE_SET_RUNTIME = "./data/inference/rules/rdfs-rules-rhodf-runtime.rules";

	public static final String UNIV_BENCH = "./data/inference/univ-bench-rdfs.rdf";

	public static final String UNIV_BENCH_RDFS = "./data/inference/univ-bench-rdfs-without-datatype-materialized.rdfs";
	public static final String UNIV_BENCH_RDFS_MODIFIED = "./data/inference/univ-bench-rdfs-without-datatype-materialized.rdfs";

	public static final String UNIV_BENCH_RHODF = "./data/inference/univ-bench-rdfs-materialized-rhodf.rdf";
	public static final String UNIV_BENCH_RHODF_MODIFIED = "./data/inference/univ-bench-rdfs-materialized-rhodf-modified.rdf";
}
