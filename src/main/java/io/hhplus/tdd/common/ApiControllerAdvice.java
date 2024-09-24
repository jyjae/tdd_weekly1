package io.hhplus.tdd.common;

import io.hhplus.tdd.exception.InvalidRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class ApiControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity.status(500).body(new ErrorResponse("500", "에러가 발생했습니다."));
    }

    @ExceptionHandler(value = InvalidRequest.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(InvalidRequest e) {
        return ResponseEntity.status(500).body(new ErrorResponse(e.getStatusCode(), e.getMessage()));
    }
}
