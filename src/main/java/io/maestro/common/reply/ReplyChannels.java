package io.maestro.common.reply;

/**
 * Where a saga type's replies go.
 *
 * <p>The orchestrator subscribes to this channel and participants address their replies to
 * it, so the two have to derive the same name from the same saga type. That is why the
 * convention lives here rather than inside either side.
 */
public final class ReplyChannels {

    private static final String SUFFIX = "-reply-channel";

    /** The channel a saga of this type expects its participants to reply on. */
    public static String forSagaType(String sagaType) {
        if (sagaType == null || sagaType.isBlank()) {
            throw new IllegalArgumentException("A reply channel needs a saga type");
        }
        return sagaType + SUFFIX;
    }

    private ReplyChannels() {
    }
}
