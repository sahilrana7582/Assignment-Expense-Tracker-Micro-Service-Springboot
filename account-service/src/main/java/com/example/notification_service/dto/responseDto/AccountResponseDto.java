package com.example.notification_service.dto.responseDto;


import com.example.notification_service.enums.AccountType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponseDto {

    private Long id;
    private String accountId;
    private String userId;
    private String name;
    private AccountType type;
    private BigDecimal balance;
    private String currency;
    private boolean isActive;
}

