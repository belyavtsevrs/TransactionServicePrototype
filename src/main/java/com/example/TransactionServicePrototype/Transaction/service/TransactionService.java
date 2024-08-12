package com.example.TransactionServicePrototype.Transaction.service;

import com.example.TransactionServicePrototype.CurrencyExchange.repository.ExchangeRateRepository;
import com.example.TransactionServicePrototype.Transaction.model.TransactionLimit;
import com.example.TransactionServicePrototype.Transaction.model.Transaction;
import com.example.TransactionServicePrototype.Transaction.repository.LimitRepository;
import com.example.TransactionServicePrototype.Transaction.repository.TransactionRepository;
import com.example.TransactionServicePrototype.Transaction.web.DTO.TransactionDTO;
import com.example.TransactionServicePrototype.Transaction.web.DTO.TransactionResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final LimitRepository limitRepository;
    private final LimitService limitService;

    public TransactionService(TransactionRepository transactionRepository,
                              ExchangeRateRepository exchangeRateRepository,
                              LimitRepository limitRepository, LimitRepository limitRepository1, LimitService limitService) {
        this.transactionRepository = transactionRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.limitRepository = limitRepository1;
        this.limitService = limitService;
    }
    public Transaction save(TransactionDTO transactionDTO) {
        Transaction transaction = TransactionDTO.Mapper.toEntity(transactionDTO);
        TransactionLimit transactionLimit = limitService.lastLimit(transaction.getExpenseCategory());
        Double transactionSum = transactionCurrencyToUSD(transaction);
        transactionLimit.setLimitSum(transactionLimit.getLimitSum() - transactionSum);

        if(transactionLimit.getLimitSum() < 0){
            transaction.setLimitExceeded(true);
        }

        log.info("limit = {}", transactionLimit);
        transaction.setTransactionLimit(transactionLimit);

        log.info("transaction = {}",transaction);
        return transactionRepository.save(transaction);
    }
    public Transaction save(Transaction transaction){
        TransactionLimit transactionLimit = limitService.lastLimit(transaction.getExpenseCategory());
        Double transactionSum = transactionCurrencyToUSD(transaction);
        transactionLimit.setLimitSum(transactionLimit.getLimitSum() - transactionSum);

        if(transactionLimit.getLimitSum() < 0){
            transaction.setLimitExceeded(true);
        }

        log.info("limit = {}", transactionLimit);
        transaction.setTransactionLimit(transactionLimit);

        log.info("transaction = {}",transaction);
        return transactionRepository.save(transaction);
    }

    private Double transactionCurrencyToUSD(Transaction transaction){
        //TODO:DONT FORGET v
        String currency = transaction.getCurrencyShortname();
        Double sum = transaction.getSum();
        Double USD;

        if("KZT".startsWith(currency)){
            USD = sum * exchangeRateRepository.findFirstBySymbolOrderByDateDesc("KZT/USD").getClose();
        }else {
            USD = sum * exchangeRateRepository.findFirstBySymbolOrderByDateDesc("RUB/USD").getClose();
        }

        return USD;
    }

    public List<TransactionResponseDTO> findAll(){
        return transactionRepository.findAll()
                                    .stream()
                                    .map(TransactionResponseDTO.Mapper::toDTO)
                                    .toList();
    }

    public List<TransactionResponseDTO> findAllWithLimitExceeded(){
        return transactionRepository.findAll()
                .stream()
                .filter(x -> x.getLimitExceeded() == true)
                .map(TransactionResponseDTO.Mapper::toDTO)
                .toList();
    }
}
