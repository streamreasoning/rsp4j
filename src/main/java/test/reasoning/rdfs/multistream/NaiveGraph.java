package test.reasoning.rdfs.multistream;

import it.polimi.rsp.baselines.enums.Entailment;
import it.polimi.rsp.baselines.enums.Maintenance;
import it.polimi.rsp.baselines.rsp.RSPEVA;
import it.polimi.rsp.baselines.rsp.stream.RSPEsperEngine;
import it.polimi.rsp.baselines.rsp.RSPQLEngine;
import it.polimi.rsp.baselines.rsp.query.execution.ContinuousQueryExecution;
import it.polimi.rsp.baselines.rsp.query.observer.ConstructResponseSysOutObserver;
import it.polimi.rsp.baselines.rsp.query.observer.SelectResponseSysOutObserver;
import it.polimi.sr.rsp.RSPQLParser;
import it.polimi.sr.rsp.RSPQuery;
import org.apache.commons.io.FileUtils;
import org.apache.jena.riot.system.IRIResolver;
import org.parboiled.Parboiled;
import org.parboiled.errors.ParseError;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;
import test.GraphStream;

import java.io.File;
import java.io.IOException;

/**
 * Created by Riccardo on 03/08/16.
 */
public class NaiveGraph {

    public static void main(String[] args) throws InterruptedException, IOException {


        RSPEsperEngine e = new RSPEVA();
        RSPQuery q = getRspQuery();
        // RSPQuery q1 = getRspQuery();

        RSPQLEngine sr = (RSPQLEngine) e;
        sr.startProcessing();
        ContinuousQueryExecution cqe = sr.registerQuery(q, Maintenance.NAIVE, Entailment.RDFS);

        // SDS sds = sr.getSDS(q);
        // sr.registerQuery(q1, sds);

        //executes a query creating the SDS (if it does not exists yet)
        // ContinuousQueryExecutionImpl cqe = je.registerQuery(q, SDS); //executes the query on the given SDS (if compatible)

        if (q.isSelectType())
            sr.registerObserver(cqe, new SelectResponseSysOutObserver(true)); // attaches a new *RSP-QL query to the SDS
        if (q.isConstructType())
            sr.registerObserver(cqe, new ConstructResponseSysOutObserver(true)); // attaches a new *RSP-QL query to the SDS

        //      (new Thread(new GraphStream(je, "D", "http://streamreasoning.org/iminds/massif/stream0", 1))).start();
        (new Thread(new GraphStream(sr, "A", "http://streamreasoning.org/iminds/massif/stream1", 1))).start();
        (new Thread(new GraphStream(sr, "B", "http://streamreasoning.org/iminds/massif/stream2", 1))).start();


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
        File file = new File("/Users/riccardo/_Projects/RSP/RSP-Baselines/src/test/resources/rspquery.q");
        return FileUtils.readFileToString(file);
    }
}
