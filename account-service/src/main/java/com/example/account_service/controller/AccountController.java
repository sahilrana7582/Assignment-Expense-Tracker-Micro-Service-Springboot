package com.example.account_service.controller;


import com.example.account_service.dto.requestDto.AccountRequestDto;
import com.example.account_service.dto.requestDto.AccountUpdateDto;
import com.example.account_service.dto.responseDto.AccountResponseDto;
import com.example.account_service.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(@Valid @RequestBody AccountRequestDto requestDTO) {
        AccountResponseDto created = accountService.createAccount(requestDTO);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable String accountId) {
        AccountResponseDto account = accountService.getAccountById(accountId);
        return ResponseEntity.ok(account);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountResponseDto>> getAccountsByUserId(@PathVariable String userId) {
        List<AccountResponseDto> accounts = accountService.getAllAccountsByUserId(userId);
        return ResponseEntity.ok(accounts);
    }


    @PutMapping("/{accountId}")
    public ResponseEntity<AccountResponseDto> updateAccount(
            @PathVariable String accountId,
            @Valid @RequestBody AccountUpdateDto updateDTO) {
        AccountResponseDto updated = accountService.updateAccount(accountId, updateDTO);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }
}
