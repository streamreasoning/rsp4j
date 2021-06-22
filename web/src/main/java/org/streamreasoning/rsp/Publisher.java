package org.streamreasoning.rsp;

public interface Publisher extends Describable {

    Publisher stream(String id, boolean fragment);

    default Publisher stream(String uri) {
        return stream(uri, false);
    }

    Publisher name(String name);

    Publisher description(String description);

    Publisher distribution(Distribution distribution);

    <E> WebStreamEndpoint<E> build();

    WebDataStream<String> fetch(String s);
}
