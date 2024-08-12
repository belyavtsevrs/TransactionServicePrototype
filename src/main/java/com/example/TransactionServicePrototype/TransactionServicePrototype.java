package com.example.TransactionServicePrototype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TransactionServicePrototype {

    public static void main(String[] args) {
        SpringApplication.run(TransactionServicePrototype.class, args);
    }

}
