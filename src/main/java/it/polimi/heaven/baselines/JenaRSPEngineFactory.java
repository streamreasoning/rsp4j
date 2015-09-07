package it.polimi.heaven.baselines;

import it.polimi.heaven.baselines.timekeeping.external.incremental.JenaEngineGraphInc;
import it.polimi.heaven.baselines.timekeeping.external.incremental.JenaEngineSerializedInc;
import it.polimi.heaven.baselines.timekeeping.external.incremental.JenaEngineStmtInc;
import it.polimi.heaven.baselines.timekeeping.external.snapshot.JenaEngineGraph;
import it.polimi.heaven.baselines.timekeeping.external.snapshot.JenaEngineSerialized;
import it.polimi.heaven.baselines.timekeeping.external.snapshot.JenaEngineStmt;
import it.polimi.heaven.core.ts.EventProcessor;
import it.polimi.heaven.core.ts.TestStand;
import it.polimi.heaven.core.ts.events.Stimulus;
import it.polimi.heaven.core.ts.rspengine.RSPEngine;
import it.polimi.heaven.enums.OntoLanguage;
import it.polimi.heaven.run.JenaReasoningListenerFactory;
import it.polimi.utils.GetPropertyValues;

public final class JenaRSPEngineFactory {

	public static RSPEngine getSerializedEngine(EventProcessor<Stimulus> next, RSPListener listener) {
		return new JenaEngineSerialized(GetPropertyValues.getEnumProperty(OntoLanguage.class, "onto_lang").name().toLowerCase(), next,
				listener != null ? listener : JenaReasoningListenerFactory.getCurrent());
	}

	public static RSPEngine getStmtEngine(TestStand next, RSPListener listener) {
		return new JenaEngineStmt(GetPropertyValues.getEnumProperty(OntoLanguage.class, "onto_lang").name().toLowerCase(), next,
				listener != null ? listener : JenaReasoningListenerFactory.getCurrent());
	}

	public static RSPEngine getJenaEngineGraph(TestStand next, RSPListener listener) {
		return new JenaEngineGraph(GetPropertyValues.getEnumProperty(OntoLanguage.class, "onto_lang").name().toLowerCase(), next,
				listener != null ? listener : JenaReasoningListenerFactory.getCurrent());
	}

	public static RSPEngine getIncrementalSerializedEngine(EventProcessor<Stimulus> next, RSPListener listener) {
		return new JenaEngineSerializedInc(GetPropertyValues.getEnumProperty(OntoLanguage.class, "onto_lang").name().toLowerCase(), next,
				listener != null ? listener : JenaReasoningListenerFactory.getCurrent());
	}

	public static RSPEngine getIncrementalStmtEngine(TestStand next, RSPListener listener) {
		return new JenaEngineStmtInc(GetPropertyValues.getEnumProperty(OntoLanguage.class, "onto_lang").name().toLowerCase(), next,
				listener != null ? listener : JenaReasoningListenerFactory.getCurrent());
	}

	public static RSPEngine getIncrementalJenaEngineGraph(TestStand next, RSPListener listener) {
		return new JenaEngineGraphInc(GetPropertyValues.getEnumProperty(OntoLanguage.class, "onto_lang").name().toLowerCase(), next,
				listener != null ? listener : JenaReasoningListenerFactory.getCurrent());
	}
}
