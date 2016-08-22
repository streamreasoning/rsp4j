package test.multistream;

import it.polimi.rsp.baselines.enums.OntoLanguage;
import it.polimi.rsp.baselines.enums.Reasoning;
import it.polimi.rsp.baselines.esper.RSPEsperEngine;
import it.polimi.rsp.baselines.jena.GraphBaseline;
import it.polimi.rsp.baselines.jena.JenaEngine;
import it.polimi.rsp.baselines.jena.events.response.SelectResponse;
import it.polimi.rsp.baselines.jena.query.BaselineQuery;
import it.polimi.rsp.baselines.jena.query.JenaCQueryExecution;
import it.polimi.streaming.EventProcessor;
import it.polimi.streaming.Response;
import org.apache.jena.query.ResultSetFormatter;
import test.Stream;

/**
 * Created by Riccardo on 03/08/16.
 */
public class NaiveBaselineTest {

    public static void main(String[] args) throws InterruptedException {

        RSPEsperEngine e = new GraphBaseline(new EventProcessor<Response>() {
            public boolean process(Response response) {
                System.out.println("[" + System.currentTimeMillis() + "] Result");
                SelectResponse sr = (SelectResponse) response;
                ResultSetFormatter.out(System.out, sr.getResults());

                return true;
            }

            public boolean setNext(EventProcessor<?> eventProcessor) {
                return false;
            }

            @Override
            public void startProcessing() {

            }

            @Override
            public void stopProcessing() {

            }
        }, null);


        BaselineQuery query = new BaselineQuery();
        query.setId("Q1");
        query.setEPLStreamQueries(new String[]{});
        query.setEPLNamedStreamQueries(new String[]{
                "select * from stream2.win:time(3 sec) output snapshot every 1 sec",
                "select * from stream3.win:time(3 sec) output snapshot every 3 sec"
        });


        query.setSparql_query("" +
                "PREFIX : <http://streamreasoning.com/> " +
                "SELECT *  " +
                "FROM NAMED :win2 " +  //parsed from FROM NAMED WINDOW :win1 ON STREAM
                "FROM NAMED :win3 " +  //parsed from FROM NAMED WINDOW :win1 ON STREAM
                "WHERE { " +
                "GRAPH ?g { ?s ?p ?o }  " +
                "}");
        //"WHERE { ?s ?p ?o }  ");
        query.setEsperStreams(new String[]{});
        query.setEsperNamedStreams(new String[][]{new String[]{":win2", "stream2"}, new String[]{":win3", "stream3"}});

        //TODO UNNAMED SREAMS (_, window, stream_uri), esper writes on "default" graph
        //TODO NAMED SREAMS (window_uri, window, stream_uri), esper writes on iri graph
        //TODO FROM NAMED WINDOW window_uri [a,b] ON STREAM stream_uri -> FROM NAMED window_uri + Select * from stream_uri.win:time([a]) output every b
        //TODO FROM WINDOW [a,b] ON STREAM stream_uri -> Select * from stream_uri.win:time([a]) output every b
        //TODO multiple output clauses works that I register the same listener to multiple epl statements


        //TODO I noticed tha different queries issues different windows.
        // Two solution are possible to allow the user to define different windows for the default stream:
        // Solution 1 consists into actually creating a named graph per stream and retrieve all of them for query
        // answering in a transparent way for the user
        // Solution 2 consists into creating a query network for the default one.
        // Solution 3 is more experimental and consists in the usage of named windows
        JenaEngine je = (JenaEngine) e;

        je.setReasoning(Reasoning.NAIVE);
        je.setOntology_language(OntoLanguage.SMPL);
        je.startProcessing();


        JenaCQueryExecution cqe = (JenaCQueryExecution) je.registerQuery(query);

        (new Thread(new Stream(je, "A", "stream2", 1))).start();
        (new Thread(new Stream(je, "B", "stream3", 1))).start();


    }
}
