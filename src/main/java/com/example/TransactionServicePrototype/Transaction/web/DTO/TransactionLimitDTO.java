package com.example.TransactionServicePrototype.Transaction.web.DTO;

import com.example.TransactionServicePrototype.Transaction.model.TransactionLimit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record TransactionLimitDTO(
        @Positive(message = "the limit amount cannot be negative")
        Double limitSum,
        @NotBlank
        String expenseCategory
) {
    public class Mapper{
        public static TransactionLimit toEntity(TransactionLimitDTO dto){
            if(dto == null){
                return null;
            }
            TransactionLimit transactionLimit = new TransactionLimit();
            transactionLimit.setLimitSum(dto.limitSum);
            transactionLimit.setExpenseCategory(dto.expenseCategory);
            return transactionLimit;
        }
    }
}
