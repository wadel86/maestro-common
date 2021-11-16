package io.maestro.common.port;

import io.maestro.common.reply.MessageHandler;

public interface ReplyConsumer {
    void subscribe(String channelId, MessageHandler messageHandler);
}
