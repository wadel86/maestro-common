package io.maestro.common.port;

import io.maestro.common.reply.MessageHandler;

/**
 * Inbound messaging port on the <em>participant</em> side: delivers the commands a saga
 * has sent to this service.
 *
 * <p>The mirror of {@link ReplyConsumer}, which is how the orchestrator hears back. A
 * participant subscribes once per command channel at start-up and expects the handler to
 * be invoked for every command that arrives on it.
 */
public interface CommandConsumer {
    void subscribe(String channelId, MessageHandler messageHandler);
}
