package com.expensemanager.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency = "USD";

    @Column(length = 50)
    private String category;

    @Column(length = 100)
    private String vendor;

    @Column(length = 50)
    private String paymentMode;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(nullable = false)
    private LocalDate expenseDate;

    @Column(nullable = false)
    private LocalTime expenseTime;

    // Default constructor for JPA
    public Expense() {}

    public Expense(BigDecimal amount, String currency, String category, String vendor, String paymentMode, String comment, LocalDate expenseDate, LocalTime expenseTime) {
        this.amount = amount;
        this.currency = currency;
        this.category = category;
        this.vendor = vendor;
        this.paymentMode = paymentMode;
        this.comment = comment;
        this.expenseDate = expenseDate;
        this.expenseTime = expenseTime;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getVendor() { return vendor; }
    public void setVendor(String vendor) { this.vendor = vendor; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDate getExpenseDate() { return expenseDate; }
    public void setExpenseDate(LocalDate expenseDate) { this.expenseDate = expenseDate; }

    public LocalTime getExpenseTime() { return expenseTime; }
    public void setExpenseTime(LocalTime expenseTime) { this.expenseTime = expenseTime; }
}
