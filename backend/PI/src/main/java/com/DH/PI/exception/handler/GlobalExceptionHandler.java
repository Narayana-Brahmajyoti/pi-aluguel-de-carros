package com.DH.PI.exception.handler;

import com.DH.PI.exception.ResourceBadRequest;
import com.DH.PI.exception.ResourceConflict;
import com.DH.PI.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.Field;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> processarErrorResourceNotFound(ResourceNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());

    }
    @ExceptionHandler({ResourceConflict.class})
    public ResponseEntity<String> processarErrorResourceConflict(ResourceConflict exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());

    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> badRequest(MethodArgumentNotValidException exception){
        FieldError fieldError = exception.getBindingResult().getFieldErrors().get(0);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldError.getDefaultMessage());
    }
    @ExceptionHandler({ResourceBadRequest.class})
    public ResponseEntity<String> resourceBadRequest(ResourceBadRequest exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
