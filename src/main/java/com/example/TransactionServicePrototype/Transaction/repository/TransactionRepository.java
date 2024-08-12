package com.example.TransactionServicePrototype.Transaction.repository;

import com.example.TransactionServicePrototype.Transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
