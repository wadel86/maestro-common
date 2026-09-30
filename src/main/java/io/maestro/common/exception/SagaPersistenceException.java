package io.maestro.common.exception;

/**
 * A saga could not be read from or written to its store.
 *
 * <p>Lives here rather than in an adapter so that application code can catch one type
 * whichever {@link io.maestro.common.port.SagaDataGateway} is in use, and so that swapping
 * a JDBC adapter for a document one is not a change to the calling code.
 */
public class SagaPersistenceException extends RuntimeException {

    public SagaPersistenceException(String message) {
        super(message);
    }

    public SagaPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
