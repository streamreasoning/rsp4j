package it.polimi.rsp.baselines.jena;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.SafeIterator;
import com.espertech.esper.event.map.MapEventBean;
import it.polimi.heaven.rsp.rsp.querying.Query;
import it.polimi.rsp.baselines.enums.OntoLanguage;
import it.polimi.rsp.baselines.enums.Reasoning;
import it.polimi.rsp.baselines.esper.RSPListener;
import it.polimi.rsp.baselines.exceptions.UnregisteredStreamExeception;
import it.polimi.rsp.baselines.jena.events.response.BaselineResponse;
import it.polimi.rsp.baselines.jena.events.response.ConstructResponse;
import it.polimi.rsp.baselines.jena.events.response.SelectResponse;
import it.polimi.rsp.baselines.jena.events.stimuli.BaselineStimulus;
import it.polimi.rsp.baselines.utils.BaselinesUtils;
import it.polimi.sr.rsp.utils.EncodingUtils;
import it.polimi.streaming.EventProcessor;
import it.polimi.streaming.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Graph;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.impl.InfModelImpl;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.riot.system.IRIResolver;
import org.apache.jena.vocabulary.ReasonerVocabulary;

import java.util.*;

@Log4j
public class JenaListener implements RSPListener {

	private final Dataset dataset;
	private final IRIResolver resolver;
	private final String resolvedDefaultStream;
	protected Graph abox;
	protected Model TBoxStar;
	protected InfModel currentAbox;

	protected Reasoner reasoner;
	protected final EventProcessor<Response> next;

	@Setter
	@Getter
	private Reasoning reasoningType;
	@Setter
	@Getter
	private OntoLanguage ontoLang;
	private Query bq;
	private org.apache.jena.query.Query q;
	private BaselineResponse current_response;
	private int response_number = 0;
	private String id_base;
	private Set<String> updatedWindowViews;
	private Set<String> defaultWindowStreamNames;
	private Map<String, String> namedWindowStreamNames;
	private Set<String> statementNames;
	private Set<String> resolvedDefaultStreamSet;

	public JenaListener(Dataset dataset, EventProcessor<Response> next, Query bq,
			org.apache.jena.query.Query sparql_query, Reasoning reasoningType, OntoLanguage ontoLang, String id_base) {
		this.next = next;
		this.bq = bq;
		this.q = sparql_query;
		this.resolver = q.getResolver();
		this.ontoLang = ontoLang;
		this.reasoningType = reasoningType;
		this.abox = ModelFactory.createMemModelMaker().createDefaultModel().getGraph();
		this.TBoxStar = ModelFactory.createMemModelMaker().createDefaultModel();
		this.id_base = id_base;
		this.reasoner = getReasoner(ontoLang);
		this.reasoner.bindSchema(TBoxStar.getGraph());
		this.dataset = dataset;
		this.dataset.setDefaultModel(new InfModelImpl(reasoner.bind(TBoxStar.getGraph())));
		this.reasoner = getReasoner(ontoLang);
		this.defaultWindowStreamNames = new HashSet<>();
		this.namedWindowStreamNames = new HashMap<String, String>();
		this.resolvedDefaultStream = resolver.resolveToStringSilent("default");
		this.statementNames = new HashSet<String>();
		this.resolvedDefaultStreamSet = new HashSet<String>();
		this.resolvedDefaultStreamSet.add(resolvedDefaultStream);
	}

