package it.polimi.rsp.baselines.rsp.sds;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.SafeIterator;
import it.polimi.heaven.rsp.rsp.querying.Query;
import it.polimi.rsp.baselines.enums.Entailment;
import it.polimi.rsp.baselines.enums.Maintenance;
import it.polimi.rsp.baselines.exceptions.UnregisteredStreamExeception;
import it.polimi.rsp.baselines.rsp.query.execution.ContinuousQueryExecution;
import it.polimi.rsp.baselines.rsp.sds.graphs.DefaultTVG;
import it.polimi.rsp.baselines.rsp.sds.graphs.NamedTVG;
import it.polimi.rsp.baselines.rsp.sds.graphs.TimeVaryingGraph;
import it.polimi.rsp.baselines.utils.BaselinesUtils;
import it.polimi.streaming.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.reasoner.InfGraph;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.riot.system.IRIResolver;
import org.apache.jena.sparql.core.DatasetImpl;
import org.apache.jena.vocabulary.ReasonerVocabulary;

import java.util.*;

/**
 * Created by riccardo on 01/07/2017.
 */
@Log4j
public class SDSImpl extends DatasetImpl implements Observer, SDS {


    private Set<ContinuousQueryExecution> observers;
    private final IRIResolver resolver;
    private final String resolvedDefaultStream;
    protected Model knowledge_base;
    protected Model TBoxStar;
    @Getter
    protected Reasoner reasoner;
    @Setter
    @Getter
    private Maintenance maintenanceType;
    @Setter
    @Getter
    private Entailment ontoLang;
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


    public SDSImpl(Model TBoxStar, Model kb, IRIResolver r, Maintenance maintenanceType, Entailment ontoLang, String id_base) {
        super(TBoxStar);

        this.observers = new HashSet<>();
        this.resolver = r;
        this.ontoLang = ontoLang;
        this.maintenanceType = maintenanceType;
        this.TBoxStar = TBoxStar;
        this.id_base = id_base;
        this.reasoner = getReasoner(ontoLang);
        this.reasoner.bindSchema(TBoxStar.getGraph());

        this.knowledge_base = graph2model(reasoner.bind(kb.getGraph()));

        this.defaultWindowStreamNames = new HashSet<>();
        this.namedWindowStreamNames = new HashMap<>();
        this.resolvedDefaultStream = resolver.resolveToStringSilent("default");
        this.statementNames = new HashSet<>();
        this.resolvedDefaultStreamSet = new HashSet<>();
        this.resolvedDefaultStreamSet.add(resolvedDefaultStream);
        this.global_tick = false;
    }


    @Override
    public synchronized void update(Observable o, Object _esp) {
        TimeVaryingGraph tvg = (TimeVaryingGraph) o;
        EPServiceProvider esp = (EPServiceProvider) _esp;

        EPStatement stmt = tvg.getTriggeringStatement();

        long cep_time = esp.getEPRuntime().getCurrentTime();
        long sys_time = System.currentTimeMillis();

        log.info("[" + Thread.currentThread() + "][" + sys_time + "] " +
                "From Statement [ " + tvg.getTriggeringStatement().getText() + "] at " + cep_time);


        setDefaultModel(getDefaultModel().union(knowledge_base));

        if (global_tick) {
            updateDataset(tvg, esp);
        }


        if (observers != null) {
            for (ContinuousQueryExecution qe : observers) {
                qe.materialize(tvg);
                qe.eval(this, stmt, cep_time);
            }

        }

        setDefaultModel(getDefaultModel().difference(knowledge_base));

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

    private Reasoner getReasoner(Entailment ontoLang) {
        Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();

        switch (ontoLang) {
            case OWL2DL:
                reasoner.setParameter(ReasonerVocabulary.PROPsetRDFSLevel, ReasonerVocabulary.RDFS_FULL);
                break;
            case RDFS:
                reasoner.setParameter(ReasonerVocabulary.PROPsetRDFSLevel, ReasonerVocabulary.RDFS_SIMPLE);
                break;
            default:
                reasoner = new GenericRuleReasoner(Rule.rulesFromURL(BaselinesUtils.RHODF_RULE_SET_RUNTIME));
        }

        return reasoner;

    }

    private String resolveWindowUri(String stream_uri) {

        if (defaultWindowStreamNames.contains(stream_uri)) {
            return resolvedDefaultStream;
        } else if (namedWindowStreamNames.containsKey(stream_uri)) {
            return namedWindowStreamNames.get(stream_uri);
        } else {
            throw new UnregisteredStreamExeception("GraphStream [" + stream_uri + "] is unregistered");
        }

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

    @Override
    public void addQueryExecutor(ContinuousQueryExecution o) {
        observers.add(o);

    }

    @Override
    public void removeQueryObserver(ContinuousQueryExecution o) {
        observers.remove(o);
    }


    public void addTimeVaryingGraph(DefaultTVG defTVG) {
        defTVG.addObserver(this);
    }

    public void addNamedTimeVaryingGraph(String window, NamedTVG namedTVG) {
        namedTVG.addObserver(this);
    }

    @Override
    public void bindTbox(Model tbox) {
        getReasoner().bindSchema(tbox);
    }

    @Override
    public Model rebind(Model def) {
        InfGraph bind = getReasoner().bind(def.getGraph());
        bind.rebind();
        return graph2model(bind);
    }

}

