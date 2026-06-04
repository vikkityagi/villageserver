package com.marketplace.village.exception;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.marketplace.village.dto.ErrorResponse;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustom(CustomException ex) {
        return ResponseEntity.badRequest().body(
            new ErrorResponse(ex.getMessage(), LocalDateTime.now())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            new ErrorResponse("Something went wrong on server side", LocalDateTime.now())
        );
    }

    
}
