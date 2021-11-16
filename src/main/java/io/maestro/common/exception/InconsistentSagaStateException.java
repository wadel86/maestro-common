package io.maestro.common.exception;

public class InconsistentSagaStateException extends RuntimeException {

    public InconsistentSagaStateException(String message) {
        super(message);
    }
}
