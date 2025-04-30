package com.example.transaction_service.exception;


import com.example.transaction_service.enums.ResponseEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {


    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFound exception,  HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(exception.getMessage());
        errorDetails.setTimestamp(LocalDateTime.now());
        errorDetails.setErrorCode(ResponseEnum.USER_NOT_FOUND);
        errorDetails.setPath(request.getRequestURI());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleException(Exception exception,  HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(exception.getMessage());
        errorDetails.setTimestamp(LocalDateTime.now());
        errorDetails.setErrorCode(ResponseEnum.INTERNAL_SERVER_ERROR);
        errorDetails.setPath(request.getRequestURI());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
