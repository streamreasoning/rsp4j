package simple.test.examples;

import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.quering.querying.ContinuousQuery;
import it.polimi.yasper.core.spe.windowing.operator.CSPARQLTimeWindowOperator;
import it.polimi.yasper.core.spe.windowing.operator.WindowOperator;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.stream.rdf.RDFStream;
import it.polimi.yasper.core.utils.EngineConfiguration;
import it.polimi.yasper.core.utils.QueryConfiguration;
import it.polimi.yasper.core.utils.RDFUtils;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.rdf.api.Graph;
import simple.querying.formatter.ContinuousQueryImpl;
import simple.querying.formatter.InstResponseSysOutFormatter;

import java.net.URL;
import java.time.Duration;

/**
 * Created by Riccardo on 03/08/16.
 */
public class CSPARQLExample {

    static CSPARQLImpl sr;

    public static void main(String[] args) throws ConfigurationException {

        URL resource = CSPARQLExample.class.getResource("/default.properties");
        QueryConfiguration config = new QueryConfiguration(resource.getPath());
        EngineConfiguration ec = EngineConfiguration.loadConfig("/default.properties");

        sr = new CSPARQLImpl(0, ec);

        //STREAM DECLARATION
        WritableRDFStream stream = new WritableRDFStream("stream1");

        Stream painter_reg = sr.register(stream);

        //_____

        ContinuousQuery q = new ContinuousQueryImpl("q1");


        WindowOperator w = new CSPARQLTimeWindowOperator(RDFUtils.createIRI("w1"), Duration.ofSeconds(2).toMillis(), Duration.ofSeconds(2).toMillis(), 0);

        q.addNamedWindow("stream1", w);

        ContinuousQueryExecution cqe = sr.register(q, config);

        cqe.addFormatter(new InstResponseSysOutFormatter("TTL", true));


        //RUNTIME DATA

        Graph graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S1"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O1")));

        stream.put(new WritableRDFStream.Elem(1000, graph));

        graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S2"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O2")));

        stream.put(new WritableRDFStream.Elem(1999, graph));

        graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S3"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O3")));

        stream.put(new WritableRDFStream.Elem(2001, graph));
        graph = RDFUtils.getInstance().createGraph();

        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S4"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O4")));

        stream.put(new WritableRDFStream.Elem(3000, graph));

        graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S5"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O5")));

        stream.put(new WritableRDFStream.Elem(5000, graph));

        graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S6"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O6")));

        stream.put(new WritableRDFStream.Elem(5000, graph));
        stream.put(new WritableRDFStream.Elem(6000, graph));

        graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S7"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O7")));

        stream.put(new WritableRDFStream.Elem(7000, graph));
        //stream.put(new it.polimi.deib.ssp.windowing.WritableRDFStream.Elem(3000, graph));


    }


}
