package com.example.transaction_service.mapper;


import com.example.transaction_service.dto.requestDto.AccountRequestDto;
import com.example.transaction_service.dto.responseDto.AccountResponseDto;
import com.example.transaction_service.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {


    public Account toAccount(AccountRequestDto accountRequestDto) {
        return Account.builder()
                .userId(accountRequestDto.getUserId())
                .name(accountRequestDto.getName())
                .type(accountRequestDto.getType())
                .balance(accountRequestDto.getBalance())
                .currency(accountRequestDto.getCurrency())
                .isActive(accountRequestDto.getIsActive())
                .build();
    }


    public AccountRequestDto toAccountRequestDto(Account account) {
        return AccountRequestDto.builder()
                .userId(account.getUserId())
                .name(account.getName())
                .type(account.getType())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .isActive(account.isActive())
                .build();
    }


    public AccountResponseDto toAccountResponseDto(Account account) {
        return AccountResponseDto.builder()
                .id(account.getId())
                .accountId(account.getAccountId())
                .userId(account.getUserId())
                .name(account.getName())
                .type(account.getType())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .isActive(account.isActive())
                .build();
    }
}
