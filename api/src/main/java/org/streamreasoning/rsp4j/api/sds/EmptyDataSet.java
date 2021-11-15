package org.streamreasoning.rsp4j.api.sds;

import java.util.Collection;
import java.util.Collections;

public class EmptyDataSet<W> implements DataSet<W> {
    @Override
    public Collection<W> getContent() {
        return Collections.emptyList();
    }

    @Override
    public String getName() {
        return "default";
    }
}
