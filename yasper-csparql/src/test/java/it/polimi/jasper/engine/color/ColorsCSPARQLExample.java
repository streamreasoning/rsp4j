package it.polimi.jasper.engine.color;

import it.polimi.sr.rsp.csparql.engine.CSPARQLEngine;
import it.polimi.sr.rsp.csparql.formatter.sysout.ResponseFormatterFactory;
import it.polimi.yasper.core.engine.config.EngineConfiguration;
import it.polimi.yasper.core.querying.ContinuousQuery;
import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.sds.SDSConfiguration;
import it.polimi.yasper.core.stream.data.DataStreamImpl;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.jena.graph.Graph;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Riccardo on 03/08/16.
 */
public class ColorsCSPARQLExample {

    static CSPARQLEngine sr;

    public static void main(String[] args) throws InterruptedException, IOException, ConfigurationException {

        URL resource = ColorsCSPARQLExample.class.getResource("/csparqlColors.properties");
        SDSConfiguration config = new SDSConfiguration(resource.getPath());
        EngineConfiguration ec = EngineConfiguration.loadConfig("/csparqlColors.properties");

        sr = new CSPARQLEngine(0, ec);

        ColorsGraphStream red = new ColorsGraphStream("Red", "http://localhost:1255/streams/red");
        ColorsGraphStream green = new ColorsGraphStream("Green", "http://localhost:1255/streams/green");
        ColorsGraphStream blue = new ColorsGraphStream("Blue", "http://localhost:1255/streams/blue");

        //FAKE STREAM: we insert y1 as from r1 and g1
        //qYellowStream must be fixed to build this stream as JOIN of red and green streams
        //   ColorsGraphStream yellow = new ColorsGraphStream("Yellow", "http://localhost:1255/streams/yellow");

        DataStreamImpl<Graph> registerRed = sr.register(red);
        DataStreamImpl<Graph> registerGreen = sr.register(green);
        DataStreamImpl<Graph> registerBlue = sr.register(blue);
        //RegisteredEPLStream registerYellow = sr.register(yellow);

        red.setWritable(registerRed);
        green.setWritable(registerGreen);
        blue.setWritable(registerBlue);
        //yellow.setWritable(registerYellow);

        //ContinuousQueryExecution cqe = sr.register(getQuery("OneStream", ".rspql"), config);
       // ContinuousQueryExecution cqe = sr.register(getQuery("TwoStreams", ".rspql"), config);
        //ContinuousQueryExecution cqe = sr.register(getQuery("YellowStream", ".rspql"), config);
        ContinuousQueryExecution cqe = sr.register(getQuery("ReasoningSubClass", ".rspql"), config);
        //ContinuousQueryExecution cqe = sr.register(getQuery("ReasoningDomainRange", ".rspql"), config);

        ContinuousQuery query = cqe.getContinuousQuery();

        //   System.out.println(qOneStream.toString());
        //   System.out.println("<<------>>");
        //   System.out.println(qTwoStreams.toString());
//        System.out.println("<<------>>");
        System.out.println(query.toString());
        System.out.println("<<------>>");
        //  System.out.println(qReasoningSubClass.toString());
        //  System.out.println("<<------>>");
        //  System.out.println(qReasoningDomainRange.toString());

        //In real application we do not have to start the stream.
        (new Thread(red)).start();
        (new Thread(green)).start();
        //  (new Thread(blue)).start();
        //  (new Thread(yellow)).start();

        if (query.isConstructType()) {
            cqe.add(ResponseFormatterFactory.getConstructResponseSysOutFormatter("JSON-LD", true));
        } else if (query.isSelectType()) {
            cqe.add(ResponseFormatterFactory.getSelectResponseSysOutFormatter("CSV", true));
        }


    }

    @SuppressWarnings("deprecation")
    public static String getQuery(String nameQuery, String suffix) throws IOException {
        URL resource = ColorsCSPARQLExample.class.getResource("/q" + nameQuery + suffix);
        System.out.println(resource.getPath());
        File file = new File(resource.getPath());
        return FileUtils.readFileToString(file);
    }

}
