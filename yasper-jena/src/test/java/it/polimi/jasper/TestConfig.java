package it.polimi.jasper;

import it.polimi.jasper.esper.engine.JenaRSPQLEngineImpl;
import it.polimi.jasper.engine.query.formatter.ResponseFormatterFactory;
import it.polimi.yasper.core.rspql.ContinuousQuery;
import it.polimi.yasper.core.rspql.ContinuousQueryExecution;
import it.polimi.yasper.core.spe.stream.rdf.RDFStream;
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

        String query = getQuery();
        ContinuousQuery q = sr.parseQuery(query);
        ContinuousQueryExecution ceq = sr.register(q, config);
        ContinuousQuery cq = ceq.getContinuousQuery();

        sr.register(cq, ResponseFormatterFactory.getGenericResponseSysOutFormatter(true)); // attaches a new *RSP-QL query to the SDS

        //In real application we do not have to start the stream.
        (new Thread(painter)).start();
        (new Thread(writer)).start();


    }

    public static String getQuery() throws IOException {
        File file = new File("/Users/riccardo/_Projects/RSP/yasper/src/test/resources/q52.rspql");
        return FileUtils.readFileToString(file);
    }

    public static JenaRSPQLEngineImpl getEngine() {
        return sr;
    }
}
