package org.streamreasoning.rsp4j.api.secret.content;

import org.streamreasoning.rsp4j.api.secret.content.Content;

public interface ContentFactory<T1, T2> {

    Content<T1, T2> createEmpty();

    Content<T1, T2> create();


}