	@Override
	public void update(EventBean[] newData, EventBean[] oldData, EPStatement stmt, EPServiceProvider esp) {
		log.info("[" + System.currentTimeMillis() + "] FROM STATEMENT: " + stmt.getText() + " AT "
				+ esp.getEPRuntime().getCurrentTime());

		List<EventBean> events = new ArrayList<EventBean>();

		for (String stmtName : statementNames) {
			if (!stmtName.equals(stmt.getName())) {
				EPStatement statement1 = esp.getEPAdministrator().getStatement(stmtName);
				log.debug("[" + System.currentTimeMillis() + "] Polling STATEMENT: " + statement1.getText() + " "
						+ statement1.getTimeLastStateChange());
				SafeIterator<EventBean> it = statement1.safeIterator();
				while (it.hasNext()) {
					EventBean next = it.next();
					log.info(next.getUnderlying());
					events.add(next);
				}

				it.close();
			}
		}

		this.updatedWindowViews = new HashSet<>();
		response_number++;

		if (newData != null)
			events.addAll(Arrays.asList(newData));

		IStreamUpdate(events);
		DStreamUpdate(oldData);

		if (q.isSelectType()) {
			QueryExecution exec = QueryExecutionFactory.create(q, dataset);
			current_response = new SelectResponse("http://streamreasoning.org/heaven/", bq, exec.execSelect());

		} else if (q.isConstructType()) {
			QueryExecution exec = QueryExecutionFactory.create(q, dataset);
			current_response = new ConstructResponse("http://streamreasoning.org/heaven/", bq, exec.execConstruct());

		}

		if (next != null) {
			log.debug("Send Event to the Receiver");
			next.process(current_response);
		}

		if (Reasoning.NAIVE.equals(reasoningType)) {
			for (String str : updatedWindowViews) {
				if (resolvedDefaultStream.equals(str)) {
					dataset.getDefaultModel().removeAll();
				} else {
					dataset.getNamedModel(str).removeAll();
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

	private void handleSingleIStream(BaselineStimulus underlying) {
		log.debug("Handling single Istream [" + underlying + "]");
		String window_uris = resolveWindowUri(EncodingUtils.encode(underlying.getStream_uri()));
		updatedWindowViews.add(window_uris);
		if (resolvedDefaultStream.equals(window_uris)) {
			Graph updated = underlying.addTo(dataset.getDefaultModel().getGraph());
			reasoner.bind(updated).rebind();
		} else {
			Model namedModel = dataset.getNamedModel(window_uris);
			Graph updated = underlying.addTo(namedModel.getGraph());
			reasoner.bind(updated).rebind();
		}
	}

	private String resolveWindowUri(String stream_uri) {

		if (defaultWindowStreamNames.contains(stream_uri)) {
			return resolvedDefaultStream;
		} else if (namedWindowStreamNames.containsKey(stream_uri)) {
			return namedWindowStreamNames.get(stream_uri);
		} else {
			throw new UnregisteredStreamExeception("StreamThread [" + stream_uri + "] is unregistered");
		}

	}

	private void IStreamUpdate(List<EventBean> newData) {
		if (newData != null && !newData.isEmpty()) {
			log.info("[" + newData.size() + "] New Events of type ["
					+ newData.get(0).getUnderlying().getClass().getSimpleName() + "]");
			for (EventBean e : newData) {
				if (e instanceof MapEventBean) {
					MapEventBean meb = (MapEventBean) e;
					if (meb.getProperties() instanceof BaselineStimulus) {
						handleSingleIStream((BaselineStimulus) e.getUnderlying());
					} else {

						for (int i = 0; i < meb.getProperties().size(); i++) {
							BaselineStimulus underlying = (BaselineStimulus) meb.get("stream_" + i);
							handleSingleIStream(underlying);
						}
					}
				}
			}
		}
	}

	private void handleSingleDStream(BaselineStimulus underlying) {
		log.debug("Handling single Istream [" + underlying + "]");
		String window_uris = resolveWindowUri(underlying.getStream_uri());
		updatedWindowViews.add(window_uris);
		if (resolvedDefaultStream.equals(window_uris)) {
			Graph updated = underlying.removeFrom(dataset.getDefaultModel().getGraph());
			reasoner.bind(updated).rebind();
		} else {
			Model namedModel = dataset.getNamedModel(window_uris);
			Graph updated = underlying.removeFrom(namedModel.getGraph());
			reasoner.bind(updated).rebind();
		}
	}

	private void DStreamUpdate(EventBean[] oldData) {
		if (oldData != null && Reasoning.INCREMENTAL.equals(reasoningType)) { // TODO
																				// not
																				// sure
																				// that
																				// it
																				// has
																				// to
																				// be
																				// only
																				// for
																				// incremental
			log.debug("[" + oldData.length + "] Old Events of type ["
					+ oldData[0].getUnderlying().getClass().getSimpleName() + "]");
			for (EventBean e : oldData) {
				if (e instanceof MapEventBean) {
					MapEventBean meb = (MapEventBean) e;
					if (meb.getProperties() instanceof BaselineStimulus) {
						handleSingleDStream((BaselineStimulus) e.getUnderlying());
					} else {
						for (int i = 0; i < meb.getProperties().size(); i++) {
							BaselineStimulus underlying = (BaselineStimulus) meb.get("stream_" + i);
							handleSingleDStream(underlying);
						}
					}
				}
			}
		}
	}

	public boolean addStatementName(String c) {
		return statementNames.add(c);
	}

	public boolean addDefaultWindowStream(String uri) {
		defaultWindowStreamNames.add(uri);
		return defaultWindowStreamNames.contains(uri);
	}

	public boolean addNamedWindowStream(String w, String s) {
		log.info("Added named window [" + w + "] on stream [" + s + " ]");
		final String uri = resolver.resolveToStringSilent(w);
		dataset.addNamedModel(uri, new InfModelImpl(reasoner.bind(TBoxStar.getGraph())));
		if (namedWindowStreamNames.containsKey(s)) {
			return false;
		} else {
			namedWindowStreamNames.put(s, uri);
			return true;
		}
	}

}