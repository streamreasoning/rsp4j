package org.streamreasoning.rsp4j.api.exceptions;

public class EngineConfigurationException extends RuntimeException {

    public EngineConfigurationException() {
    }

    public EngineConfigurationException(String message) {
        super(message);
    }

    public EngineConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EngineConfigurationException(Throwable cause) {
        super(cause);
    }

    public EngineConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
