package com.example.transaction_service.service;


import com.example.transaction_service.dto.requestDto.*;
import com.example.transaction_service.dto.responseDto.*;
import com.example.transaction_service.enums.AccountType;
import com.example.transaction_service.exception.ResourceNotFound;
import com.example.transaction_service.mapper.AccountMapper;
import com.example.transaction_service.model.Account;
import com.example.transaction_service.repository.AccountRepository;
import com.example.expense_tracker.common.UserCreatedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;


    @KafkaListener(
            topics = "${kafka.topic.name}",
            groupId = "${kafka.topic.group-id}",
            containerFactory = "userCreatedEventKafkaListenerContainerFactory"
    )
    public void consumeUserCreatedEvent(ConsumerRecord<String, UserCreatedEvent> record) {
        UserCreatedEvent event = record.value();
        Account account = Account.builder()
                .accountId(UUID.randomUUID().toString())
                .userId(event.getUserId())
                .name("Default Account")
                .type(AccountType.DUMMY)
                .balance(BigDecimal.valueOf(0.0))
                .currency("INR")
                .isActive(true)
                .build();
        accountRepository.save(account);
    }

    @Override
    public AccountResponseDto createAccount(AccountRequestDto dto) {
        Account account = Account.builder()
                .accountId(UUID.randomUUID().toString())
                .userId(dto.getUserId())
                .name(dto.getName())
                .type(dto.getType())
                .balance(dto.getBalance())
                .currency(dto.getCurrency())
                .isActive(dto.getIsActive())
                .build();

        Account saved = accountRepository.save(account);
        return accountMapper.toAccountResponseDto(saved);
    }

    @Override
    public AccountResponseDto getAccountById(String accountId) {
        Account account = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new ResourceNotFound("Account not found with ID: " + accountId));
        return accountMapper.toAccountResponseDto(account);
    }

    @Override
    public List<AccountResponseDto> getAllAccountsByUserId(String userId) {
        List<Account> accounts = accountRepository.findAllByUserId(userId);
        return accounts.stream().map(accountMapper::toAccountResponseDto).collect(Collectors.toList());
    }

    @Override
    public AccountResponseDto updateAccount(String accountId, AccountUpdateDto dto) {
        Account account = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new ResourceNotFound("Account not found with ID: " + accountId));

        if (dto.getName() != null) account.setName(dto.getName());
        if (dto.getType() != null) account.setType(dto.getType());
        if (dto.getBalance() != null) account.setBalance(dto.getBalance());
        if (dto.getCurrency() != null) account.setCurrency(dto.getCurrency());
        if (dto.getIsActive() != null) account.setActive(dto.getIsActive());

        Account updatedAccount = accountRepository.save(account);
        return accountMapper.toAccountResponseDto(updatedAccount);

    }

    @Override
    public void deleteAccount(String accountId) {
        Account account = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new ResourceNotFound("Account not found with ID: " + accountId));
        accountRepository.delete(account);
    }


}
