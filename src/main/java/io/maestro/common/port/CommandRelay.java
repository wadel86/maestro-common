package io.maestro.common.port;

/**
 * Moves commands a saga has recorded but not yet dispatched onto the broker.
 *
 * <p>The second half of the outbox. A {@link SagaDataGateway} durably records a command
 * beside the saga that sent it; a relay publishes it through a {@link CommandProducer} and
 * marks it sent. Splitting the two is what makes a crash survivable — an unpublished
 * command is simply picked up on the next pass — and is why delivery is at least once,
 * so participants must be idempotent.
 *
 * <p>Each adapter relays from wherever its gateway put the command: a JDBC adapter from an
 * outbox table, a document adapter from inside the saga document. This interface is what
 * lets an application schedule either one the same way, and swap them without touching the
 * code that drives it.
 *
 * <p>One pass per call, left for the application to schedule on whatever it already uses.
 */
public interface CommandRelay {

    /**
     * Publishes the commands currently waiting, up to whatever batch size the
     * implementation was configured with.
     *
     * @return how many commands were published
     */
    int publishPending();
}
