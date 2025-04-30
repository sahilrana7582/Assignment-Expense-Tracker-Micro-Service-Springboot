package com.example.user_service.dto.responseDto;

import com.example.user_service.enums.TransactionCategory;
import com.example.user_service.enums.TransactionStatus;
import com.example.user_service.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponseDTO {
    private String transactionId;
    private String userId;
    private String accountId;
    private BigDecimal amount;
    private String currency;
    private TransactionType type;
    private TransactionCategory category;
    private TransactionStatus status;
    private String description;
}