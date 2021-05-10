package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;


import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.Window;
import org.streamreasoning.rsp4j.api.secret.content.Content;

import java.util.Iterator;

public interface Scope<I,O> {

    Iterator<? extends Window> apply(I arg, long ts, Content<I,O> content);
}
