package it.polimi.baselines.timekeeping.external.incremental.listener;

import it.polimi.baselines.timekeeping.external.snapshot.listener.abstracts.JenaIncrementalListener;
import it.polimi.processing.EventProcessor;
import it.polimi.processing.events.CTEvent;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.impl.InfModelImpl;
import com.hp.hpl.jena.reasoner.InfGraph;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;

public class JenaIncRhoDFListener extends JenaIncrementalListener {

	private final String aBoxRuleset;

	public JenaIncRhoDFListener(Model tbox, String aBoxRuleset, EventProcessor<CTEvent> collector) {
		super(tbox, collector);
		this.aBoxRuleset = aBoxRuleset;
		reasoner = new GenericRuleReasoner(Rule.rulesFromURL(this.aBoxRuleset));
		// TODO reasoner.setParameter(ReasonerVocabulary.PROPruleMode, "forwardRETE");
		InfGraph bind = reasoner.bindSchema(TBoxStar.getGraph()).bind(abox.getGraph());
		ABoxStar = new InfModelImpl(bind);
	}

}