import it.polimi.jasper.parser.SPARQLParser;
import it.polimi.jasper.parser.SPARQLQuery;
import org.apache.commons.io.FileUtils;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QueryParseException;
import org.apache.jena.riot.system.IRIResolver;
import org.apache.jena.sparql.core.QueryCompare;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.parboiled.Parboiled;
import org.parboiled.errors.ParseError;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by Riccardo on 09/08/16.
 */
@RunWith(Parameterized.class)
public class Sparql11QueryTest {

    private static final String folder = "/yasper-jena/src/test/resources/parser/";
    static String input;
    static org.apache.jena.query.Query toCompare;
    private static boolean res;
    private static String f;

    public Sparql11QueryTest(String f, boolean res) {
        this.res = res;
        this.f = f;
    }

    @Parameters
    public static Collection<Object[]> data() throws IOException {
        List<Object[]> obj = new ArrayList<>();
        File resourcesDirectory = new File("src/test/resources/parser/");

        Arrays.stream(resourcesDirectory.list()).map((resourcesDirectory.getAbsolutePath() + "/")::concat).map(File::new).sorted()
                .flatMap(folder -> Arrays.stream(folder.list())
                        .map(d -> folder.getAbsolutePath() + "/" + d)).sorted()
                .filter(f -> !f.contains(".arq"))
                .filter(f -> !f.contains("false"))
                .filter(f -> !f.contains("bad"))
                .filter(f -> !f.contains(".sh"))
                .forEach(f -> obj.add(new Object[]{f, true}));


        return obj;
    }

    public static void process() throws URISyntaxException, IOException {
        System.out.println("<=====FILE=====>");
        System.out.println(f);
        input = FileUtils.readFileToString(new File(f));
        System.out.println("<=====INPUT=====>");
        System.out.println(input);
        System.out.println("<=====JENA=====>");
        toCompare = QueryFactory.create(input);
        System.out.println(toCompare.toString());

        SPARQLParser parser = Parboiled.createParser(SPARQLParser.class);
        parser.setResolver(IRIResolver.create());
        ReportingParseRunner reportingParseRunner = new ReportingParseRunner(parser.Query());
        ParsingResult<SPARQLQuery> result = reportingParseRunner.run(input);
        if (result.hasErrors()) {
            for (ParseError arg : result.parseErrors) {
                System.out.println(input.substring(0, arg.getStartIndex()) + "|->" + input.substring(arg.getStartIndex(), arg.getEndIndex()) + "<-|" + input.substring(arg.getEndIndex() + 1, input.length() - 1));
            }
        }
        org.apache.jena.query.Query q = result.parseTreeRoot.getChildren().get(0).getValue().getQ();
        System.out.println("<=====ME=====>");
        System.out.println(q.toString());
        QueryCompare.PrintMessages = true;
        assertEquals(res, QueryCompare.equals(toCompare, q));
    }

    @Before
    public void load() throws URISyntaxException, IOException {


    }

    @Test
    public void test() {
        try {
            (new Sparql11QueryTest(f, res)).process();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (QueryParseException epe) {
            epe.printStackTrace();
        }
    }


}

