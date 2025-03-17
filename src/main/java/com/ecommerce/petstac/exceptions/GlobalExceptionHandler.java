package com.ecommerce.petstac.exceptions;


import jakarta.validation.ConstraintDeclarationException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> result = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(err-> {
            String fieldName = ((FieldError)err).getField();
            String errMsg = err.getDefaultMessage();
            result.put(fieldName, errMsg);
        });
         return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<String> myUnexpectedTypeException(ConstraintDeclarationException exception) {
        String errMsg = exception.getLocalizedMessage()+" you added non blank annotation on age field";
         return new ResponseEntity<>(errMsg, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> myNullPointerException(NullPointerException exception) {
        String errMsg = exception.getLocalizedMessage()+" This field is null";
         return new ResponseEntity<>(errMsg, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> myResourceNotFoundException(ResourceNotFoundException exception) {
        String message = exception.getMessage();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<String> myAPIException(APIException exception) {
        String message = exception.getMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MathException.class)
    public ResponseEntity<String> myMathException(MathException exception) {
        String message = exception.getMessage();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
