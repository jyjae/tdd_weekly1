package io.hhplus.tdd.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class InvalidRequest extends RuntimeException {

    private final Map<String, String> validation = new HashMap<>();

    public InvalidRequest(String message) {
        super(message);
    }

    public InvalidRequest(String message, Throwable cause) {
        super(message, cause);
    }

    public String getStatusCode() {
        return "400";
    }
}
