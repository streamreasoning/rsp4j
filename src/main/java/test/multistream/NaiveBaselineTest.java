package test.multistream;

import it.polimi.heaven.core.enums.Reasoning;
import it.polimi.heaven.core.teststand.EventProcessor;
import it.polimi.heaven.core.teststand.rsp.RSPEngine;
import it.polimi.heaven.core.teststand.rsp.data.Response;
import it.polimi.rsp.baselines.enums.OntoLanguage;
import it.polimi.rsp.baselines.jena.GraphBaseline;
import it.polimi.rsp.baselines.jena.JenaEngine;
import it.polimi.rsp.baselines.jena.events.response.SelectResponse;
import it.polimi.rsp.baselines.jena.query.BaselineQuery;
import it.polimi.rsp.baselines.jena.query.JenaCQueryExecution;
import org.apache.jena.query.ResultSetFormatter;
import test.Stream;

/**
 * Created by Riccardo on 03/08/16.
 */
public class NaiveBaselineTest {

    public static void main(String[] args) throws InterruptedException {

        RSPEngine e = new GraphBaseline(new EventProcessor<Response>() {
            public boolean process(Response response) {
                System.out.println("Process");
                SelectResponse sr = (SelectResponse) response;
                ResultSetFormatter.out(System.out, sr.getResults());

                return true;
            }

            public boolean setNext(EventProcessor<?> eventProcessor) {
                return false;
            }
        }, null);


        BaselineQuery query = new BaselineQuery();
        query.setEsper_queries("select * from " +
                "srcomstream0.win:time(3 sec), " +
                "srcomstream3.win:time(3 sec), " +
                "srcomstream2.win:time(3 sec), " +
                "srcomstream1.win:time(3 sec) " +
                "output snapshot every 1 sec ");
        query.setSparql_query("" +
                "PREFIX : <srcom> " +
                "SELECT ?s ?p ?o  " +
                "FROM  :stream0 " +
                "FROM  :stream3 " +
                "FROM NAMED :stream1 " +
                "FROM NAMED :stream2 " +
                "WHERE { " +
                "{?s ?p ?o } UNION {" +
                "   GRAPH ?g { ?s ?p ?o } } " +
                "}");
        //"WHERE { ?s ?p ?o }  ");
        query.setEsperNamedStreams(new String[]{"srcomstream1", "srcomstream2"});
        query.setEsperStreams(new String[]{"srcomstream0","srcomstream3"});
        //TODO streams and named streams

        JenaEngine je = (JenaEngine) e;

        je.setReasoning(Reasoning.NAIVE);
        je.setOntology_language(OntoLanguage.SMPL);
        je.startProcessing();

        JenaCQueryExecution cqe = (JenaCQueryExecution) je.registerQuery(query);

        (new Thread(new Stream(je, "JohnathanDoe Named", "srcomstream1"))).start();
        (new Thread(new Stream(je, "JanineDoe ", "srcomstream0"))).start();
        (new Thread(new Stream(je, "JanineDoe 3", "srcomstream3"))).start();
        (new Thread(new Stream(je, "JanineDoe2 Named ", "srcomstream2"))).start();


    }
}
