package engine.reasoning.rhod.multistream;

import it.polimi.jasper.engine.JenaRSPQLEngineImpl;
import it.polimi.jasper.engine.query.formatter.ResponseFormatterFactory;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.utils.QueryConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import engine.GraphStream;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Riccardo on 03/08/16.
 */
public class TestConfig {

    public static void main(String[] args) throws InterruptedException, IOException, ConfigurationException {

        JenaRSPQLEngineImpl sr = new JenaRSPQLEngineImpl(0);
        URL resource = TestConfig.class.getResource("/jasper.properties");
        QueryConfiguration config = new QueryConfiguration(resource.getPath());

        GraphStream painter = new GraphStream("Painter", "http://streamreasoning.org/iminds/massif/stream1", 1);
        GraphStream writer = new GraphStream("Writer", "http://streamreasoning.org/iminds/massif/stream2", 5);

        sr.registerStream(painter);
        sr.registerStream(writer);

        ContinuousQueryExecution ceq = sr.registerQuery(getInput(), config);

        String queryId = ceq.getQueryID();

        sr.registerObserver(queryId, ResponseFormatterFactory.getGenericResponseSysOutFormatter(true)); // attaches a new *RSP-QL query to the SDS

        sr.startProcessing();

        //In real application we do not have to start the stream.
        (new Thread(painter)).start();
        (new Thread(writer)).start();

        //Thread.sleep(30000);

        //sr.unregisterQuery(queryId);

    }

    public static String getInput() throws IOException {
        File file = new File("/Users/riccardo/_Projects/RSP/yasper/src/test/resources/q52.rspql");
        return FileUtils.readFileToString(file);
    }
}
