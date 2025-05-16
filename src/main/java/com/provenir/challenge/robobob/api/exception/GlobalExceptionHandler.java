package com.provenir.challenge.robobob.api.exception;

import com.provenir.challenge.robobob.api.constants.ErrorMsg;
import com.provenir.challenge.robobob.api.dto.ErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgException(IllegalArgumentException ex){
        logger.warn("Invalid input: {}",ex.getMessage());
        ErrorResponseDto error = new ErrorResponseDto(
                ErrorMsg.INVALID_INPUT.name(),
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex){

        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(
                error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMsg = error.getDefaultMessage();
                    errors.put(fieldName,errorMsg);
                });
        logger.warn("Validation error: {}",errors);
        ErrorResponseDto error = new ErrorResponseDto(
                ErrorMsg.VALIDATION_ERROR.name(),
                ex.getMessage(),
                errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex){

        logger.error("Unhandled exception",ex);
        ErrorResponseDto error = new ErrorResponseDto(
                ErrorMsg.INTERNAL_SERVER_ERROR.name(),
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

    }
}
