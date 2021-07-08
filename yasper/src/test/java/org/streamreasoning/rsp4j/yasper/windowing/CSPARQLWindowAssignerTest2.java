package org.streamreasoning.rsp4j.yasper.windowing;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.Triple;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.report.ReportImpl;
import org.streamreasoning.rsp4j.api.secret.report.strategies.OnWindowClose;
import org.streamreasoning.rsp4j.api.secret.time.TimeImpl;
import org.streamreasoning.rsp4j.yasper.StreamViewImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.*;
import org.streamreasoning.rsp4j.yasper.sds.SDSImpl;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import static junit.framework.TestCase.assertTrue;

public class CSPARQLWindowAssignerTest2 {

    private static class TestEvent implements EventBean<Long>{

        private Map<String, Long> attributes;
        private long ts;

        public TestEvent(Map<String, Long> attributes, long ts) {
            this.attributes = attributes;
            this.ts = ts;
        }

        public static TestEvent createEvent(long value, long ts){
            Map<String, Long> testMap = new HashMap<>();
            testMap.put("value", value);
            return new TestEvent(testMap, ts);
        }

        @Override
        public Long getValue(String attributeName) {
            return attributes.get(attributeName);
        }

        @Override
        public long getTime() {
            return ts;
        }

        @Override
        public boolean equals(Object obj) {
            TestEvent other = (TestEvent) obj;
            return other.getTime() == other.getTime() && attributes.equals(other.attributes);
        }
    }

    @Test
    public void testThreshold() {

        Report report = new ReportImpl();
        report.add(new OnWindowClose());

        Tick tick = Tick.TIME_DRIVEN;
        ReportGrain report_grain = ReportGrain.SINGLE;


        //WINDOW DECLARATION
        TimeImpl time = new TimeImpl(0);

        Scope<TestEvent> scope = new ThresholdWindowing<>("value", Long::compareTo, 60L);

        StreamToRelationOp<TestEvent, Collection<TestEvent>> windowStreamToRelationOp = new CSPARQLStreamToRelationOpSingle<TestEvent,Long>(RDFUtils.createIRI("w1"), time, tick, report, report_grain, scope);

        //ENGINE INTERNALS - HOW THE REPORTING POLICY, TICK AND REPORT GRAIN INFLUENCE THE RUNTIME

        StreamViewImpl v = new StreamViewImpl();

        TimeVarying<Collection<TestEvent>> timeVarying = windowStreamToRelationOp.get();

        Tester tester = new Tester();

        v.addObserver((o, arg) -> {
            Long arg1 = (Long) arg;
            timeVarying.materialize(arg1);
            System.err.println(arg1);
            System.err.println(timeVarying.get());
            tester.test(timeVarying.get());
        });

        TestEvent event = TestEvent.createEvent(50L, 1000);
        Set<TestEvent> resultingEvents = new HashSet<>();

        windowStreamToRelationOp.notify(event, 1000);

        tester.setExpected(resultingEvents);
        //RUNTIME DATA
        windowStreamToRelationOp.notify(event, 1000);

        event = TestEvent.createEvent(61L, 1001);
        resultingEvents.add(event);

        windowStreamToRelationOp.notify(event, 1001);

        event = TestEvent.createEvent(79L, 1002);
        resultingEvents.add(event);

        windowStreamToRelationOp.notify(event, 1002);

        event = TestEvent.createEvent(80L, 1003);
        resultingEvents.add(event);

        windowStreamToRelationOp.notify(event, 1003);

        event = TestEvent.createEvent(50L, 1004);
        resultingEvents.clear();

        windowStreamToRelationOp.notify(event, 1004);


        event = TestEvent.createEvent(80L, 1005);
        resultingEvents.add(event);

        windowStreamToRelationOp.notify(event, 1005);


    }

    @Test
    public void testDelta() {

        Report report = new ReportImpl();
        report.add(new OnWindowClose());

        Tick tick = Tick.TIME_DRIVEN;
        ReportGrain report_grain = ReportGrain.SINGLE;


        //WINDOW DECLARATION
        TimeImpl time = new TimeImpl(0);

        Scope<TestEvent> scope = new DeltaWindowing<>("value", 10L);

        StreamToRelationOp<TestEvent, Collection<TestEvent>> windowStreamToRelationOp = new CSPARQLStreamToRelationOpSingle<>(RDFUtils.createIRI("w1"), time, tick, report, report_grain, scope);

        //ENGINE INTERNALS - HOW THE REPORTING POLICY, TICK AND REPORT GRAIN INFLUENCE THE RUNTIME

        StreamViewImpl v = new StreamViewImpl();

        TimeVarying<Collection<TestEvent>> timeVarying = windowStreamToRelationOp.get();

        Tester tester = new Tester();

        v.addObserver((o, arg) -> {
            Long arg1 = (Long) arg;
            timeVarying.materialize(arg1);
            System.err.println(arg1);
            System.err.println(timeVarying.get());
            tester.test(timeVarying.get());
        });

        TestEvent event = TestEvent.createEvent(50L, 1000);
        Set<TestEvent> resultingEvents = new HashSet<>();

        windowStreamToRelationOp.notify(event, 1000);

        tester.setExpected(resultingEvents);
        //RUNTIME DATA
        windowStreamToRelationOp.notify(event, 1000);

        event = TestEvent.createEvent(59L, 1001);
        resultingEvents.add(event);

        windowStreamToRelationOp.notify(event, 1001);

        event = TestEvent.createEvent(58L, 1002);
        resultingEvents.add(event);

        windowStreamToRelationOp.notify(event, 1002);

        event = TestEvent.createEvent(56L, 1003);
        resultingEvents.add(event);

        windowStreamToRelationOp.notify(event, 1003);

        event = TestEvent.createEvent(80L, 1004);
        resultingEvents.clear();
        resultingEvents.add(event);

        windowStreamToRelationOp.notify(event, 1004);

        event = TestEvent.createEvent(86L, 1005);
        resultingEvents.add(event);

        windowStreamToRelationOp.notify(event, 1005);

    }

