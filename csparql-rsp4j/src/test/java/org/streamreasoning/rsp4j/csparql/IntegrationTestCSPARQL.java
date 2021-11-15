package org.streamreasoning.rsp4j.csparql;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.DataStreamImpl;
import org.streamreasoning.rsp4j.io.sources.FileSource;
import org.streamreasoning.rsp4j.io.utils.RDFBase;
import org.streamreasoning.rsp4j.io.utils.parsing.JenaRDFParsingStrategy;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;

public class IntegrationTestCSPARQL {

    public static void main(String[] args){
        String query = "REGISTER QUERY WhoLikesWhat AS "
                + "PREFIX ex: <http://myexample.org/> "
                //+ "CONSTRUCT {?s ex:test ?o} "
                + "Select * "
                + "FROM STREAM </tmp/stream.log> [RANGE 1s STEP 1s] "
                + "WHERE { ?s ?p ?o }";
        CSPARQLEngineRSP4J csparql = new CSPARQLEngineRSP4J();

        JenaRDFParsingStrategy parsingStrategy = new JenaRDFParsingStrategy(RDFBase.TTL);

        String filePath = "/tmp/stream.log";
        FileSource fileSource = new FileSource(filePath, 100, parsingStrategy);
        csparql.register(fileSource);

        DataStream outputStream = new DataStreamImpl<>("http://out/stream");
        csparql.setSelectOutput(outputStream);

        ContinuousQuery<Graph, Graph, Binding, Binding> cq = csparql.parseCSPARQLSelect(query);
        ContinuousQueryExecution<Graph, Graph, Binding, Binding> cqe = csparql.parseSelect(cq);

        outputStream.addConsumer((el,ts)->System.out.println(el + " @ " + ts));

        fileSource.stream();
    }
}
