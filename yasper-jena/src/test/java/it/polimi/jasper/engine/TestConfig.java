package it.polimi.jasper.engine;

import it.polimi.jasper.engine.spe.JenaRSPQLEngineImpl;
import it.polimi.jasper.engine.querying.formatter.ResponseFormatterFactory;
import it.polimi.jasper.engine.streaming.GraphStream;
import it.polimi.yasper.core.quering.ContinuousQuery;
import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.stream.rdf.RDFStream;
import it.polimi.yasper.core.utils.EngineConfiguration;
import it.polimi.yasper.core.utils.QueryConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Riccardo on 03/08/16.
 */
public class TestConfig {

    static JenaRSPQLEngineImpl sr;

    public static void main(String[] args) throws InterruptedException, IOException, ConfigurationException {

        URL resource = TestConfig.class.getResource("/jasper.properties");
        QueryConfiguration config = new QueryConfiguration(resource.getPath());
        EngineConfiguration ec = EngineConfiguration.loadConfig("/jasper.properties");

        sr = new JenaRSPQLEngineImpl(0, ec);

        GraphStream painter = new GraphStream("Painter", "http://streamreasoning.org/jasper/streams/stream1", 1);
        GraphStream writer = new GraphStream("Writer", "http://streamreasoning.org/jasper/streams/stream2", 5);

        painter.setRSPEngine(sr);
        writer.setRSPEngine(sr);

        RDFStream painter_reg = sr.register(painter);
        RDFStream writer_reg = sr.register(writer);

        ContinuousQuery q = sr.parseQuery(getQuery("yql"));
        ContinuousQuery q2 = sr.parse(getQuery("rspql"));

        System.out.println(q.toString());
        System.out.println("<<------>>");
        System.out.println(q2.toString());

        ContinuousQueryExecution ceq = sr.register(q2, config);
        ContinuousQuery cq = ceq.getContinuousQuery();

        sr.register(cq, ResponseFormatterFactory.getGenericResponseSysOutFormatter(true)); // attaches a new *RSP-QL query to the SDS

        //In real application we do not have to start the stream.
        (new Thread(painter)).start();
        (new Thread(writer)).start();


    }

    public static String getQuery(String suffix) throws IOException {
        File file = new File("/Users/riccardo/_Projects/RSP/yasper/src/test/resources/q52."+suffix);
        return FileUtils.readFileToString(file);
    }

    public static JenaRSPQLEngineImpl getEngine() {
        return sr;
    }
}
