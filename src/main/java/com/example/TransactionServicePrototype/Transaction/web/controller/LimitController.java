package com.example.TransactionServicePrototype.Transaction.web.controller;

import com.example.TransactionServicePrototype.Transaction.model.TransactionLimit;
import com.example.TransactionServicePrototype.Transaction.service.LimitService;
import com.example.TransactionServicePrototype.Transaction.web.DTO.TransactionLimitDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/limits")
public class LimitController {
    private final LimitService limitService;

    public LimitController(LimitService limitService) {
        this.limitService = limitService;
    }

    @PostMapping("/set-limit")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TransactionLimit> setLimit(@Valid @RequestBody TransactionLimitDTO limit){
        TransactionLimit transactionLimit = limitService.setLimit(limit);
        return ResponseEntity.ok().body(transactionLimit);
    }

}
