package org.streamreasoning.rsp4j.esper.engine;

import org.streamreasoning.rsp4j.csparql2.engine.CSPARQLEngine;

import org.streamreasoning.rsp4j.csparql2.engine.JenaContinuousQueryExecution;
import org.streamreasoning.rsp4j.csparql2.sysout.ResponseFormatterFactory;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.jena.graph.Graph;
import org.streamreasoning.rsp4j.api.engine.config.EngineConfiguration;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.sds.SDSConfiguration;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Riccardo on 03/08/16.
 */
public class CSPARQLStaticDataExample {

    static CSPARQLEngine sr;

    public static void main(String[] args) throws InterruptedException, IOException, ConfigurationException {

        URL resource = CSPARQLStaticDataExample.class.getResource("/csparql.properties");
        SDSConfiguration config = new SDSConfiguration(resource.getPath());
        EngineConfiguration ec = EngineConfiguration.loadConfig("/csparql.properties");


        sr = new CSPARQLEngine(0, ec);

        GraphStream writer = new GraphStream("Artist", "http://differenthost:12134/stream2", 1);

        DataStream<Graph> register = sr.register(writer);

        writer.setWritable(register);

        JenaContinuousQueryExecution cqe = (JenaContinuousQueryExecution)sr.register(getQuery(".rspql"), config);

        ContinuousQuery query = cqe.query();

        System.out.println(query.toString());

        System.out.println("<<------>>");

        if (query.isConstructType()) {
            cqe.addQueryFormatter(ResponseFormatterFactory.getConstructResponseSysOutFormatter("JSON-LD", true));
        } else if (query.isSelectType()) {
            cqe.addQueryFormatter(ResponseFormatterFactory.getSelectResponseSysOutFormatter("TABLE", true)); //or "CSV" or "JSON" or "JSON-LD"
        }

        DataStream outputStream = cqe.outstream();

        if (outputStream != null)
            outputStream.addConsumer((o, l) -> System.out.println(o));

        //In real application we do not have to start the stream.
        (new Thread(writer)).start();

    }

    public static String getQuery(String suffix) throws IOException {
        URL resource = CSPARQLStaticDataExample.class.getResource("/q52_static" + suffix);
        System.out.println(resource.getPath());
        File file = new File(resource.getPath());
        return FileUtils.readFileToString(file);
    }

}
