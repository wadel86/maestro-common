package io.maestro.common.exception;

public class BadSagaTypeException extends RuntimeException {
    String message;

    public BadSagaTypeException(String message){
        super(message);
    }
}
