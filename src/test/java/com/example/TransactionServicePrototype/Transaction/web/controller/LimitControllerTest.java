package com.example.TransactionServicePrototype.Transaction.web.controller;

import com.example.TransactionServicePrototype.Transaction.model.TransactionLimit;
import com.example.TransactionServicePrototype.Transaction.service.LimitService;
import com.example.TransactionServicePrototype.Transaction.web.DTO.TransactionLimitDTO;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class LimitControllerTest {
    @Mock
    private LimitService limitService;
    @InjectMocks
    private LimitController limitController;
    private MockMvc mockMvc;
    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(limitController).build();
    }
    @Test
    void setLimit() throws Exception{
        TransactionLimit transactionLimit = new TransactionLimit(
                1000.0,
                "service"
        );
        Mockito.when(limitService.setLimit(Mockito.any(TransactionLimitDTO.class))).thenReturn(transactionLimit);

        mockMvc.perform(post("/limits/set-limit")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(transactionLimit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(transactionLimit.getId()))
                .andExpect(jsonPath("$.limitSum").value(transactionLimit.getLimitSum()))
                .andExpect(jsonPath("$.expenseCategory").value(transactionLimit.getExpenseCategory()))
                .andExpect(jsonPath("$.limitCurrencyShortname").value(transactionLimit.getLimitCurrencyShortname()))
                .andExpect(jsonPath("$.limitDateTime").value(transactionLimit.getLimitDateTime()));
    }
}