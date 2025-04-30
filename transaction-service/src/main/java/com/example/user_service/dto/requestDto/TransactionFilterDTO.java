package com.example.user_service.dto.requestDto;

import com.example.user_service.enums.TransactionCategory;
import com.example.user_service.enums.TransactionStatus;
import com.example.user_service.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionFilterDTO {

    private String userId;
    private String accountId;
    private TransactionCategory category;
    private TransactionStatus status;
    private TransactionType type;
    private String currency;

}