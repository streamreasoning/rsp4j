package it.polimi.deib.sr.rsp.api.exceptions;

public class OutOfOrderElementException extends RuntimeException {
    public OutOfOrderElementException(String message) {
        super(message);
    }
}
