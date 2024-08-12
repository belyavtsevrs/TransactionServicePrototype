package com.example.TransactionServicePrototype.Transaction.service;


import com.example.TransactionServicePrototype.Transaction.model.TransactionLimit;
import com.example.TransactionServicePrototype.Transaction.repository.LimitRepository;
import com.example.TransactionServicePrototype.Transaction.web.DTO.TransactionLimitDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class LimitService {
    private final LimitRepository limitRepository;

    public LimitService(LimitRepository limitRepository) {
        this.limitRepository = limitRepository;
    }
    public TransactionLimit setLimit(TransactionLimitDTO dto){
        TransactionLimit transactionLimit = TransactionLimitDTO.Mapper.toEntity(dto);

        limitRepository.findFirstByExpenseCategoryOrderByIdDesc(dto.expenseCategory())
                .ifPresent(previousLimit ->
                        transactionLimit.setLimitSum(previousLimit.getLimitSum() + transactionLimit.getLimitSum()));

        return limitRepository.save(transactionLimit);
    }

    public TransactionLimit lastLimit(String limitCategory){
        TransactionLimit transactionLimit = limitRepository.findFirstByExpenseCategoryOrderByIdDesc(limitCategory).orElse(null);
        TransactionLimit newLimit = new TransactionLimit();

        if(transactionLimit == null){
            newLimit.setLimitSum(1000.0d);
            newLimit.setExpenseCategory(limitCategory);
            return newLimit;
        } else
            newLimit.setExpenseCategory(transactionLimit.getExpenseCategory());
            newLimit.setLimitSum(transactionLimit.getLimitSum());

        log.info("limit = {}", transactionLimit);

        return newLimit;
    }
}
