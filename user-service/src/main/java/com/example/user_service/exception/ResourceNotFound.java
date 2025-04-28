package com.example.user_service.exception;

import org.springframework.http.ResponseEntity;

public class ResourceNotFound extends RuntimeException {

    public ResourceNotFound(String message) {
        super(message);
    }
}
