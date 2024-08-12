package com.example.TransactionServicePrototype.Transaction.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@Entity
public class TransactionLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double limitSum;
    private String limitDateTime;
    @NotNull
    private String expenseCategory;
    private String limitCurrencyShortname = "USD";
    @OneToOne(mappedBy = "transactionLimit")
    private Transaction transaction;

    @PrePersist
    private void init(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
        limitDateTime = ZonedDateTime.now().format(formatter);
    }

    public TransactionLimit(Double limitSum, String expenseCategory) {
        this.limitSum = limitSum;
        this.expenseCategory = expenseCategory;
    }

    public TransactionLimit(){

    }
    @Override
    public String toString() {
        return "TransactionLimit{" +
                "id=" + id +
                ", limitSum=" + limitSum +
                ", limitDateTime=" + limitDateTime +
                ", expenseCategory='" + expenseCategory + '\'' +
                ", limitCurrencyShortname='" + limitCurrencyShortname + '\'' +
                ", transaction=" + transaction +
                '}';
    }
}

