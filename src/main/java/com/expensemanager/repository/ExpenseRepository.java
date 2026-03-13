package com.expensemanager.repository;

import com.expensemanager.model.Expense;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    // Custom query methods can be added here if needed
}
