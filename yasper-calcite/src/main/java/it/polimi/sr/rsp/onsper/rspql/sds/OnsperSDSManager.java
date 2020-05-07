package it.polimi.sr.rsp.onsper.rspql.sds;

import it.polimi.sr.rsp.onsper.rspql.VirtualSDS;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.R2ROntop;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.Relation;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.db.OBDAManager;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.db.SDSQuerySchema;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.query.ContinuousRewritableQueryImpl;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.query.execution.DirectContinuousQueryExecution;
import it.polimi.sr.rsp.onsper.spe.operators.r2s.responses.JRStream;
import it.polimi.sr.rsp.onsper.spe.operators.r2s.responses.RelationalSolution;
import it.polimi.sr.rsp.onsper.spe.operators.s2r.operator.OBDAWindowOperator1;
import it.polimi.sr.rsp.onsper.streams.RegisteredVirtualRDFStream;
import it.polimi.yasper.core.RDFUtils;
import it.polimi.yasper.core.engine.config.EngineConfiguration;
import it.polimi.yasper.core.enums.ReportGrain;
import it.polimi.yasper.core.enums.Tick;
import it.polimi.yasper.core.operators.s2r.StreamToRelationOperator;
import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.sds.SDSManager;
import it.polimi.yasper.core.sds.timevarying.TimeVarying;
import it.polimi.yasper.core.secret.report.Report;
import it.polimi.yasper.core.secret.report.ReportImpl;
import it.polimi.yasper.core.secret.report.strategies.OnWindowClose;
import it.polimi.yasper.core.stream.web.WebStream;
import it.unibz.inf.ontop.exception.OBDASpecificationException;
import it.unibz.inf.ontop.exception.OntopReformulationException;
import lombok.extern.java.Log;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.jooq.lambda.tuple.Tuple;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.util.Map;

/**
 * Created by riccardo on 05/09/2017.
 */
@Log
public class OnsperSDSManager implements SDSManager {

    private final Map<String, RegisteredVirtualRDFStream> registeredStreams;
    private final OBDAManager ontop_manager;

    private final ContinuousRewritableQueryImpl query;
    private final Graph mappings;
    private final EngineConfiguration config;
    private OWLOntology tbox;
    private final OWLOntologyManager owlOntologyManager;
    private VirtualSDS sds;
    private DirectContinuousQueryExecution cqe;

    public OnsperSDSManager(RDF rdf4j, EngineConfiguration config, ContinuousRewritableQueryImpl query, OBDAManager obdaManager, Map<String, RegisteredVirtualRDFStream> registeredStreams) {
        this.registeredStreams = registeredStreams;
        this.query = query;
        this.ontop_manager = obdaManager;
        this.owlOntologyManager = OWLManager.createOWLOntologyManager();
        this.mappings = rdf4j.createGraph();
        this.config = config;
    }

    @Override
    public SDS build() {
        try {
            SDSQuerySchema.Builder dbBuilder = new SDSQuerySchema.Builder();

            this.query.getWindowMap().values().stream()
                    .map(WebStream::getURI)
                    .filter(registeredStreams::containsKey)
                    .map(registeredStreams::get)
                    .map(RegisteredVirtualRDFStream::mappings)
                    .flatMap(Graph::stream)
                    .forEach(mappings::add);

            this.tbox = owlOntologyManager.createOntology(org.semanticweb.owlapi.model.IRI.create(this.config.getString("rsp_engine.tbox_location")));

            this.sds = new VirtualSDS(this);


            String qid = query.getID();

            query.getWindowMap()
                    .forEach((w, s) -> {

                        //get the stream object form the registered ones
                        RegisteredVirtualRDFStream relStream = this.registeredStreams.get(s.getURI());

                        //The view over the stream corresponds to the window

                        Relation<Tuple> relation = this.ontop_manager.createMetadata(dbBuilder, this.ontop_manager.metadata(), qid, s.getURI(), w.iri(), relStream.getSchema());
                        IRI iri = RDFUtils.createIRI(w.iri());

                        //Report actually depends on the engine configuration
                        Report report = new ReportImpl();
                        report.add(new OnWindowClose());

                        //Only this operator is supported currently
                        StreamToRelationOperator<Tuple, Relation<Tuple>> vwo = new OBDAWindowOperator1(
                                iri,
                                w.getRange(),
                                w.getStep(),
                                w.getT0(),
                                relStream.getSchema(),
                                relStream.mappings(),
                                relation,
                                Tick.TIME_DRIVEN,
                                report,
                                ReportGrain.SINGLE, sds);

                        TimeVarying<Relation<Tuple>> set = vwo.apply(relStream);

                        if (vwo.named())
                            this.sds.add(iri, set);
                        else
                            this.sds.add(set);

                    });

            R2ROntop r2r = new R2ROntop();

            JRStream<RelationalSolution.Result> r2s = new JRStream<>();

            this.cqe = new DirectContinuousQueryExecution(this.sds, this.query, r2r, r2s);


            //add the query name as a database within the root schema
            this.ontop_manager.addDB(qid, dbBuilder);

            this.ontop_manager.createExecution(this.cqe, this.mappings, this.tbox, this.ontop_manager.metadata(), this.query);
            return sds;
        } catch (OBDASpecificationException | OntopReformulationException | OWLOntologyCreationException e) {
            throw new SDSCreationException(e.getCause().getMessage());
        }
    }


    @Override
    public ContinuousQueryExecution getContinuousQueryExecution() {
        return cqe;
    }


}
