package it.polimi.sr.rsp.csparql.sysout;

import com.github.jsonldjava.core.JsonLdOptions;
import it.polimi.jasper.querying.results.SolutionMappingImpl;
import it.polimi.yasper.core.format.QueryResultFormatter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.atlas.io.IndentedWriter;
import org.apache.jena.graph.Node;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.query.ResultSetRewindable;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.JsonLDWriteContext;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.riot.system.PrefixMap;
import org.apache.jena.riot.system.PrefixMapStd;
import org.apache.jena.riot.writer.JsonLDWriter;
import org.apache.jena.sparql.algebra.Table;
import org.apache.jena.sparql.core.DatasetGraphFactory;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.engine.Plan;
import org.apache.jena.sparql.engine.QueryIterator;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.resultset.RDFOutput;
import org.apache.jena.sparql.serializer.SerializationContext;
import org.apache.jena.sparql.util.FmtUtils;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.Observable;

/**
 * Created by riccardo on 03/07/2017.
 */
@Log4j
public abstract class SelectResponseDefaultFormatter extends QueryResultFormatter {

    private JsonLDWriter jsonLDWriter = new JsonLDWriter(RDFFormat.JSONLD_FLATTEN_PRETTY);
    private long last_result = -1L;
    private PrefixMap pm = new PrefixMapStd();
    private JsonLDWriteContext context = new JsonLDWriteContext();
    private JsonLdOptions options = new JsonLdOptions();


    public SelectResponseDefaultFormatter(String format, boolean distinct) {
        super(format, distinct);
        options.setPruneBlankNodeIdentifiers(true);
        context.setOptions(options);
    }

    @Override
    public void update(Observable o, Object arg) {
        Table sr = (Table) arg;
        this.format(sr);
    }

    public void format(Table sr) {
        output(sr, IndentedWriter.stdout, null);
    }

    public void format(Binding sr) {
        sr.vars().forEachRemaining(var -> {
            System.out.println(var.getVarName() + " -> " + sr.get(var).toString());
        });
    }

    public static void output(Table table, IndentedWriter out, SerializationContext sCxt) {
        if ( sCxt != null ) {} // Prefix. But then qnames are wrong.
        out.print("(table") ;
        out.incIndent() ;
        QueryIterator qIter = table.iterator(null) ;
        for (; qIter.hasNext();) {
            out.println() ;
            Binding binding = qIter.nextBinding() ;
            output(binding, out, sCxt) ;
        }
        out.decIndent() ;

        out.print(")\n") ;
    }

    private static void output(Binding binding, IndentedWriter out, SerializationContext sCxt) {
        out.print("(row") ;
        for (Iterator<Var> iter = binding.vars(); iter.hasNext();) {
            Var v = iter.next() ;
            Node n = binding.get(v) ;
            out.print(" ") ;
            out.print(Plan.startMarker2) ;
            out.print(FmtUtils.stringForNode(v)) ;
            out.print(" ") ;
            out.print(FmtUtils.stringForNode(n)) ;
            out.print(Plan.finishMarker2) ;
        }
        out.print(")\n") ;
    }

    public void format(SolutionMappingImpl sr) {
        long cep_timestamp = sr.getCep_timestamp();
        if (cep_timestamp != last_result && distinct) {
            last_result = cep_timestamp;
            log.info("[" + System.currentTimeMillis() + "] Result at [" + last_result + "]");

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ResultSetRewindable results = null;

            switch (format) {
                case "CSV":
                    ResultSetFormatter.outputAsCSV(outputStream, results);
                    break;
                case "JSON-LD":
                    Model model = RDFOutput.encodeAsModel(results);
                    model.getNsPrefixMap().forEach(pm::add);
                    jsonLDWriter.write(outputStream, DatasetGraphFactory.create(model.getGraph()), pm, "", context);
                    break;
                case "JSON":
                    ResultSetFormatter.outputAsJSON(outputStream, results);
                    break;
                case "TABLE":
                    ResultSetFormatter.out(outputStream, results);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid Format: " + format);
            }

            out(new String(outputStream.toByteArray()));
        }
    }

    protected abstract void out(String s);

}
