package test.collector;

import it.polimi.rsp.baselines.jena.events.response.SelectResponse;
import it.polimi.streaming.EventProcessor;
import it.polimi.streaming.Response;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.*;

/**
 * Created by Riccardo on 04/08/16.
 */
public class SelectQueryTestingCollector implements EventProcessor<Response> {

    public boolean process(Response response) {

        SelectResponse sr = (SelectResponse) response;
        ResultSetFormatter.out(System.out, sr.getResults());

        return false;
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

    public void addStatement(Statement s, long app_timestamp) {
        Model m = ModelFactory.createDefaultModel();
        Literal ts = ResourceFactory.createTypedLiteral(app_timestamp + "", XSDDatatype.XSDdateTimeStamp);
        Property dp = ResourceFactory.createProperty("http://www.w3.org/2001/XMLSchema#dateTime");
        Statement ts_stmt = ResourceFactory.createStatement(s.getSubject(), dp, ts);




    }
}

