package org.streamreasoning.rsp4j.api.sds;

import java.util.Collection;

public interface DataSet<T> {

    Collection<T> getContent();
    String getName();
}
