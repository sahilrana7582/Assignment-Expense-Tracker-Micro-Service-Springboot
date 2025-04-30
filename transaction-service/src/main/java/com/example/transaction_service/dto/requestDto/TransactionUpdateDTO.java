package com.example.transaction_service.dto.requestDto;


import com.example.transaction_service.enums.TransactionCategory;
import com.example.transaction_service.enums.TransactionStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionUpdateDTO {
    @NotNull(message = "Transaction ID is required")
    private String transactionId;

    private TransactionCategory category;
    private TransactionStatus status;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}
