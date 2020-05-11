package ee.ut.sr.rsp.binsper;

import it.polimi.jasper.engine.EsperRSPEngine;
import it.polimi.jasper.engine.esper.EsperStreamRegistrationService;
import it.polimi.sr.rsp.csparql.syntax.RSPQLJenaQuery;
import it.polimi.yasper.core.engine.config.EngineConfiguration;
import it.polimi.yasper.core.engine.features.QueryRegistrationFeature;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.enums.ReportGrain;
import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.sds.SDSConfiguration;
import it.polimi.yasper.core.secret.report.ReportImpl;
import it.polimi.yasper.core.secret.report.strategies.ReportingStrategy;
import lombok.extern.java.Log;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.jena.graph.Triple;
import org.apache.jena.sparql.engine.binding.Binding;

import java.util.Arrays;
import java.util.Set;

@Log
public class Binsper extends EsperRSPEngine<Triple> implements QueryRegistrationFeature<RSPQLJenaQuery> {

    private Maintenance maintenance;

    public Binsper(long t0, EngineConfiguration configuration) {
        super(t0, configuration);
        this.reportGrain = ReportGrain.SINGLE;
        this.maintenance = Maintenance.NAIVE;
        this.stream_registration_service = new EsperStreamRegistrationService<>(admin);
    }

    public void setReport(ReportingStrategy... sr) {
        this.report = new ReportImpl();
        Arrays.stream(sr).forEach(this.report::add);
    }

    @Override
    public ContinuousQueryExecution<Triple, Binding, Set<Binding>> register(RSPQLJenaQuery continuousQuery) {
        try {
            return register(continuousQuery, SDSConfiguration.getDefault());
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public ContinuousQueryExecution<Triple, Binding, Set<Binding>> register(RSPQLJenaQuery q, SDSConfiguration c) {
        return null;
    }

}