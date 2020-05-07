package it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.db;

import com.google.inject.Injector;
import eu.optique.r2rml.api.model.TriplesMap;
import eu.optique.r2rml.api.model.impl.R2RMLMappingManagerImpl;
import it.polimi.sr.rsp.onsper.engine.OnsperConfiguration;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.Relation;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.rewriting.OntopJavaTypeFactory;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.query.execution.DirectContinuousQueryExecution;
import it.polimi.yasper.core.querying.ContinuousQuery;
import it.polimi.yasper.core.stream.metadata.SchemaEntry;
import it.polimi.yasper.core.stream.metadata.StreamSchema;
import it.unibz.inf.ontop.answering.reformulation.QueryReformulator;
import it.unibz.inf.ontop.answering.reformulation.impl.SQLExecutableQuery;
import it.unibz.inf.ontop.answering.reformulation.input.SPARQLQuery;
import it.unibz.inf.ontop.dbschema.DatabaseRelationDefinition;
import it.unibz.inf.ontop.dbschema.RDBMetadata;
import it.unibz.inf.ontop.dbschema.RDBMetadataExtractionTools;
import it.unibz.inf.ontop.dbschema.RelationID;
import it.unibz.inf.ontop.exception.*;
import it.unibz.inf.ontop.injection.*;
import it.unibz.inf.ontop.rdf4j.repository.OntopRepository;
import it.unibz.inf.ontop.spec.OBDASpecification;
import it.unibz.inf.ontop.spec.mapping.parser.SQLMappingParser;
import it.unibz.inf.ontop.spec.mapping.pp.SQLPPMapping;
import it.unibz.inf.ontop.spec.mapping.serializer.SQLPPMappingToR2RMLConverter;
import lombok.extern.log4j.Log4j;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.jdbc.CalciteJdbc41Factory;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.jdbc.Driver;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.jooq.lambda.tuple.Tuple;
import org.semanticweb.owlapi.model.OWLOntology;

import java.io.File;
import java.sql.Types;
import java.util.Collection;
import java.util.Comparator;
import java.util.Properties;
import java.util.Set;

@Log4j
public class OBDAManager {

    private final String username;
    private final String password;
    private final String jdbcDriver;
    private final String jdbcURL;

    private final CalciteConnection connection;
    private final CalciteSchema rootSchema;

    private RDBMetadata metadata;

    public OBDAManager(OnsperConfiguration configuration) {

        this.username = configuration.getJDBCUSER();
        this.password = configuration.getJDBCPassword();
        this.jdbcDriver = configuration.getJDBCDriver();
        this.jdbcURL = configuration.getJDBCURL();

        CalciteJdbc41Factory factory = new CalciteJdbc41Factory();
        this.rootSchema = CalciteSchema.createRootSchema(true);
        this.connection = factory.newConnection(new Driver(), factory, configuration.getJDBCURL(), new Properties(), rootSchema, new OntopJavaTypeFactory());

    }

    public RDBMetadata metadata() {
        if (this.metadata == null) {
            this.metadata = RDBMetadataExtractionTools.createDummyMetadata(jdbcURL);
        }
        return this.metadata;
    }

    public void repository(Graph mappings, OWLOntology tbox) {
        RDBMetadata dbMetadata = RDBMetadataExtractionTools.createDummyMetadata(jdbcURL);

        OntopRepository repository = OntopRepository.defaultRepository(OntopSQLOWLAPIConfiguration.defaultBuilder()
                .r2rmlMappingGraph(mappings)
                .ontology(tbox)
                .enableProvidedDBMetadataCompletion(false)
                .dbMetadata(dbMetadata)
                .jdbcDriver(jdbcDriver).jdbcUrl(jdbcURL).jdbcUser(username).jdbcPassword(password)
                .build());

        repository.initialize();
    }

