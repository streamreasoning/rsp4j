package it.polimi.jasper.engine.sds;

import it.polimi.yasper.core.quering.SDS;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import it.polimi.yasper.simple.windowing.TimeVarying;
import it.polimi.yasper.core.spe.time.Time;
import it.polimi.yasper.core.spe.time.TimeFactory;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.IRI;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.compose.MultiUnion;
import org.apache.jena.rdf.model.impl.InfModelImpl;
import org.apache.jena.reasoner.InfGraph;
import org.apache.jena.riot.system.IRIResolver;
import org.apache.jena.sparql.core.DatasetGraphFactory;
import org.apache.jena.sparql.core.DatasetImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by riccardo on 01/07/2017.
 */
@Log4j
public class JenaSDS extends DatasetImpl implements SDS<InfGraph> {

    private boolean partialWindowsEnabled = false;
    private Time time = TimeFactory.getInstance();
    @Getter
    protected TVGReasoner reasoner;

    private List<TimeVarying<InfGraph>> tvgs = new ArrayList<>();
    private final IRIResolver resolver;

    protected JenaSDS(Graph def, IRIResolver resolver) {
        super(DatasetGraphFactory.create(def));
        this.resolver = resolver;
    }


    @Override
    public void beforeEval() {

    }

    @Override
    public void afterEval() {
        //setDefaultModel(getDefaultModel().difference(knowledge_base));
    }

    @Override
    public <T extends TimeVarying<InfGraph>> void add(IRI iri, T tvg) {
        addNamedModel(resolver.resolveToString(iri.getIRIString()), new InfModelImpl(tvg.asT()));
        tvgs.add(tvg);
    }

    @Override
    public <T extends TimeVarying<InfGraph>> void add(T tvg) {
        MultiUnion graph = (MultiUnion) getDefaultModel().getGraph();
        graph.addGraph(tvg.asT());
        tvgs.add(tvg);
    }


    @Override
    public void eval(long ts) {
        if (partialWindowsEnabled) {
            tvgs.forEach(g -> g.eval(time.getAppTime()));
        }
    }

}