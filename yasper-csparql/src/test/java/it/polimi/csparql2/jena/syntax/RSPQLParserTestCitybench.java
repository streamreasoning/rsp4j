package it.polimi.csparql2.jena.syntax;

import org.apache.commons.io.FileUtils;
import org.apache.jena.query.QueryParseException;
import org.apache.jena.sparql.core.QueryCompare;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Riccardo on 09/08/16.
 */
@RunWith(Parameterized.class)
public class RSPQLParserTestCitybench {
    private static final String pathname = "/Users/rictomm/_Projects/Semantic/StreamReasoning/RSP/yasper/src/test/resources/queries/citybench/rspql/";
    static String input;
    static org.apache.jena.query.Query toCompare;
    private static boolean res;
    private static String f;

    public RSPQLParserTestCitybench(String f, boolean res) {
        this.res = res;
        this.f = f;
    }

    @Parameters
    public static Collection<Object[]> data() throws IOException {
        List<Object[]> obj = new ArrayList<>();
        File resourcesDirectory = new File(pathname);

        Arrays.stream(resourcesDirectory.list())
                .filter(f -> f.contains(".rspql"))
                .forEach(f -> obj.add(new Object[]{pathname + f, true}));


        return obj;
    }

    public static void process() throws URISyntaxException, IOException {
        System.out.println("<=====FILE=====>\n");
        System.out.println(f);
        input = FileUtils.readFileToString(new File(f));
        System.out.println("<=====INPUT=====>\n");
        System.out.println(input);
        System.out.println("<=====RSPQL=====>\n");

        RSPQLJenaQuery rspql = QueryFactory.parse("http://example.org/", input);
        System.err.println(rspql.toString());

        System.out.println("<=====JENA=====>\n");
        toCompare = org.apache.jena.query.QueryFactory.create(rspql.getSPARQL());
        System.out.println(toCompare.toString());


        QueryCompare.PrintMessages = true;
        assertEquals(toCompare.toString(), rspql.toString());


    }

    @Before
    public void load() throws URISyntaxException, IOException {


    }

    @Test
    public void test() {
        try {
            (new RSPQLParserTestCitybench(f, res)).process();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (QueryParseException epe) {
            epe.printStackTrace();
        }
    }


}

