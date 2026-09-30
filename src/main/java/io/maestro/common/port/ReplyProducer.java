package io.maestro.common.port;

import io.maestro.common.reply.Message;

/**
 * Outbound messaging port on the <em>participant</em> side: sends a reply back to the
 * saga that issued the command.
 *
 * <p>The mirror of {@link CommandProducer}. The reply's correlation headers are already
 * set by the participant runtime, copied from the command being answered, so an
 * implementation only has to put the message on {@code replyChannel} as it stands.
 */
public interface ReplyProducer {
    void sendReply(String replyChannel, Message reply);
}
