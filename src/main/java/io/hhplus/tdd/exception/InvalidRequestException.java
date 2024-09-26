package io.hhplus.tdd.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class InvalidRequestException extends RuntimeException {

    private final Map<String, String> validation = new HashMap<>();

    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getStatusCode() {
        return "400";
    }
}
