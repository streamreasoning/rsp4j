package org.streamreasoning.rsp;

import org.streamreasoning.rsp.enums.Format;
import org.streamreasoning.rsp.enums.License;
import org.streamreasoning.rsp.enums.Protocol;
import org.streamreasoning.rsp.enums.Security;

public interface Distribution extends Describable {

    Distribution access(String id, boolean fragment);

    default Distribution access(String uri) {
        return access(uri, false);
    }

    Distribution protocol(Protocol protocol);

    Distribution security(Security security);

    Distribution license(License license);

    Distribution format(Format format);

    <E> WebStreamEndpoint<E> build(String path);

}
