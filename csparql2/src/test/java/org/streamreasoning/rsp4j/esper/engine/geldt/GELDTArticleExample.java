package org.streamreasoning.rsp4j.esper.engine.geldt;

import org.streamreasoning.rsp4j.csparql2.engine.CSPARQLEngine;
import org.streamreasoning.rsp4j.csparql2.engine.JenaContinuousQueryExecution;
import org.streamreasoning.rsp4j.csparql2.sysout.ResponseFormatterFactory;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.jena.graph.Graph;
import org.streamreasoning.rsp4j.api.engine.config.EngineConfiguration;
import org.streamreasoning.rsp4j.api.sds.SDSConfiguration;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Riccardo on 03/08/16.
 */
public class GELDTArticleExample extends GELDTExample {


    public static void main(String[] args) throws InterruptedException, IOException, ConfigurationException {

        URL resource = GELDTArticleExample.class.getResource("/geldt/csparqlGELDT.properties");
        SDSConfiguration config = new SDSConfiguration(resource.getPath());
        EngineConfiguration ec = EngineConfiguration.loadConfig("/geldt/csparqlGELDT.properties");

        sr = new CSPARQLEngine(0, ec);

        type = "article";
        GELDTGraphStream dt = new GELDTGraphStream(3, "Donald Trump", type);

        System.out.println(dt);

        DataStream<Graph> dtr = sr.register(dt);

        dt.setWritable(dtr);

        JenaContinuousQueryExecution cqe = (JenaContinuousQueryExecution)sr.register(getQuery("OneStream", ".rspql", type), config);

        new Thread(dt).start();

        if (cqe.query().isConstructType()) {
            cqe.addQueryFormatter(ResponseFormatterFactory.getConstructResponseSysOutFormatter("JSON-LD", true));
        } else if (cqe.query().isSelectType()) {
            cqe.addQueryFormatter(ResponseFormatterFactory.getSelectResponseSysOutFormatter("CSV", true));
        }
    }

}
