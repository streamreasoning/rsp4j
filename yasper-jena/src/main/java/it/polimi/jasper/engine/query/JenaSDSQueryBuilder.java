package it.polimi.jasper.engine.query;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import it.polimi.jasper.engine.instantaneous.GraphBase;
import it.polimi.jasper.engine.instantaneous.JenaGraph;
import it.polimi.jasper.engine.query.execution.observer.ContinuousQueryExecutionFactory;
import it.polimi.jasper.engine.reasoning.InstantaneousInfGraph;
import it.polimi.jasper.engine.reasoning.JenaTVGReasoner;
import it.polimi.jasper.engine.reasoning.TimeVaryingInfGraph;
import it.polimi.jasper.engine.sds.JenaSDS;
import it.polimi.jasper.engine.stream.RegisteredRDFStream;
import it.polimi.jasper.parser.streams.WindowedStreamNode;
import it.polimi.rspql.SDSBuilder;
import it.polimi.rspql.cql.s2_.WindowOperator;
import it.polimi.rspql.querying.ContinuousQueryExecution;
import it.polimi.rspql.querying.SDS;
import it.polimi.rspql.timevarying.TimeVarying;
import it.polimi.yasper.core.enums.Entailment;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.exceptions.UnregisteredStreamExeception;
import it.polimi.yasper.core.stream.RegisteredStream;
import it.polimi.yasper.core.utils.EncodingUtils;
import it.polimi.yasper.core.utils.EngineConfiguration;
import it.polimi.yasper.core.utils.QueryConfiguration;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Node;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.impl.InfModelImpl;
import org.apache.jena.rdf.model.impl.ModelCom;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.riot.system.IRIResolver;

import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.yasper.core.query.operators.s2r.EPLFactory.toEPL;
import static it.polimi.yasper.core.query.operators.s2r.EPLFactory.toIREPL;

/**
 * Created by riccardo on 05/09/2017.
 */
@Log4j
@RequiredArgsConstructor
public class JenaSDSQueryBuilder implements SDSBuilder<RSPQuery> {

    @NonNull
    protected EngineConfiguration rsp_config;
    @NonNull
    protected QueryConfiguration queryConfiguration;
    @Getter
    protected JenaTVGReasoner reasoner;
    @NonNull
    private EPAdministrator cepAdm;
    @NonNull
    private EPServiceProvider cep;
    @NonNull
    private Map<String, RegisteredRDFStream> registeredStreams;
    private boolean is_deltas;
    @Getter
    private SDS sds;
    @Getter
    private ContinuousQueryExecution qe;
    private Maintenance maintenance;
    private IRIResolver resolver;
    private RSPQuery query;

    private DefaultTVG defaultTVG;
    private List<NamedTVG> namedWOFS;

    public JenaSDSQueryBuilder(EPAdministrator cepAdm, EPServiceProvider cep, Map<String, RegisteredRDFStream> registeredStreams, EngineConfiguration rsp_config, QueryConfiguration c) {
        this.cepAdm = cepAdm;
        this.cep = cep;
        this.registeredStreams = registeredStreams;
        this.rsp_config = rsp_config;
        this.queryConfiguration = c;
        namedWOFS = new ArrayList<>();

    }


    @Override
    public void visit(RSPQuery bq) {
        this.query = bq;
        this.query.setConfiguration(queryConfiguration);
        String tboxLocation = queryConfiguration.getTboxLocation();
        Model tbox = ModelFactory.createDefaultModel().read(tboxLocation);
        this.maintenance = queryConfiguration.getSdsMaintainance();
        Entailment entailment = queryConfiguration.getReasoningEntailment();
        boolean recursionEnabled = rsp_config.isRecursionEnables();

        log.info("Registering Query [" + bq.getName() + "]");
        log.info(bq.getQ().toString());

        is_deltas = Maintenance.INCREMENTAL.equals(maintenance);

        if (bq.isRecursive() && !recursionEnabled) {
            throw new UnsupportedOperationException("Recursion must be enabled");
        }



        reasoner = ContinuousQueryExecutionFactory.getGenericRuleReasoner(entailment, tbox);

        GraphBase base = new GraphBase();
        Model m = loadStaticGraph(bq, new ModelCom(base));
        InfModel kb_star = ModelFactory.createInfModel(reasoner.bind(m.getGraph()));
        defaultTVG = new DefaultTVG(base);

        //SET DEFAULT STREAM
        this.resolver = bq.getResolver();


        JenaSDS jenaSDS = new JenaSDS(tbox, kb_star, defaultTVG, resolver, maintenance, cep, this.reasoner, rsp_config.partialWindowsEnabled());

        addNamedStaticGraph(bq, jenaSDS, this.reasoner);

        List<TimeVarying> collect = bq.getWindowMap().entrySet().stream().map(e -> apply(e.getKey(), registeredStreams.get(e.getValue().getURI()))).collect(Collectors.toList());

        //SET ALL NAMED MODELS
        this.namedWOFS.forEach(namedTVG -> {
            String uri = resolver.resolveToString(namedTVG.getWoa().getName());
            JenaGraph g = namedTVG.getContent();
            InstantaneousInfGraph bind = reasoner.bind(g);
            jenaSDS.addNamedModel(uri, new InfModelImpl(bind));
            namedTVG.setContent(bind);
        });


        sds = jenaSDS;
        qe = ContinuousQueryExecutionFactory.createObserver(bq, sds, this.reasoner);
        collect.forEach(tvi -> qe.add(tvi));


    }

