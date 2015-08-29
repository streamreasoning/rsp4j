package it.polimi.heaven.baselines.timekeeping.external.snapshot.listener;

import it.polimi.heaven.baselines.timekeeping.external.snapshot.listener.abstracts.JenaNaiveListener;
import it.polimi.heaven.core.ts.EventProcessor;
import it.polimi.heaven.core.ts.events.Stimulus;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;

public final class JenaFullListener extends JenaNaiveListener {

	public JenaFullListener(Model tbox, EventProcessor<Stimulus> next) {
		super(tbox, next);
	}

	@Override
	protected Reasoner getReasoner() {
		Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
		reasoner.setParameter(ReasonerVocabulary.PROPsetRDFSLevel, ReasonerVocabulary.RDFS_FULL);
		return reasoner;
	}

}