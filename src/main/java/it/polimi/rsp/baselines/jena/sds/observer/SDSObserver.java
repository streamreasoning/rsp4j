package it.polimi.rsp.baselines.jena.sds.observer;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.SafeIterator;
import it.polimi.heaven.rsp.rsp.querying.Query;
import it.polimi.rsp.baselines.enums.OntoLanguage;
import it.polimi.rsp.baselines.enums.Reasoning;
import it.polimi.rsp.baselines.exceptions.UnregisteredStreamExeception;
import it.polimi.rsp.baselines.jena.events.response.BaselineResponse;
import it.polimi.rsp.baselines.jena.events.response.ConstructResponse;
import it.polimi.rsp.baselines.jena.events.response.SelectResponse;
import it.polimi.rsp.baselines.jena.sds.SDS;
import it.polimi.rsp.baselines.utils.BaselinesUtils;
import it.polimi.streaming.EventProcessor;
import it.polimi.streaming.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.GraphUtil;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.riot.system.IRIResolver;
import org.apache.jena.sparql.core.DatasetImpl;
import org.apache.jena.sparql.util.ModelUtils;
import org.apache.jena.vocabulary.ReasonerVocabulary;

import java.util.*;

/**
 * Created by riccardo on 01/07/2017.
 */
@Log4j
public class SDSObserver extends DatasetImpl implements Observer, SDS {


    protected final EventProcessor<Response> next;
    private final IRIResolver resolver;
    private final String resolvedDefaultStream;
    protected Model knowledge_base;
    protected Model TBoxStar;
    @Getter
    protected Reasoner reasoner;
    @Setter
    @Getter
    private Reasoning reasoningType;
    @Setter
    @Getter
    private OntoLanguage ontoLang;
    private Query bq;
    private org.apache.jena.query.Query q;
    private Response current_response;
    private int response_number = 0;
    private String id_base;
    private Set<String> updatedWindowViews;
    private Set<String> defaultWindowStreamNames;
    private Map<String, String> namedWindowStreamNames;
    private Set<String> statementNames;
    private Set<String> resolvedDefaultStreamSet;
    private boolean global_tick;


    public SDSObserver(Model TBoxStar, Model kb, EventProcessor<Response> next, Query bq, org.apache.jena.query.Query sparql_query, Reasoning reasoningType, OntoLanguage ontoLang, String id_base) {
        super(TBoxStar);
        this.knowledge_base = kb;
        this.next = next;
        this.bq = bq;
        this.q = sparql_query;
        this.resolver = q.getResolver();
        this.ontoLang = ontoLang;
        this.reasoningType = reasoningType;
        this.TBoxStar = TBoxStar;
        this.id_base = id_base;
        this.reasoner = getReasoner(ontoLang);
        this.reasoner.bindSchema(TBoxStar.getGraph());
        this.defaultWindowStreamNames = new HashSet<>();
        this.namedWindowStreamNames = new HashMap<>();
        this.resolvedDefaultStream = resolver.resolveToStringSilent("default");
        this.statementNames = new HashSet<>();
        this.resolvedDefaultStreamSet = new HashSet<>();
        this.resolvedDefaultStreamSet.add(resolvedDefaultStream);
        this.global_tick = true;
    }


    @Override
    public synchronized void update(Observable o, Object _esp) {
        TimeVaryingGraph tvg = (TimeVaryingGraph) o;

        EPServiceProvider esp = (EPServiceProvider) _esp;

        log.info("[" + Thread.currentThread() + "][" + System.currentTimeMillis() + "] " +
                "FROM STATEMENT: " + tvg.getTriggeringStatement().getText() + " AT "
                + esp.getEPRuntime().getCurrentTime());


        GraphUtil.addInto(getDefaultModel().getGraph(), knowledge_base.getGraph());

        current_response = exec(tvg, esp);

        GraphUtil.deleteFrom(getDefaultModel().getGraph(), knowledge_base.getGraph());


        if (next != null) {
            log.debug("Send Event to the Receiver");
            next.process(current_response);
        }

      /*  if (Reasoning.NAIVE.equals(reasoningType)) {
            for (String str : updatedWindowViews) {
                if (resolvedDefaultStream.equals(str)) {
                    //TODO is it better to remove only those affected?
                    getDefaultModel().removeAll();
                    getDefaultModel().add(this.TBoxStar);
                } else {
                    getNamedModel(str).removeAll();
                }
            }
        }*/
    }

    private Response exec(TimeVaryingGraph tvg, EPServiceProvider esp) {
        if (global_tick) {
            updateDataset(tvg, esp);
        }

        if (q.isSelectType()) {
            QueryExecution exec = QueryExecutionFactory.create(q, this);
            return new SelectResponse("http://streamreasoning.org/heaven/", bq, exec.execSelect(), esp.getEPRuntime().getCurrentTime());

        } else if (q.isConstructType()) {
            QueryExecution exec = QueryExecutionFactory.create(q, this);
            return new ConstructResponse("http://streamreasoning.org/heaven/", bq, exec.execConstruct(), esp.getEPRuntime().getCurrentTime());

        }

        return null;

    }

    private void updateDataset(TimeVaryingGraph tvg, EPServiceProvider esp) {
        EPStatement stmt = tvg.getTriggeringStatement();
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

    @Override
    public boolean addStatementName(String c) {
        return statementNames.add(c);
    }

    @Override
    public boolean addDefaultWindowStream(String uri) {
        defaultWindowStreamNames.add(uri);
        return defaultWindowStreamNames.contains(uri);
    }

    @Override
    public boolean addNamedWindowStream(String w, String s, Model model) {
        log.info("Added named window [" + w + "] on stream [" + s + " ]");
        final String uri = resolver.resolveToStringSilent(w);
        addNamedModel(uri, model);
        if (namedWindowStreamNames.containsKey(s)) {
            return false;
        } else {
            namedWindowStreamNames.put(s, uri);
            return true;
        }
    }

    @Override
    public Model bind(Graph g) {
        return graph2model(getReasoner().bind(g));
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
}