    @Override
    public SDS getSDS() {
        return sds;
    }

    @Override
    public RSPQuery getContinuousQuery() {
        return query;
    }

    @Override
    public ContinuousQueryExecution getContinuousQueryExecution() {
        return qe;
    }

    private void addNamedStaticGraph(RSPQuery bq, JenaSDS sds, Reasoner reasoner) {
        //Named Static Graphs
        if (bq.getNamedGraphURIs() != null)
            for (String g : bq.getNamedGraphURIs()) {
                log.info(g);
                if (!isWindow(bq.getNamedwindows().keySet(), g)) {
                    Model m = ModelFactory.createDefaultModel().read(g);
                    TimeVaryingInfGraph bind = (TimeVaryingInfGraph) reasoner.bind(m.getGraph());
                    sds.addNamedModel(g, new InfModelImpl(bind));
                }
            }
    }

    private Model loadStaticGraph(RSPQuery bq, Model def) {
        //Default Static Graph
        if (bq.getGraphURIs() != null)
            for (String g : bq.getGraphURIs()) {
                log.info(g);
                if (!isWindow(bq.getWindows(), g)) {
                    def = def.read(g);
                }
            }
        return def;
    }

    protected boolean isWindow(Set<?> windows, String g) {
        if (windows != null) {
            Iterator<?> iterator = windows.iterator();
            while (iterator.hasNext()) {
                Object next = iterator.next();
                if (next instanceof WindowedStreamNode && ((WindowedStreamNode) next).getStreamURI().equals(g)) {
                    return true;
                } else if (next instanceof Node && ((Node) next).getURI().equals(g)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void checkStreamExistence(String uri) {
        String encode = EncodingUtils.encode(uri);
        if (cepAdm.getStatement(encode) == null) {
            throw new UnregisteredStreamExeception("Stream [" + uri + "] encoded as [" + encode + "]");
        }
    }


    private TimeVarying apply(WindowOperator wo, RegisteredStream s) {
        String stream_uri = s.getURI();
        String window_uri = wo.getName();
        String encoded_window_name = EncodingUtils.encode(window_uri);
        String encoded_stream_name = EncodingUtils.encode(stream_uri);
        checkStreamExistence(stream_uri);

        EPStatementObjectModel eplm = is_deltas ? toIREPL(wo, s) : toEPL(wo, s);
        EPStatement statement = cepAdm.getStatement(encoded_window_name) != null ? cepAdm.getStatement(encoded_window_name) : cepAdm.create(eplm, encoded_window_name);
        String windowEPL = eplm.toEPL();

        //TODO not sure they still need the SDS
        //s.setSDS(sds);
        //s.setInstantaneousItem(g);

        log.debug("WindowOperatorDefinition [ " + windowEPL.replace(encoded_window_name, window_uri).replace(encoded_stream_name, stream_uri) + "]");
        return get(wo.isNamed(), statement, wo);
    }

    private TimeVarying get(boolean named, EPStatement s, WindowOperator wo) {
        if (named) {
            NamedTVG n = new NamedTVG(this.maintenance, wo);
            s.addListener(n);
            namedWOFS.add(n);
            return n;
        } else {
            s.addListener(defaultTVG);
            defaultTVG.setWindowOperator(wo);
            return defaultTVG;
        }
    }
}
