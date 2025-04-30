package com.example.user_service.repository;

import com.example.user_service.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountId(String accountId);
    List<Account> findAllByUserId(String userId);
}
