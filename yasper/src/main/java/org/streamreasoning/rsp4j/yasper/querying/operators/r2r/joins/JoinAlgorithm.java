package org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins;

import java.util.Set;

public interface JoinAlgorithm<T> {
    Set<T> join(Set<T> bindings1, Set<T> bindings2);
}
