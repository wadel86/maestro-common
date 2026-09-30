package io.maestro.common.saga.instance;

import io.maestro.common.exception.InconsistentSagaStateException;

/**
 * Where a saga instance has got to: a {@link SagaState} and a pointer into its
 * definition's step list.
 *
 * <p><strong>The pointer always means the index of the step the saga is currently
 * concerned with</strong>, in both directions:
 *
 * <ul>
 *   <li>{@link SagaState#CREATED} &mdash; {@code -1}: nothing has run yet.</li>
 *   <li>{@link SagaState#EXECUTING} &mdash; the step running or awaiting a reply. Steps
 *       {@code 0 .. pointer-1} have completed; reaching the step count means done.</li>
 *   <li>{@link SagaState#COMPENSATING} &mdash; the next step <em>to undo</em>, counting
 *       down. Back at {@code -1}, everything that ran has been undone.</li>
 * </ul>
 *
 * <p>Both ends of a saga's life sit at {@code -1}, which is what makes "compensation is
 * complete" cheap to assert. {@link #reverseToCompensation()} is the hinge: a step that
 * fails at index {@code p} did not complete and so needs no undo, which is why the pointer
 * lands on {@code p - 1}.
 *
 * <p>Read that together with {@code SagaDefinition.getNextSteps} and
 * {@code getStepsToCompensate} in maestro-core, which index into the step list with it. A
 * change here that is not mirrored there is how a saga comes to skip a compensation or
 * never finish one.
 *
 * <p>Not thread-safe: callers are expected to serialize access to a given instance.
 */
public class SagaExecutionState {
    private int pointer;
    private SagaState state;

    public SagaExecutionState(int pointer, SagaState state) {
        this.pointer = pointer;
        this.state = state;
    }

    public static SagaExecutionState initialize(){
        return new SagaExecutionState(-1, SagaState.CREATED);
    }

    public void putInStartMode(){
        if(!SagaState.CREATED.equals(this.state)){
            throw new InconsistentSagaStateException
                    ("In order to be started, a saga must be in CREATED state");
        }
        this.pointer = 0;
        this.state = SagaState.EXECUTING;
    }

    public void putInTerminateMode(){
        if(SagaState.CREATED.equals(this.state)
                || SagaState.TERMINATED.equals(this.state) ){
            throw new InconsistentSagaStateException
                    ("In order to be terminated, a saga must be in EXECUTING or COMPENSATION state");
        }
        this.state = SagaState.TERMINATED;
    }

    public void stepUp(){
        if(!SagaState.EXECUTING.equals(this.state)){
            throw new InconsistentSagaStateException
                    ("Can't step up a non executing saga");
        }
        this.pointer += 1;
    }

    public void reverseToCompensation(){
        if(!SagaState.EXECUTING.equals(this.state)){
            throw new InconsistentSagaStateException
                    ("Can't reverse a non executing saga");
        }
        this.pointer -= 1;
        this.state = SagaState.COMPENSATING;
    }

    public void stepDown(){
        if(!SagaState.COMPENSATING.equals(this.state)){
            throw new InconsistentSagaStateException
                    ("Can't step down a non compensating saga");
        }
        this.pointer -= 1;
    }

    public SagaState getState() {
        return state;
    }

    public int getPointer() {
        return pointer;
    }
}
