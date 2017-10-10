package it.polimi.sr.onsper.query;

import it.polimi.rspql.SDSBuilder;
import it.polimi.rspql.querying.ContinuousQueryExecution;
import it.polimi.rspql.querying.SDS;
import it.polimi.rspql.timevarying.TimeVarying;
import it.polimi.sr.onsper.engine.NamedTVR;
import it.polimi.sr.onsper.engine.Relation;
import it.polimi.sr.onsper.query.execution.DirectContinuousQueryExecution;
import it.polimi.sr.onsper.query.schema.CalciteOBDASpecification;
import it.polimi.sr.onsper.query.schema.SDSQuerySchema;
import it.polimi.sr.onsper.sds.VirtualSDSImpl;
import it.polimi.sr.onsper.streams.RegisteredRelStream;
import it.polimi.sr.onsper.streams.RelStream;
import it.polimi.yasper.core.stream.SchemaEntry;
import it.polimi.yasper.core.stream.StreamSchema;
import it.unibz.inf.ontop.answering.reformulation.QueryReformulator;
import it.unibz.inf.ontop.answering.reformulation.impl.SQLExecutableQuery;
import it.unibz.inf.ontop.answering.reformulation.input.SPARQLQuery;
import it.unibz.inf.ontop.dbschema.DatabaseRelationDefinition;
import it.unibz.inf.ontop.dbschema.RDBMetadata;
import it.unibz.inf.ontop.dbschema.RDBMetadataExtractionTools;
import it.unibz.inf.ontop.dbschema.RelationID;
import it.unibz.inf.ontop.exception.*;
import it.unibz.inf.ontop.injection.OntopMappingSQLAllOWLAPIConfiguration;
import it.unibz.inf.ontop.injection.OntopOBDAConfiguration;
import it.unibz.inf.ontop.injection.OntopReformulationSQLConfiguration;
import it.unibz.inf.ontop.spec.OBDASpecification;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by riccardo on 05/09/2017.
 */
public class OBSDAQueryBuilder implements SDSBuilder<OBDAQuery> {


    private final Map<String, RegisteredRelStream> registeredStreams;
    private final Map<String, Graph> mappings;
    private CalciteConnection connection;
    private SchemaPlus rootSchema;
    private SDS sds;
    private ContinuousQueryExecution cqe;
    private String jdbcDriver = "org.apache.calcite.jdbc.Driver";
    private String jdbcURL = "jdbc:calcite:";

    public OBSDAQueryBuilder(CalciteConnection calciteConnection, Map<String, RegisteredRelStream> registeredStreams, Map<String, Graph> mappings) {
        this.connection = calciteConnection;
        this.registeredStreams = registeredStreams;
        this.mappings = mappings;
//        this.rootSchema = calciteConnection.getRootSchema();
        //      this.sds = new VirtualSDSImpl(rootSchema);


        /*TODO

                SQL dialect of calcite for ontop
                Calcite Metadata Exposure: something is already possible, must be tricky since
                it might depend on how many different sources are connected
                DBMetadata for calcite construction for calcite
                Create Books as



         */

    }


    //NOTE this builder has to create
    //TODO a Incremental Refletive Schema Composed by the views over the Stream
    //TODO An OBDA Specification to pass to ontop in order to obtain an SQL Query
    // TODO R2RML Mappings
    // TODO Ontology
    // TODO DBMEtadata collecting stream schema definition, i.e the part to extract and add to the calcite schema

    // TODO calcite metadata using streams and windows
        /* NOTE we can consider the option to build up our own metadata by running
         stream analysis at creation time? Maybe we can infer some sort of
         foreign key across the selcted streams...*/

    //NOTE Assumption: streams are already carrying the Reflective Schema to add to calcite
    //NOTE just have to create the collection to query that practically represents the view
    // An EPL still maintains this view add and deleting object through a Listener
    // Eventually I will get rid of this editing Esper internals directly (and removing all the
    // shitting part of the code that use reflection

    //TODO do we admit each stream to have its vocabulary?
//            Set<OWLAxiom> axiomSet = new HashSet<>();
//            query.getNamedWindowsSet().forEach(w -> {
//
//
//            });

    //TODO
              /* try {
                    OWLOntology ontology1 = manager.createOntology(IRI.create(relStream.getTboxUri()));
                    axiomSet.addAll(ontology1.getAxioms());
                } catch (OWLOntologyCreationException e) {
                    e.printStackTrace();
                }*/

