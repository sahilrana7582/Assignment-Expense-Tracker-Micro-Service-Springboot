package com.example.account_service.service;




import com.example.account_service.dto.requestDto.AccountRequestDto;
import com.example.account_service.dto.requestDto.AccountUpdateDto;
import com.example.account_service.dto.responseDto.AccountResponseDto;

import java.util.List;

public interface AccountService {
    AccountResponseDto createAccount(AccountRequestDto requestDTO);

    AccountResponseDto getAccountById(String accountId);

    List<AccountResponseDto> getAllAccountsByUserId(String userId);

    AccountResponseDto updateAccount(String accountId, AccountUpdateDto updateDTO);

    void deleteAccount(String accountId);
}

