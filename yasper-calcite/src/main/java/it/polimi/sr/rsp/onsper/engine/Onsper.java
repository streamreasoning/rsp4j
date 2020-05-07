package it.polimi.sr.rsp.onsper.engine;

import it.polimi.sr.rsp.onsper.spe.operators.r2r.query.ContinuousRewritableQueryImpl;
import it.polimi.sr.rsp.onsper.streams.RegisteredVirtualRDFStream;
import it.polimi.yasper.core.engine.features.QueryRegistrationFeature;
import it.polimi.yasper.core.engine.features.StreamRegistrationFeature;
import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.sds.SDSConfiguration;
import lombok.extern.log4j.Log4j;
import it.polimi.sr.rsp.onsper.rspql.sds.OnsperSDSManager;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.db.OBDAManager;
import it.polimi.sr.rsp.onsper.streams.VirtualRDFStream;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.rdf4j.RDF4J;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by riccardo on 05/09/2017.
 */
@Log4j
public class Onsper implements QueryRegistrationFeature<ContinuousRewritableQueryImpl>, StreamRegistrationFeature<RegisteredVirtualRDFStream, VirtualRDFStream> {

    private final OnsperConfiguration configuration;
    private final OBDAManager ontopManager;
    private Map<String, RegisteredVirtualRDFStream> registeredStreams = new HashMap<>();
    private RDF rdf4j = new RDF4J();

    public Onsper(long t0, OnsperConfiguration configuration) {
        this.configuration = configuration;
        this.ontopManager = new OBDAManager(configuration);

    }

    @Override
    public ContinuousQueryExecution register(ContinuousRewritableQueryImpl continuousQuery) {
        OnsperSDSManager builder = new OnsperSDSManager(rdf4j, configuration, continuousQuery, ontopManager, registeredStreams);
        SDS build = builder.build();
        return builder.getContinuousQueryExecution();
    }

    @Override
    public ContinuousQueryExecution register(ContinuousRewritableQueryImpl continuousRewritableQuery, SDSConfiguration c) {
        return null;
    }


    @Override
    public RegisteredVirtualRDFStream register(VirtualRDFStream s) {
        RegisteredVirtualRDFStream rs = new RegisteredVirtualRDFStream(s.getURI(), s.getSchema(), s.mappings());
        registeredStreams.put(s.getURI(), rs);
        return rs;
    }

    public RegisteredVirtualRDFStream register(String stream, Graph mappings) {
        RegisteredVirtualRDFStream rs = registeredStreams.get(stream);
        rs.mappings(mappings);
        registeredStreams.put(rs.getURI(), rs);
        return rs;
    }

}
