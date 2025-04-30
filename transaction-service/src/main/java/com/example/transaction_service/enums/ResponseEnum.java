package com.example.transaction_service.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseEnum {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    USER_CREATED(HttpStatus.CREATED, "User created"),
    USER_UPDATED(HttpStatus.OK, "User updated"),
    USER_DELETED(HttpStatus.OK, "User deleted"),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "User already exists"),
    USER_EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "User email already exists"),
    USER_ID_ALREADY_EXISTS(HttpStatus.CONFLICT, "User ID already exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad request");

    private final HttpStatus status;
    private final String message;

    ResponseEnum(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
