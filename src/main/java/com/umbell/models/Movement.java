package com.umbell.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Movement {
    private Long code;
    private Category category; // Changed from int typeId to Category object
    private BigDecimal value;
    private LocalDate date;
    private String description;
    private Long accountCode; // Foreign key to Account

    public Movement() {
        this.date = LocalDate.now();
    }

    // Getters and Setters

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(Long accountCode) {
        this.accountCode = accountCode;
    }
} 