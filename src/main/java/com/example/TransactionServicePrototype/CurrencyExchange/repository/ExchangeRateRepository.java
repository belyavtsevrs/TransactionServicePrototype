package com.example.TransactionServicePrototype.CurrencyExchange.repository;

import com.example.TransactionServicePrototype.CurrencyExchange.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate,Long> {
    ExchangeRate findBySymbolOrderByDateDesc(String symbol);
    ExchangeRate findFirstBySymbolOrderByDateDesc(String symbol);
}
