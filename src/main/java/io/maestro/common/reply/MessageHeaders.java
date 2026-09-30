package io.maestro.common.reply;

/**
 * The header names and values that make up the wire contract between a saga and its
 * participants.
 *
 * <p>These were string literals spread across the orchestrator, which meant every
 * participant and adapter had to reproduce them by hand and a typo surfaced as a saga
 * that silently stopped making progress.
 *
 * <p>A {@link io.maestro.common.port.CommandProducer} stamps {@link #SAGA_ID} and
 * {@link #SAGA_TYPE} onto each outgoing command. A participant echoes both back on its
 * reply and adds {@link #REPLY_TYPE} and {@link #REPLY_OUTCOME}, which is how the
 * orchestrator finds the saga instance waiting on it and learns whether to go on or
 * unwind.
 */
public final class MessageHeaders {

    /** Id of the saga instance a command or reply belongs to. */
    public static final String SAGA_ID = "Saga-ID";

    /** Type of the saga a command or reply belongs to. */
    public static final String SAGA_TYPE = "Saga-Type";

    /**
     * Index of the saga step a command belongs to, taken from the instance's execution
     * state pointer at dispatch time.
     *
     * <p>Echoed back, it lets the orchestrator tell a reply it is waiting for from one it
     * has already acted on, so a redelivery cannot step the saga a second time. A
     * participant that omits it is trusted, so this is not required of existing ones.
     */
    public static final String SAGA_STEP = "Saga-Step";

    /**
     * Fully qualified class name of the reply payload, used to pick the handler
     * registered by {@code onReply} and to deserialize the body.
     */
    public static final String REPLY_TYPE = "reply-type";

    /** Whether the participant succeeded: {@link #SUCCESS} or {@link #FAILURE}. */
    public static final String REPLY_OUTCOME = "reply-outcome";

    /** {@link #REPLY_OUTCOME} value meaning the participant succeeded. */
    public static final String SUCCESS = "success";

    /**
     * {@link #REPLY_OUTCOME} value meaning the participant failed. Any value other than
     * {@link #SUCCESS} is read as a failure, so this is the conventional one to send
     * rather than the only one recognised.
     */
    public static final String FAILURE = "failure";

    private MessageHeaders() {
    }
}
