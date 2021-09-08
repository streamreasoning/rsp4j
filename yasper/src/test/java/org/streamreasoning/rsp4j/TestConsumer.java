package org.streamreasoning.rsp4j;

import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class TestConsumer implements org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer<Binding> {


    private final Map<Long, Set<Binding>> testset;

    public TestConsumer(Map<Long, Set<Binding>> resultSet) {
        this.testset = resultSet;
    }

    @Override
    public void notify(Binding arg, long ts) {
        assertTrue(testset.containsKey(ts));
        Set<Binding> expected = testset.get(Long.valueOf(ts));
        assertTrue(expected.contains(arg));
    }
}