package org.streamreasoning.rsp4j.abstraction.monitoring;

import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;

import java.util.Collection;
import java.util.stream.Stream;

public class MonitoringR2SProxy<R,O> implements RelationToStreamOperator<R,O> {

    private final String name;
    private final RelationToStreamOperator<R,O> r2s;

    public MonitoringR2SProxy(RelationToStreamOperator<R,O> r2s, String name){
        this.r2s = r2s;
        this.name = name;
    }
    public MonitoringR2SProxy(RelationToStreamOperator<R,O> r2s){
        this(r2s,r2s.getClass().getSimpleName());
    }

    public Stream<O> eval(Stream<R> sml, long ts) {
        long t0 = System.currentTimeMillis();
        Stream<O> result = r2s.eval(sml,ts);
        long resultTime = System.currentTimeMillis() - t0;
        RSP4JMonitor.reportEvalTime(this.name,"eval",resultTime);
        return result;
    }

    public Collection<O> eval(TimeVarying<Collection<R>> sml, long ts) {
        long t0 = System.currentTimeMillis();
        Collection<O> result = r2s.eval(sml,ts);
        long resultTime = System.currentTimeMillis() - t0;
        RSP4JMonitor.reportEvalTime(this.name,"eval",resultTime);
        return result;
    }
}
