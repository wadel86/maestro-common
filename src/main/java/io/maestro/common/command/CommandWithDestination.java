package io.maestro.common.command;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A command a saga wants a remote participant to carry out, together with the channel it
 * must be delivered to.
 *
 * <p>A saga's remote step produces one of these from its saga data:
 *
 * <pre>
 * .invokeRemoteParticipant(data -&gt;
 *         CommandWithDestination.to("inventory-service", new ReserveStock(data.orderId)))
 * </pre>
 *
 * <p>The orchestrator only routes it. Serializing {@link #getCommand()} and mapping
 * {@link #getDestination()} onto whatever the broker calls a channel &mdash; a Kafka
 * topic, a JMS queue, an AMQP routing key &mdash; is the
 * {@link io.maestro.common.port.CommandProducer} adapter's business.
 */
public class CommandWithDestination {

    private final String destination;
    private final Object command;
    private final Map<String, String> headers;

    /** Reads better than the constructor inside a saga definition's lambda. */
    public static CommandWithDestination to(String destination, Object command) {
        return new CommandWithDestination(destination, command);
    }

    public CommandWithDestination(String destination, Object command) {
        this(destination, command, Collections.emptyMap());
    }

    public CommandWithDestination(String destination, Object command,
                                  Map<String, String> headers) {
        if (destination == null || destination.isBlank()) {
            throw new IllegalArgumentException("A command needs a destination channel");
        }
        if (command == null) {
            throw new IllegalArgumentException(
                    "A command to " + destination + " needs a payload");
        }
        this.destination = destination;
        this.command = command;
        this.headers = headers == null
                ? Collections.emptyMap()
                : Collections.unmodifiableMap(new HashMap<>(headers));
    }

    /** Channel the command must be delivered to. */
    public String getDestination() {
        return destination;
    }

    /** The command payload, left to the producer adapter to serialize. */
    public Object getCommand() {
        return command;
    }

    /**
     * Headers the saga wants to travel with the command. The framework's own correlation
     * headers are added by the producer adapter and are not part of this map.
     *
     * @see io.maestro.common.reply.MessageHeaders
     */
    public Map<String, String> getHeaders() {
        return headers;
    }
}
