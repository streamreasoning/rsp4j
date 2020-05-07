package ee.ut.dsg.gsp.seraph.poc;


import ee.ut.dsg.gsp.seraph.*;
import it.polimi.jasper.operators.r2s.JRStream;
import it.polimi.jasper.operators.s2r.epl.RuntimeManager;
import it.polimi.jasper.sds.SDSImpl;
import it.polimi.jasper.secret.EsperTime;
import it.polimi.jasper.streams.EPLStream;
import it.polimi.yasper.core.engine.config.EngineConfiguration;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.enums.Tick;
import it.polimi.yasper.core.format.QueryResultFormatter;
import it.polimi.yasper.core.operators.s2r.syntax.WindowNode;
import it.polimi.yasper.core.sds.SDSConfiguration;
import it.polimi.yasper.core.sds.timevarying.TimeVarying;
import it.polimi.yasper.core.secret.report.ReportImpl;
import it.polimi.yasper.core.stream.data.DataStreamImpl;
import neo4j.*;
import org.apache.commons.configuration.ConfigurationException;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestDatabaseManagementServiceBuilder;

import java.util.Map;
import java.util.Observable;

import static org.neo4j.configuration.GraphDatabaseSettings.DEFAULT_DATABASE_NAME;

public class KaypherExample {

    static Kaypher sr;

    public static EngineConfiguration aDefault;


    public static void main(String[] args) {

        TestDatabaseManagementServiceBuilder builder = new TestDatabaseManagementServiceBuilder();
        GraphDatabaseService db = builder.impermanent().build().database(DEFAULT_DATABASE_NAME);
        String path = KaypherExample.class.getClassLoader().getResource("kaypher.properties").getPath();

        try {
            EngineConfiguration en = new EngineConfiguration(path);
            sr = new Kaypher(0, en);
            SDSConfiguration config = new SDSConfiguration(path);


            Seraph q = new Seraph("MATCH (n:Person)-[p]->(n1:Person) RETURN n, keys(n)");

            SDSImpl<PGraph> sds = new SDSImpl<PGraph>();

            WindowNode window = new TestWindowNode();
            EsperPGWindowOperator wo = new EsperPGWindowOperator(
                    Tick.TIME_DRIVEN,
                    new ReportImpl(),
                    true,
                    Maintenance.NAIVE,
                    new EsperTime(RuntimeManager.getEPRuntime(), 0),
                    window,
                    sds,
                    db);

            PGraphStream writer = new PGraphStream("stream1", null);

            Neo4jContinuousQueryExecution cqe = new Neo4jContinuousQueryExecution(
                    new DataStreamImpl<>("stream1"),
                    q,
                    sds,
                    new R2ROperatorCypher(q, sds, "SocialNetwork", db),
                    new JRStream<Map<String, Object>>(),
                    wo);

            EPLStream<PGraph> register = (EPLStream<PGraph>) sr.register(writer);

            TimeVarying<PGraph> apply = wo.apply(register);

            sds.add(apply);

            writer.setWritable(register);

            sds.addObserver(cqe);

            cqe.add(new QueryResultFormatter("Neo4j", true) {
                @Override
                public void update(Observable o, Object arg) {
                    System.out.println(arg);
                }
            });

            //In real application we do not have to start the stream.
            (new Thread(writer)).start();

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
