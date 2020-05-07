package ready2go;

import it.polimi.yasper.core.RDFUtils;
import it.polimi.yasper.core.operators.s2r.syntax.WindowNode;
import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.stream.metadata.SchemaEntry;
import it.polimi.yasper.core.stream.metadata.StreamSchema;
import it.unibz.inf.ontop.exception.DuplicateMappingException;
import it.unibz.inf.ontop.exception.InvalidMappingException;
import it.unibz.inf.ontop.exception.MappingIOException;
import it.polimi.sr.rsp.onsper.engine.Onsper;
import it.polimi.sr.rsp.onsper.engine.OnsperConfiguration;
import it.polimi.sr.rsp.onsper.rspql.InstResponseSysOutFormatter;
import it.polimi.sr.rsp.onsper.rspql.windowing.WindowNodeImpl;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.db.OBDAManager;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.schema.SimpleSchemaEntry;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.query.ContinuousRewritableQueryImpl;
import it.polimi.sr.rsp.onsper.streams.RegisteredVirtualRDFStream;
import it.polimi.sr.rsp.onsper.streams.VirtualRDFStream;
import org.apache.commons.configuration.ConfigurationException;
import org.jooq.lambda.tuple.Tuple2;

import java.net.URL;
import java.sql.Types;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;


public class Main {

    public static void main(String[] args) throws ConfigurationException, DuplicateMappingException, MappingIOException, InvalidMappingException {

        URL resource = Main.class.getResource("/test.properties");
        OnsperConfiguration ec = new OnsperConfiguration(resource.getPath());

        Onsper sr = new Onsper(0, ec);

        VirtualRDFStream stream = getStream1();

        RegisteredVirtualRDFStream register = sr.register(stream);

        ContinuousRewritableQueryImpl q = new ContinuousRewritableQueryImpl("q1", "SELECT ?c ?t WHERE { GRAPH ?g {?c a <http://www.streamreasoning.org/ontologies/2018/9/colors#Color> ;  <http://www.streamreasoning.org/ontologies/2018/9/colors#hasTime> ?t }}");

        WindowNode wn = new WindowNodeImpl(RDFUtils.createIRI("w1"), Duration.ofSeconds(2), Duration.ofSeconds(2), 0);
        q.addNamedWindow("stream1", wn);

        ContinuousQueryExecution cqe = sr.register(q);


        cqe.add(new InstResponseSysOutFormatter("TTL", true));

        //RUNTIME DATA

        Tuple2<Integer, String> tuple = new Tuple2<>(1000, "Red");
        register.put(tuple, 1000);
        register.put(tuple = new Tuple2<>(2001, "Blue"), tuple.v1);
        register.put(tuple = new Tuple2<>(3000, "Yellow"), tuple.v1);
        register.put(tuple = new Tuple2<>(4000, "Yellow"), tuple.v1);
        register.put(tuple = new Tuple2<>(4003, "Green"), tuple.v1);
        register.put(tuple = new Tuple2<>(6001, "Red"), tuple.v1);

    }

    private static VirtualRDFStream getStream1() throws DuplicateMappingException, MappingIOException, InvalidMappingException {
        Set<SchemaEntry> entries = new HashSet<>();
        entries.add(new SimpleSchemaEntry("timestamp", "Integer", 1, Types.INTEGER));
        entries.add(new SimpleSchemaEntry("color", "String", 2, Types.VARCHAR));
        StreamSchema<SchemaEntry> schema = new StreamSchema<SchemaEntry>() {
            @Override
            public Set<SchemaEntry> entrySet() {
                return entries;
            }

            @Override
            public boolean validate(SchemaEntry schemaEntry) {
                return entries.contains(schemaEntry);
            }
        };

        URL resource = Main.class.getResource("/colors.obda");
        URL calcite = Main.class.getResource("/calcite.properties");

        return new VirtualRDFStream("stream1", schema, OBDAManager.obda2R2RMLRDF(calcite.getPath(), resource.getPath()));

    }
}
