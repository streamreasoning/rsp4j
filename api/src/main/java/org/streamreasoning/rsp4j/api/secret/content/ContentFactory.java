package org.streamreasoning.rsp4j.api.secret.content;

public interface ContentFactory<T1, T2> {

    Content<T1, T2> createEmpty();

    Content<T1, T2> create();


}
