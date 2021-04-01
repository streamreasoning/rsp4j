package org.streamreasoning.rsp4j.api.exceptions;

public class OutOfOrderElementException extends RuntimeException {
    public OutOfOrderElementException(String message) {
        super(message);
    }
}
