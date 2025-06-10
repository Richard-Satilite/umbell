package com.umbell.models;

import java.math.BigDecimal;

public abstract class Category {
    private Long id;
    private String name;
    private BigDecimal monthLimit;
    private String categoryType; // Will be set by subclasses (e.g., "Income", "Expenses", "Investment")

    public Category() {
        // Default constructor
    }

    public Category(String name, BigDecimal monthLimit, String categoryType) {
        this.name = name;
        this.monthLimit = monthLimit;
        this.categoryType = categoryType;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMonthLimit() {
        return monthLimit;
    }

    public void setMonthLimit(BigDecimal monthLimit) {
        this.monthLimit = monthLimit;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
} 