package com.example.transaction_service.controller;


import com.example.transaction_service.dto.requestDto.TransactionFilterDTO;
import com.example.transaction_service.dto.requestDto.TransactionRequestDTO;
import com.example.transaction_service.dto.requestDto.TransactionUpdateDTO;
import com.example.transaction_service.dto.responseDto.TransactionResponseDTO;
import com.example.transaction_service.enums.TransactionCategory;
import com.example.transaction_service.enums.TransactionStatus;
import com.example.transaction_service.enums.TransactionType;
import com.example.transaction_service.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @Valid @RequestBody TransactionRequestDTO requestDTO) {
        log.info("Request to create transaction received");
        TransactionResponseDTO responseDTO = transactionService.createTransaction(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(
            @PathVariable String transactionId) {
        log.info("Request to get transaction by ID: {}", transactionId);
        TransactionResponseDTO responseDTO = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<TransactionResponseDTO>> getTransactionsByUserId(
            @PathVariable String userId,
            Pageable pageable) {
        log.info("Request to get transactions for user ID: {}", userId);
        Page<TransactionResponseDTO> transactions = transactionService.getTransactionsByUserId(userId, pageable);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<Page<TransactionResponseDTO>> getTransactionsByAccountId(
            @PathVariable String accountId,
            Pageable pageable) {
        log.info("Request to get transactions for account ID: {}", accountId);
        Page<TransactionResponseDTO> transactions = transactionService.getTransactionsByAccountId(accountId, pageable);
        return ResponseEntity.ok(transactions);
    }

    @PutMapping
    public ResponseEntity<TransactionResponseDTO> updateTransaction(
            @Valid @RequestBody TransactionUpdateDTO updateDTO) {
        log.info("Request to update transaction: {}", updateDTO.getTransactionId());
        TransactionResponseDTO responseDTO = transactionService.updateTransaction(updateDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<TransactionResponseDTO>> filterTransactions(
            @RequestBody TransactionFilterDTO filterDTO) {
        log.info("Request to filter transactions");
        List<TransactionResponseDTO> transactions = transactionService.filterTransactions(filterDTO);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/stats/category/{userId}")
    public ResponseEntity<Map<String, Object>> getTransactionStatsByCategory(
            @PathVariable String userId) {
        log.info("Request to get transaction stats by category for user ID: {}", userId);
        Map<String, Object> stats = transactionService.getTransactionStatsByCategory(userId);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/stats/status/{userId}")
    public ResponseEntity<Map<String, Object>> getTransactionStatsByStatus(
            @PathVariable String userId) {
        log.info("Request to get transaction stats by status for user ID: {}", userId);
        Map<String, Object> stats = transactionService.getTransactionStatsByStatus(userId);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/categories")
    public ResponseEntity<TransactionCategory[]> getAllTransactionCategories() {
        log.info("Request to get all transaction categories");
        return ResponseEntity.ok(TransactionCategory.values());
    }

    @GetMapping("/statuses")
    public ResponseEntity<TransactionStatus[]> getAllTransactionStatuses() {
        log.info("Request to get all transaction statuses");
        return ResponseEntity.ok(TransactionStatus.values());
    }

    @GetMapping("/types")
    public ResponseEntity<TransactionType[]> getAllTransactionTypes() {
        log.info("Request to get all transaction types");
        return ResponseEntity.ok(TransactionType.values());
    }
}
