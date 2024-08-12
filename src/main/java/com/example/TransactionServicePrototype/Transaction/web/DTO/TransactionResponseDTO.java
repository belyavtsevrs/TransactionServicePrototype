package com.example.TransactionServicePrototype.Transaction.web.DTO;

import com.example.TransactionServicePrototype.Transaction.model.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record TransactionResponseDTO
        (String accountFrom,
         String accountTo,
         String currencyShortname,
         Double sum,
         String expenseCategory,
         String dateTime,
         Double limitSum,
         String limitCurrencyShortname,
         String limitDateTime
        )
{
    public class Mapper{
        public static TransactionResponseDTO toDTO(Transaction transaction){
            if(transaction == null){
                return null;
            }
            Double limitSum = BigDecimal.valueOf(transaction.getTransactionLimit().getLimitSum())
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
            String limitCurrencyShortname = transaction.getTransactionLimit().getLimitCurrencyShortname();
            String limitDateTime = transaction.getTransactionLimit().getLimitDateTime();

            TransactionResponseDTO transactionResponseDTO
                    = new TransactionResponseDTO(
                    String.format("%010d",transaction.getAccountFrom()),
                    String.format("%010d",transaction.getAccountTo()),
                    transaction.getCurrencyShortname(),
                    transaction.getSum(),
                    transaction.getExpenseCategory(),
                    transaction.getDateTime(),
                    limitSum,
                    limitCurrencyShortname,
                    limitDateTime
            );
            return transactionResponseDTO;
        }
    }
}
