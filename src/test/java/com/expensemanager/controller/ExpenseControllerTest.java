package com.expensemanager.controller;

import com.expensemanager.model.Expense;
import com.expensemanager.service.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private ExpenseController expenseController;

    private ObjectMapper objectMapper;
    private Expense sampleExpense;
    private final UUID expenseId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();
        objectMapper = new ObjectMapper();

        sampleExpense = new Expense();
        sampleExpense.setId(expenseId);
        sampleExpense.setAmount(new BigDecimal("50.00"));
        sampleExpense.setCategory("Transport");
        sampleExpense.setVendor("Uber");
    }

    @Test
    void testCreateExpense() throws Exception {
        when(expenseService.createExpense(any(Expense.class))).thenReturn(sampleExpense);

        mockMvc.perform(post("/api/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleExpense)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/expenses/" + expenseId))
                .andExpect(jsonPath("$.id").value(expenseId.toString()))
                .andExpect(jsonPath("$.vendor").value("Uber"))
                .andExpect(jsonPath("$.amount").value(50.00));

        verify(expenseService, times(1)).createExpense(any(Expense.class));
    }

    @Test
    void testGetAllExpenses() throws Exception {
        when(expenseService.getAllExpenses()).thenReturn(Arrays.asList(sampleExpense));

        mockMvc.perform(get("/api/expenses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].vendor").value("Uber"));

        verify(expenseService, times(1)).getAllExpenses();
    }

    @Test
    void testGetExpenseById_Found() throws Exception {
        when(expenseService.getExpenseById(expenseId)).thenReturn(sampleExpense);

        mockMvc.perform(get("/api/expenses/{id}", expenseId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expenseId.toString()));

        verify(expenseService, times(1)).getExpenseById(expenseId);
    }

    @Test
    void testGetExpenseById_NotFound() throws Exception {
        when(expenseService.getExpenseById(expenseId)).thenReturn(null);

        mockMvc.perform(get("/api/expenses/{id}", expenseId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(expenseService, times(1)).getExpenseById(expenseId);
    }

    @Test
    void testDeleteExpense() throws Exception {
        doNothing().when(expenseService).deleteExpense(expenseId);

        mockMvc.perform(delete("/api/expenses/{id}", expenseId))
                .andExpect(status().isNoContent());

        verify(expenseService, times(1)).deleteExpense(expenseId);
    }
}