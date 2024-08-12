package com.example.TransactionServicePrototype.Transaction.repository;

import com.example.TransactionServicePrototype.Transaction.model.TransactionLimit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LimitRepository extends JpaRepository<TransactionLimit,Long> {
    List<TransactionLimit> findByExpenseCategory(String category);
    Optional<TransactionLimit> findFirstByExpenseCategoryOrderByIdDesc(String category);
}
