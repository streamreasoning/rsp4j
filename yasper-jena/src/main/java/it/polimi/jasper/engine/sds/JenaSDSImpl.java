package it.polimi.jasper.engine.sds;

import com.espertech.esper.client.EPServiceProvider;
import it.polimi.jasper.engine.JenaRSPQLEngineImpl;
import it.polimi.jasper.engine.instantaneous.InstantaneousModel;
import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.yasper.core.SDS;
import it.polimi.yasper.core.enums.Entailment;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.query.ContinuousQuery;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecution;
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
import java.util.stream.Stream;

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
    private List<NamedTVG> namedTVGs;
    private DefaultTVG defTVG;


    public JenaSDSImpl(Model tbox_star, Model knowledge_base_star, IRIResolver r, Maintenance maintenanceType, String id_base, EPServiceProvider esp, JenaRSPQLEngineImpl rsp) {
        super(ModelFactory.createDefaultModel());
        this.executions = new HashMap<>();
        this.windows = new HashMap<RSPQuery, List<TimeVaryingGraph>>();
        this.cep = esp;
        this.resolver = r;
        this.namedTVGs = new ArrayList<>();
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
    public synchronized void update(Observable o, Object a) {
        TimeVaryingGraph tvg = (TimeVaryingGraph) o;
        long cep_time = tvg.getTimestamp();
        long sys_time = System.currentTimeMillis();

        log.info("[" + Thread.currentThread() + "][" + sys_time + "] " +
                "From Statement [ " + tvg.getTriggeringStatement().getText() + "] at " + cep_time);

        setDefaultModel(getDefaultModel().union(knowledge_base));

        if (rsp.getRsp_config().partialWindowsEnabled()) {
            applyPartialDatasets(tvg);
        }

        consolidate(this, tvg, cep_time);

        setDefaultModel(getDefaultModel().difference(knowledge_base));
    }

    private void applyPartialDatasets(TimeVaryingGraph tvg) {
        Stream<NamedTVG> namedTVGStream = namedTVGs.stream().filter(g -> !g.equals(tvg));
        namedTVGStream.forEach(namedTVG -> namedTVG.update(tvg.getTimestamp()));

    }

    @Override
    public boolean addDefaultWindowStream(String uri) {
        return defaultWindowStreamNames.contains(uri);
    }

    @Override
    public void addDefaultWindow(InstantaneousModel m) {
        setDefaultModel(m);
    }

    @Override
    public void addNamedWindowStream(String w, Model model) {
        log.info("Added named window [" + w + "] model [" + model + " ]");
        addNamedModel(resolver.resolveToStringSilent(w), model);
    }

    public void addTimeVaryingGraph(DefaultTVG defTVG) {
        defTVG.addObserver(this);
        this.defTVG = defTVG;
    }

    public void addNamedTimeVaryingGraph(String uri, NamedTVG namedTVG) {
        namedTVG.addObserver(this);
        namedTVGs.add(namedTVG);
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