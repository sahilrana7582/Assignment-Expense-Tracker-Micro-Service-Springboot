package com.example.transaction_service.service;


import com.example.transaction_service.dto.requestDto.TransactionFilterDTO;
import com.example.transaction_service.dto.requestDto.TransactionUpdateDTO;
import com.example.transaction_service.dto.responseDto.TransactionResponseDTO;
import com.example.transaction_service.enums.TransactionStatus;
import com.example.transaction_service.exception.ResourceNotFound;
import com.example.transaction_service.mapper.TransactionMapper;
import com.example.transaction_service.model.Transaction;
import com.example.transaction_service.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Transactional
    public TransactionResponseDTO createTransaction(com.example.transaction_service.dto.requestDto.TransactionRequestDTO requestDTO) {
        log.info("Creating transaction for user: {}", requestDTO.getUserId());

        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .userId(requestDTO.getUserId())
                .accountId(requestDTO.getAccountId())
                .amount(requestDTO.getAmount())
                .currency(requestDTO.getCurrency())
                .type(requestDTO.getType())
                .category(requestDTO.getCategory())
                .status(TransactionStatus.COMPLETED) // Setting as completed by default for simplicity
                .description(requestDTO.getDescription())
                .build();

        transaction = transactionRepository.save(transaction);

        return transactionMapper.toResponseDTO(transaction);
    }

    public TransactionResponseDTO getTransactionById(String transactionId) {
        log.info("Getting transaction with ID: {}", transactionId);

        Transaction transaction = transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new ResourceNotFound("Transaction not found"));

        return transactionMapper.toResponseDTO(transaction);
    }

    public Page<TransactionResponseDTO> getTransactionsByUserId(String userId, Pageable pageable) {
        log.info("Getting transactions for user: {}", userId);

        Page<Transaction> transactions = transactionRepository.findByUserId(userId, pageable);

        return transactions.map(transactionMapper::toResponseDTO);
    }

    public Page<TransactionResponseDTO> getTransactionsByAccountId(String accountId, Pageable pageable) {
        log.info("Getting transactions for account: {}", accountId);

        Page<Transaction> transactions = transactionRepository.findByAccountId(accountId,  pageable);

        return transactions.map(transactionMapper::toResponseDTO);
    }

    @Transactional
    public TransactionResponseDTO updateTransaction(TransactionUpdateDTO updateDTO) {
        log.info("Updating transaction: {}", updateDTO.getTransactionId());

        Transaction transaction = transactionRepository.findByTransactionId(updateDTO.getTransactionId())
                .orElseThrow(() -> new ResourceNotFound("Transaction not found"));

        // Update fields if provided
        if (updateDTO.getCategory() != null) {
            transaction.setCategory(updateDTO.getCategory());
        }

        if (updateDTO.getStatus() != null) {
            transaction.setStatus(updateDTO.getStatus());
        }

        if (updateDTO.getDescription() != null) {
            transaction.setDescription(updateDTO.getDescription());
        }

        transaction = transactionRepository.save(transaction);

        return transactionMapper.toResponseDTO(transaction);
    }

    public List<TransactionResponseDTO> filterTransactions(TransactionFilterDTO filterDTO) {
        log.info("Filtering transactions for user: {}", filterDTO.getUserId());

        List<Transaction> transactions;

        if (filterDTO.getCategory() != null && filterDTO.getStatus() != null) {
            transactions = transactionRepository.findByUserIdAndCategoryAndStatus(
                    filterDTO.getUserId(), filterDTO.getCategory(), filterDTO.getStatus());
        } else if (filterDTO.getCategory() != null) {
            transactions = transactionRepository.findByUserIdAndCategory(
                    filterDTO.getUserId(), filterDTO.getCategory());
        } else if (filterDTO.getStatus() != null) {
            transactions = transactionRepository.findByUserIdAndStatus(
                    filterDTO.getUserId(), filterDTO.getStatus());
        } else if (filterDTO.getType() != null) {
            transactions = transactionRepository.findByUserIdAndType(
                    filterDTO.getUserId(), filterDTO.getType());
        } else {
            transactions = transactionRepository.findByUserId(
                    filterDTO.getUserId(), Pageable.unpaged()).getContent();
        }

        return transactions.stream()
                .map(transactionMapper::toResponseDTO)
                .toList();
    }

    public Map<String, Object> getTransactionStatsByCategory(String userId) {
        log.info("Getting transaction stats by category for user: {}", userId);

        List<Object[]> categoryStats = transactionRepository.countTransactionsByCategory(userId);

        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> categories = new ArrayList<>();

        for (Object[] stat : categoryStats) {
            Map<String, Object> categoryMap = new HashMap<>();
            categoryMap.put("category", stat[0]);
            categoryMap.put("count", stat[1]);
            categories.add(categoryMap);
        }

        result.put("categoryBreakdown", categories);

        return result;
    }

    public Map<String, Object> getTransactionStatsByStatus(String userId) {
        log.info("Getting transaction stats by status for user: {}", userId);

        List<Object[]> statusStats = transactionRepository.countTransactionsByStatus(userId);

        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> statuses = new ArrayList<>();

        for (Object[] stat : statusStats) {
            Map<String, Object> statusMap = new HashMap<>();
            statusMap.put("status", stat[0]);
            statusMap.put("count", stat[1]);
            statuses.add(statusMap);
        }

        result.put("statusBreakdown", statuses);

        return result;
    }
}