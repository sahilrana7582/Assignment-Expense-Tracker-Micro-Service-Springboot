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
public class AccountRequestDto {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Account name is required")
    @Size(min = 2, max = 100, message = "Account name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Account type is required")
    private AccountType type;

    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance must be positive")
    private BigDecimal balance;

    @NotBlank(message = "Currency is required")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a valid 3-letter ISO code (e.g., USD, EUR)")
    private String currency;

    @NotNull(message = "Active status must be specified")
    private Boolean isActive;
}