    @Test
    public void testAggregate() {

        Report report = new ReportImpl();
        report.add(new OnWindowClose());

        Tick tick = Tick.TIME_DRIVEN;
        ReportGrain report_grain = ReportGrain.SINGLE;


        //WINDOW DECLARATION
        TimeImpl time = new TimeImpl(0);

        Scope<TestEvent> scope = new AggregateWindowing<>((testEvent, aLong) -> testEvent.attributes.get("value") + aLong, 0L, 100L);

        StreamToRelationOp<TestEvent, Collection<TestEvent>> windowStreamToRelationOp = new CSPARQLStreamToRelationOpSingle<>(RDFUtils.createIRI("w1"), time, tick, report, report_grain, scope);

        //ENGINE INTERNALS - HOW THE REPORTING POLICY, TICK AND REPORT GRAIN INFLUENCE THE RUNTIME

        StreamViewImpl v = new StreamViewImpl();

        TimeVarying<Collection<TestEvent>> timeVarying = windowStreamToRelationOp.get();

        Tester tester = new Tester();

        v.addObserver((o, arg) -> {
            Long arg1 = (Long) arg;
            timeVarying.materialize(arg1);
            System.err.println(arg1);
            System.err.println(timeVarying.get());
            tester.test(timeVarying.get());
        });

        TestEvent event = TestEvent.createEvent(50L, 1000);
        Set<TestEvent> resultingEvents = new HashSet<>();

        windowStreamToRelationOp.notify(event, 1000);

        tester.setExpected(resultingEvents);
        //RUNTIME DATA
        windowStreamToRelationOp.notify(event, 1000);

        event = TestEvent.createEvent(20L, 1001);
        resultingEvents.add(event);

        windowStreamToRelationOp.notify(event, 1001);

        event = TestEvent.createEvent(20L, 1002);
        resultingEvents.add(event);

        windowStreamToRelationOp.notify(event, 1002);

        event = TestEvent.createEvent(20L, 1003);
        resultingEvents.clear();
        resultingEvents.add(event);

        windowStreamToRelationOp.notify(event, 1003);

        event = TestEvent.createEvent(30L, 1004);

        resultingEvents.add(event);

        windowStreamToRelationOp.notify(event, 1004);

        event = TestEvent.createEvent(40L, 1005);
        resultingEvents.add(event);

        windowStreamToRelationOp.notify(event, 1005);

    }

    @Test
    public void testBoundaries() {

        Report report = new ReportImpl();
        report.add(new OnWindowClose());

        Tick tick = Tick.TIME_DRIVEN;
        ReportGrain report_grain = ReportGrain.SINGLE;


        //WINDOW DECLARATION
        TimeImpl time = new TimeImpl(0);

        Long[] longs = {0L, 10L, 20L, 30L, 40L, 50L, 60L, 70L, 80L, 90L};


        Scope<TestEvent> scope = new BoundaryWindowing<>(longs,"value");

        StreamToRelationOp<TestEvent, Collection<TestEvent>> windowStreamToRelationOp = new CSPARQLStreamToRelationOpSingle<>(RDFUtils.createIRI("w1"), time, tick, report, report_grain, scope);

        //ENGINE INTERNALS - HOW THE REPORTING POLICY, TICK AND REPORT GRAIN INFLUENCE THE RUNTIME

        StreamViewImpl v = new StreamViewImpl();

        TimeVarying<Collection<TestEvent>> timeVarying = windowStreamToRelationOp.get();

        Tester tester = new Tester();

        v.addObserver((o, arg) -> {
            Long arg1 = (Long) arg;
            timeVarying.materialize(arg1);
            System.err.println(arg1);
            System.err.println(timeVarying.get());
            tester.test(timeVarying.get());
        });

        Set<TestEvent> resultingEvents = new HashSet<>();

        tester.setExpected(resultingEvents);
        //RUNTIME DATA

        TestEvent event = TestEvent.createEvent(55L, 1000);
        resultingEvents.add(event);

        windowStreamToRelationOp.notify(event, 1000);

        event = TestEvent.createEvent(56L, 1002);
        resultingEvents.add(event);

        windowStreamToRelationOp.notify(event, 1002);

        event = TestEvent.createEvent(22L, 1003);
        resultingEvents.clear();
        resultingEvents.add(event);

        windowStreamToRelationOp.notify(event, 1003);

        event = TestEvent.createEvent(25L, 1004);

        resultingEvents.add(event);

        windowStreamToRelationOp.notify(event, 1004);

        event = TestEvent.createEvent(27L, 1005);
        resultingEvents.add(event);

        windowStreamToRelationOp.notify(event, 1005);

    }



    private class Tester {

        Collection<TestEvent> expected;

        public void test(Collection<TestEvent> testEvents) {
            testEvents.forEach(event ->
                    assertTrue(expected.contains(event)));
            expected.forEach(event ->
                    assertTrue(testEvents.contains(event)));
        }

        public void setExpected(Collection<TestEvent> expected) {
            this.expected = expected;
        }

    }
}
