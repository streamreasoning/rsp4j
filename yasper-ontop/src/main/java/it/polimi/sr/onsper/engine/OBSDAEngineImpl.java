package it.polimi.sr.onsper.engine;

import it.polimi.rspql.SDSBuilder;
import it.polimi.rspql.Stream;
import it.polimi.rspql.querying.ContinuousQuery;
import it.polimi.rspql.querying.ContinuousQueryExecution;
import it.polimi.sr.onsper.query.OBSDAQueryBuilder;
import it.polimi.sr.onsper.query.schema.OntopJavaTypeFactory;
import it.polimi.sr.onsper.streams.RegisteredRelStream;
import it.polimi.sr.onsper.streams.RelStream;
import it.polimi.yasper.core.engine.RSPQLEngineImpl;
import it.polimi.yasper.core.query.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.stream.StreamSchema;
import it.polimi.yasper.core.utils.EngineConfiguration;
import it.polimi.yasper.core.utils.QueryConfiguration;
import lombok.extern.log4j.Log4j;
import org.apache.calcite.jdbc.*;
import org.apache.commons.rdf.api.Graph;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static it.polimi.yasper.core.query.operators.s2r.EPLFactory.toEPLSchema;

/**
 * Created by riccardo on 05/09/2017.
 */
@Log4j
public class OBSDAEngineImpl extends RSPQLEngineImpl<RelStream, RegisteredRelStream> {

    private CalciteConnection calciteConnection;
    private Map<String, Graph> mappings;

    public OBSDAEngineImpl(long t0, EngineConfiguration configuration) {
        super(t0, configuration);
        this.mappings = new HashMap<>();
        try {
            Class.forName("org.apache.calcite.jdbc.Driver");
            CalciteJdbc41Factory factory = new CalciteJdbc41Factory();
            Driver driver = new Driver();

            this.calciteConnection = factory.newConnection(driver, factory, "jdbc:calcite:", new Properties(), CalciteSchema.createRootSchema(true), new OntopJavaTypeFactory());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public RegisteredRelStream register(RelStream s) {
        StreamSchema.Factory.registerSchema(s.getSchema());
        // UpdatableSchema schema = new UpdatableReflectiveSchema(s.getClass());
        RegisteredRelStream registeredRelStream = new RegisteredRelStream(s, createStream(toEPLSchema(s), s.getURI()), rsp_config.getStreamSchema());
        registeredStreams.put(s.getURI(), registeredRelStream);
        return registeredRelStream;
    }

    @Override
    public ContinuousQuery parseQuery(String input) {
        return null;
    }

    @Override
    public ContinuousQueryExecution register(ContinuousQuery q, QueryConfiguration c) {
        SDSBuilder builder = new OBSDAQueryBuilder(calciteConnection, registeredStreams, mappings);
        //   q.accept(builder, c);
        return null;
    }

    @Override
    public ContinuousQueryExecution register(String q, QueryConfiguration c) {
        return null;
    }

    @Override
    public void unregister(ContinuousQuery qId) {

    }

    @Override
    public void register(ContinuousQuery q, QueryResponseFormatter o) {

    }

    @Override
    public void unregister(ContinuousQuery q, QueryResponseFormatter o) {

    }

    @Override
    public void register(ContinuousQueryExecution cqe, QueryResponseFormatter o) {

    }

    @Override
    public void unregister(ContinuousQueryExecution cqe, QueryResponseFormatter o) {

    }

    public void register(Stream s, Graph m) {
        register(s.getURI(), m);
    }

    public void register(String s, Graph m) {

        if (registeredStreams.containsKey(s)) {
            mappings.put(s, m);
        }
    }
}
