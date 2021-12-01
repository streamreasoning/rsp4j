package org.streamreasoning.rsp4j.csparql2.sysout;

import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Graph;
import org.apache.jena.sparql.algebra.Table;
import org.apache.jena.sparql.engine.binding.Binding;
import org.streamreasoning.rsp4j.api.format.QueryResultFormatter;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by riccardo on 03/07/2017.
 */
@Log4j
public class GenericResponseSysOutFormatter extends QueryResultFormatter implements Observer {

    private final SelectSysOutDefaultFormatter sf;
    private final ConstructSysOutDefaultFormatter cf;
    long last_result = -1L;

    public GenericResponseSysOutFormatter(String format, boolean distinct) {
        super(format, distinct);

        this.cf = new ConstructSysOutDefaultFormatter(format, distinct);
        this.sf = new SelectSysOutDefaultFormatter(format, distinct);

    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Binding) {
            sf.format((Binding) arg);
        } else if (arg instanceof Graph) {
            cf.format((Graph) arg);
        } else if (arg instanceof Table) {
            sf.format((Table) arg);
        }
    }

    @Override
    public void notify(Object arg, long ts) {
        throw new UnsupportedOperationException();

    }
}
