package it.polimi.jasper.sds;

import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.sds.timevarying.TimeVarying;
import it.polimi.yasper.core.secret.time.Time;
import it.polimi.yasper.core.secret.time.TimeFactory;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.IRI;

import java.util.*;

/**
 * Created by riccardo on 01/07/2017.
 */
@Log4j
public class SDSImpl<T> extends Observable implements SDS<T>, Observer {

    private boolean partialWindowsEnabled = false;
    private Time time = TimeFactory.getInstance();
    private List<TimeVarying<T>> tvgs = new ArrayList<>();

    public Collection<TimeVarying<T>> asTimeVaryingEs() {
        return tvgs;
    }

    @Override
    public void add(IRI iri, TimeVarying<T> tvg) {
        tvgs.add(tvg);
    }

    @Override
    public void add(TimeVarying<T> tvg) {
        tvgs.add(tvg);
    }

    @Override
    public void materialize(long ts) {
        tvgs.forEach(g -> g.materialize(time.getAppTime()));
    }


    @Override
    public void update(Observable o, Object arg) {
        materialize((Long) arg);
        setChanged();
        notifyObservers(arg);
    }

    public List<TimeVarying<T>> tvgs() {
        return tvgs;
    }

}