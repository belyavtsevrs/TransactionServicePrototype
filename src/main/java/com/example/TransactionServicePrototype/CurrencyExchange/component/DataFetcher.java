package com.example.TransactionServicePrototype.CurrencyExchange.component;


import com.example.TransactionServicePrototype.CurrencyExchange.service.CurrencyService;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class DataFetcher {
    private final CurrencyService currencyService;
    private final DataProcessor dataProcessor;

    public DataFetcher(CurrencyService currencyService, DataProcessor dataProcessor) {
        this.currencyService = currencyService;
        this.dataProcessor = dataProcessor;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fetchDataOnStartup() {
        fetchData();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void fetchData() {
        try {
            String interval = "1day";
            String KztUsd = "USD/KZT";
            String RubUsd = "RUB/USD";
            CompletableFuture<String> kztUsdFuture = currencyService.getExchangeRates(KztUsd, interval);
            CompletableFuture<String> rubUsdFuture = currencyService.getExchangeRates(RubUsd, interval);

            kztUsdFuture.thenAccept(kztUsdRates -> {
                try {
                    dataProcessor.processAndSaveData(kztUsdRates, KztUsd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            rubUsdFuture.thenAccept(rubUsdRates -> {
                try {
                    dataProcessor.processAndSaveData(rubUsdRates, RubUsd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            CompletableFuture.allOf(kztUsdFuture, rubUsdFuture).join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void onDestroy() {
        currencyService.closeClient();
    }
}
