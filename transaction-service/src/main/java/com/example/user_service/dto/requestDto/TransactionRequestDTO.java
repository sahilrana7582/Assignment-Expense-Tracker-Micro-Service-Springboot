package com.example.user_service.dto.requestDto;


import com.example.user_service.enums.TransactionCategory;
import com.example.user_service.enums.TransactionType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequestDTO {
    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Account ID is required")
    private String accountId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a valid 3-letter ISO code")
    private String currency;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    @NotNull(message = "Category is required")
    private TransactionCategory category;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}





