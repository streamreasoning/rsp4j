package it.polimi.heaven.baselines.timekeeping.external.snapshot.listener.abstracts;

import it.polimi.heaven.GetPropertyValues;
import it.polimi.heaven.baselines.RSPListener;
import it.polimi.heaven.baselines.events.jena.JenaEsperEvent;
import it.polimi.heaven.core.ts.EventProcessor;
import it.polimi.heaven.core.ts.events.RSPEngineResult;
import it.polimi.heaven.core.ts.events.Stimulus;
import it.polimi.heaven.core.ts.events.TripleContainer;

import java.util.HashSet;
import java.util.Set;

import lombok.extern.log4j.Log4j;

import com.espertech.esper.client.EventBean;
import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.InfModelImpl;
import com.hp.hpl.jena.reasoner.InfGraph;
import com.hp.hpl.jena.reasoner.Reasoner;

@Log4j
public abstract class JenaNaiveListener implements RSPListener {

	private final Model TBoxStar;
	private Graph abox;
	private InfModel ABoxStar;
	private Reasoner reasoner;
	private final EventProcessor<Stimulus> next;
	private int eventNumber = 0;
	private Set<TripleContainer> ABoxTriples;

	public JenaNaiveListener(Model tbox, EventProcessor<Stimulus> next) {
		this.TBoxStar = tbox;
		this.next = next;
	}

	@Override
	public void update(EventBean[] newData, EventBean[] oldData) {

		if (oldData != null) {
			log.debug("[" + oldData.length + "] Old Events are still here");

		}

		if (newData != null) {

			log.debug("[" + newData.length + "] New Events of type [" + newData[0].getUnderlying().getClass().getSimpleName() + "]");

			abox = ModelFactory.createMemModelMaker().createDefaultModel().getGraph();
			ABoxTriples = new HashSet<TripleContainer>();

			for (EventBean e : newData) {
				log.debug(e.getUnderlying().toString());
				JenaEsperEvent underlying = (JenaEsperEvent) e.getUnderlying();
				abox = underlying.addTo(abox);
				ABoxTriples.addAll(underlying.serialize());
			}

			reasoner = getReasoner();
			InfGraph graph = reasoner.bindSchema(TBoxStar.getGraph()).bind(abox);
			ABoxStar = new InfModelImpl(graph);

			Set<TripleContainer> statements = new HashSet<TripleContainer>();
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
				if (GetPropertyValues.getBooleanProperty("abox_log_enabled")) {
					next.process(new RSPEngineResult("", ABoxTriples, eventNumber, 0, outputTimestamp, true));
				}
			}
		}
	}

	protected abstract Reasoner getReasoner();

}