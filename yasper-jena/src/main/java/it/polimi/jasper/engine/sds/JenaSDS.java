package it.polimi.jasper.engine.sds;

import it.polimi.jasper.engine.instantaneous.JenaGraph;
import it.polimi.jasper.engine.query.DefaultTVG;
import it.polimi.jasper.engine.query.NamedTVG;
import it.polimi.jasper.engine.reasoning.InstantaneousInfGraph;
import it.polimi.yasper.core.rspql.SDS;
import it.polimi.yasper.core.simple.windowing.TimeVarying;
import it.polimi.yasper.core.spe.time.Time;
import it.polimi.yasper.core.spe.time.TimeFactory;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.system.IRIResolver;
import org.apache.jena.sparql.core.DatasetImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by riccardo on 01/07/2017.
 */
@Log4j
public class JenaSDS extends DatasetImpl implements SDS {

    private final IRIResolver resolver;
    private final Maintenance maintenanceType;
    private final boolean partialWindowsEnabled;
    private final Time time = TimeFactory.getInstance();
    @Getter
    protected TVGReasoner reasoner;

    @Setter
    @Getter
    protected Model knowledge_base;
    protected Model tbox;

    private List<NamedTVG> namedWOFS;
    private DefaultTVG defTVG;


    public JenaSDS(Model tbox_star,
                   Model knowledge_base_star,
                   DefaultTVG defaultTVG,
                   IRIResolver r,
                   Maintenance maintenanceType,
                   TVGReasoner<InstantaneousInfGraph, JenaGraph> reasoner,
                   boolean partialWindowsEnabled) {
        super(ModelFactory.createDefaultModel());
        this.maintenanceType = maintenanceType;
        this.resolver = r;
        this.namedWOFS = new ArrayList<>();

        this.tbox = tbox_star;
        this.knowledge_base = knowledge_base_star;
        this.defTVG = defaultTVG;

        this.reasoner = reasoner;
        this.partialWindowsEnabled = partialWindowsEnabled;

    }

    @Override
    public void beforeEval() {
        setDefaultModel(getDefaultModel().union(knowledge_base));
        if (partialWindowsEnabled) {
            namedWOFS.forEach(namedWOF -> {
                namedWOF.update(time.getAppTime());
            });
        }
    }

    @Override
    public void afterEval() {
        setDefaultModel(getDefaultModel().difference(knowledge_base));
    }

    @Override
    public <T extends TimeVarying<Graph>> void add(IRI iri, T tvg) {

    }

    @Override
    public <T extends TimeVarying<Graph>> void add(T tvg) {

    }


    @Override
    public void eval(long ts) {

    }

}