package it.polimi.heaven.baselines.jena.abstracts;

import it.polimi.heaven.BaselinesUtils;
import it.polimi.heaven.GetPropertyValues;
import it.polimi.heaven.baselines.esper.RSPListener;
import it.polimi.heaven.baselines.jena.events.BaselineEvent;
import it.polimi.heaven.core.enums.Reasoning;
import it.polimi.heaven.core.ts.EventProcessor;
import it.polimi.heaven.core.ts.events.RSPEngineResult;
import it.polimi.heaven.core.ts.events.Stimulus;
import it.polimi.heaven.core.ts.events.TripleContainer;
import it.polimi.heaven.enums.OntoLanguage;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import com.espertech.esper.client.EventBean;
import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.impl.InfModelImpl;
import com.hp.hpl.jena.reasoner.InfGraph;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;

@Log4j
public class JenaListener implements RSPListener {

	protected Graph abox;
	protected Model TBoxStar;
	protected InfModel ABoxStar;

	protected Reasoner reasoner;
	protected final EventProcessor<Stimulus> next;

	private int eventNumber = 0;
	private final Set<TripleContainer> ABoxTriples;
	private Set<TripleContainer> statements;

	@Setter
	@Getter
	private Reasoning reasoningType;
	@Setter
	@Getter
	private OntoLanguage ontoLang;
	private Query q;

	public JenaListener(Model tbox, EventProcessor<Stimulus> next, String queryString, Reasoning reasoningType, OntoLanguage ontoLang) {
		this(next, queryString, reasoningType, ontoLang);
		this.TBoxStar = tbox;
		reasoner = getReasoner(ontoLang);
		reasoner.bindSchema(TBoxStar.getGraph());
		InfGraph graph = reasoner.bind(abox);
		ABoxStar = new InfModelImpl(graph);
	}

	public JenaListener(EventProcessor<Stimulus> next, String queryString, Reasoning reasoningType, OntoLanguage ontoLang) {
		this.next = next;
		this.q = QueryFactory.create(queryString);
		this.ontoLang = ontoLang;
		this.reasoningType = reasoningType;
		this.abox = ModelFactory.createMemModelMaker().createDefaultModel().getGraph();
		this.TBoxStar = ModelFactory.createMemModelMaker().createDefaultModel();
		ABoxTriples = new HashSet<TripleContainer>();
	}

	@Override
	public void update(EventBean[] newData, EventBean[] oldData) {

		IStreamUpdate(newData);
		DStreamUpdate(oldData);

		reasoner = getReasoner(ontoLang);
		reasoner.bindSchema(TBoxStar.getGraph());
		InfGraph graph = reasoner.bind(abox);
		ABoxStar = new InfModelImpl(graph);
		ABoxStar.rebind(); // forcing the reasoning to be executed

		if (q.isSelectType()) {
			QueryExecution exec = QueryExecutionFactory.create(q, ABoxStar);
			ResultSet selection = exec.execSelect();
			while (selection.hasNext()) {
				QuerySolution s = selection.next();
				System.out.println(s.toString());
			}
		} else if (q.isConstructType()) {
			QueryExecution exec = QueryExecutionFactory.create(q, ABoxStar);
			Iterator<Triple> iterator = exec.execConstructTriples();
			Set<TripleContainer> statements = new HashSet<TripleContainer>();
			while (iterator.hasNext()) {
				Triple t = iterator.next();
				statements.add(new TripleContainer(t.getSubject().toString(), t.getPredicate().toString(), t.getObject().toString()));
			}

			long outputTimestamp = System.currentTimeMillis();

			if (next != null) {
				log.debug("Send Event to the StoreCollector");
				eventNumber++;
				// the input and the output timestamps are the same because this
				// result is realted to the window not to the stimulus
				next.process(new RSPEngineResult("", "", statements, eventNumber, 0, outputTimestamp, false));
				if (GetPropertyValues.getBooleanProperty("abox_log_enabled")) {
					next.process(new RSPEngineResult("", "", ABoxTriples, eventNumber, 0, outputTimestamp, true));
				}
			}
		}

	}

	private Reasoner getReasoner(OntoLanguage ontoLang) {
		Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
		switch (ontoLang) {
		case FULL:
			reasoner.setParameter(ReasonerVocabulary.PROPsetRDFSLevel, ReasonerVocabulary.RDFS_FULL);
			break;
		case SMPL:
			reasoner.setParameter(ReasonerVocabulary.PROPsetRDFSLevel, ReasonerVocabulary.RDFS_SIMPLE);
			break;
		default:
			reasoner = new GenericRuleReasoner(Rule.rulesFromURL(BaselinesUtils.RHODF_RULE_SET_RUNTIME));
		}

		return reasoner;

	}

	private void IStreamUpdate(EventBean[] newData) {
		if (newData != null) {
			log.info("[" + newData.length + "] New Events of type [" + newData[0].getUnderlying().getClass().getSimpleName() + "]");
			for (EventBean e : newData) {
				log.info(e.getUnderlying().toString());
				BaselineEvent underlying = (BaselineEvent) e.getUnderlying();
				ABoxStar = ModelFactory.createInfModel((InfGraph) underlying.addTo(ABoxStar.getGraph()));
				ABoxTriples.addAll(underlying.serialize());
			}
		}
	}

	private void DStreamUpdate(EventBean[] oldData) {
		if (oldData != null && Reasoning.INCREMENTAL.equals(reasoningType)) {
			log.debug("[" + oldData.length + "] Old Events of type [" + oldData[0].getUnderlying().getClass().getSimpleName() + "]");
			for (EventBean e : oldData) {
				log.debug(e.getUnderlying().toString());
				BaselineEvent underlying = (BaselineEvent) e.getUnderlying();
				ABoxStar = ModelFactory.createInfModel((InfGraph) underlying.removeFrom(ABoxStar.getGraph()));
				ABoxTriples.removeAll(underlying.serialize());
			}
		}
	}
}