package com.example.user_service.dto.requestDto;


import com.example.user_service.enums.AccountType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountUpdateDto {

    @Size(min = 2, max = 100, message = "Account name must be between 2 and 100 characters")
    private String name;

    private AccountType type;

    @DecimalMin(value = "0.0", inclusive = true, message = "Balance must be positive")
    private BigDecimal balance;

    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a valid 3-letter ISO code (e.g., USD, EUR)")
    private String currency;

    private Boolean isActive;
}
