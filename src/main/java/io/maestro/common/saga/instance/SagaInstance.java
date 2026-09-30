package io.maestro.common.saga.instance;

public class SagaInstance {
    private String id;
    private String sagaType;
    private SagaExecutionState sagaExecutionState;
    private SagaSerializedData serializedData;

    public SagaInstance(String id, String sagaType, SagaExecutionState sagaExecutionState, SagaSerializedData serializedData){
        this.id = id;
        this.sagaType = sagaType;
        this.sagaExecutionState = sagaExecutionState;
        this.serializedData = serializedData;
    }

    public String getId() {
        return id;
    }

    /**
     * Type of the saga this instance belongs to. A {@link io.maestro.common.port.SagaDataGateway}
     * needs it to store the instance under the same key its {@code findSaga} looks up by.
     */
    public String getSagaType() {
        return sagaType;
    }

    public SagaSerializedData getSerializedData() {
        return serializedData;
    }

    public void setSerializedData(SagaSerializedData serializedData) {
        this.serializedData = serializedData;
    }

    public SagaExecutionState getSagaExecutionState() {
        return sagaExecutionState;
    }

    public void start(){
        this.sagaExecutionState.putInStartMode();
    }

    public void terminate(){
        this.sagaExecutionState.putInTerminateMode();
    }

    public void reverseToCompensationState(){
        this.sagaExecutionState.reverseToCompensation();
    }

    public void stepUp(){
        this.sagaExecutionState.stepUp();
    }

    public void stepDown(){
       this.sagaExecutionState.stepDown();
    }
}
