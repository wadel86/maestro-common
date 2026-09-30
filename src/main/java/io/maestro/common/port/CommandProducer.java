package io.maestro.common.port;

import io.maestro.common.command.CommandWithDestination;

/**
 * Outbound messaging port: publishes a saga's command to a remote participant.
 *
 * <p>Implementations are expected to stamp the correlation headers onto the outgoing
 * message &mdash; {@link io.maestro.common.reply.MessageHeaders#SAGA_ID},
 * {@link io.maestro.common.reply.MessageHeaders#SAGA_TYPE} and, from the instance saved
 * alongside it, {@link io.maestro.common.reply.MessageHeaders#SAGA_STEP} &mdash; and to
 * have participants echo them back. That is what lets the orchestrator match a reply to
 * the saga instance waiting on it, and tell a fresh reply from a redelivered one.
 *
 * <p>This port is driven by the relay behind
 * {@link SagaDataGateway#saveSagaAndSendCommand}, not by the orchestrator directly.
 */
public interface CommandProducer {
    void sendCommand(String sagaType, String sagaId, CommandWithDestination command);
}
