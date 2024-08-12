package com.example.TransactionServicePrototype.Transaction.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.TransactionServicePrototype.CurrencyExchange.repository.ExchangeRateRepository;
import com.example.TransactionServicePrototype.Transaction.model.Transaction;
import com.example.TransactionServicePrototype.Transaction.model.TransactionLimit;
import com.example.TransactionServicePrototype.Transaction.service.LimitService;
import com.example.TransactionServicePrototype.Transaction.service.TransactionService;
import com.example.TransactionServicePrototype.Transaction.web.DTO.TransactionDTO;
import com.example.TransactionServicePrototype.Transaction.web.DTO.TransactionResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {
    @Mock
    private TransactionService transactionService;
    @Mock
    private LimitService limitService;
    @Mock
    private ExchangeRateRepository exchangeRateRepository;
    @InjectMocks
    private TransactionController transactionController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    void save() throws Exception {
        Transaction transaction = new Transaction(
                13,
                213,
                "service",
                127590.53d,
                "RUB",
                false
        );

        Mockito.when(transactionService.save(Mockito.any(TransactionDTO.class))).thenReturn(transaction);

        mockMvc.perform(post("/transactions/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(transaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(transaction.getId()))
                .andExpect(jsonPath("$.accountFrom").value(transaction.getAccountFrom()))
                .andExpect(jsonPath("$.accountTo").value(transaction.getAccountTo()))
                .andExpect(jsonPath("$.currencyShortname").value(transaction.getCurrencyShortname()))
                .andExpect(jsonPath("$.sum").value(transaction.getSum()))
                .andExpect(jsonPath("$.expenseCategory").value(transaction.getExpenseCategory()))
                .andExpect(jsonPath("$.dateTime").value(transaction.getDateTime()))
                .andExpect(jsonPath("$.limitExceeded").value(transaction.getLimitExceeded()));
    }

    @Test
    void findAll() throws Exception {
        Transaction transaction = new Transaction(
                13,
                213,
                "service",
                127590.53,
                "RUB",
                false
        );
        TransactionLimit existingLimit = new TransactionLimit(
                1000.0,
                "service"
        );
        transaction.setTransactionLimit(existingLimit);

        TransactionResponseDTO dto = TransactionResponseDTO.Mapper.toDTO(transaction);
        List<TransactionResponseDTO> transactionResponseDTOs = List.of(dto);

        Mockito.when(transactionService.findAll()).thenReturn(transactionResponseDTOs);

        mockMvc.perform(get("/transactions/all-transactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountFrom").value(dto.accountFrom()))
                .andExpect(jsonPath("$[0].accountTo").value(dto.accountTo()))
                .andExpect(jsonPath("$[0].currencyShortname").value(dto.currencyShortname()))
                .andExpect(jsonPath("$[0].sum").value(dto.sum()))
                .andExpect(jsonPath("$[0].expenseCategory").value(dto.expenseCategory()))
                .andExpect(jsonPath("$[0].dateTime").value(dto.dateTime()))
                .andExpect(jsonPath("$[0].limitSum").value(dto.limitSum()))
                .andExpect(jsonPath("$[0].limitCurrencyShortname").value(dto.limitCurrencyShortname()))
                .andExpect(jsonPath("$[0].limitDateTime").value(dto.limitDateTime()));
    }
    @Test
    void findAllWithLimitExceeded() throws Exception {
        Transaction transaction = new Transaction(
                13,
                213,
                "service",
                127590.53,
                "RUB",
                false
        );
        TransactionLimit existingLimit = new TransactionLimit(
                1000.0,
                "service"
        );
        transaction.setTransactionLimit(existingLimit);

        TransactionResponseDTO dto = TransactionResponseDTO.Mapper.toDTO(transaction);
        List<TransactionResponseDTO> transactionResponseDTOs = List.of(dto);

        Mockito.when(transactionService.findAllWithLimitExceeded()).thenReturn(transactionResponseDTOs);

        mockMvc.perform(get("/transactions/limit-excess-transaction")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountFrom").value(dto.accountFrom()))
                .andExpect(jsonPath("$[0].accountTo").value(dto.accountTo()))
                .andExpect(jsonPath("$[0].currencyShortname").value(dto.currencyShortname()))
                .andExpect(jsonPath("$[0].sum").value(dto.sum()))
                .andExpect(jsonPath("$[0].expenseCategory").value(dto.expenseCategory()))
                .andExpect(jsonPath("$[0].dateTime").value(dto.dateTime()))
                .andExpect(jsonPath("$[0].limitSum").value(dto.limitSum()))
                .andExpect(jsonPath("$[0].limitCurrencyShortname").value(dto.limitCurrencyShortname()))
                .andExpect(jsonPath("$[0].limitDateTime").value(dto.limitDateTime()));
    }
}