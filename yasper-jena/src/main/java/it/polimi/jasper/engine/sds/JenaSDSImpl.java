package it.polimi.jasper.engine.sds;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.SafeIterator;
import it.polimi.jasper.engine.JenaRSPQLEngineImpl;
import it.polimi.jasper.engine.instantaneous.InstantaneousModel;
import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.yasper.core.SDS;
import it.polimi.yasper.core.enums.Entailment;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.exceptions.UnregisteredStreamExeception;
import it.polimi.yasper.core.query.ContinuousQuery;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.query.operators.s2r.WindowOperator;
import it.polimi.yasper.core.timevarying.DefaultTVG;
import it.polimi.yasper.core.timevarying.NamedTVG;
import it.polimi.yasper.core.timevarying.TimeVaryingGraph;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.riot.system.IRIResolver;
import org.apache.jena.sparql.core.DatasetImpl;

import java.util.*;

/**
 * Created by riccardo on 01/07/2017.
 */
@Log4j
public class JenaSDSImpl extends DatasetImpl implements Observer, JenaSDS {

    private final EPServiceProvider cep;
    private final JenaRSPQLEngineImpl rsp;
    private final IRIResolver resolver;
    private final String resolvedDefaultStream;
    protected Model knowledge_base;
    protected Model tbox;
    @Getter
    protected Reasoner reasoner;
    private Map<RSPQuery, ContinuousQueryExecution> executions;
    private Map<RSPQuery, List<TimeVaryingGraph>> windows;
    @Setter
    @Getter
    private Maintenance maintenanceType;
    @Setter
    @Getter
    private Entailment ontoLang;
    private org.apache.jena.query.Query q;
    private Set<String> defaultWindowStreamNames;
    private Map<String, String> namedWindowStreamNames;
    private Set<String> statementNames;
    private Set<String> resolvedDefaultStreamSet;
    private boolean global_tick;
    private List<NamedTVG> namedWindows;
    private DefaultTVG defWindow;


    public JenaSDSImpl(Model tbox_star, Model knowledge_base_star, IRIResolver r, Maintenance maintenanceType, String id_base, EPServiceProvider esp, JenaRSPQLEngineImpl rsp) {
        super(ModelFactory.createDefaultModel());
        this.executions = new HashMap<>();
        this.windows = new HashMap<RSPQuery, List<TimeVaryingGraph>>();
        this.cep = esp;
        this.resolver = r;
        this.namedWindows = new ArrayList<>();
        this.maintenanceType = maintenanceType;
        this.tbox = tbox_star;
        this.knowledge_base = knowledge_base_star;
        this.defaultWindowStreamNames = new HashSet<>();
        this.namedWindowStreamNames = new HashMap<>();
        this.resolvedDefaultStream = resolver.resolveToStringSilent("default");
        this.statementNames = new HashSet<>();
        this.resolvedDefaultStreamSet = new HashSet<>();
        this.resolvedDefaultStreamSet.add(resolvedDefaultStream);
        this.global_tick = false;
        this.rsp = rsp;
    }


    @Override
    public synchronized void update(Observable o, Object _ts) {
        TimeVaryingGraph tvg = (TimeVaryingGraph) o;
        long cep_time = tvg.getTimestamp();
        long sys_time = System.currentTimeMillis();

        log.info("[" + Thread.currentThread() + "][" + sys_time + "] " +
                "From Statement [ " + tvg.getTriggeringStatement().getText() + "] at " + cep_time);

        setDefaultModel(getDefaultModel().union(knowledge_base));

        if (global_tick) {
            updateDataset(tvg, cep);
        }

        consolidate(this, tvg, cep_time);

        setDefaultModel(getDefaultModel().difference(knowledge_base));
    }

    private void updateDataset(TimeVaryingGraph tvg, EPServiceProvider esp) {
        WindowOperator stmt = tvg.getTriggeringStatement();
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

    private String resolveWindowUri(String stream_uri) {

        if (defaultWindowStreamNames.contains(stream_uri)) {
            return resolvedDefaultStream;
        } else if (namedWindowStreamNames.containsKey(stream_uri)) {
            return namedWindowStreamNames.get(stream_uri);
        } else {
            throw new UnregisteredStreamExeception("GraphS2RTestStream [" + stream_uri + "] is unregistered");
        }
    }

    @Override
    public boolean addDefaultWindowStream(String statementName, String uri) {
        resolvedDefaultStreamSet.add(statementName);
        defaultWindowStreamNames.add(uri);
        return defaultWindowStreamNames.contains(uri);
    }

    @Override
    public void addDefaultWindow(InstantaneousModel m) {
        setDefaultModel(m);
    }

    @Override
    public boolean addNamedWindowStream(String w, String s, Model model) {
        //TODO remove cast
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

    public void addTimeVaryingGraph(DefaultTVG defTVG) {
        defTVG.addObserver(this);
        this.defWindow = defTVG;
    }

    public void addNamedTimeVaryingGraph(String statementName, String window_uri, String stream_uri, NamedTVG namedTVG) {
        statementNames.add(statementName);
        namedTVG.addObserver(this);
        namedWindows.add(namedTVG);
    }

    @Override
    public void addQueryExecutor(ContinuousQuery bq, ContinuousQueryExecution o) {
        executions.put((RSPQuery) bq, o);
    }

    private void consolidate(SDS sds, TimeVaryingGraph tvg, long cep_time) {
        if (executions != null && !executions.isEmpty()) {
            for (Map.Entry<RSPQuery, ContinuousQueryExecution> e : executions.entrySet()) {
                e.getValue().eval(sds, tvg, cep_time);
                //e.getValue().eval(sds, tvg, bq.getR2S, cep_time);
            }
        }
    }

}