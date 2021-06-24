package org.streamreasoning.rsp.enums;

public enum Protocol {
    HTTP("http://"), WebSocket("ws://"), HTTPLongPolling("http://"), KAFKA("kafka://"), HTTPStreaming("http://"), SSE("sse://"), MTTQ("mttq://"), QUIC("quic://"), STOMP("stomp://");

    private final String protocolSchema;

    Protocol(String s) {
        this.protocolSchema = s;
    }

    public String schema() {
        return protocolSchema;
    }
}
