package test;

import it.polimi.heaven.core.enums.Reasoning;
import it.polimi.heaven.core.teststand.EventProcessor;
import it.polimi.heaven.core.teststand.rsp.RSPEngine;
import it.polimi.heaven.core.teststand.rsp.data.Response;
import it.polimi.rsp.baselines.enums.OntoLanguage;
import it.polimi.rsp.baselines.jena.GraphBaseline;
import it.polimi.rsp.baselines.jena.JenaEngine;
import it.polimi.rsp.baselines.jena.events.response.SelectResponse;
import it.polimi.rsp.baselines.jena.events.stimuli.GraphStimulus;
import it.polimi.rsp.baselines.jena.query.BaselineQuery;
import org.apache.jena.graph.Graph;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.*;

/**
 * Created by Riccardo on 03/08/16.
 */
public class IncrementalTumblingSimpleQueryTest {

    public static void main(String[] args) throws InterruptedException {

        RSPEngine e = new GraphBaseline(new EventProcessor<Response>() {
            public boolean process(Response response) {
                SelectResponse sr = (SelectResponse) response;
                ResultSetFormatter.out(System.out, sr.getResults());

                return false;
            }

            public boolean setNext(EventProcessor<?> eventProcessor) {
                return false;
            }
        });


        BaselineQuery query = new BaselineQuery();
        String incremental_query = "select irstream * from stream1.win:time_batch( 5 msec )";
        query.setEsper_queries(incremental_query);
        query.setSparql_query("SELECT ?s ?p ?o  WHERE {?s ?p ?o} ORDER BY ?o");
        query.setEsperStreams(new String[]{"stream1"});
        query.setEsperNamedStreams(new String[]{});

        JenaEngine je = (JenaEngine) e;

        je.setReasoning(Reasoning.INCREMENTAL);
        je.setOntology_language(OntoLanguage.SMPL);
        je.registerQuery(query);
        je.startProcessing();


        for (int i = 0; i < 50; i++) {
            System.out.println("Sending...[" + i + "]");
            e.process(new GraphStimulus(i, getGraph(i), "stream1"));
            Thread.sleep(1000);
        }
    }


    private static Graph getGraph(int i) {
        Model m = ModelFactory.createDefaultModel();
        Resource subject = ResourceFactory.createResource("http://somewhere/Event" + i);
        Property predicate = ResourceFactory.createProperty("http://somewhere/id");
        Literal object = ModelFactory.createDefaultModel().createTypedLiteral(new Integer(i));
        Statement stmt = ResourceFactory.createStatement(subject, predicate, object);
        m.add(stmt);
        return m.getGraph();
    }

}