package it.polimi.baselines.timekeeping.external.incremental.listener;

import it.polimi.baselines.timekeeping.external.snapshot.listener.abstracts.JenaIncrementalListener;
import it.polimi.processing.EventProcessor;
import it.polimi.processing.events.CTEvent;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.impl.InfModelImpl;
import com.hp.hpl.jena.reasoner.InfGraph;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;

public class JenaIncFullListener extends JenaIncrementalListener {

	public JenaIncFullListener(Model tbox, EventProcessor<CTEvent> collector) {
		super(tbox, collector);

		reasoner = ReasonerRegistry.getRDFSReasoner();
		reasoner.setParameter(ReasonerVocabulary.PROPsetRDFSLevel, ReasonerVocabulary.RDFS_FULL);

		InfGraph bind = reasoner.bindSchema(TBoxStar.getGraph()).bind(abox.getGraph());
		ABoxStar = new InfModelImpl(bind);
	}

}