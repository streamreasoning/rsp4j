package it.polimi.heaven.baselines.jena.timekeeping.external.incremental.listener;

import it.polimi.heaven.baselines.jena.timekeeping.external.snapshot.listener.abstracts.JenaIncrementalListener;
import it.polimi.heaven.core.ts.EventProcessor;
import it.polimi.heaven.core.ts.events.Stimulus;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.impl.InfModelImpl;
import com.hp.hpl.jena.reasoner.InfGraph;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;

public class JenaIncSMPLListener extends JenaIncrementalListener {

	public JenaIncSMPLListener(Model tbox, EventProcessor<Stimulus> next) {
		super(tbox, next);

		reasoner = ReasonerRegistry.getRDFSReasoner();
		reasoner.setParameter(ReasonerVocabulary.PROPsetRDFSLevel, ReasonerVocabulary.RDFS_SIMPLE);

		InfGraph bind = reasoner.bindSchema(TBoxStar.getGraph()).bind(abox.getGraph());
		ABoxStar = new InfModelImpl(bind);
	}

}