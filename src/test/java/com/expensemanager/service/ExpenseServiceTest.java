package com.expensemanager.service;

import com.expensemanager.model.Expense;
import com.expensemanager.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    private Expense sampleExpense;
    private final UUID expenseId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        sampleExpense = new Expense();
        sampleExpense.setId(expenseId);
        sampleExpense.setAmount(new BigDecimal("29.99"));
        sampleExpense.setCategory("Food");
        sampleExpense.setVendor("Uber Eats");
    }

    @Test
    void testCreateExpense() {
        when(expenseRepository.save(any(Expense.class))).thenReturn(sampleExpense);

        Expense created = expenseService.createExpense(sampleExpense);

        assertNotNull(created);
        assertEquals(new BigDecimal("29.99"), created.getAmount());
        assertEquals("Food", created.getCategory());
        verify(expenseRepository, times(1)).save(sampleExpense);
    }

    @Test
    void testGetAllExpenses() {
        when(expenseRepository.findAll()).thenReturn(Arrays.asList(sampleExpense));

        List<Expense> expenses = expenseService.getAllExpenses();

        assertEquals(1, expenses.size());
        assertEquals("Uber Eats", expenses.get(0).getVendor());
        verify(expenseRepository, times(1)).findAll();
    }

    @Test
    void testGetExpenseById_Found() {
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(sampleExpense));

        Expense found = expenseService.getExpenseById(expenseId);

        assertNotNull(found);
        assertEquals(expenseId, found.getId());
        verify(expenseRepository, times(1)).findById(expenseId);
    }

    @Test
    void testGetExpenseById_NotFound() {
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.empty());

        Expense found = expenseService.getExpenseById(expenseId);

        assertNull(found);
        verify(expenseRepository, times(1)).findById(expenseId);
    }

    @Test
    void testDeleteExpense() {
        doNothing().when(expenseRepository).deleteById(expenseId);

        expenseService.deleteExpense(expenseId);

        verify(expenseRepository, times(1)).deleteById(expenseId);
    }
}
