package it.polimi.baselines;

import it.polimi.baselines.timekeeping.external.incremental.listener.JenaIncFullListener;
import it.polimi.baselines.timekeeping.external.incremental.listener.JenaIncRhoDFListener;
import it.polimi.baselines.timekeeping.external.incremental.listener.JenaIncSMPLListener;
import it.polimi.baselines.timekeeping.external.snapshot.listener.JenaFullListener;
import it.polimi.baselines.timekeeping.external.snapshot.listener.JenaRhoDFListener;
import it.polimi.baselines.timekeeping.external.snapshot.listener.JenaSMPLListener;
import it.polimi.processing.EventProcessor;
import it.polimi.processing.events.CTEvent;
import it.polimi.processing.rspengine.abstracts.RSPListener;
import it.polimi.processing.teststand.core.TestStand;
import it.polimi.utils.FileUtils;
import it.polimi.utils.RDFSUtils;
import lombok.extern.log4j.Log4j;

@Log4j
public final class JenaReasoningListenerFactory {

	private static RSPListener listener;

	public static RSPListener getSMPLListener(EventProcessor<CTEvent> next) {
		log.info("Select SMPLListener ");
		return listener = new JenaSMPLListener(RDFSUtils.loadModel(FileUtils.UNIV_BENCH_RDFS_MODIFIED), next);
	}

	public static RSPListener getRhoDfListener(EventProcessor<CTEvent> next) {
		log.info("Select RHODFListener ");
		return listener = new JenaRhoDFListener(RDFSUtils.loadModel(FileUtils.UNIV_BENCH_RHODF_MODIFIED), FileUtils.RHODF_RULE_SET_RUNTIME, next);
	}

	public static RSPListener getFULLListener(TestStand next) {
		log.info("Select FullListener ");
		return listener = new JenaFullListener(RDFSUtils.loadModel(FileUtils.UNIV_BENCH_RHODF_MODIFIED), next);
	}

	public static RSPListener getIncrementalSMPLListener(EventProcessor<CTEvent> next) {
		log.info("Select SMPLListener INC");
		return listener = new JenaIncSMPLListener(RDFSUtils.loadModel(FileUtils.UNIV_BENCH_RDFS_MODIFIED), next);
	}

	public static RSPListener getIncrementalRhoDfListener(EventProcessor<CTEvent> next) {
		log.info("Select RHODFListener INC  ");
		return listener = new JenaIncRhoDFListener(RDFSUtils.loadModel(FileUtils.UNIV_BENCH_RHODF_MODIFIED), FileUtils.RHODF_RULE_SET_RUNTIME, next);
	}

	public static RSPListener getIncrementalFULLListener(TestStand next) {
		log.info("Select FullListener INC ");
		return listener = new JenaIncFullListener(RDFSUtils.loadModel(FileUtils.UNIV_BENCH_RHODF_MODIFIED), next);
	}

	public static RSPListener getCurrent() {
		return listener;
	}

}
