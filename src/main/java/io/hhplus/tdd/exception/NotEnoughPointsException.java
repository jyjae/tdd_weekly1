package io.hhplus.tdd.exception;

import java.util.HashMap;
import java.util.Map;

public class NotEnoughPointsException  extends RuntimeException {

    private final Map<String, String> validation = new HashMap<>();

    public NotEnoughPointsException(String message) {
        super(message);
    }

    public NotEnoughPointsException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getStatusCode() {
        return "500";
    }
}
