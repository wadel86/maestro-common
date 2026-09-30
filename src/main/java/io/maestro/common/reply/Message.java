package io.maestro.common.reply;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A message on the wire between a saga and a participant: a command going out, or a reply
 * coming back.
 *
 * <p>Everything in it arrived from another service, so headers and payload are untrusted
 * input. {@link MessageHeaders} describes the ones the framework expects.
 */
public class Message {
    private final String sagaType;
    private final Map<String, String> headers;
    private final String payload;

    public Message(String sagaType, Map<String, String> headers, String payload) {
        this.sagaType = sagaType;
        this.headers = headers == null
                ? Collections.emptyMap()
                : Collections.unmodifiableMap(new HashMap<>(headers));
        this.payload = payload;
    }

    public String getSagaType() {
        return sagaType;
    }

    /** The header's value, or {@code null} when the sender did not set it. */
    public String getHeader(String header) {
        return headers.get(header);
    }

    /**
     * Every header on the message. A participant uses this to echo a command's
     * correlation headers back onto its reply.
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getPayload() {
        return payload;
    }
}
