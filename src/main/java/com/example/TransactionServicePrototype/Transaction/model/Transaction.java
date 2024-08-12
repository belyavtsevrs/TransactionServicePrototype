package com.example.TransactionServicePrototype.Transaction.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Positive(message = "the field must contain only positive number")
    private Integer accountFrom;
    @Positive(message = "the field must contain only positive number")
    private Integer accountTo;
    @Size(max = 3,min = 3)
    private String currencyShortname;
    @Positive(message = "the field must contain only positive number")
    private Double sum;
    private String expenseCategory;
    private String dateTime;
    private Boolean limitExceeded = false;
    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn( name = "limit_id",referencedColumnName = "id")
    private TransactionLimit transactionLimit;

    public Transaction(Integer accountFrom, Integer accountTo, String currencyShortname, Double sum, String expenseCategory, Boolean limitExceeded) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.currencyShortname = currencyShortname;
        this.sum = sum;
        this.expenseCategory = expenseCategory;
        this.limitExceeded = limitExceeded;
    }
    public Transaction(Integer accountFrom, Integer accountTo, String currencyShortname, Double sum, String expenseCategory) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.currencyShortname = currencyShortname;
        this.sum = sum;
        this.expenseCategory = expenseCategory;
    }
    public Transaction(){

    }

    @PrePersist
    private void init(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
        dateTime = ZonedDateTime.now().format(formatter);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                ", currencyShortname='" + currencyShortname + '\'' +
                ", sum=" + sum +
                ", expenseCategory='" + expenseCategory + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", limitExceeded=" + limitExceeded +
                '}';
    }
}
