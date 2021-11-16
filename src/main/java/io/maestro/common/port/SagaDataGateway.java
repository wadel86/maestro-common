package io.maestro.common.port;

import io.maestro.core.command.CommandWithDestination;
import io.maestro.core.instance.SagaInstance;

public interface SagaDataGateway {
    SagaInstance saveSaga(SagaInstance saga);
    SagaInstance saveSagaAndSendCommand(SagaInstance saga, CommandWithDestination command);
    SagaInstance findSaga(String sagaId, String sagaType);
}
