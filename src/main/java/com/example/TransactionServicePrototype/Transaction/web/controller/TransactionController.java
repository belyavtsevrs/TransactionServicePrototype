package com.example.TransactionServicePrototype.Transaction.web.controller;


import com.example.TransactionServicePrototype.Transaction.model.Transaction;
import com.example.TransactionServicePrototype.Transaction.service.TransactionService;
import com.example.TransactionServicePrototype.Transaction.web.DTO.TransactionDTO;
import com.example.TransactionServicePrototype.Transaction.web.DTO.TransactionResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Transaction API")
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/save")
    public ResponseEntity<Transaction> save(@Valid @RequestBody TransactionDTO transaction){
        Transaction savedTransaction = transactionService.save(transaction);
        return ResponseEntity.status(HttpStatus.OK).body(savedTransaction);
    }

    @GetMapping("/all-transactions")
    @ResponseStatus(HttpStatus.CREATED)
    private ResponseEntity<List<TransactionResponseDTO>> findAll(){
        return ResponseEntity.ok().body(transactionService.findAll());
    }

    @GetMapping("/limit-excess-transaction")
    private ResponseEntity<List<TransactionResponseDTO>> findAllWithLimitExceed(){
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findAllWithLimitExceeded());
    }
}