    public Relation<Tuple> createMetadata(SDSQuerySchema.Builder dbBuilder, RDBMetadata dbMetadata, String streamID, String schemaName, String viewName, StreamSchema schema) {
        String schemaName1 = "\"" + streamID + "\"";
        String viewName1 = "\"" + viewName + "\"";
        RelationID relationID = dbMetadata.getQuotedIDFactory().createRelationID(schemaName1, viewName1);
        DatabaseRelationDefinition table = dbMetadata.createDatabaseRelation(relationID);
        Set<SchemaEntry> set = schema.entrySet();
        set.stream()
                .sorted(Comparator.comparingInt(SchemaEntry::getIndex))
                .forEach((SchemaEntry e) -> {
                    table.addAttribute(
                            dbMetadata.getQuotedIDFactory().createAttributeID(e.getID()),
                            e.getType(),
                            e.getTypeName(),
                            e.canNull());
                });

        return dbBuilder.addTuple(streamID, viewName, table);
    }


    public DirectContinuousQueryExecution createExecution(DirectContinuousQueryExecution cqe, Graph mappings, OWLOntology tbox, RDBMetadata dbMetadata, ContinuousQuery query) throws OBDASpecificationException, OntopReformulationException {
        OntopOBDAConfiguration config1 = OntopMappingSQLAllOWLAPIConfiguration.defaultBuilder()
                .r2rmlMappingGraph(mappings)
                .ontology(tbox)
                .enableProvidedDBMetadataCompletion(false)
                .dbMetadata(metadata)
                .jdbcDriver(jdbcDriver).jdbcUrl(jdbcURL).jdbcUser(username).jdbcPassword(password)
                .build();

        OBDASpecification specStream = config1.loadSpecification();

        OntopReformulationSQLConfiguration config = OntopReformulationSQLConfiguration.defaultBuilder()
                .enableIRISafeEncoding(false)
                .obdaSpecification(new CalciteOBDASpecification(specStream.getSaturatedMapping(), specStream.getSaturatedTBox(), dbMetadata))
                .jdbcDriver(jdbcDriver).jdbcUrl(jdbcURL).build();


        QueryReformulator queryReformulator = config.loadQueryReformulator();

        SPARQLQuery q = queryReformulator.getInputQueryFactory().createSPARQLQuery(query.toString());

        SQLExecutableQuery sqlq = (SQLExecutableQuery) queryReformulator.reformulateIntoNativeQuery(q);

        log.info(sqlq.getSQL());

        cqe.setConnection(sqlq, connection);
        return cqe;
    }

    public void addDB(String qid, SDSQuerySchema.Builder builder) {
        rootSchema.add(qid, builder.build());
    }

    public static final String integer = "Integer";
    public static final String string = "String";
    public static final String doubleT = "Double";
    public static final String date = "Date";
    public static final String longT = "Long";
    public static final String timestamp = "timestamp";

    public static int convert(String type) {
        switch (type) {
            case integer:
                return Types.INTEGER;
            case doubleT:
                return Types.DOUBLE;
            case longT:
                return Types.BIGINT;
            case date:
                return Types.DATE;
            case timestamp:
                return Types.TIMESTAMP;
            case string:
                return Types.VARCHAR;
            default:
                return Types.VARCHAR;

        }

    }

    public static Graph obda2R2RMLRDF(String properties, String mappings) throws DuplicateMappingException, MappingIOException, InvalidMappingException {

        OntopMappingSQLAllConfiguration configuration = OntopMappingSQLAllConfiguration.defaultBuilder()
                .propertyFile(properties)
                .build();

        Injector injector = configuration.getInjector();

        SQLMappingParser mappingParser = injector.getInstance(SQLMappingParser.class);

        SQLPPMapping parse = mappingParser.parse(new File(mappings));

        SQLPPMappingToR2RMLConverter converter = new SQLPPMappingToR2RMLConverter(parse, null);

        R2RMLMappingManagerImpl mm = new R2RMLMappingManagerImpl(new RDF4J());

        Collection<TriplesMap> tripleMaps = converter.getTripleMaps();

        return mm.exportMappings(tripleMaps);
    }
}
