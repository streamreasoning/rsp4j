package it.polimi.jasper.engine;

import it.polimi.csparql2.jena.engine.Jasper;
import it.polimi.yasper.core.engine.config.ConfigurationUtils;
import it.polimi.yasper.core.engine.config.EngineConfiguration;
import it.polimi.yasper.core.querying.ContinuousQuery;
import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.sds.SDSConfiguration;
import it.polimi.yasper.core.stream.data.DataStreamImpl;
import it.polimi.yasper.core.stream.data.WebDataStream;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.jena.graph.Graph;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Riccardo on 03/08/16.
 */
public class CSPARQLExample {

    static Jasper sr;

    public static void main(String[] args) throws InterruptedException, IOException, ConfigurationException {

        URL resource = CSPARQLExample.class.getResource("/csparql.properties");
        SDSConfiguration config = new SDSConfiguration(resource.getPath());
        EngineConfiguration ec = EngineConfiguration.loadConfig("/csparql.properties");

        config.setProperty(ConfigurationUtils.TBOX_LOCATION, "https://raw.githubusercontent.com/riccardotommasini/csparql2/master/src/test/resources/artist.tbox.owl?token=ACeO0Zrl_qG-5YPpHh6T_VvZYqXsxFgJks5brMV-wA%3D%3D");

        sr = new Jasper(0, ec);

        GraphStream writer = new GraphStream("Writer", "http://differenthost:12134/stream2", 1);

        DataStreamImpl<Graph> register = sr.register(writer);

        writer.setWritable(register);

        ContinuousQueryExecution cqe = sr.register(getQuery(".rspql"), config);

        ContinuousQuery query = cqe.getContinuousQuery();

        System.out.println(query.toString());

        System.out.println("<<------>>");

//        if (query.isConstructType()) {
//            cqe.add(ResponseFormatterFactory.getConstructResponseSysOutFormatter("JSON-LD", true));
//        } else if (query.isSelectType()) {
//            cqe.add(ResponseFormatterFactory.getSelectResponseSysOutFormatter("TABLE", true)); //or "CSV" or "JSON" or "JSON-LD"
//        }

        WebDataStream outputStream = cqe.outstream();

        outputStream.addConsumer((o, l) -> System.out.println(o));

        //In real application we do not have to start the stream.
        (new Thread(writer)).start();

    }

    public static String getQuery(String suffix) throws IOException {
        URL resource = CSPARQLExample.class.getResource("/q52" + suffix);
        System.out.println(resource.getPath());
        File file = new File(resource.getPath());
        return FileUtils.readFileToString(file);
    }

}
