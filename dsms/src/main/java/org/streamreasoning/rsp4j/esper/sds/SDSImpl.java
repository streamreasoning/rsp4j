package org.streamreasoning.rsp4j.esper.sds;


import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.IRI;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.secret.time.TimeFactory;

import java.util.*;
import java.util.stream.Stream;

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


//    public void materialize(long ts) {
//        tvgs.forEach(g -> g.materialize(time.getAppTime()));
//    }

    @Override
    public void materialized() {
        tvgs.forEach(g -> g.materialize(time.getAppTime()));
    }

    @Override
    public Stream<T> toStream() {
        return null;
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