package it.polimi.rsp.baselines.jena;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.event.map.MapEventBean;
import it.polimi.heaven.core.enums.Reasoning;
import it.polimi.heaven.core.teststand.EventProcessor;
import it.polimi.heaven.core.teststand.data.RDFLine;
import it.polimi.heaven.core.teststand.rsp.data.Response;
import it.polimi.rsp.baselines.enums.OntoLanguage;
import it.polimi.rsp.baselines.esper.RSPListener;
import it.polimi.rsp.baselines.exceptions.UnregisteredStreamExeception;
import it.polimi.rsp.baselines.jena.events.response.BaselineResponse;
import it.polimi.rsp.baselines.jena.events.response.ConstructResponse;
import it.polimi.rsp.baselines.jena.events.response.SelectResponse;
import it.polimi.rsp.baselines.jena.events.stimuli.BaselineStimulus;
import it.polimi.rsp.baselines.jena.query.BaselineQuery;
import it.polimi.rsp.baselines.utils.BaselinesUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Graph;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.impl.InfModelImpl;
import org.apache.jena.reasoner.InfGraph;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.riot.system.IRIResolver;
import org.apache.jena.vocabulary.ReasonerVocabulary;

import java.util.HashSet;
import java.util.Set;

@Log4j
public class JenaListener implements RSPListener {

    private final Dataset dataset;
    private final IRIResolver resolver;
    protected Graph abox;
    protected Model TBoxStar;
    protected InfModel currentAbox;

    protected Reasoner reasoner;
    protected final EventProcessor<Response> next;

    private final Set<RDFLine> ABoxTriples;

    @Setter
    @Getter
    private Reasoning reasoningType;
    @Setter
    @Getter
    private OntoLanguage ontoLang;
    private BaselineQuery bq;
    private Query q;
    private BaselineResponse current_response;
    private int response_number = 0;
    private String id_base;
    private Set<String> updatedStream;
    private Set<String> defaultStreamMember, namedStreams;

    public JenaListener(Dataset dataset, EventProcessor<Response> next, BaselineQuery bq, Reasoning reasoningType, OntoLanguage ontoLang, String id_base) {
        this.next = next;
        this.bq = bq;
        this.q = QueryFactory.create(bq.getSparql_query());
        this.resolver = q.getResolver();
        this.ontoLang = ontoLang;
        this.reasoningType = reasoningType;
        this.abox = ModelFactory.createMemModelMaker().createDefaultModel().getGraph();
        this.TBoxStar = ModelFactory.createMemModelMaker().createDefaultModel();
        this.id_base = id_base;
        this.ABoxTriples = new HashSet<>();
        this.reasoner = getReasoner(ontoLang);
        this.reasoner.bindSchema(TBoxStar.getGraph());
        this.dataset = dataset;
        this.dataset.setDefaultModel(new InfModelImpl(reasoner.bind(TBoxStar.getGraph())));
        this.reasoner = getReasoner(ontoLang);
        this.defaultStreamMember = new HashSet<>();
        this.namedStreams = new HashSet<>();
    }

    @Override
    public void update(EventBean[] newData, EventBean[] oldData) {

        this.updatedStream = new HashSet<>();
        response_number++;

        IStreamUpdate(newData);
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
            for (String str : updatedStream) {
                if (defaultStreamMember.contains(str)) {
                    dataset.getDefaultModel().removeAll();
                } else if (namedStreams.contains(str)) {
                    dataset.getNamedModel(str).removeAll();
                } else {
                    throw new UnregisteredStreamExeception("Stream [" + str + "] is unregistered");
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
        String stream_name = resolver.resolveToStringSilent(underlying.getStream_name());

        Model streamGraph;

        if (defaultStreamMember.contains(stream_name)) {
            streamGraph = dataset.getDefaultModel();
        } else if (namedStreams.contains(stream_name)) {
            streamGraph = dataset.getNamedModel(stream_name);
        } else {
            throw new UnregisteredStreamExeception("Stream [" + stream_name + "] is unregistered. ");
        }

        updatedStream.add(stream_name);

        Graph updated = underlying.addTo(streamGraph.getGraph());
        InfGraph graph = reasoner.bind(updated);
        graph.rebind();

        //ABoxTriples.addAll(underlying.serialize());
    }

    private void IStreamUpdate(EventBean[] newData) {
        if (newData != null) {
            log.info("[" + newData.length + "] New Events of type [" + newData[0].getUnderlying().getClass().getSimpleName() + "]");
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
        log.debug("Handling single Dstream [" + underlying + "]");
        String stream_name = resolver.resolveToStringSilent(underlying.getStream_name());

        Model streamGraph;

        if (defaultStreamMember.contains(stream_name)) {
            streamGraph = dataset.getDefaultModel();
        } else if (namedStreams.contains(stream_name)) {
            streamGraph = dataset.getNamedModel(stream_name);
        } else {
            throw new UnregisteredStreamExeception("Stream [" + stream_name + "] is unregistered. ");
        }

        updatedStream.add(stream_name);

        Graph updated = underlying.removeFrom(streamGraph.getGraph());
        InfGraph graph = reasoner.bind(updated);
        graph.rebind();
    }

    private void DStreamUpdate(EventBean[] oldData) {
        if (oldData != null && Reasoning.INCREMENTAL.equals(reasoningType)) { //TODO not sure that it has to be only for incremental
            log.debug("[" + oldData.length + "] Old Events of type [" + oldData[0].getUnderlying().getClass().getSimpleName() + "]");
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

    public boolean addStream(String c) {
        String uri = resolver.resolveToStringSilent(c);
        defaultStreamMember.add(uri);
        return defaultStreamMember.contains(uri);
    }

    public boolean addNamedStream(String c) {
        log.debug("Added named stream [" + c + " ]");

        final String uri = resolver.resolveToStringSilent(c);
        dataset.addNamedModel(uri, new InfModelImpl(reasoner.bind(TBoxStar.getGraph())));
        namedStreams.add(uri);
        return namedStreams.contains(uri);
    }
}