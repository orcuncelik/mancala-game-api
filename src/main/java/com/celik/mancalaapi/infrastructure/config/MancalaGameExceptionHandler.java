package com.celik.mancalaapi.infrastructure.config;

import com.celik.mancalaapi.domain.exception.GameNotFoundException;
import com.celik.mancalaapi.domain.exception.InvalidPitException;
import com.celik.mancalaapi.infrastructure.exception.GameStateNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MancalaGameExceptionHandler {
    @ExceptionHandler({GameNotFoundException.class, GameStateNotFoundException.class, InvalidPitException.class})
    public ResponseEntity<Object> handleCustomExceptions(RuntimeException ex, WebRequest request) {
        HttpStatus status;
        if (isNotFoundException(ex)) {
            status = HttpStatus.NOT_FOUND;
        } else if (isBadRequestException(ex)) {
            status = HttpStatus.BAD_REQUEST;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", status.value());
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("timeStamp", System.currentTimeMillis());

        return new ResponseEntity<>(errorDetails, status);
    }

    private boolean isNotFoundException(RuntimeException ex) {
        return ex instanceof GameNotFoundException
                || ex instanceof GameStateNotFoundException;
    }

    private boolean isBadRequestException(RuntimeException ex) {
        return ex instanceof InvalidPitException;
    }
}

