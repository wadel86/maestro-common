package io.maestro.common.saga.instance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SagaInstanceTests {

    @Mock
    private final SagaExecutionState sagaExecutionState
            = new SagaExecutionState(-1, SagaState.CREATED);

    @Test
    public void shouldExposeTheIdentityAGatewayNeedsToStoreIt() {
        //given
        SagaInstance sagaInstance
                = new SagaInstance("saga-id", "saga-type", sagaExecutionState, null);
        //then
        assertEquals("saga-id", sagaInstance.getId());
        assertEquals("saga-type", sagaInstance.getSagaType());
    }

    @Test
    public void whenNotSavedYet_thenShouldHaveNoId() {
        //given
        SagaInstance sagaInstance
                = new SagaInstance(null, "saga-type", sagaExecutionState, null);
        //then
        assertNull(sagaInstance.getId());
        assertEquals("saga-type", sagaInstance.getSagaType());
    }

    @Test
    public void start_shouldPutInstanceStateInStartMode() {
        //given
        SagaInstance sagaInstance
                = new SagaInstance("saga-id", "saga-type", sagaExecutionState, null);
        //when
        doNothing().when(sagaExecutionState).putInStartMode();
        sagaInstance.start();
        //then
        verify(sagaExecutionState, times(1)).putInStartMode();
    }

    @Test
    public void terminate_shouldPutInstanceStateInTerminatedMode() {
        //given
        SagaInstance sagaInstance
                = new SagaInstance("saga-id", "saga-type", sagaExecutionState, null);
        //when
        doNothing().when(sagaExecutionState).putInTerminateMode();
        sagaInstance.terminate();
        //then
        verify(sagaExecutionState, times(1)).putInTerminateMode();
    }

    @Test
    public void reverseToCompensationState_shouldReverseInstanceStateToCompensateMode() {
        //given
        SagaInstance sagaInstance
                = new SagaInstance("saga-id", "saga-type", sagaExecutionState, null);
        //when
        doNothing().when(sagaExecutionState).reverseToCompensation();
        sagaInstance.reverseToCompensationState();
        //then
        verify(sagaExecutionState, times(1)).reverseToCompensation();
    }

    @Test
    public void stepUp_shouldStepsUpInstanceStatePointer() {
        //given
        SagaInstance sagaInstance
                = new SagaInstance("saga-id", "saga-type", sagaExecutionState, null);
        //when
        doNothing().when(sagaExecutionState).stepUp();
        sagaInstance.stepUp();
        //then
        verify(sagaExecutionState, times(1)).stepUp();
    }

    @Test
    public void stepDown_shouldStepsDownInstanceStatePointer() {
        //given
        SagaInstance sagaInstance
                = new SagaInstance("saga-id", "saga-type", sagaExecutionState, null);
        //when
        doNothing().when(sagaExecutionState).stepDown();
        sagaInstance.stepDown();
        //then
        verify(sagaExecutionState, times(1)).stepDown();
    }

}
