package io.maestro.common.port;

import io.maestro.core.command.CommandWithDestination;

public interface CommandProducer {
    void sendCommand(String sagaType, String sagaId, CommandWithDestination command);
}
