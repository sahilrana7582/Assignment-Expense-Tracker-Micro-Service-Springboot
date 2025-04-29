package com.example.notification_service.dto.responseDto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
}
