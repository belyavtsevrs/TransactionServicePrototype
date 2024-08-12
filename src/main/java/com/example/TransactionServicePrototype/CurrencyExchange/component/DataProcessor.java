package com.example.TransactionServicePrototype.CurrencyExchange.component;


import com.example.TransactionServicePrototype.CurrencyExchange.model.ExchangeRate;
import com.example.TransactionServicePrototype.CurrencyExchange.repository.ExchangeRateRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class DataProcessor {
    private final ExchangeRateRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DataProcessor(ExchangeRateRepository repository) {
        this.repository = repository;
    }

    public void processAndSaveData(String jsonData, String symbol) throws IOException {
        log.info("jsonData = {}",jsonData);
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode valuesNode = rootNode.path("values");
        log.info("valuesNode = {}",valuesNode);
        if (valuesNode.isArray() && valuesNode.size() > 0) {
            JsonNode latestNode = valuesNode.get(0);
            String date = latestNode.path("datetime").asText();
            double close = latestNode.path("close").asDouble();

            ExchangeRate exchangeRate = new ExchangeRate();
            exchangeRate.setSymbol(symbol);
            exchangeRate.setDate(date);
            exchangeRate.setClose(close);
            KztToUsdExchange(exchangeRate);

            log.info("exchangeRate = {}",exchangeRate);
            repository.save(exchangeRate);
        } else {
            ExchangeRate lastRate = repository.findFirstBySymbolOrderByDateDesc(symbol);

            ExchangeRate newRate = new ExchangeRate();
            newRate.setSymbol(symbol);
            newRate.setDate(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
            newRate.setClose(lastRate.getClose());

            repository.save(newRate);
        }
    }

    private void KztToUsdExchange(ExchangeRate exchangeRate){
        if("USD/KZT".equals(exchangeRate.getSymbol())){
            exchangeRate.setSymbol("KZT/USD");
            BigDecimal bigDecimal = new BigDecimal(1/exchangeRate.getClose()).setScale(5, RoundingMode.HALF_UP);
            exchangeRate.setClose(bigDecimal.doubleValue());
        }
    }
}
