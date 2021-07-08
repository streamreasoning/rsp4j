package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;

public interface EventBean<V> {
    public V getValue(String attributeName);
    public long getTime();
}
