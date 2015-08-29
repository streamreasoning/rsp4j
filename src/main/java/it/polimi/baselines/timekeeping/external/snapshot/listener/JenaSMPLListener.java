package it.polimi.baselines.timekeeping.external.snapshot.listener;

import it.polimi.baselines.timekeeping.external.snapshot.listener.abstracts.JenaNaiveListener;
import it.polimi.processing.EventProcessor;
import it.polimi.processing.events.CTEvent;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;

public final class JenaSMPLListener extends JenaNaiveListener {

	public JenaSMPLListener(Model tbox, EventProcessor<CTEvent> next) {
		super(tbox, next);
	}

	@Override
	protected Reasoner getReasoner() {
		Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
		reasoner.setParameter(ReasonerVocabulary.PROPsetRDFSLevel, ReasonerVocabulary.RDFS_SIMPLE);
		return reasoner;
	}

}