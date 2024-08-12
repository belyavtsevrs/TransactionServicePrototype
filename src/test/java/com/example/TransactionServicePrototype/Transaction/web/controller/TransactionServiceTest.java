package com.example.TransactionServicePrototype.Transaction.web.controller;

import com.example.TransactionServicePrototype.CurrencyExchange.model.ExchangeRate;
import com.example.TransactionServicePrototype.CurrencyExchange.repository.ExchangeRateRepository;
import com.example.TransactionServicePrototype.Transaction.model.Transaction;
import com.example.TransactionServicePrototype.Transaction.model.TransactionLimit;
import com.example.TransactionServicePrototype.Transaction.repository.TransactionRepository;
import com.example.TransactionServicePrototype.Transaction.service.LimitService;
import com.example.TransactionServicePrototype.Transaction.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static reactor.core.publisher.Mono.when;


@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private ExchangeRateRepository exchangeRateRepository;
    @Mock
    private LimitService limitService;
    @InjectMocks
    private TransactionService transactionService;

    @Test
    void save_IfFlagTrue_True() {
        //TODO ADAPT FOR DTO
        ExchangeRate exchangeRate = new ExchangeRate(
                "RUB/USD",
                0.012
        );

        Mockito.when(exchangeRateRepository.findFirstBySymbolOrderByDateDesc("RUB/USD"))
                .thenReturn(exchangeRate);

        Transaction transaction = new Transaction(
                13,
                213,
                "RUB",
                127590.53,
                "service"
        );

        TransactionLimit existingLimit = new TransactionLimit(
                1000.0,
                "service"
        );
        transaction.setTransactionLimit(existingLimit);

        Mockito.when(limitService.lastLimit(transaction.getExpenseCategory())).thenReturn(existingLimit);
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
        Transaction after = transactionService.save(transaction);

        assertNotNull(after);
        assertEquals(true,after.getLimitExceeded());
        assertEquals(true, after.getTransactionLimit().getLimitSum() < 0);
    }
    /*@Test
    void save_IfFlagTrue_True() {
        ExchangeRate exchangeRate = new ExchangeRate(
                "RUB/USD",
                0.012
        );

        Mockito.when(exchangeRateRepository.findFirstBySymbolOrderByDateDesc("RUB/USD"))
                .thenReturn(exchangeRate);

        TransactionDTO transaction = new TransactionDTO(
                13,
                213,
                "RUB",
                127590.53,
                "service"
        );

        TransactionLimit transactionLimit = new TransactionLimit(
                1000.0,
                "service"
        );


        Mockito.when(limitService.lastLimit(transaction.expenseCategory())).thenReturn(transactionLimit);
        Mockito.when(transactionService.save(transaction));
        Transaction after = transactionService.save(transaction);
        after.setTransactionLimit(transactionLimit);
        assertNotNull(after);
        assertEquals(true,after.getLimitExceeded());
        assertEquals(true, after.getTransactionLimit().getLimitSum() < 0);
    }*/
}