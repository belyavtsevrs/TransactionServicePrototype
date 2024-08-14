package com.example.TransactionServicePrototype.CurrencyExchange.service;


import  org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class CurrencyService {
    @Value("${api.twelvedata.key}")
    private String key;
    private final AsyncHttpClient client = Dsl.asyncHttpClient();

    public CompletableFuture<String> getExchangeRates(String symbol, String interval) {
        String url = String.format("https://api.twelvedata.com/time_series?symbol=%s&interval=%s&apikey=%s&format=JSON",
                symbol, interval, key);

        return client.prepareGet(url)
                .execute()
                .toCompletableFuture()
                .thenApply(response -> response.getResponseBody());
    }

    public void closeClient() {
        try {
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
