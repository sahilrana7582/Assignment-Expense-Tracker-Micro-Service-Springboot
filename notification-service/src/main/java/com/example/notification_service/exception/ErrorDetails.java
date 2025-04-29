package com.example.notification_service.exception;

import com.example.notification_service.enums.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {

    private ResponseEnum errorCode;
    private String message;
    private String path;
    private LocalDateTime timestamp;

}
