package test.engine.reasoning.pellet;

import it.polimi.jasper.engine.JenaRSPQLEngineImpl;
import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.jasper.engine.query.formatter.ConstructResponseSysOutFormatter;
import it.polimi.jasper.engine.query.formatter.SelectResponseSysOutFormatter;
import it.polimi.jasper.parser.RSPQLParser;
import it.polimi.yasper.core.enums.Entailment;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecution;
import org.apache.commons.io.FileUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.system.IRIResolver;
import org.parboiled.Parboiled;
import org.parboiled.errors.ParseError;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;
import test.engine.GraphStream;

import java.io.File;
import java.io.IOException;

/**
 * Created by Riccardo on 03/08/16.
 */
public class IncrementalPellet {

    public static void main(String[] args) throws InterruptedException, IOException {

        RSPQuery q = getRspQuery();

        JenaRSPQLEngineImpl sr = new JenaRSPQLEngineImpl(0);
        sr.startProcessing();

        Model tbox = ModelFactory.createDefaultModel().read("/Users/riccardo/_Projects/RSP/RSP-Baselines/src/main/resources/artist.tbox.owl");
        ContinuousQueryExecution cqe = sr.registerQuery(q, tbox, Maintenance.INCREMENTAL, Entailment.PELLET);

        if (q.isSelectType())
<<<<<<< HEAD
            sr.registerObserver(q.getName(), new SelectResponseSysOutFormatter(true)); // attaches a new *RSP-QL query to the SDS
        if (q.isConstructType())
            sr.registerObserver(q.getName(), new ConstructResponseSysOutFormatter(true)); // attaches a new *RSP-QL query to the SDS
=======
            sr.registerObserver(q.getId(), new SelectResponseSysOutFormatter(true)); // attaches a new *RSP-QL query to the SDS
        if (q.isConstructType())
            sr.registerObserver(q.getId(), new ConstructResponseSysOutFormatter(true)); // attaches a new *RSP-QL query to the SDS
>>>>>>> 0d0d3db19324bd0be27b794b12ae18bae86a2475

        (new Thread(new GraphStream("Painter", "http://streamreasoning.org/iminds/massif/stream1", 1))).start();
        //(new Thread(new GraphS2RTestStream(sr, "Writer", "http://streamreasoning.org/iminds/massif/stream2", 1))).start();

    }

    private static RSPQuery getRspQuery() throws IOException {
        String input = getInput();

        RSPQLParser parser = Parboiled.createParser(RSPQLParser.class);

        parser.setResolver(IRIResolver.create());

        ParsingResult<RSPQuery> result = new ReportingParseRunner(parser.Query()).run(input);

        if (result.hasErrors()) {
            for (ParseError arg : result.parseErrors) {
                System.out.println(input.substring(0, arg.getStartIndex()) + "|->" + input.substring(arg.getStartIndex(), arg.getEndIndex()) + "<-|" + input.substring(arg.getEndIndex() + 1, input.length() - 1));
            }
        }
        return result.resultValue;
    }

    public static String getInput() throws IOException {
        File file = new File("/Users/riccardo/_Projects/RSP/RSP-Baselines/src/it.polimi.jasper.test/resources/rspquery.q");
        return FileUtils.readFileToString(file);
    }
}
