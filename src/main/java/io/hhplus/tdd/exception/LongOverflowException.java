package io.hhplus.tdd.exception;

import java.util.HashMap;
import java.util.Map;

public class LongOverflowException extends RuntimeException {

    private final Map<String, String> validation = new HashMap<>();

    public LongOverflowException(String message) {
        super(message);
    }

    public LongOverflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getStatusCode() {
        return "500";
    }
}
