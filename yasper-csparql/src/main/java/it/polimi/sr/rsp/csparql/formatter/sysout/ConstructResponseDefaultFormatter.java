package it.polimi.sr.rsp.csparql.formatter.sysout;

import it.polimi.yasper.core.format.QueryResultFormatter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.ByteArrayOutputStream;
import java.util.Observable;

/**
 * Created by riccardo on 03/07/2017.
 */
@Log4j
public abstract class ConstructResponseDefaultFormatter extends QueryResultFormatter {

    long last_result = -1L;

    public ConstructResponseDefaultFormatter(String format, boolean distinct) {
        super(format, distinct);
    }

    @Override
    public void update(Observable o, Object arg) {
        Graph sr = (Graph) arg;
        this.format(sr);
    }

    public void format(Graph sr) {
        Model modelForGraph = ModelFactory.createModelForGraph(sr);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        modelForGraph.write(outputStream, format);
        log.debug("[" + System.currentTimeMillis() + "] Result at [" + last_result + "]");
        out(new String(outputStream.toByteArray()));
    }

    protected abstract void out(String s);
}
