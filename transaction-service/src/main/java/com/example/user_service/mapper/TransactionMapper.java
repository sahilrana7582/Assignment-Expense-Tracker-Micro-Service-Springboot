package com.example.user_service.mapper;


import com.example.user_service.dto.requestDto.TransactionRequestDTO;
import com.example.user_service.dto.responseDto.TransactionResponseDTO;
import com.example.user_service.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionResponseDTO toResponseDTO(Transaction transaction) {
        return TransactionResponseDTO.builder()
                .transactionId(transaction.getTransactionId())
                .userId(transaction.getUserId())
                .accountId(transaction.getAccountId())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .type(transaction.getType())
                .category(transaction.getCategory())
                .status(transaction.getStatus())
                .description(transaction.getDescription())
                .build();
    }

    public Transaction toEntity(TransactionRequestDTO requestDTO) {
        return Transaction.builder()
                .userId(requestDTO.getUserId())
                .accountId(requestDTO.getAccountId())
                .amount(requestDTO.getAmount())
                .currency(requestDTO.getCurrency())
                .type(requestDTO.getType())
                .category(requestDTO.getCategory())
                .description(requestDTO.getDescription())
                .build();
    }
}