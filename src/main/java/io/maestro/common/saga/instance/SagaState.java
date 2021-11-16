package io.maestro.common.saga.instance;

public enum SagaState {
    CREATED, EXECUTING, COMPENSATING, TERMINATED;
}
