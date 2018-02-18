package it.polimi.spe.exceptions;

public class OutOfOrderElementException extends RuntimeException {
    public OutOfOrderElementException(String message) {
        super(message);
    }
}
