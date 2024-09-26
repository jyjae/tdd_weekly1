package io.hhplus.tdd.common;

import io.hhplus.tdd.exception.InvalidRequestException;
import io.hhplus.tdd.exception.LongOverflowException;
import io.hhplus.tdd.exception.NotEnoughPointsException;
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

    @ExceptionHandler(value = InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(InvalidRequestException e) {
        return ResponseEntity.status(500).body(new ErrorResponse(e.getStatusCode(), e.getMessage()));
    }

    @ExceptionHandler(value = LongOverflowException.class)
    public ResponseEntity<ErrorResponse> handleLongOverflowException(LongOverflowException e) {
        return ResponseEntity.status(500).body(new ErrorResponse(e.getStatusCode(), e.getMessage()));
    }

    @ExceptionHandler(value = NotEnoughPointsException.class)
    public ResponseEntity<ErrorResponse> handleNotEnoughPointsException(NotEnoughPointsException e) {
        return ResponseEntity.status(500).body(new ErrorResponse(e.getStatusCode(), e.getMessage()));
    }
}
