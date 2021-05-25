package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.Window;
import java.util.Iterator;

/**
 *
 * @param <I>
 */
public interface Scope<I> {
    Iterator<? extends Window> apply(I arg, long ts);
}
