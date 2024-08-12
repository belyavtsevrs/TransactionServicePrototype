package com.example.TransactionServicePrototype.CurrencyExchange.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    private String date;
    private double close;

    public ExchangeRate(String symbol, double close) {
        this.symbol = symbol;
        this.close = close;
    }
    public ExchangeRate(){

    }
    @Override
    public String toString() {
        return "ExchangeRate{" +
                "symbol='" + symbol + '\'' +
                ", date='" + date + '\'' +
                ", rate=" + close +
                '}';
    }
}
