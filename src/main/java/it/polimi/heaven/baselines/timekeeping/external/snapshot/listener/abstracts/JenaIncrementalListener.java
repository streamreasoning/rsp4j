package it.polimi.heaven.baselines.timekeeping.external.snapshot.listener.abstracts;

import it.polimi.heaven.baselines.RSPListener;
import it.polimi.heaven.baselines.events.jena.JenaEsperEvent;
import it.polimi.heaven.core.ts.EventProcessor;
import it.polimi.heaven.core.ts.events.RSPEngineResult;
import it.polimi.heaven.core.ts.events.Stimulus;
import it.polimi.heaven.core.ts.events.TripleContainer;
import it.polimi.heaven.services.system.ExecutionEnvirorment;

import java.util.HashSet;
import java.util.Set;

import lombok.extern.log4j.Log4j;

import com.espertech.esper.client.EventBean;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.InfGraph;
import com.hp.hpl.jena.reasoner.Reasoner;

@Log4j
public abstract class JenaIncrementalListener implements RSPListener {

	protected Model abox;
	protected final Model TBoxStar;
	protected InfModel ABoxStar;

	protected Reasoner reasoner;
	protected final EventProcessor<Stimulus> next;

	private int eventNumber = 0;
	private final Set<TripleContainer> ABoxTriples;
	private Set<TripleContainer> statements;

	public JenaIncrementalListener(Model tbox, EventProcessor<Stimulus> next) {
		this.TBoxStar = tbox;
		this.next = next;
		abox = ModelFactory.createMemModelMaker().createDefaultModel();
		ABoxTriples = new HashSet<TripleContainer>();
	}

	@Override
	public void update(EventBean[] newData, EventBean[] oldData) {

		log.debug("-- Event in Window [" + abox.size() + "] [" + ABoxStar.size() + "] [" + ABoxTriples.size() + "] --");

		if (oldData != null) {
			log.debug("[" + newData.length + "] Old Events of type [" + newData[0].getUnderlying().getClass().getSimpleName() + "]");
			for (EventBean e : oldData) {
				log.debug(e.getUnderlying().toString());
				JenaEsperEvent underlying = (JenaEsperEvent) e.getUnderlying();
				ABoxStar = ModelFactory.createInfModel((InfGraph) underlying.removeFrom(ABoxStar.getGraph()));
				ABoxTriples.removeAll(underlying.serialize());
			}
		}

		if (newData != null) {
			log.debug("[" + newData.length + "] New Events of type [" + newData[0].getUnderlying().getClass().getSimpleName() + "]");
			for (EventBean e : newData) {
				log.debug(e.getUnderlying().toString());
				JenaEsperEvent underlying = (JenaEsperEvent) e.getUnderlying();
				ABoxStar = ModelFactory.createInfModel((InfGraph) underlying.addTo(ABoxStar.getGraph()));
				ABoxTriples.addAll(underlying.serialize());
			}
		}

		log.debug("Window dimension in triples [" + ABoxStar.size() + "]");

		statements = new HashSet<TripleContainer>();
		ABoxStar.rebind(); // TODO verificare l'effort del rebind
		Model difference = ABoxStar.difference(TBoxStar);
		StmtIterator iterator = difference.listStatements();

		Triple t;
		TripleContainer statementStrings;
		while (iterator.hasNext()) {
			t = iterator.next().asTriple();
			statementStrings = new TripleContainer(t.getSubject().toString(), t.getPredicate().toString(), t.getObject().toString());
			statements.add(statementStrings);
		}

		long outputTimestamp = System.currentTimeMillis();

		if (next != null) {
			log.debug("Send Event to the StoreCollector");
			eventNumber++;
			next.process(new RSPEngineResult("", statements, eventNumber, 0, outputTimestamp, false));
			if (ExecutionEnvirorment.aboxLogEnabled) {
				next.process(new RSPEngineResult("", ABoxTriples, eventNumber, 0, outputTimestamp, true));
			}
		}

	}

}