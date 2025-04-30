package com.example.transaction_service.repository;

import com.example.transaction_service.enums.TransactionCategory;
import com.example.transaction_service.enums.TransactionStatus;
import com.example.transaction_service.enums.TransactionType;
import com.example.transaction_service.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionId(String transactionId);

    Page<Transaction> findByUserId(String userId, Pageable pageable);

    Page<Transaction> findByAccountId(String accountId, Pageable pageable);

    List<Transaction> findByUserIdAndCategory(String userId, TransactionCategory category);

    List<Transaction> findByUserIdAndStatus(String userId, TransactionStatus status);

    List<Transaction> findByUserIdAndType(String userId, TransactionType type);

    @Query(
            "SELECT t FROM Transaction t WHERE t.userId = :userId AND t.category = :category AND t.status = :status"
    )
    List<Transaction> findByUserIdAndCategoryAndStatus(
            @Param("userId") String userId,
            @Param("category") TransactionCategory category,
            @Param("status") TransactionStatus status
    );

    @Query("SELECT t.category, COUNT(t) as count FROM Transaction t WHERE t.userId = :userId GROUP BY t.category ORDER BY count DESC")
    List<Object[]> countTransactionsByCategory(@Param("userId") String userId);

    @Query("SELECT t.status, COUNT(t) as count FROM Transaction t WHERE t.userId = :userId GROUP BY t.status ORDER BY count DESC")
    List<Object[]> countTransactionsByStatus(@Param("userId") String userId);

}
