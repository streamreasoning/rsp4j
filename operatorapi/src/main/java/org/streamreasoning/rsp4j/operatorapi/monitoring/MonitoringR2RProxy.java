package org.streamreasoning.rsp4j.operatorapi.monitoring;

import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;

import java.util.Collection;
import java.util.stream.Stream;

public class MonitoringR2RProxy<W,R> implements RelationToRelationOperator<W,R> {

    private final RelationToRelationOperator<W,R> monitoredR2R;
    private final String name;

    public MonitoringR2RProxy(RelationToRelationOperator<W,R> monitoredR2R){
        this(monitoredR2R,monitoredR2R.getClass().getName());
    }
    public MonitoringR2RProxy(RelationToRelationOperator<W,R> monitoredR2R, String name){
        this.monitoredR2R = monitoredR2R;
        this.name = name;
    }
    @Override
    public Stream<R> eval(Stream<W> sds) {
        long t0 = System.currentTimeMillis();
        Stream<R> result = monitoredR2R.eval(sds);
        long resultTime = System.currentTimeMillis() - t0;
        RSP4JMonitor.reportEvalTime(this.name,"eval",resultTime);
        return result;
    }

    @Override
    public TimeVarying<Collection<R>> apply(SDS<W> sds) {
        long t0 = System.currentTimeMillis();
        TimeVarying<Collection<R>> result = monitoredR2R.apply(sds);
        long resultTime = System.currentTimeMillis() - t0;
        RSP4JMonitor.reportEvalTime(this.name,"apply",resultTime);

        return result;
    }

    @Override
    public SolutionMapping<R> createSolutionMapping(R result) {
        long t0 = System.currentTimeMillis();
        SolutionMapping<R> solutionResult = monitoredR2R.createSolutionMapping(result);
        long resultTime = System.currentTimeMillis() - t0;
        RSP4JMonitor.reportEvalTime(this.name,"createSolutionMapping",resultTime);

        return solutionResult;
    }
}
