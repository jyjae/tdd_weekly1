package io.hhplus.tdd.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class PointCommandException extends RuntimeException {

    private final Map<String, String> validation = new HashMap<>();

    public PointCommandException(String message) {
        super(message);
    }

    public PointCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getStatusCode() {
        return "500";
    }
}
