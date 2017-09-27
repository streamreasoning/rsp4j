package it.polimi.jasper.engine.sds;

import com.espertech.esper.client.EPServiceProvider;
import it.polimi.jasper.engine.instantaneous.JenaGraph;
import it.polimi.jasper.engine.query.DefaultTVG;
import it.polimi.jasper.engine.query.NamedTVG;
import it.polimi.jasper.engine.reasoning.InstantaneousInfGraph;
import it.polimi.rspql.querying.SDS;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
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

    private final EPServiceProvider cep;
    private final IRIResolver resolver;
    private final Maintenance maintenanceType;
    private final boolean partialWindowsEnabled;

    @Getter
    protected TVGReasoner reasoner;

    @Setter
    @Getter
    protected Model knowledge_base;
    protected Model tbox;

    private List<NamedTVG> namedWOFS;
    private DefaultTVG defTVG;


    public JenaSDS(Model tbox_star, Model knowledge_base_star, DefaultTVG defaultTVG, IRIResolver r, Maintenance maintenanceType, EPServiceProvider esp, TVGReasoner<InstantaneousInfGraph, JenaGraph> reasoner, boolean partialWindowsEnabled) {
        super(ModelFactory.createDefaultModel());
        this.maintenanceType = maintenanceType;
        this.cep = esp;
        this.resolver = r;
        this.namedWOFS = new ArrayList<>();

        this.tbox = tbox_star;
        this.knowledge_base = knowledge_base_star;
        this.defTVG=defaultTVG;

        this.reasoner = reasoner;
        this.partialWindowsEnabled = partialWindowsEnabled;

    }


    @Override
    public void beforeEval() {
        setDefaultModel(getDefaultModel().union(knowledge_base));
        if (partialWindowsEnabled) {
            namedWOFS.stream().forEach(namedWOF -> namedWOF.update(cep.getEPRuntime().getCurrentTime()));
        }
    }

    @Override
    public void afterEval() {
        setDefaultModel(getDefaultModel().difference(knowledge_base));
    }

}