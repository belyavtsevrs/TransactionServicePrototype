package com.example.TransactionServicePrototype.Transaction.web.DTO;

import com.example.TransactionServicePrototype.Transaction.model.Transaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record TransactionDTO(
        @Positive(message = "the field cannot be negative")
        Integer accountFrom,
        @Positive(message = "the field cannot be negative")
        Integer accountTo,
        String currencyShortname,
        @Positive(message = "the sum cannot be negative")
        Double sum,
        @NotBlank
        String expenseCategory
) {
    public class Mapper{
        public static Transaction toEntity(TransactionDTO dto){
            if(dto == null){
                return null;
            }
            Transaction transaction = new Transaction(
                    dto.accountFrom(),
                    dto.accountTo(),
                    dto.currencyShortname(),
                    dto.sum(),
                    dto.expenseCategory()
            );
            return transaction;
        }

        public static TransactionDTO toDTO(Transaction transaction){
            if(transaction == null){
                return null;
            }

            TransactionDTO dto = new TransactionDTO(
                    transaction.getAccountFrom(),
                    transaction.getAccountTo(),
                    transaction.getCurrencyShortname(),
                    transaction.getSum(),
                    transaction.getExpenseCategory()
            );

            return dto;
        }
    }
}