    @Override
    public void visit(OBDAQuery query) {

        try {
            RDF rdf4j = new RDF4J();
            Graph graph = rdf4j.createGraph();
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLOntology ontology = manager.createOntology(query.getTBox());

            RDBMetadata dbMetadata = RDBMetadataExtractionTools.createDummyMetadata("jdbc:calcite:");

            SDSQuerySchema.Builder dbBuilder = new SDSQuerySchema.Builder();

            Set<TimeVarying> views = new HashSet<>();
            query.getWindowMap().forEach((w, s) -> mappings.get(s.getURI()).stream().forEach(t -> {
                graph.add(t);
                RelStream relStream = registeredStreams.get(s);
                String viewName = relStream.getURI() + w.getName();
                StreamSchema schema = relStream.getSchema();
                views.add(createMetadata(dbBuilder, dbMetadata, query.getID(), viewName, schema));
            }));

            OntopOBDAConfiguration config = OntopMappingSQLAllOWLAPIConfiguration.defaultBuilder()
                    .r2rmlMappingGraph(graph)
                    .ontology(ontology)
                    .enableProvidedDBMetadataCompletion(false)
                    .dbMetadata(dbMetadata)
                    .jdbcDriver(jdbcDriver).jdbcUrl(jdbcURL).jdbcUser("root").jdbcPassword("root")
                    .build();

            OBDASpecification specStream = config.loadSpecification();


            OntopReformulationSQLConfiguration c = OntopReformulationSQLConfiguration.defaultBuilder()
                    .obdaSpecification(new CalciteOBDASpecification(specStream.getSaturatedMapping(), specStream.getSaturatedTBox(), specStream.getVocabulary(), dbMetadata))
                    .jdbcDriver(jdbcDriver).jdbcUrl(jdbcURL).build();


            this.cqe = createExecution(c, sds, query);

            //Observe tvr with cqe
            views.forEach(v -> cqe.add(v));

            this.sds = new VirtualSDSImpl(rootSchema, connection);

        } catch (OBDASpecificationException e) {
        } catch (OntopConnectionException e) {
        } catch (OntopResultConversionException e) {
        } catch (OntopQueryEvaluationException e) {
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        } catch (OntopReformulationException e) {
            e.printStackTrace();
        }

    }

    private NamedTVR createMetadata(SDSQuerySchema.Builder dbBuilder, RDBMetadata dbMetadata, String schemaName, String viewName, StreamSchema schema) {
        //TODO unroll the stream schema into a ontop's metadata (I.e. add the required methods)
        //TODO consider to include metadata that describe the temporal constraints of the window, although they are not used yet
        RelationID relationID = dbMetadata.getQuotedIDFactory().createRelationID(schemaName, viewName);
        DatabaseRelationDefinition table = dbMetadata.createDatabaseRelation(relationID);
        schema.entrySet().stream().sorted(Comparator.comparingInt(SchemaEntry::getIndex)).forEach(e -> {
            //Add to ontop
            table.addAttribute(dbMetadata.getQuotedIDFactory().createAttributeID(e.getID()), e.getType(), e.getTypeName(), e.canNull());
            // table.addUniqueConstraint(UniqueConstraint.primaryKeyOf(attr));

        });

        Relation<Object> add = dbBuilder.add(schemaName, viewName, table);
        //Add to calcite
        return new NamedTVR(viewName, schema, add);
    }

    private ContinuousQueryExecution createExecution(OntopReformulationSQLConfiguration config, SDS sds, OBDAQuery query) throws OBDASpecificationException, OntopConnectionException, OntopQueryEvaluationException, OntopResultConversionException, OntopReformulationException {

        QueryReformulator queryReformulator = config.loadQueryReformulator();
        SPARQLQuery q = queryReformulator.getInputQueryFactory().createSPARQLQuery(query.toString());
        SQLExecutableQuery sqlq = (SQLExecutableQuery) queryReformulator.reformulateIntoNativeQuery(q);

        return new DirectContinuousQueryExecution(sds, query, q, sqlq, connection);
    }

    @Override
    public SDS getSDS() {
        return sds;
    }

    @Override
    public OBSDAQueryImpl getContinuousQuery() {
        return null;
    }

    @Override
    public ContinuousQueryExecution getContinuousQueryExecution() {
        return null;
    }

}
