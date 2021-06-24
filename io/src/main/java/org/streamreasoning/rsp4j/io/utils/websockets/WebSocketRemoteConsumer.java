package org.streamreasoning.rsp4j.io.utils.websockets;

import org.eclipse.jetty.websocket.api.Session;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.io.utils.serialization.StringSerializationStrategy;

import java.io.IOException;

public class WebSocketRemoteConsumer<T> implements Consumer<T> {


    private final Session session;
    private final StringSerializationStrategy<T> serializationStrategy;

    public WebSocketRemoteConsumer(Session session, StringSerializationStrategy<T> serializationStrategy) {
        this.session = session;
        this.serializationStrategy = serializationStrategy;
    }

    @Override
    public void notify(T arg, long ts) {
        try {
            String serializedString = serializationStrategy.serialize(arg);
            session.getRemote().sendString(serializedString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
